package org.noix.api.manager.tasks;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.noix.api.manager.exceptions.InvalidDataException;
import org.noix.api.manager.tasks.dto.CreateTaskRequest;
import org.noix.api.manager.tasks.dto.TaskDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.NoPermissionException;
import java.util.List;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @DeleteMapping("/deleteAll")
    public ResponseEntity<Void> deleteAll(HttpServletRequest request) {
        taskService.deleteAllForUser(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<TaskDTO>> getAll(HttpServletRequest request) {
        return ResponseEntity.ok(taskService.getAllForUser(request));
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<Void> updateBasicTask(
            @PathVariable Long taskId,
            @RequestBody TaskDTO task,
            HttpServletRequest request
    ) throws NoPermissionException {
        taskService.updateTask(taskId, task, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/create")
    public ResponseEntity<Task> createTask(
            HttpServletRequest servletRequest,
            @RequestBody CreateTaskRequest createRequest
    ) {
        Task task = taskService.createTask(createRequest, servletRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> multipleDelete(
            @RequestBody Long[] ids,
            HttpServletRequest request
    ) throws NoPermissionException {
        taskService.deleteTasks(request, ids);
        return ResponseEntity.ok("Success");
    }


}
