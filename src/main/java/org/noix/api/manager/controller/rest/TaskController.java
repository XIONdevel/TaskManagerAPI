package org.noix.api.manager.controller.rest;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.noix.api.manager.entity.task.Task;
import org.noix.api.manager.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/getAll")
    public ResponseEntity<List<Task>> getAll(HttpServletRequest request) {
        return ResponseEntity.ok(taskService.getAllForUser(request));
    }

    @PostMapping("/deleteAll")
    public ResponseEntity<Void> deleteAll(HttpServletRequest request) {
        taskService.deleteAllForUser(request);
        return ResponseEntity.ok().build();
    }
}
