package com.woori.bookspring.service;

import com.woori.bookspring.entity.ebook.Ebook;
import com.woori.bookspring.entity.ebook.Inventory;
import com.woori.bookspring.entity.ebook.InventoryEbook;
import com.woori.bookspring.entity.user.User;
import com.woori.bookspring.repository.InventoryEbookRepository;
import com.woori.bookspring.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final InventoryEbookRepository inventoryEBookRepository;

    public Inventory createInventory(User user) {
        Inventory inventory = Inventory.builder()
                .user(user)
                .build();
        return inventoryRepository.save(inventory);
    }

    public InventoryEbook createInventoryEBook(Inventory inventory, Ebook ebook) {
        InventoryEbook inventoryEBook = InventoryEbook.builder()
                .inventory(inventory)
                .ebook(ebook)
                .build();
        return inventoryEBookRepository.save(inventoryEBook);
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
