package com.moklyak.spreader.controller;

import com.moklyak.spreader.dto.OutputDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Objects;

@RestController
public class CallBackController {

    private final Logger log = LoggerFactory.getLogger(CallBackController.class);

    private String currId;
    @Autowired
    private UIRestController uiRestController;
    @PostMapping("/callback")
    public ResponseEntity<Void> callBack(@RequestBody OutputDTO dto){
        propagateCurrId();
        if (Objects.equals(currId, dto.getId())) {
            try {
                log.info("Processing callback: " + Arrays.asList(dto.getLines()));
                boolean res = Boolean.parseBoolean(dto.getLines()[2]);
                if (res) {
                    BigInteger variantInt = new BigInteger(dto.getLines()[3], 7);
                    if (uiRestController.getAnswer().equals(BigInteger.valueOf(-1))) {
                        uiRestController.setAnswer(variantInt);
                    }
                }
            } catch (Exception ex) {
                log.error("boolean not 3 [2]");
                log.error(ex.getMessage());
            }
        }
        return ResponseEntity.ok().build();
    }

    public void propagateCurrId() {
        currId = uiRestController.propagateId();
    }
}
