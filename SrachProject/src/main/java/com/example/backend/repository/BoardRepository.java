package com.example.backend.repository;

import com.example.backend.dto.BoardDto;
import com.example.backend.model.Board;
import com.example.backend.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    Optional<Board> findBoardByCodeName(String codeName);

    boolean existsBoardByCodeName(String codeName);

    @Modifying
    @Query("UPDATE Board b set b.postCounter = ?1 where b.codeName = ?2")
    void updatePostCounterByCodeName(Long count, String codeName);

    @Modifying
    void deleteBoardByCodeName(String codeName);

    @Modifying
    @Query("UPDATE Board b set b.codeName = :newCodename, b.name = :newName, b.subject = :newSubject where b.codeName = :oldCodename")
    void updateBoardByCodename(@Param("newCodename") String newCodename,
                     @Param("newName") String newName,
                     @Param("newSubject") Subject subject,
                     @Param("oldCodename") String oldCodename);

}
