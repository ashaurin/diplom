package com.github.ashaurin.diplom.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import com.github.ashaurin.diplom.model.Vote;

import java.time.LocalDate;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote>{

    @Query("SELECT v FROM Vote v WHERE v.user.id = :userId AND v.date = :date")
    Optional<Vote> getByDate(int userId, LocalDate date);

}
