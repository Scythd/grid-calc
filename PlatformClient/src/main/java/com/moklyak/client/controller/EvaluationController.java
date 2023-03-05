package com.moklyak.client.controller;


import com.moklyak.client.dto.InputDTO;
import com.moklyak.client.dto.OutputDTO;
import com.moklyak.client.feign.PlatformClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

@RestController
@RequestMapping("")
public class EvaluationController {

    Executor executor = Executors.newSingleThreadExecutor();
    @Autowired
    private PlatformClient platformClient;

    @PostMapping(value = "/calc", consumes = { "multipart/form-data" })
    public ResponseEntity<?> calc(@ModelAttribute InputDTO inputDTO){

        try {
            createDummyIfNotExists();
            inputDTO.getJarFile().transferTo(Path.of("./temp/calc.jar"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Runnable runnable = () -> {
            OutputDTO outputDTO = new OutputDTO();
            outputDTO.setId(inputDTO.getId());
            StringBuilder sb = new StringBuilder();
            sb.append("java -jar ./temp/calc.jar");
            for (String s : inputDTO.getArgs()) {
                sb.append(" ");
                sb.append(s);
            }
            Process p = null;
            try {
                p = Runtime.getRuntime().exec(sb.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            BufferedReader r = p.inputReader();
            outputDTO.setLines(r.lines().toArray(String[]::new));
            platformClient.callback(outputDTO);
            HealthCheckController.setBusy(false);
        };
        executor.execute(runnable);
        HealthCheckController.setBusy(true);
        return ResponseEntity.ok().build();
    }

    private void createDummyIfNotExists() {
        File f = new File(Path.of("./temp/calc.jar").toUri());
        if (!f.exists()){
            try {
                f.getParentFile().mkdirs();
                f.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
