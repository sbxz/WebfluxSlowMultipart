package com.example.mvcmultipart;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class FileController {

    /**
     * With the MultipartFile endpoint for a 200mb file, it takes approximately 700ms for the
     * request
     */
    @PostMapping(path = "multipart-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadMultipartFile(@RequestPart(value = "file") MultipartFile file) {
        log.info("Received multipartFile {}", file.getOriginalFilename());
    }
}
