package com.example.board_query.api;

import com.example.board_query.api.model.Board;
import com.example.board_query.api.model.BoardDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;

    // 게시글 목록 조회
    public BoardDto.PageRes list(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        // 페이징 처리 ⭕, 페이지 번호가 필요하다 => Page 반환
        // 페이징 처리 ⭕, 페이지 번호가 필요없다. => Slice 반환
        Page<Board> result = boardRepository.findAll(pageRequest);
        return BoardDto.PageRes.from(result);
    }

    // 게시글 상세 조회
    public BoardDto.ReadRes read(Long idx) {
        Board board = boardRepository.findById(idx).orElseThrow();
        return BoardDto.ReadRes.from(board);
    }
}
