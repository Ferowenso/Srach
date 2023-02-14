package com.example.backend.service;


import com.example.backend.dto.PostDto;
import com.example.backend.exception.PostingErrorException;
import com.example.backend.model.Board;
import com.example.backend.model.Post;
import com.example.backend.model.Thread;
import com.example.backend.repository.BoardRepository;
import com.example.backend.repository.PostRepository;
import com.example.backend.repository.ThreadRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    private final ThreadService threadService;

    private final ModelMapper modelMapper;
    private final BoardService boardService;

    @Autowired
    public PostService(PostRepository postRepository,
                       ThreadService threadService,
                       ModelMapper modelMapper,
                       BoardService boardService) {
        this.postRepository = postRepository;
        this.threadService = threadService;
        this.modelMapper = modelMapper;
        this.boardService = boardService;
    }

    public Post createPost(PostDto postDto, String boardCode, Long threadNumber){
        Thread thread = threadService.getThread(boardCode, threadNumber);
        if (thread.isLocked()){
            throw new PostingErrorException();
        }
        if (postDto.getUsername() == null){
            postDto.setUsername("Аноним");
        }
        Post post = modelMapper.map(postDto, Post.class);
        Board board = boardService.getBoard(boardCode);
        Long postCount = board.getPostCounter()+1;
        LocalDateTime nowTime = LocalDateTime.now();
        post.setDateTime(nowTime);
        if (!postDto.isSage()){
            threadService.updateLastPostTimeByBoardAndThreadNumber(nowTime, boardCode, threadNumber);
        }
        post.setPostNumber(postCount);
        boardService.updatePostCounterByCodeName(postCount, boardCode);
        post.setThread(thread);
        return postRepository.save(post);
    }

    public Post getPost(String boardCode, Long threadNumber, Long postNumber){
        Thread thread = threadService.getThread(boardCode, threadNumber);
        return postRepository.findByThreadAndPostNumber(thread, postNumber).orElseThrow(EntityNotFoundException::new);
    }

    public List<Post> getLastPosts(String boardCode, Long threadNumber){
        Thread thread = threadService.getThread(boardCode, threadNumber);
        List<Post> postList = postRepository.findTop3ByThreadOrderByDateTimeDesc(thread);
        Collections.reverse(postList);
        return postList;
    }

    public List<Post> getPosts(String boardCode, Long threadNumber){
        Thread thread = threadService.getThread(boardCode, threadNumber);
        return postRepository.findAllByThread(thread);
    }

    public void deletePostByThreadAndPostNumber(String boardCode ,Long threadNumber, Long postNumber){
        Thread thread = threadService.getThread(boardCode, threadNumber);
        postRepository.deletePostByThreadAndPostNumber(thread, postNumber);

    }

}
