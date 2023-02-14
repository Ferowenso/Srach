package com.example.backend.controller;

import com.example.backend.dto.ThreadDto;
import com.example.backend.model.Thread;
import com.example.backend.service.ThreadService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/boards/{boardCode}/threads")
@Log4j2
public class ThreadController {

    private final ThreadService threadService;

    @Autowired
    public ThreadController(ThreadService threadService) {
        this.threadService = threadService;
    }

    @GetMapping()
    public ResponseEntity<List<Thread>> getThreads(@PathVariable String boardCode){
        return new ResponseEntity<>(threadService.getThreads(boardCode), HttpStatus.OK);
    }

    @GetMapping("{threadNumber}")
    public ResponseEntity<Thread> getThread(@PathVariable String boardCode, @PathVariable Long threadNumber){
        return new ResponseEntity<>(threadService.getThread(boardCode, threadNumber), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Thread> createThread(@RequestBody @Valid ThreadDto threadDto,
                                               @PathVariable String boardCode,
                                               HttpServletRequest request){
        threadDto.setIp(request.getRemoteAddr());
        return new ResponseEntity<>(threadService.createThread(threadDto, boardCode), HttpStatus.CREATED);
    }

    @DeleteMapping("{threadNumber}")
    public ResponseEntity deleteThread(@PathVariable String boardCode, @PathVariable Long threadNumber){
        threadService.deleteThreadByBoardCodenameAndThreadNumber(boardCode, threadNumber);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PatchMapping("{threadNumber}")
    public ResponseEntity lockThread(@PathVariable String boardCode, @PathVariable Long threadNumber){
        threadService.lockThread(boardCode, threadNumber);
        return new ResponseEntity(HttpStatus.OK);
    }

}
