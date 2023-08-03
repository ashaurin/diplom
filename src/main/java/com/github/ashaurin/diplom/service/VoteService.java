package com.github.ashaurin.diplom.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.ashaurin.diplom.model.Vote;
import com.github.ashaurin.diplom.repository.UserRepository;
import com.github.ashaurin.diplom.repository.VoteRepository;

@Service
@AllArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;
    private final UserRepository userRepository;


    @Transactional
    public Vote save(int userId, Vote vote) {
        vote.setUser(userRepository.getExisted(userId));
        return voteRepository.save(vote);

    }

    @Transactional
    public void delete(int userId) {
        voteRepository.deleteExisted(userId);
    }
}
