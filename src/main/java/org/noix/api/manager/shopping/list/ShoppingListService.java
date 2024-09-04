package org.noix.api.manager.shopping.list;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.noix.api.manager.exceptions.InvalidDataException;
import org.noix.api.manager.shopping.dto.CreateListRequest;
import org.noix.api.manager.users.User;
import org.noix.api.manager.users.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.naming.NoPermissionException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShoppingListService {

    private final static Logger logger = LoggerFactory.getLogger(ShoppingListService.class);
    private final ShoppingListRepository listRepository;
    private final UserService userService;

    public List<ShoppingList> getAllForUser(HttpServletRequest request) {
        User user = userService.getUserFromRequest(request);
        return listRepository.findAllByOwner(user);
    }

    public ShoppingList createList(CreateListRequest createRequest, HttpServletRequest servletRequest) {
        if (!createRequest.isNameValid()) {
            throw new InvalidDataException("List name is not valid");
        }
        User user = userService.getUserFromRequest(servletRequest);
        return listRepository.save(
                ShoppingList.builder()
                        .owner(user)
                        .name(createRequest.getName())
                        .items(createRequest.getItems())
                        .build()
        );
    }

    public ShoppingList updateList(
            Long listId,
            ShoppingList updatedList,
            HttpServletRequest request
    ) {
        if (!updatedList.isNameValid()) {
            throw new InvalidDataException("List name is not valid");
        }

        User user = userService.getUserFromRequest(request);
        ShoppingList list = listRepository.findByIdAndOwner(listId, user)
                .orElseThrow(() -> new EntityNotFoundException("List does not found"));
        list.setName(updatedList.getName());
        return listRepository.save(list);
    }

    public void deleteList(Long id, HttpServletRequest request) throws NoPermissionException {
        User user = userService.getUserFromRequest(request);
        Optional<ShoppingList> optionalList = listRepository.findById(id);
        if (optionalList.isPresent()) {
            ShoppingList list = optionalList.get();
            if (list.getOwnerId().equals(user.getId())) {
                listRepository.deleteById(id);
            } else {
                logger.warn("Attempt to delete list for another user, listId: {}, userId: {}", id, user.getId());
                throw new NoPermissionException("You dont have permission to delete this list");
            }
        }
    }

    public void deleteAll(HttpServletRequest request) {
        User user = userService.getUserFromRequest(request);
        listRepository.deleteAllByOwner(user);
    }

}
