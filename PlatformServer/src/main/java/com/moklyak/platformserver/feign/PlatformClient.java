package com.moklyak.platformserver.feign;

import com.moklyak.platformserver.dto.ClientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "platform", url = "http://${platform.ip}:${platform.port}/")
public interface PlatformClient {
    @PostMapping("/client/add")
    ResponseEntity<?> addClient(ClientDTO client);
}
