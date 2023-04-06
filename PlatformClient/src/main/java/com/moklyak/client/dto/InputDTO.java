package com.moklyak.client.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import java.io.Serializable;

@Data
public class InputDTO implements Serializable {
    private String id;
    private MultipartFile jarFile;
    private String args;
}
