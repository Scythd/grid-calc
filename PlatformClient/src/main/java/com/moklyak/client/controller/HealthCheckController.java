package com.moklyak.client.controller;

import com.moklyak.client.dto.HealthCheckRequestDTO;
import com.moklyak.client.dto.HealthCheckResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    private static boolean busy = false;

    @PostMapping("/healthcheck")
    ResponseEntity<HealthCheckResponseDTO> healthcheck(@RequestBody HealthCheckRequestDTO dto){
        HealthCheckResponseDTO a = new HealthCheckResponseDTO();
        a.setB(busy);
        return ResponseEntity.ok(a);
    }

    @GetMapping("/healthcheck")
    ResponseEntity<String> healthcheck(){
        if (busy){
            return ResponseEntity.ok("Busy");
        } else {
            return ResponseEntity.ok("Healthy");
        }
    }

    public static void setBusy(boolean busy) {
        HealthCheckController.busy = busy;
    }
}
