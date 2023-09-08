package com.woori.bookspring.service;

import com.woori.bookspring.dto.InventoryEbookDto;
import com.woori.bookspring.entity.ebook.Ebook;
import com.woori.bookspring.entity.ebook.Inventory;
import com.woori.bookspring.entity.ebook.InventoryEbook;
import com.woori.bookspring.entity.user.User;
import com.woori.bookspring.repository.EbookRepository;
import com.woori.bookspring.repository.InventoryEbookRepository;
import com.woori.bookspring.repository.InventoryRepository;
import com.woori.bookspring.repository.UserRepository;
import com.woori.bookspring.repository.InventoryEbookRepository;
import com.woori.bookspring.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryEbookRepository inventoryEbookRepository;
    private final EbookRepository ebookRepository;
    private final UserRepository userRepository;
  
    public Inventory getInventory(String username) {
        return inventoryRepository.findByUser_Username(username).orElseGet(() -> {
            User user = userRepository.findById(username).get();
            return inventoryRepository.save(Inventory.createInventory(user));
        });
    }

    public InventoryEbook createInventoryEbook(Long ebookId,String username){
        Ebook ebook = ebookRepository.findById(ebookId).get();
        Inventory inventory = getInventory(username);
        return inventoryEbookRepository.save(InventoryEbook.createInventoryEbook(inventory,ebook));
    }

    public List<InventoryEbookDto> getInventoryEbookList(String username) {

        return inventoryEbookRepository.findByInventory(getInventory(username)).stream().map(InventoryEbook::of).toList();
    }

    public Inventory createInventory(User user) {
        Inventory inventory = Inventory.builder()
                .user(user)
                .build();
        return inventoryRepository.save(inventory);
    }

    //조회
    public List<Inventory> getInventoryList() {
        return inventoryRepository.findAll();
    }

    //단건 조회
    public Long getInventoryId(Long id) {
        return inventoryRepository.findById(id).get().getId();
    }

    // 삭제
    public void deleteInventory(Long id) {
        inventoryRepository.deleteById(id);
    }

}
