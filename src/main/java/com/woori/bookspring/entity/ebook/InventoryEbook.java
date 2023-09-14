package com.woori.bookspring.entity.ebook;

import com.woori.bookspring.dto.EbookFormDto;
import com.woori.bookspring.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class InventoryEbook extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ebook_id")
    private Ebook ebook;

    public static InventoryEbook createInventoryEbook(Inventory inventory, Ebook ebook){
        return InventoryEbook.builder()
                .inventory(inventory)
                .ebook(ebook)
                .build();
    }

    public EbookFormDto of() {
        return EbookFormDto.builder()
                .id(ebook.getId())
                .inventoryEbookId(id)
                .title(ebook.getTitle())
                .intro(ebook.getIntro())
                .url(ebook.getCover().getUrl())
                .build();
    }
}