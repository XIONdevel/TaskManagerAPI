package org.noix.api.manager.shopping.lists;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.noix.api.manager.shopping.items.ShoppingItemService;
import org.noix.api.manager.shopping.items.dto.ShoppingItemDTO;
import org.noix.api.manager.shopping.lists.dto.ListMapper;
import org.noix.api.manager.shopping.lists.dto.ShoppingListDTO;
import org.noix.api.manager.users.User;
import org.noix.api.manager.users.UserService;
import org.noix.api.manager.utils.RequestProcessingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.naming.NoPermissionException;
import java.util.ArrayList;
import java.util.List;

/*
* methods:
* CREATE:
*   List +
*   Tasks +
* READ:
*   Lists +
*   Tasks +
* UPDATE:
*   List name +
*   Task +
*   Tasks +
* DELETE:
*   List +
*   Task +
*/
@Service
@RequiredArgsConstructor
public class ShoppingListService {

    private static final Logger logger = LoggerFactory.getLogger(ShoppingListService.class);
    private final ListMapper listMapper;
    private final UserService userService;
    private final ShoppingItemService itemService;
    private final ShoppingListRepository listRepository;
    private final RequestProcessingService requestService;


    public ShoppingListDTO createList(String listName, HttpServletRequest request) {
        User user = userService.getUserFromRequest(request);
        ShoppingList savedList = listRepository.save(new ShoppingList(listName, user));
        return listMapper.toDTO(savedList);
    }

    public List<ShoppingListDTO> getAllForUser(HttpServletRequest request) {
        User user = userService.getUserFromRequest(request);
        List<ShoppingList> lists = listRepository.findAllByOwner(user);
        List<ShoppingListDTO> filledLists = new ArrayList<>();
        for (ShoppingList list : lists) {
            List<ShoppingItemDTO> items = itemService.getAll(list);
            ShoppingListDTO dto = listMapper.toDTO(list, items);
            filledLists.add(dto);
        }
        return filledLists;
    }

    public ShoppingListDTO updateList(
            Long listId,
            ShoppingListDTO updatedList,
            HttpServletRequest request
    ) throws NoPermissionException {
        User user = userService.getUserFromRequest(request);
        ShoppingList oldList = listRepository.findById(listId)
                .orElseThrow(() -> new EntityNotFoundException("List not found"));

        if (oldList.getOwnerId().equals(user.getId())) {
            oldList.setName(updatedList.getName());
            ShoppingListDTO savedList = listMapper.toDTO(listRepository.save(oldList));
            List<ShoppingItemDTO> savedItems = itemService.saveAll(updatedList.getItems(), oldList, request);
            savedList.setItems(savedItems);
            return savedList;
        } else {
            logger.warn("Attempt to update list for another user, user id: {}, user ip: {}",
                    user.getId(), requestService.getClientIp(request));
            throw new NoPermissionException("You don`t have permission to update this list");
        }
    }

    public void deleteList(Long listId, HttpServletRequest request) throws NoPermissionException {
        User user = userService.getUserFromRequest(request);
        ShoppingList list = listRepository.findById(listId)
                .orElseThrow(() -> new EntityNotFoundException("List not found"));
        if (list.getOwnerId().equals(user.getId())) {
            itemService.deleteAll(list);
            listRepository.deleteById(listId);
            logger.debug("Deleted list with id: {}", listId);
        } else {
            logger.warn("Attempt to delete list for another user, user id: {}, user ip: {}",
                    user.getId(), requestService.getClientIp(request));
            throw new NoPermissionException("You don`t have permission to delete this list");
        }
    }
}
