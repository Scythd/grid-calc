package com.moklyak.client.controller;


import com.moklyak.client.dto.InputDTO;
import com.moklyak.client.dto.OutputDTO;
import com.moklyak.client.feign.PlatformClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.concurrent.*;

@RestController
@RequestMapping("/")
public class EvaluationController {

    private final Logger log = LoggerFactory.getLogger(EvaluationController.class);
    Executor executor = Executors.newSingleThreadExecutor();
    @Autowired
    private PlatformClient platformClient;

    @PostMapping(value = "/calc", consumes = { "multipart/form-data" })
    public ResponseEntity<Boolean> calc(@ModelAttribute InputDTO inputDTO){
        if (HealthCheckController.isBusy()){
            log.info("denied processing - busy");
            return ResponseEntity.ok(false);
        }
        log.info("start processing: " + inputDTO.getId() + " " + Arrays.asList(inputDTO.getArgs()));
        HealthCheckController.setBusy(true);
        try {
            createDummyIfNotExists();
            inputDTO.getJarFile().transferTo(Path.of("./temp/calc.jar"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String[] args = inputDTO.getArgs().split(",");
        Runnable runnable = () -> {
            OutputDTO outputDTO = new OutputDTO();
            outputDTO.setId(inputDTO.getId());
            StringBuilder sb = new StringBuilder();
            sb.append("java -jar ./temp/calc.jar");
            for (String s : args) {
                sb.append(" ");
                sb.append(s);
            }
            Process p = null;
            try {
                p = Runtime.getRuntime().exec(sb.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                p.onExit().get();
            } catch (Exception ex) {
                log.warn(ex.toString());
            }
            BufferedReader r = p.inputReader();
            outputDTO.setLines(r.lines().toArray(String[]::new));
            platformClient.callback(outputDTO);
            HealthCheckController.setBusy(false);
            log.info("end processing: " + inputDTO.getId() + " " + Arrays.asList(outputDTO.getLines()));
        };
        executor.execute(runnable);
        return ResponseEntity.ok(true);
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
