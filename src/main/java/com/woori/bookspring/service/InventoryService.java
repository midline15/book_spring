package com.woori.bookspring.service;

import com.woori.bookspring.dto.EbookFormDto;
import com.woori.bookspring.dto.InventoryEbookDto;
import com.woori.bookspring.entity.ebook.Ebook;
import com.woori.bookspring.entity.ebook.Inventory;
import com.woori.bookspring.entity.ebook.InventoryEbook;
import com.woori.bookspring.entity.User;
import com.woori.bookspring.repository.EbookRepository;
import com.woori.bookspring.repository.InventoryEbookRepository;
import com.woori.bookspring.repository.InventoryRepository;
import com.woori.bookspring.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
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
  
    public Inventory getInventory(String email) {
        return inventoryRepository.findByUser_Email(email).orElseGet(() -> {
            User user = userRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
            return inventoryRepository.save(Inventory.createInventory(user));
        });
    }

    public EbookFormDto createInventoryEbook(Long ebookId,String email){
        Ebook ebook = ebookRepository.findById(ebookId).orElseThrow(EntityNotFoundException::new);
        Inventory inventory = getInventory(email);
        return inventoryEbookRepository.findByInventoryAndEbook(inventory, ebook).orElseGet(() ->
            inventoryEbookRepository.save(InventoryEbook.createInventoryEbook(inventory,ebook))
        ).of();

    }

    public List<EbookFormDto> getInventoryEbookList(String email) {

        return inventoryEbookRepository.findByInventory(getInventory(email)).stream().map(InventoryEbook::of).toList();
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

    public void deleteInventoryEbook(Long inventoryEbookId) {
        inventoryEbookRepository.deleteById(inventoryEbookId);
    }
}
