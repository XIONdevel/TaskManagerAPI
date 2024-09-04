package org.noix.api.manager.shopping.list;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.noix.api.manager.shopping.dto.CreateListRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.NoPermissionException;
import java.util.List;

@RequestMapping("/api/lists")
@RestController
@RequiredArgsConstructor
public class ShoppingListController {

    private final ShoppingListService listService;

    @GetMapping("/getAll")
    public ResponseEntity<List<ShoppingList>> getAllForUser(HttpServletRequest request) {
        List<ShoppingList> lists = listService.getAllForUser(request);
        return ResponseEntity.ok(lists);
    }

    @PostMapping("/create")
    public ResponseEntity<ShoppingList> createList(
            @RequestBody CreateListRequest createRequest,
            HttpServletRequest servletRequest
    ) {
        ShoppingList createdList = listService.createList(createRequest, servletRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdList);
    }

    @PutMapping("/{listId}")
    public ResponseEntity<ShoppingList> updateList(
            @PathVariable Long listId,
            @RequestBody ShoppingList updatedList,
            HttpServletRequest request
    ) {
        ShoppingList createdList = listService.updateList(listId, updatedList, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdList);
    }

    @DeleteMapping("/{listId}")
    public ResponseEntity<Void> deleteList(
            @PathVariable Long listId,
            HttpServletRequest request
    ) throws NoPermissionException {
        listService.deleteList(listId, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<Void> deleteList(
            HttpServletRequest request
    ) {
        listService.deleteAll(request);
        return ResponseEntity.ok().build();
    }
}
