package com.moklyak.spreader.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigInteger;

@Data
@AllArgsConstructor
public class ProgressDto {
    BigInteger currentCompletedTaskCount;
    BigInteger totalReceivedTaskCount;

}
