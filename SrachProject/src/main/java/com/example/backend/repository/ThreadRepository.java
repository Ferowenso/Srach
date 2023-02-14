package com.example.backend.repository;

import com.example.backend.model.Board;
import com.example.backend.model.Thread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ThreadRepository extends JpaRepository<Thread, Long> {

    List<Thread> findAllByBoardOrderByLastPostTimeDesc(Board board);

    Optional<Thread> findByBoardAndThreadNumber(Board board, Long threadNumber);

    @Modifying
    @Query("UPDATE Thread t set t.lastPostTime = ?1 where t.board = ?2 and t.threadNumber = ?3")
    void updateLastPostTimeByBoardAndThreadNumber(LocalDateTime lastPostTime, Board board, Long threadNumber);

    @Modifying
    void deleteThreadByBoardAndThreadNumber(Board board, Long threadNumber);

    @Modifying
    @Query("UPDATE Thread  t set t.locked = 'true' where t.board = ?1 and  t.threadNumber = ?2")
    void lockThread(Board board, Long threadNumber);
}
