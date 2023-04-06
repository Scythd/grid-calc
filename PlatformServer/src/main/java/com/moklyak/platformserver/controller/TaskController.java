package com.moklyak.platformserver.controller;

import com.moklyak.platformserver.dto.OutputDTO;
import com.moklyak.platformserver.feign.CallbackFeignTemplate;
import com.moklyak.platformserver.service.Task;
import com.moklyak.platformserver.service.TaskSchedulerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Arrays;

@RestController
@RequestMapping("/")
public class TaskController {

    private final Logger log = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private CallbackFeignTemplate callbackFeignTemplate;
    @Autowired
    private TaskSchedulerService taskSchedulerService;

    @PostMapping("/callback")
    public ResponseEntity<?> callback(@RequestBody OutputDTO dto){
        String id = dto.getId();
        log.info("callback task " + dto.getId() + " " + Arrays.asList(dto.getLines()));
        Task t = taskSchedulerService.taskComplete(id);
        dto.setId(t.getS().getId());
        callbackFeignTemplate.callback(URI.create("http://" + t.getS().getCallbackURL()), dto);

        return ResponseEntity.ok().build();
    }
}
