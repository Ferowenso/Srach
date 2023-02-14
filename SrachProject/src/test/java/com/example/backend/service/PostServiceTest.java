package com.example.backend.service;

import com.example.backend.dto.PostDto;
import com.example.backend.exception.PostingErrorException;
import com.example.backend.model.Board;
import com.example.backend.model.Post;
import com.example.backend.model.Thread;
import com.example.backend.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.persistence.EntityNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = PostService.class)
public class PostServiceTest {

    @MockBean
    PostRepository postRepository;

    @MockBean
    ThreadService threadService;

    @MockBean
    ModelMapper modelMapper;

    @MockBean
    BoardService boardService;

    @Autowired
    PostService postService;

    @Test
    void shouldCreatePost(){
        Thread exceptedThread = new Thread();
        exceptedThread.setLocked(false);
        when(threadService.getThread("b", 1L)).thenReturn(exceptedThread);

        Post expectedPost = new Post();

        PostDto postDto = new PostDto();
        postDto.setSage(false);
        when(modelMapper.map(postDto, Post.class)).thenReturn(expectedPost);

        Board exceptedBoard = new Board();
        exceptedBoard.setPostCounter(0L);
        when(boardService.getBoard("b")).thenReturn(exceptedBoard);

        when(postRepository.save(expectedPost)).thenReturn(expectedPost);

        Post actualPost = postService.createPost(postDto, "b", 1L);

        verify(modelMapper).map(postDto, Post.class);
        verify(postRepository).save(expectedPost);
        assertEquals(expectedPost, actualPost);
    }

    @Test
    void shouldThrowPostingError(){
        Thread exceptedThread = new Thread();
        exceptedThread.setLocked(true);
        when(threadService.getThread("b", 1L)).thenReturn(exceptedThread);

        PostDto postDto = new PostDto();
        assertThrows(PostingErrorException.class, () -> postService.createPost(postDto, "b", 1L));
    }

    @Test
    void shouldThrowExceptionWhenPostNotFound() {

        Thread exceptedThread = new Thread();
        when(threadService.getThread("b", 1L)).thenReturn(exceptedThread);

        when(postRepository.findByThreadAndPostNumber(exceptedThread, 1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> postService.getPost("b", 1L, 1L));

        verify(postRepository).findByThreadAndPostNumber(exceptedThread, 1L);
    }

    @Test
    void shouldGetPost(){
        Thread exceptedThread = new Thread();
        when(threadService.getThread("b", 1L)).thenReturn(exceptedThread);

        Post expectedPost = new Post();
        when(postRepository.findByThreadAndPostNumber(exceptedThread, 1L)).thenReturn(Optional.of(expectedPost));

        Post actualPost = postService.getPost("b", 1L, 1L);

        verify(postRepository).findByThreadAndPostNumber(exceptedThread, 1L);
        assertEquals(expectedPost, actualPost);
    }

    @Test
    void shouldDeletePost(){
        Thread exceptedThread = new Thread();

        when(threadService.getThread("b", 1L)).thenReturn(exceptedThread);

        postService.deletePostByThreadAndPostNumber("b", 1L, 1L);

        verify(postRepository).deletePostByThreadAndPostNumber(exceptedThread, 1L);
    }

    @Test
    void shouldGetPosts(){
        Thread exceptedThread = new Thread();
        when(threadService.getThread("b", 1L)).thenReturn(exceptedThread);

        List<Post> expectedPosts = Arrays.asList(new Post(), new Post(), new Post());

        when(postRepository.findAllByThread(exceptedThread)).thenReturn(expectedPosts);

        List<Post> actualPosts = postService.getPosts("b", 1L);

        verify(postRepository).findAllByThread(exceptedThread);
        assertEquals(expectedPosts, actualPosts);
    }

    @Test
    void shouldGetLastPosts(){
        Thread exceptedThread = new Thread();
        when(threadService.getThread("b", 1L)).thenReturn(exceptedThread);

        List<Post> expectedPosts = Arrays.asList(new Post(), new Post(), new Post());

        when(postRepository.findTop3ByThreadOrderByDateTimeDesc(exceptedThread)).thenReturn(expectedPosts);

        List<Post> actualPosts = postService.getLastPosts("b", 1L);

        verify(postRepository).findTop3ByThreadOrderByDateTimeDesc(exceptedThread);
        assertEquals(expectedPosts, actualPosts);
    }

}
