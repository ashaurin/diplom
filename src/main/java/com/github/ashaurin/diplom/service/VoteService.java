package com.github.ashaurin.diplom.service;

import com.github.ashaurin.diplom.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.ashaurin.diplom.model.Vote;
import com.github.ashaurin.diplom.repository.UserRepository;
import com.github.ashaurin.diplom.repository.VoteRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import static java.time.LocalDateTime.of;

@Service
@AllArgsConstructor
public class VoteService {

    private static final int checkHour = 11;
    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public Vote update(int userId, int restaurantId) {
        Vote vote = voteRepository.getByDateExisted(userId, LocalDate.now());
        vote.setRestaurant(restaurantRepository.getExisted(restaurantId));
        return voteRepository.save(vote);
    }


    @Transactional
    public Vote create(int userId, int restaurantId) {
        checkNew(userId, LocalDate.now());
        Vote vote = new Vote(null);
        vote.setDate(LocalDate.now());
        vote.setUser(userRepository.getExisted(userId));
        vote.setRestaurant(restaurantRepository.getExisted(restaurantId));
        return voteRepository.save(vote);

    }

    @Transactional
    public void delete(int userId, LocalDate date) {
        Vote vote = voteRepository.getByDateExisted(userId, date);
        voteRepository.deleteExisted(vote.id());
    }

    public static void checkTime() {
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(of(now.getYear(), now.getMonth(), now.getDayOfMonth(), checkHour, 0)) ) {
            throw new IllegalArgumentException("It is too late, vote can't be changed");
        }
    }

    public void checkNew(int userId, LocalDate date) {
        if (voteRepository.getByDate(userId, date).isPresent()){
            throw new IllegalArgumentException("You have already voted");
        }
    }

}
