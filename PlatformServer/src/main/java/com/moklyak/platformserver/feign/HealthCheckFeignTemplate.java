package com.moklyak.platformserver.feign;

import com.moklyak.platformserver.dto.HealthCheckRequestDTO;
import com.moklyak.platformserver.dto.HealthCheckResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;

@FeignClient(name = "platform", url = "/")
public interface HealthCheckFeignTemplate {
    @PostMapping("/healthcheck")
    ResponseEntity<HealthCheckResponseDTO> healthcheck(URI baseUri, @RequestBody HealthCheckRequestDTO dto);
}
