package com.moklyak.platformserver.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.Serializable;
import java.util.List;

@Data
public class InputDTO implements Serializable {
    private String id;
    private String args;
    private File jarFile;
}
