package com.example.backend.repository;

import com.example.backend.model.Post;
import com.example.backend.model.Thread;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByThreadAndPostNumber(Thread thread, Long postNumber);

    List<Post> findTop3ByThreadOrderByDateTimeDesc(Thread thread);

    List<Post> findAllByThread(Thread thread);

    @Modifying
    void deletePostByThreadAndPostNumber(Thread thread, Long postNumber);
}
