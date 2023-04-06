package com.moklyak.spreader.feign;


import com.moklyak.spreader.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

@FeignClient(name = "platformUIClient", url = "http://${platform.ip}:${platform.port}/api")
public interface ServerFeignTemplate {

    @GetMapping("/register")
    public ResponseEntity<String> register();

    @PostMapping(value = "/setJar", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> setJar(@RequestBody SetJarDto dto);

    @PostMapping("/addTask")
    public ResponseEntity<String> addTask(@RequestBody AddTaskDto dto);

    @PostMapping("/addTasks")
    public ResponseEntity<String> addTasks(@RequestBody AddTasksDto dto);

    @PostMapping("/setCallbackURL")
    public ResponseEntity<String> setCallback(@RequestBody SetCallbackDto dto);

    @PostMapping("/ready")
    public ResponseEntity<String> ready(@RequestBody String id);

    @PostMapping("/complete")
    public ResponseEntity<String> taskComplete(@RequestBody String id);

    @PostMapping("/progress")
    public ResponseEntity<ProgressDto> progress(@RequestBody String id);
}
