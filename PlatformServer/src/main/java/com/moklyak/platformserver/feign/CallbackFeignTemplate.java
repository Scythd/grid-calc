package com.moklyak.platformserver.feign;

import com.moklyak.platformserver.dto.HealthCheckRequestDTO;
import com.moklyak.platformserver.dto.HealthCheckResponseDTO;
import com.moklyak.platformserver.dto.OutputDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;

@FeignClient(name = "evaluationCallback", url = "/")
public interface CallbackFeignTemplate {

    @PostMapping
    ResponseEntity<HealthCheckResponseDTO> callback(URI baseUri, @RequestBody OutputDTO dto);
}
