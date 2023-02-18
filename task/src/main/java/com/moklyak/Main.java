package com.moklyak;

import com.moklyak.enums.VFE;
import com.moklyak.others.InputField;
import com.moklyak.others.Variant;
import com.moklyak.services.InputService;
import com.moklyak.services.ValidationService;

import java.math.BigInteger;
import java.time.Instant;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        String resVal = null;
        InputField task = InputService.parseTaskArgument(args[0], args[1]);
        BigInteger boundStart = InputService.parseBound(args[2]);
        BigInteger boundEnd = InputService.parseBound(args[3]);
        BigInteger curr = boundStart;
        while (!curr.equals(boundEnd)){

            Variant variant = InputService.variantFromBigIntegerAndTask(task, curr);
            boolean res = ValidationService.validate(variant, task);
            if (res) {
                resVal = Arrays.stream(variant.getFields())
                                    .flatMap(x -> Arrays.stream(x))
                                    .map(x -> Arrays.asList(VFE.values()).indexOf(x))
                                    .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                                    .toString();
                System.out.println(String.format("%s %s %s %s", boundStart, boundEnd, true, resVal));
                break;
            }
            curr = curr.add(BigInteger.ONE);
        }
        if (resVal == null){
            System.out.println(String.format("%s %s %s %s", boundStart, boundEnd, "false", "-1"));
        }
    }


}