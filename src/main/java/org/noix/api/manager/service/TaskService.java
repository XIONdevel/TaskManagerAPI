package org.noix.api.manager.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.noix.api.manager.dto.BasicTaskDTO;
import org.noix.api.manager.entity.BasicTask;
import org.noix.api.manager.entity.User;
import org.noix.api.manager.entity.role.Role;
import org.noix.api.manager.exception.DataNotValidException;
import org.noix.api.manager.repository.BasicTaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.naming.NoPermissionException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final static Logger logger = LoggerFactory.getLogger(TaskService.class);
    private final RequestProcessingService requestService;
    private final BasicTaskRepository basicRepository;
    private final UserService userService;

    public List<BasicTaskDTO> getAllBasicForUser(HttpServletRequest request) {
        User user = userService.getUserFromRequest(request);
        return basicRepository.findAllByOwnerAsDTO(user);
    }

    public void deleteAllForUser(HttpServletRequest request) {
        User user = userService.getUserFromRequest(request);
        basicRepository.deleteAllByOwner(user);
    }

    public void updateBasicTask(Long taskId, BasicTask task, HttpServletRequest request) {
        User user = userService.getUserFromRequest(request);
        if (task == null || task.getName().isEmpty()) {
            throw new DataNotValidException("You can`t save empty task");
        }

        try {
            BasicTask bTask = basicRepository.findById(taskId).get();
            if (!Objects.equals(bTask.getOwner().getId(), user.getId())) {
                final String ip = requestService.getClientIp(request);
                logger.warn("Attempt update desk for another user, task id: {}, user id: {}, user ip: {}", taskId, user.getId(), ip);
                throw new NoPermissionException("You don`t have permission to delete this desk");
            }
        } catch (Exception ignored) {
        }

        task.setId(taskId);
        task.setOwner(user);
        task.update();
        basicRepository.save(task);
    }

    public void deleteTasks(HttpServletRequest request, Long... taskId) throws NoPermissionException {
        User user = userService.getUserFromRequest(request);
        String userIp = requestService.getClientIp(request);
        for (Long id : taskId) {
            deleteTask(id, user, userIp);
        }
    }

    private void deleteTask(Long taskId, User user, String userIp) throws NoPermissionException {
        if (!basicRepository.existsByIdAndOwner(taskId, user) && user.getRole() != Role.ADMIN) {
            logger.warn("Attempt delete desk for another user, task id: {}, user id: {}, user ip: {}", taskId, user.getId(), userIp);
            throw new NoPermissionException("You don`t have permission to delete this task");
        }
        basicRepository.deleteById(taskId);
    }

    public BasicTask createTask(
            String name,
            String description,
            HttpServletRequest request
    ) {
        User user = userService.getUserFromRequest(request);
        if (name.isEmpty()) {
            throw new DataNotValidException("You can`t create empty task");
        }
        BasicTask task = new BasicTask(name, description, user);
        return basicRepository.save(task);
    }
}
