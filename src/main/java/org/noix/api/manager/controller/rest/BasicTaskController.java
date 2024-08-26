package org.noix.api.manager.controller.rest;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.noix.api.manager.dto.BasicTaskDTO;
import org.noix.api.manager.entity.BasicTask;
import org.noix.api.manager.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.NoPermissionException;
import java.util.List;

@RestController
@RequestMapping("/api/task/basic")
@RequiredArgsConstructor
public class BasicTaskController {

    private final TaskService taskService;

    @GetMapping("/getAll")
    public ResponseEntity<List<BasicTaskDTO>> getAll(HttpServletRequest request) {
        return ResponseEntity.ok(taskService.getAllBasicForUser(request));
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<Void> updateBasicTask(
            @PathVariable Long taskId,
            @RequestBody BasicTask task,
            HttpServletRequest request
    ) {
        taskService.updateBasicTask(taskId, task, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId, HttpServletRequest request) throws NoPermissionException {
        taskService.deleteTask(taskId, request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/create")
    public ResponseEntity<BasicTask> createTask(
            HttpServletRequest request,
            @RequestBody BasicTaskDTO dto
            ) {
        BasicTask task = taskService.createTask(dto.getName(), dto.getDescription(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }

}
