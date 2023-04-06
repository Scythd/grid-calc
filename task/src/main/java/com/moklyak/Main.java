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
    public final static BigInteger SEVEN = BigInteger.valueOf(7);
    public static void main(String[] args) {

        Instant test1, test2;
        test1 = Instant.now();
        String resVal = null;
        InputField task = InputService.parseTaskArgument(args[0], args[1]);
        BigInteger boundStart = InputService.parseBound(args[2]);
        BigInteger boundEnd = InputService.parseBound(args[3]);
        BigInteger curr = boundStart;

        Variant variant = InputService.variantFromBigIntegerAndTask(task, curr);
        int length = variant.getFields().length * variant.getFields()[0].length;

        while (curr.compareTo(boundEnd) == -1){

            int res = ValidationService.validateLine(variant);

            if ((res - length) == 0){
                if (ValidationService.validateCircles(variant, task) && ValidationService.validateSingleLine(variant)) {
                    resVal = Arrays.stream(variant.getFields())
                            .flatMap(x -> Arrays.stream(x))
                            .map(x -> Arrays.asList(VFE.values()).indexOf(x))
                            .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                            .toString();
                    System.out.println(padLeftZeros(boundStart.toString(7), length));
                    System.out.println(padLeftZeros(boundEnd.toString(7), length));
                    System.out.println("true");
                    System.out.println(resVal);
                    break;
                }
                curr = curr.add(BigInteger.ONE);
                variant = InputService.variantFromBigIntegerAndTask(task, curr);
            } else {
                int t1 = length - res - 1;
                BigInteger tmp = SEVEN.pow(t1);
                curr = curr.add(tmp);
                curr = curr.subtract(curr.mod(tmp));
                variant = InputService.variantFromBigIntegerAndTask(task, curr);
            }

        }
        if (resVal == null){
            System.out.println(padLeftZeros(boundStart.toString(7), length));
            System.out.println(padLeftZeros(boundEnd.toString(7), length));
            System.out.println("false");
            System.out.println("-1");
        }
        test2 = Instant.now();
        System.out.println((test2.toEpochMilli() - test1.toEpochMilli()));
    }

    private static String padLeftZeros(String inputString, int length){
        return String.format("%1$" + length + "s", inputString).replace(' ', '0');
    }

    public static void draw(Variant v){
        StringBuilder sb = new StringBuilder();
        for (VFE[] v1 : v.getFields()){
            for (VFE v2 : v1){
                sb.append(v2.getSymbol());
            }
            sb.append('\n');
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append("\n---------------------------------------");
        System.out.println(sb.toString());
    }

    public static boolean test(){
        Instant test1, test2;
        test1 = Instant.now();
        String resVal = null;
        InputField task = InputService.getTaskStub();
        Variant variant = InputService.getVariantStub();
        int length = variant.getFields().length * variant.getFields()[0].length;

        for (int i = 0 ; i < 10000000; i++){

            int res = ValidationService.validateLine(variant);

            if ((res - length) == 0){
                if (ValidationService.validateCircles(variant, task) && ValidationService.validateSingleLine(variant)) {
                    resVal = Arrays.stream(variant.getFields())
                            .flatMap(x -> Arrays.stream(x))
                            .map(x -> Arrays.asList(VFE.values()).indexOf(x))
                            .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                            .toString();
                }
            } else {

            }

        }
        if (resVal == null){
            // System.out.println(padLeftZeros(boundStart.toString(7), length));
            // System.out.println(padLeftZeros(boundEnd.toString(7), length));
            // System.out.println("false");
            // System.out.println("-1");
        }
        test2 = Instant.now();
        System.out.println((test2.toEpochMilli() - test1.toEpochMilli()));
        return resVal != null;
    }

}