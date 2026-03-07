package com.example.board_query.api.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

public class BoardDto {

    // 게시글 목록 조회 페이지 응답
    @Builder
    @Getter
    public static class PageRes {
        private List<ListRes> boardList;    // 기존 ListRes 활용
        private int totalPage;              // 전체 페이지 수
        private long totalCount;            // 전체 게시글 수
        private int currentPage;            // 현재 페이지 번호
        private int currentSize;            // 페이지당 사이즈 (페이지당 게시글 수)

        public static PageRes from(Page<Board> result) {
            return PageRes.builder()
                    .boardList(result.get().map(ListRes::from).toList())
                    .totalPage(result.getTotalPages())
                    .totalCount(result.getTotalElements())
                    .currentPage(result.getPageable().getPageNumber())
                    .currentSize(result.getPageable().getPageSize())
                    .build();
        }
    }

    // 게시글 목록 조회 응답
    @Builder
    @Getter
    public static class ListRes {
        private Long idx;
        private String title;
        private String writer;

        public static ListRes from(Board entity) {
            return ListRes.builder()
                    .idx(entity.getIdx())
                    .title(entity.getTitle())
                    .writer(entity.getUserName())
                    .build();
        }
    }


    // 게시글 상세 조회 응답
    @Builder
    @Getter
    public static class ReadRes {
        private Long idx;
        private String title;
        private String contents;
        private String writer;

        public static ReadRes from(Board entity) {
            return ReadRes.builder()
                    .idx(entity.getIdx())
                    .title(entity.getTitle())
                    .contents(entity.getContents())
                    .writer(entity.getUserName())
                    .build();
        }
    }
}