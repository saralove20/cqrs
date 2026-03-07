package com.example.board_query.api;

import com.example.board_core.common.model.BaseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import com.example.board_query.api.model.BoardDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173",allowCredentials = "true")
@RequestMapping("/board")
@RestController
@RequiredArgsConstructor
@Tag(name="게시판 기능")
public class BoardController {
    private final BoardService boardService;

    // 게시글 목록 조회
    @GetMapping("/list")
    public ResponseEntity list(
            @RequestParam(required = true, defaultValue = "0") int page,
            @RequestParam(required = true, defaultValue = "5") int size
    ) {
        BoardDto.PageRes dto = boardService.list(page, size);
        return ResponseEntity.ok(BaseResponse.success(dto));
    }

    // 게시글 상세 조회
    @GetMapping("/read/{idx}")
    public ResponseEntity read(@PathVariable Long idx) {
        BoardDto.ReadRes dto = boardService.read(idx);
        return ResponseEntity.ok(BaseResponse.success(dto));
    }
}

