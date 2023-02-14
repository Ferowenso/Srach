package com.example.backend.service;


import com.example.backend.dto.ThreadDto;
import com.example.backend.model.Board;
import com.example.backend.model.Thread;
import com.example.backend.repository.BoardRepository;
import com.example.backend.repository.ThreadRepository;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Log4j2
public class ThreadService {
    private final BoardRepository boardRepository;

    private final ThreadRepository threadRepository;

    private final BoardService boardService;

    private final ModelMapper modelMapper;

    @Autowired
    public ThreadService(ThreadRepository threadRepository, BoardService boardService, ModelMapper modelMapper,
                         BoardRepository boardRepository) {
        this.threadRepository = threadRepository;
        this.boardService = boardService;
        this.modelMapper = modelMapper;
        this.boardRepository = boardRepository;
    }

    public Thread createThread(ThreadDto threadDto, String codeName){
        if (threadDto.getUsername() == null){
            threadDto.setUsername("Аноним");
        }
        Board board = boardService.getBoard(codeName);
        LocalDateTime nowTime = LocalDateTime.now();
        Thread thread = modelMapper.map(threadDto, Thread.class);
        Long postCount = board.getPostCounter()+1;
        thread.setThreadNumber(postCount);
        boardService.updatePostCounterByCodeName(postCount, codeName);
        thread.setCreatedAt(nowTime);
        thread.setLastPostTime(nowTime);
        thread.setBoard(board);
        thread.setLocked(false);
        return threadRepository.save(thread);
    }

    public Thread getThread(String codeName, Long threadNumber){
        Board board = boardService.getBoard(codeName);
        return threadRepository.findByBoardAndThreadNumber(board, threadNumber).orElseThrow(EntityNotFoundException::new);
    }

    public List<Thread> getThreads(String codeName){
        Board board = boardService.getBoard(codeName);
        return threadRepository.findAllByBoardOrderByLastPostTimeDesc(board);
    }

    @Transactional
    public void updateLastPostTimeByBoardAndThreadNumber(LocalDateTime lastPostTime, String codeName, Long threadNumber){
        Board board = boardService.getBoard(codeName);
        threadRepository.updateLastPostTimeByBoardAndThreadNumber(lastPostTime, board, threadNumber);
    }

    public void deleteThreadByBoardCodenameAndThreadNumber(String codeName, Long threadNumber){
        Board board = boardService.getBoard(codeName);
        threadRepository.deleteThreadByBoardAndThreadNumber(board, threadNumber);
    }

    public void lockThread(String codeName, Long threadNumber){
        Board board = boardService.getBoard(codeName);
        threadRepository.lockThread(board, threadNumber);
    }
}
