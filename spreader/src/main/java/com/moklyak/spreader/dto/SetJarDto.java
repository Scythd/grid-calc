package com.moklyak.spreader.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Data
@AllArgsConstructor
public class SetJarDto {
    String id;
    File jarFile;
}
