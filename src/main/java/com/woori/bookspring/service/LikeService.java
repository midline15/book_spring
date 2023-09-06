package com.woori.bookspring.service;

import com.woori.bookspring.entity.ebook.Like;
import com.woori.bookspring.entity.ebook.LikeEbook;
import com.woori.bookspring.entity.user.User;
import com.woori.bookspring.repository.LikeEbookRepository;
import com.woori.bookspring.repository.LikeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final LikeEbookRepository likeEbookRepository;

    public Like getLike(User user){
        return likeRepository.findByUser(user);
    }

    public List<LikeEbook> getLikeEbookList(Like like){
        return likeEbookRepository.findByLike(like);
    }
}
