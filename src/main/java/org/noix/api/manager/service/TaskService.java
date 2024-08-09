package org.noix.api.manager.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.noix.api.manager.entity.BasicTask;
import org.noix.api.manager.entity.User;
import org.noix.api.manager.repository.BasicTaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final BasicTaskRepository basicRepository;
    private final UserService userService;

    public List<BasicTask> getAllForUser(HttpServletRequest request) {
        User user = userService.getUserFromRequest(request);
        return basicRepository.findAllByOwner(user);
    }

    public void deleteAllForUser(HttpServletRequest request) {
        User user = userService.getUserFromRequest(request);
        basicRepository.deleteAllByOwner(user);
    }





}
