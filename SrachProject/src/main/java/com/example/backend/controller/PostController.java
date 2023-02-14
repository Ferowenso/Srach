package com.example.backend.controller;


import com.example.backend.dto.PostDto;
import com.example.backend.model.Post;
import com.example.backend.repository.PostRepository;
import com.example.backend.service.PostService;
import lombok.extern.log4j.Log4j2;
import org.apache.http.client.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpUtils;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/boards/{boardCode}/threads/{theadNumber}/posts")
@Log4j2
public class PostController {

    private final PostService postService;
    private final PostRepository postRepository;

    @Autowired
    public PostController(PostService postService,
                          PostRepository postRepository) {
        this.postService = postService;
        this.postRepository = postRepository;
    }

    @GetMapping
    public ResponseEntity<List<Post>> getPosts(@RequestParam(name = "last", defaultValue = "false", required = false) Boolean last,
                                               @PathVariable String boardCode,
                                               @PathVariable Long theadNumber){
        if(last){
            return new ResponseEntity<>(postService.getLastPosts(boardCode, theadNumber), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(postService.getPosts(boardCode, theadNumber), HttpStatus.OK);
        }

    }

    @GetMapping("{postNumber}")
    public ResponseEntity<Post> getPost(@PathVariable String boardCode, @PathVariable Long theadNumber, @PathVariable Long postNumber){
        return new ResponseEntity<>(postService.getPost(boardCode, theadNumber, postNumber), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody @Valid PostDto postDto,
                                           @PathVariable String boardCode,
                                           @PathVariable Long theadNumber,
                                           HttpServletRequest request){
        postDto.setIp(request.getRemoteAddr());
        return new ResponseEntity<>(postService.createPost(postDto, boardCode, theadNumber), HttpStatus.CREATED);
    }

    @DeleteMapping("{postNumber}")
    public ResponseEntity deletePost(@PathVariable String boardCode,
                                     @PathVariable Long threadNumber,
                                     @PathVariable Long postNumber){
        postService.deletePostByThreadAndPostNumber(boardCode, threadNumber, postNumber);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
