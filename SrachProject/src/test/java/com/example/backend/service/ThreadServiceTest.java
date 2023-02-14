package com.example.backend.service;

import com.example.backend.dto.ThreadDto;
import com.example.backend.model.Board;
import com.example.backend.model.Thread;
import com.example.backend.repository.BoardRepository;
import com.example.backend.repository.ThreadRepository;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = ThreadService.class)
public class ThreadServiceTest {

    @MockBean
    ThreadRepository threadRepository;

    @MockBean
    BoardService boardService;

    @MockBean
    BoardRepository boardRepository;

    @MockBean
    ModelMapper modelMapper;

    @Autowired
    ThreadService threadService;

    @Test
    void shouldCreateThread(){
        ThreadDto threadDto = new ThreadDto();

        Board exceptedBoard = new Board();
        exceptedBoard.setPostCounter(0L);
        exceptedBoard.setCodeName("b");
        when(boardService.getBoard("b")).thenReturn(exceptedBoard);

        Thread expectedThread = new Thread();
        expectedThread.setBoard(exceptedBoard);
        when(modelMapper.map(threadDto, Thread.class)).thenReturn(expectedThread);

        when(threadRepository.save(expectedThread)).thenReturn(expectedThread);

        Thread actualThread = threadService.createThread(threadDto, "b");

        verify(modelMapper).map(threadDto, Thread.class);
        verify(threadRepository).save(expectedThread);
        assertEquals(expectedThread, actualThread);
    }

    @Test
    void shouldGetTest(){
        Board exceptedBoard = new Board();
        exceptedBoard.setCodeName("b");

        when(boardService.getBoard("b")).thenReturn(exceptedBoard);

        Thread expectedThread = new Thread();
        expectedThread.setBoard(exceptedBoard);
        expectedThread.setThreadNumber(1L);

        when(threadRepository.findByBoardAndThreadNumber(exceptedBoard, 1L)).thenReturn(Optional.of(expectedThread));

        Thread actualThread = threadService.getThread("b", 1L);

        verify(threadRepository).findByBoardAndThreadNumber(exceptedBoard, 1L);
        assertEquals(expectedThread, actualThread);
    }

    @Test
    void shouldThrowExceptionWhenThreadNotFound() {

        Board exceptedBoard = new Board();
        exceptedBoard.setCodeName("b");

        when(boardService.getBoard("b")).thenReturn(exceptedBoard);

        when(threadRepository.findByBoardAndThreadNumber(exceptedBoard, 1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> threadService.getThread("b", 1L));

        verify(threadRepository).findByBoardAndThreadNumber(exceptedBoard, 1L);
    }

    @Test
    void shouldGetThreads(){
        Board exceptedBoard = new Board();
        exceptedBoard.setCodeName("b");

        when(boardService.getBoard("b")).thenReturn(exceptedBoard);

        List<Thread> expectedThreads = Arrays.asList(new Thread(), new Thread(), new Thread());

        when(threadRepository.findAllByBoardOrderByLastPostTimeDesc(exceptedBoard)).thenReturn(expectedThreads);

        List<Thread> actualThreads = threadService.getThreads("b");

        verify(threadRepository).findAllByBoardOrderByLastPostTimeDesc(exceptedBoard);
        assertEquals(expectedThreads, actualThreads);
    }

    @Test
    void shouldUpdateLastPostTimeByBoardAndThreadNumber(){
        Board exceptedBoard = new Board();
        exceptedBoard.setCodeName("b");

        LocalDateTime now = LocalDateTime.now();

        when(boardService.getBoard("b")).thenReturn(exceptedBoard);

        threadService.updateLastPostTimeByBoardAndThreadNumber(now, "b", 1L);

        verify(threadRepository).updateLastPostTimeByBoardAndThreadNumber(now, exceptedBoard, 1L);
    }

    @Test
    void shouldDeleteThread(){
        Board exceptedBoard = new Board();
        exceptedBoard.setCodeName("b");

        when(boardService.getBoard("b")).thenReturn(exceptedBoard);

        threadService.deleteThreadByBoardCodenameAndThreadNumber("b", 1L);

        verify(threadRepository).deleteThreadByBoardAndThreadNumber(exceptedBoard, 1L);
    }

    @Test
    void shouldLockThread(){
        Board exceptedBoard = new Board();
        exceptedBoard.setCodeName("b");

        when(boardService.getBoard("b")).thenReturn(exceptedBoard);

        threadService.lockThread("b", 1L);

        verify(threadRepository).lockThread(exceptedBoard, 1L);
    }

}
