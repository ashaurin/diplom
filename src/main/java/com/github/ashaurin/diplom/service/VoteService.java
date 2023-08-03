package com.github.ashaurin.diplom.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.ashaurin.diplom.model.Vote;
import com.github.ashaurin.diplom.repository.UserRepository;
import com.github.ashaurin.diplom.repository.VoteRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static java.time.LocalDateTime.of;

@Service
@AllArgsConstructor
public class VoteService {

    private static final int checkHour = 11;
    private final VoteRepository voteRepository;
    private final UserRepository userRepository;


    @Transactional
    public Vote save(int userId, Vote vote) {
        vote.setUser(userRepository.getExisted(userId));
        return voteRepository.save(vote);

    }

    @Transactional
    public void delete(int userId, LocalDate date) {
        Optional<Vote> vote = voteRepository.getByDate(userId, date);
        voteRepository.deleteExisted(vote.get().id());
    }

    public static void checkTime() {
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(of(now.getYear(), now.getMonth(), now.getDayOfMonth(), checkHour, 0)) ) {
            throw new IllegalArgumentException("It is too late, vote can't be changed");
        }
    }
}
