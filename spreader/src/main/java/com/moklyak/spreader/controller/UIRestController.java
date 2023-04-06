package com.moklyak.spreader.controller;

import com.moklyak.spreader.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.Arrays;

@RestController
public class UIRestController {

    private BigInteger answer = BigInteger.valueOf(-1);
    private int width, height;
    @Autowired
    public TaskService taskService;

    @PostMapping("/ui/start")
    public ResponseEntity<?> start(@RequestBody int[][] circles){
        answer = BigInteger.valueOf(-1);
        width = circles[0].length;
        height = circles.length;
        taskService.start(circles);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/ui/cancel")
    ResponseEntity<?> cancel(){

        taskService.cancel();

        return ResponseEntity.ok().build();
    }

    @PostMapping("/ui/progress")
    ResponseEntity<Integer> progress(){
        if (!answer.equals(BigInteger.valueOf(-1))){
            return ResponseEntity.ok(100);
        }
        int res = taskService.progress();
        return ResponseEntity.ok(res);
    }

    @PostMapping("/ui/answer")
    ResponseEntity<?> answer(){
        if (answer.equals(BigInteger.valueOf(-1))){
            return ResponseEntity.ok(-1);
        }

        int[] res = Arrays.stream(
                    String.format("%1$" + width * height + "s", answer.toString(7))
                            .replace(' ', '0')
                            .split("")
                )
                .mapToInt(Integer::parseInt)
                .toArray();
        taskService.cancel();
        return ResponseEntity.ok(res);
    }

    public String propagateId(){
        return taskService.getCurrId();
    }
    public void setAnswer(BigInteger answer) {
        this.answer = answer;
    }

    public BigInteger getAnswer() {
        return answer;
    }
}
