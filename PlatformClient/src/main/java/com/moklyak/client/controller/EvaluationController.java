package com.moklyak.client.controller;


import com.moklyak.client.dto.InputDTO;
import com.moklyak.client.dto.OutputDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Path;

@RestController
@RequestMapping("")
public class EvaluationController {

    @PostMapping("/calc")
    public ResponseEntity<?> calc(@RequestBody InputDTO inputDTO){
        OutputDTO outputDTO = new OutputDTO();
        try {
            inputDTO.getJarFile().transferTo(Path.of("./temp/calc.jar"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("java -jar ./temp/calc.jar");
            for (String s : inputDTO.getArgs()){
                sb.append(" ");
                sb.append(s);
            }
            Process p = Runtime.getRuntime().exec(sb.toString());
            BufferedReader r = p.inputReader();
            outputDTO.setLines(r.lines().toArray(String[]::new));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(outputDTO);
    }

}
