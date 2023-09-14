package com.woori.bookspring.service;

import com.woori.bookspring.dto.EbookDto;
import com.woori.bookspring.dto.EbookFormDto;
import com.woori.bookspring.entity.ebook.Ebook;
import com.woori.bookspring.entity.ebook.Like;
import com.woori.bookspring.entity.ebook.LikeEbook;
import com.woori.bookspring.entity.User;
import com.woori.bookspring.repository.EbookRepository;
import com.woori.bookspring.repository.LikeEbookRepository;
import com.woori.bookspring.repository.LikeRepository;
import com.woori.bookspring.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final LikeEbookRepository likeEbookRepository;
    private final EbookRepository ebookRepository;
    private final UserRepository userRepository;

    public Like createLike(User user) {
        return likeRepository.save(Like.createLike(user));
    }

    public Like getLike(String email) {
        return likeRepository.findByUser_Email(email).orElseGet(() -> {
            User user = userRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
            return createLike(user);
        });
    }

    public List<EbookFormDto> getLikeEbookList(String email) {
        return likeEbookRepository.findByLikes(getLike(email)).stream().map(LikeEbook::of).toList();
    }

    public void addLikeEbook(Long ebookId, String email) {
        Ebook ebook = ebookRepository.findById(ebookId).orElseThrow(EntityNotFoundException::new);
        likeEbookRepository.save(LikeEbook.createLikeEbook(ebook, getLike(email)));
    }

    public void deleteLikeEbook(Long likeEbookId) {
        likeEbookRepository.deleteById(likeEbookId);
    }
}
