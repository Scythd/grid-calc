package com.moklyak.client.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class InputDTO {
    private MultipartFile jarFile;
    private String[] args;
}
