package org.noix.api.manager.shopping;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.noix.api.manager.shopping.lists.ShoppingListService;
import org.noix.api.manager.shopping.lists.dto.CreateListRequest;
import org.noix.api.manager.shopping.lists.dto.ShoppingListDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.NoPermissionException;
import java.util.List;

@RequestMapping("/api/shopping")
@RestController
@RequiredArgsConstructor
public class ShoppingController {

    private final ShoppingListService listService;

    @GetMapping("/getAll")
    public ResponseEntity<List<ShoppingListDTO>> getAll(HttpServletRequest request) {
        List<ShoppingListDTO> lists = listService.getAllForUser(request);
        return ResponseEntity.ok(lists);
    }

    @PutMapping("/{listId}")
    public ResponseEntity<ShoppingListDTO> updateList(
            @PathVariable Long listId,
            @RequestBody ShoppingListDTO updatedList,
            HttpServletRequest request
    ) throws NoPermissionException {
        ShoppingListDTO list = listService.updateList(listId, updatedList, request);
        return ResponseEntity.status(201).body(list);
    }

    @PostMapping("/create")
    public ResponseEntity<ShoppingListDTO> createList(
            @RequestBody CreateListRequest createRequest,
            HttpServletRequest request
    ) {
        System.out.println(createRequest.getName());
        ShoppingListDTO createdList = listService.createList(createRequest.getName(), request);
        return ResponseEntity.status(201).body(createdList);
    }

    @DeleteMapping("/{listId}")
    public ResponseEntity<Void> deleteList(
            @PathVariable Long listId,
            HttpServletRequest request
    ) throws NoPermissionException {
        listService.deleteList(listId, request);
        return ResponseEntity.ok().build();
    }



}
