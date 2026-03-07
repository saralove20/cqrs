package com.example.board_command.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

public class BoardDto {
    // 게시글 작성 요청
    @Getter
    public static class RegReq {
        @Schema(description = "제목, 제목은 50글자까지만 입력 가능합니다.", required = true, example = "제목01")
        private String title;
        private String contents;

        public Board toEntity(Long userIdx, String userName) {
            return Board.builder()
                    .title(this.title)
                    .contents(this.contents)
                    .userIdx(userIdx)
                    .userName(userName)
                    .build();
        }
    }

    // 게시글 작성 응답
    @Builder
    @Getter
    public static class RegRes {
        private Long idx;
        private String title;
        private String contents;

        public static RegRes from(Board entity) {
            return RegRes.builder()
                    .idx(entity.getIdx())
                    .title(entity.getTitle())
                    .contents(entity.getContents())
                    .build();
        }
    }
}