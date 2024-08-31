package org.noix.api.manager.tasks;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
