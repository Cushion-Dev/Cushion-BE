package com.chzzk.cushion.chatroom.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "검색어 자동 완성 응답")
@Data
public class SearchWordAutoCompleteResponse {

    @Schema(description = "검색어 자동 완성 목록", example = "[\"김철민(상사)\", \"김철수(동료)\", \"김철현(동료)\"]")
    private List<String> words;
}
