package org.noix.api.manager.shopping.items;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.noix.api.manager.shopping.items.dto.ItemMapper;
import org.noix.api.manager.shopping.items.dto.ShoppingItemDTO;
import org.noix.api.manager.shopping.lists.ShoppingList;
import org.noix.api.manager.shopping.lists.ShoppingListRepository;
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

@Service
@RequiredArgsConstructor
public class ShoppingItemService {

    private final static Logger logger = LoggerFactory.getLogger(ShoppingItemService.class);
    private final RequestProcessingService requestService;
    private final ShoppingItemRepository itemRepository;
    private final ItemMapper itemMapper;
    private final UserService userService;

    public List<ShoppingItemDTO> saveAll(
            List<ShoppingItemDTO> items,
            ShoppingList list,
            HttpServletRequest request
    ) throws NoPermissionException {
        User user = userService.getUserFromRequest(request);
        if (!user.equals(list.getOwner()) && !user.isAdmin()) {
            logger.warn("Attempt to update tasks without permission, user id: {}, user ip: {}",
                    user.getId(), requestService.getClientIp(request));
            throw new NoPermissionException("You don`t have permission to edit this list");
        }

        List<ShoppingItemDTO> savedItems = new ArrayList<>();
        for (ShoppingItemDTO item : items) {
            if (item.getId() != null && itemRepository.existsByIdAndList(item.getId(), list)) {
                final long itemId = item.getId();
                ShoppingItem savedItem = itemRepository.findById(itemId)
                        .orElseThrow(() -> new EntityNotFoundException("Item not found, id: " + itemId));

                if (!savedItem.equals(itemMapper.toDomain(item, list))) {
                    savedItem.setName(item.getName());
                    savedItem.setQuantity(item.getQuantity());
                    savedItem.setBought(item.getBought());
                    savedItem.setPrice(item.getPrice());
                    itemRepository.save(savedItem);
                    savedItems.add(itemMapper.toDTO(savedItem));
                    logger.debug("Item updated, id: {}", itemId);
                }
            } else {
                savedItems.add(createItem(item, list));
            }
        }
        return savedItems;
    }

    public ShoppingItemDTO createItem(ShoppingItemDTO item, ShoppingList list) {
        ShoppingItem savedItem = itemRepository.save(itemMapper.toDomain(item, list));
        logger.debug("Item saved id: {}", savedItem.getId());
        return itemMapper.toDTO(savedItem, list);
    }

    public List<ShoppingItemDTO> getAll(ShoppingList list) {
        List<ShoppingItem> lists = itemRepository.findAllByList(list);
        return itemMapper.toDTO(lists);
    }

    public void deleteAll(ShoppingList list) {
        itemRepository.deleteAllByList(list);
    }
}
