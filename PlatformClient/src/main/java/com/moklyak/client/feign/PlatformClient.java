package com.moklyak.client.feign;

import com.moklyak.client.dto.ClientDTO;
import com.moklyak.client.dto.OutputDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "platform", url = "http://${platform.ip}:${platform.port}/")
public interface PlatformClient {
    @PostMapping("/client/add")
    ResponseEntity<?> addClient(ClientDTO client);

    @PostMapping("/callback")
    public ResponseEntity<?> callback(@RequestBody OutputDTO dto);
}
