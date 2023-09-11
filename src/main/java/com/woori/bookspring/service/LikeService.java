package com.woori.bookspring.service;

import com.woori.bookspring.dto.EbookDto;
import com.woori.bookspring.entity.ebook.Ebook;
import com.woori.bookspring.entity.ebook.Like;
import com.woori.bookspring.entity.ebook.LikeEbook;
import com.woori.bookspring.entity.user.User;
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

    public Like getLike(String username) {
        return likeRepository.findByUser_Username(username).orElseGet(() -> {
            User user = userRepository.findById(username).orElseThrow(EntityNotFoundException::new);
            return createLike(user);
        });
    }

    public List<EbookDto> getLikeEbookList(String username) {
        List<LikeEbook> likeEbookList = likeEbookRepository.findByLikeId(getLike(username).getId());
        return likeEbookList.stream().map(LikeEbook::of).toList();
    }

    public void addLikeEbook(Long ebookId, String username) {
        Ebook ebook = ebookRepository.findById(ebookId).orElseThrow(EntityNotFoundException::new);
        likeEbookRepository.save(LikeEbook.createLikeEbook(ebook, getLike(username)));
    }

    //조아요(찜) 수정
    public void updateLike(Like like) {
        likeRepository.save(like);
    }

    //조아요(찜) 삭제
    public void deleteLike(Long id) {
        likeRepository.deleteById(id);
    }
}
