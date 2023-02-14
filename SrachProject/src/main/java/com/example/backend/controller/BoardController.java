package com.example.backend.controller;


import com.example.backend.dto.BoardDto;
import com.example.backend.model.Board;
import com.example.backend.service.BoardService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Log4j2
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping
    public ResponseEntity<List<Board>> getBoards(){
        return new ResponseEntity<>(boardService.getBoards(), HttpStatus.OK);
    }

    @GetMapping("/{codeName}")
    public ResponseEntity<Board> getBoard(@PathVariable String codeName){
        return new ResponseEntity<>(boardService.getBoard(codeName), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Board> createBoard(@RequestBody @Valid BoardDto boardDto){
        return new ResponseEntity<>(boardService.createBoard(boardDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{codeName}")
    public ResponseEntity deleteBoard(@PathVariable String codeName){
        boardService.deleteBoardByCodename(codeName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{codeName}")
    public ResponseEntity<Board> editBoard(@RequestBody @Valid BoardDto boardDto, @PathVariable String codeName){
        boardService.editBoardByCodename(codeName, boardDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
