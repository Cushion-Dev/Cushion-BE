package com.chzzk.cushion.style.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
public class ImageParsingRequest {

    private List<MultipartFile> images;
}
