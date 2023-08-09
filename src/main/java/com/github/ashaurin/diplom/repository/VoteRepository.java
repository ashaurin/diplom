package com.github.ashaurin.diplom.repository;

import com.github.ashaurin.diplom.error.NotFoundException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import com.github.ashaurin.diplom.model.Vote;

import java.time.LocalDate;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote>{

    @Query("SELECT v FROM Vote v LEFT JOIN FETCH v.restaurant WHERE v.user.id = :userId AND v.date = :date")
    Optional<Vote> getByDate(int userId, LocalDate date);

    default Vote getByDateExisted(int userId, LocalDate date) {
        return getByDate(userId, date).orElseThrow(() -> new NotFoundException("Entity with userId=" + userId + " and date=" + date + "not found"));
    }

}
