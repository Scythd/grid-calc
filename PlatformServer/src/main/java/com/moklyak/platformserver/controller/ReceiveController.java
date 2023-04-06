package com.moklyak.platformserver.controller;


import com.moklyak.platformserver.dto.*;
import com.moklyak.platformserver.service.Session;
import com.moklyak.platformserver.service.TaskSchedulerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Supplier;

@RestController
@RequestMapping("/api")
public class ReceiveController {

    //later make timeout
    List<Session> sessions = new CopyOnWriteArrayList<Session>();
    Supplier<String> idGen = () -> UUID.randomUUID().toString();

    @Autowired
    private TaskSchedulerService taskSchedulerService;

    @GetMapping("/register")
    public ResponseEntity<String> register(){
        String id = idGen.get();
        Session s = new Session();
        s.setId(id);
        sessions.add(s);
        return ResponseEntity.ok(id);
    }

    @PostMapping(value = "/setJar", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> setJar(@ModelAttribute SetJarDto dto){
        String id = dto.getId();
        Session s = new Session();
        s.setId(id);
        s = sessions.get(sessions.indexOf(s));
        s.setJar(dto.getJarFile());
        return ResponseEntity.ok(id);
    }

    @PostMapping("/setCallbackURL")
    public ResponseEntity<String> setCallback(@RequestBody SetCallbackDto dto){
        String id = dto.getId();
        Session s = new Session();
        s.setId(id);
        s = sessions.get(sessions.indexOf(s));
        s.setCallbackURL(dto.getCallbackURL());
        return ResponseEntity.ok(id);
    }

    @PostMapping("/addTask")
    public ResponseEntity<String> addTask(@RequestBody AddTaskDto dto){
        String id = dto.getId();
        Session s = new Session();
        s.setId(id);
        s = sessions.get(sessions.indexOf(s));
        s.addTask(dto.getTask());
        return ResponseEntity.ok(id);
    }

    @PostMapping("/addTasks")
    public ResponseEntity<String> addTasks(@RequestBody AddTasksDto dto){
        String id = dto.getId();
        Session s = new Session();
        s.setId(id);
        s = sessions.get(sessions.indexOf(s));
        s.addTasks(dto.getTasks());
        return ResponseEntity.ok(id);
    }

    @PostMapping("/ready")
    public ResponseEntity<String> ready(@RequestBody String id){
        Session s = new Session();
        s.setId(id);
        s = sessions.get(sessions.indexOf(s));

        taskSchedulerService.addSession(s);

        return ResponseEntity.ok(id);
    }

    @PostMapping("/complete")
    public ResponseEntity<String> taskComplete(@RequestBody String id){
        Session s = new Session();
        s.setId(id);
        s = sessions.get(sessions.indexOf(s));

        taskSchedulerService.removeSession(s);
        sessions.remove(s);

        return ResponseEntity.ok(id);
    }

    @PostMapping("/progress")
    public ResponseEntity<ProgressDto> progress(@RequestBody String id){
        Session s = new Session();
        s.setId(id);
        s = sessions.get(sessions.indexOf(s));

        return ResponseEntity.ok(new ProgressDto(s.getCurrentCompletedTaskCount(), s.getTotalReceivedTaskCount()));
    }
}
