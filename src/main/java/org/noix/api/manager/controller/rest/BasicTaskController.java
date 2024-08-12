package org.noix.api.manager.controller.rest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.noix.api.manager.entity.BasicTask;
import org.noix.api.manager.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.NoPermissionException;

@RestController
@RequestMapping("/api/task/basic")
@RequiredArgsConstructor
public class BasicTaskController {

    private final TaskService taskService;


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
    public void createTask(
            @RequestBody String name,
            @RequestBody String description,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        taskService.createTask(name, description, request, response);
    }

}
