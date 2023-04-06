package com.moklyak.platformserver.feign;

import com.moklyak.platformserver.dto.InputDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;

import java.net.URI;

@FeignClient(name = "evaluation", url = "/")
public interface EvaluationFeignTemplate {
    @PostMapping(value = "/calc", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Boolean> calc(URI baseUri, @ModelAttribute InputDTO inputDTO);
}

