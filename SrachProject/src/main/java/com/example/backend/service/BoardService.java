package com.example.backend.service;

import com.example.backend.dto.BoardDto;
import com.example.backend.model.Board;
import com.example.backend.repository.BoardRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class BoardService {

    private final BoardRepository boardRepository ;

    private final ModelMapper modelMapper;

    @Autowired
    public BoardService(BoardRepository boardRepository, ModelMapper modelMapper) {
        this.boardRepository = boardRepository;
        this.modelMapper = modelMapper;
    }


    public Board createBoard(BoardDto boardDto){
        if (boardRepository.existsBoardByCodeName(boardDto.getCodeName())){
            throw new EntityExistsException("Борда уже существует");
        }
        Board newBoard = modelMapper.map(boardDto, Board.class);
        newBoard.setPostCounter(0L);
        return boardRepository.save(newBoard);
    }

    public Board getBoard(String codeName){
        return boardRepository.findBoardByCodeName(codeName).
                orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public void updatePostCounterByCodeName(Long count, String codeName){
        boardRepository.updatePostCounterByCodeName(count, codeName);
    }

    public List<Board> getBoards(){
        return boardRepository.findAll();
    }

    public void deleteBoardByCodename(String codeName){
        boardRepository.deleteBoardByCodeName(codeName);
    }

    @Transactional
    public void editBoardByCodename(String codeName, BoardDto boardDto){
        boardRepository.updateBoardByCodename(boardDto.getCodeName(),
                boardDto.getName(),
                boardDto.getSubject(),
                codeName);
    }
}
