package com.github.ashaurin.diplom.repository;

import com.github.ashaurin.diplom.error.NotFoundException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import com.github.ashaurin.diplom.model.Vote;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote>{

    @Transactional
    @Modifying
    @Query("DELETE FROM Vote v WHERE v.user.id = :userId AND v.date = current_date")
    int delete(int userId);

    @Query("SELECT v FROM Vote v WHERE v.date = current_date ORDER BY v.id DESC")
    List<Vote> getAll();

    @Query("SELECT v FROM Vote v WHERE v.user.id = :userId AND v.date = current_date")
    Optional<Vote> get(int userId);

}