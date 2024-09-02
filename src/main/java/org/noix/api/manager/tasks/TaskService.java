package org.noix.api.manager.tasks;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.noix.api.manager.tasks.dto.CreateTaskRequest;
import org.noix.api.manager.tasks.dto.TaskDTO;
import org.noix.api.manager.utils.RequestProcessingService;
import org.noix.api.manager.users.UserService;
import org.noix.api.manager.users.User;
import org.noix.api.manager.exceptions.InvalidDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.naming.NoPermissionException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final static Logger logger = LoggerFactory.getLogger(TaskService.class);
    private final RequestProcessingService requestService;
    private final TaskRepository taskRepository;
    private final UserService userService;

    public List<TaskDTO> getAllForUser(HttpServletRequest request) {
        User user = userService.getUserFromRequest(request);
        return taskRepository.findAllByOwnerAsDTO(user);
    }

    public void deleteAllForUser(HttpServletRequest request) {
        User user = userService.getUserFromRequest(request);
        taskRepository.deleteAllByOwner(user);
    }

    public void updateTask(
            Long taskId,
            TaskDTO updatedTask,
            HttpServletRequest request
    ) throws NoPermissionException, InvalidDataException {
        if (updatedTask == null) {
            throw new InvalidDataException("You can`t save empty task");
        } else if (!updatedTask.isNameValid()) {
            throw new InvalidDataException("Task name should be 6 to 30 symbols long");
        }

        User user = userService.getUserFromRequest(request);
        Long userId = user.getId();

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task with same id not found"));

        if (task.getOwnerId().equals(userId)) {
            task.setName(updatedTask.getName());
            task.setDescription(updatedTask.getDescription());
            task.update();
            taskRepository.save(task);
        } else {
            final String ip = requestService.getClientIp(request);
            logger.warn("Attempt update desk for another user, attempted user id: {}, user ip: {}", userId, ip);
            throw new NoPermissionException("You don`t have permission to update this task");
        }
    }

    public void deleteTasks(HttpServletRequest request, Long... taskId) throws NoPermissionException {
        User user = userService.getUserFromRequest(request);
        String userIp = requestService.getClientIp(request);
        for (Long id : taskId) {
            deleteTask(id, user, userIp);
        }
    }

    private void deleteTask(Long taskId, User user, String userIp) throws NoPermissionException {
        if (taskRepository.existsByIdAndOwner(taskId, user) || user.isAdmin()) {
            taskRepository.deleteById(taskId);
        } else {
            logger.warn("Attempt delete desk for another user, task id: {}, user id: {}, user ip: {}", taskId, user.getId(), userIp);
            throw new NoPermissionException("You don`t have permission to delete this task");
        }
    }

    public Task createTask(
            CreateTaskRequest createRequest,
            HttpServletRequest servletRequest
    ) {
        User user = userService.getUserFromRequest(servletRequest);
        if (!createRequest.isNameValid()) {
            throw new InvalidDataException("You can`t create empty task");
        }
        Task task = new Task(createRequest, user);
        return taskRepository.save(task);
    }
}
