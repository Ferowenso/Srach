package com.example.backend.service;

import com.example.backend.dto.BoardDto;
import com.example.backend.model.Board;
import com.example.backend.model.Subject;
import com.example.backend.repository.BoardRepository;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = BoardService.class)
public class BoardServiceTest {

    @MockBean
    BoardRepository boardRepository;

    @MockBean
    ModelMapper modelMapper;

    @Autowired
    BoardService boardService;

    @Test
    void shouldCreateBoard() {
        BoardDto boardDto = new BoardDto("b", "Бред", Subject.ADULT);

        Board expectedBoard = new Board();
        expectedBoard.setCodeName("b");
        expectedBoard.setPostCounter(0L);

        when(boardRepository.existsBoardByCodeName("b")).thenReturn(false);
        when(modelMapper.map(boardDto, Board.class)).thenReturn(expectedBoard);
        when(boardRepository.save(expectedBoard)).thenReturn(expectedBoard);

        Board actualBoard = boardService.createBoard(boardDto);

        verify(modelMapper).map(boardDto, Board.class);
        verify(boardRepository).save(expectedBoard);
        assertEquals(expectedBoard, actualBoard);
    }

    @Test
    void shouldExistBoard(){
        BoardDto boardDto = new BoardDto("b", "Бред", Subject.ADULT);

        when(boardRepository.existsBoardByCodeName("b")).thenReturn(true);

        assertThrows(EntityExistsException.class, () -> boardService.createBoard(boardDto));

        verify(modelMapper, never()).map(boardDto, Board.class);
        verify(boardRepository, never()).save(any(Board.class));
    }

    @Test
    void shouldGetBoard(){
        Board expectedBoard = new Board();
        expectedBoard.setCodeName("b");

        when(boardRepository.findBoardByCodeName("b")).thenReturn(Optional.of(expectedBoard));

        Board actualBoard = boardService.getBoard("b");

        verify(boardRepository).findBoardByCodeName("b");
        assertEquals(expectedBoard, actualBoard);

    }

    @Test
    void shouldThrowExceptionWhenBoardNotFound() {

        when(boardRepository.findBoardByCodeName("b")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> boardService.getBoard("b"));

        verify(boardRepository).findBoardByCodeName("b");
    }

    @Test
    void shouldDeleteBoard() {

        boardService.deleteBoardByCodename("b");

        verify(boardRepository).deleteBoardByCodeName("b");
    }

    @Test
    void shouldUpdateCounter(){

        boardService.updatePostCounterByCodeName(1L, "b");

        verify(boardRepository).updatePostCounterByCodeName(1L, "b");
    }

    @Test
    void shouldGetBoards(){
        List<Board> expectedBoards = Arrays.asList(new Board(), new Board(), new Board());

        when(boardRepository.findAll()).thenReturn(expectedBoards);

        List<Board> actualBoards = boardService.getBoards();

        verify(boardRepository).findAll();
        assertEquals(expectedBoards, actualBoards);
    }

    @Test
    void shouldEditBoard(){
        boardService.editBoardByCodename("b", new BoardDto("a", "anime", Subject.INTERESTS));

        verify(boardRepository).updateBoardByCodename("a", "anime", Subject.INTERESTS, "b");
    }
}
