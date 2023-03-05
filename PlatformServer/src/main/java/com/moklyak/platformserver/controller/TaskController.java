package com.moklyak.platformserver.controller;

import com.moklyak.platformserver.dto.OutputDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/")
public class TaskController {

    private final Logger log = LoggerFactory.getLogger(ClientController.class);

    @PostMapping("/callback")
    public ResponseEntity<?> callback(@RequestBody OutputDTO dto){
        log.info(Arrays.toString(dto.getLines()));
        return ResponseEntity.ok().build();
    }
}
