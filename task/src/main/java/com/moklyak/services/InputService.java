package com.moklyak.services;

import com.moklyak.enums.IFE;
import com.moklyak.enums.VFE;
import com.moklyak.others.InputField;
import com.moklyak.others.Variant;

import java.math.BigInteger;
import java.util.Arrays;

public class InputService {

    public static InputField parseTaskArgument(String len, String taskString){
        int length = Integer.parseInt(len);
        int rowCount = taskString.length() / length;
        InputField res = new InputField();
        IFE[][] toSet = new IFE[rowCount][length];
        char temp;
        for (int i = 0; i < rowCount; i++){
            for (int j = 0; j < length; j++){
               temp  =  taskString.charAt(i*length + j);
               toSet[i][j] = IFE.values()[Character.getNumericValue(temp)];
            }
        }
        res.setContent(toSet);
        return res;
    }

    public static BigInteger parseBound(String bound){
        return new BigInteger(bound, 7);
    }

    public static Variant variantFromBigIntegerAndTask(InputField task, BigInteger variantInt){
        int rowSize, columnSize;
        rowSize = task.getContent().length;
        columnSize = task.getContent()[0].length;
        Variant res = new Variant();
        VFE[][] toSet = new VFE[rowSize][columnSize];
        String variantString = variantInt.toString(7);
        int leftPadZerosCount = rowSize * columnSize - variantString.length();
        char temp;
        for (int i = 0; i < rowSize; i++){
            for (int j = 0; j < columnSize; j++){
                if ((i*columnSize + j) < leftPadZerosCount){
                    toSet[i][j] = VFE.EMPTY;
                } else {
                    temp = variantString.charAt(i * columnSize + j - leftPadZerosCount);
                    toSet[i][j] = VFE.values()[Character.getNumericValue(temp)];
                }
            }
        }

        res.setFields(toSet);
        return res;
    }
    //stub
    public static InputField getTaskStub(){
        InputField result = new InputField();
        IFE[][] in;
        in = new IFE[][]{
            {IFE.EMPTY, IFE.EMPTY, IFE.WHITE, IFE.EMPTY, IFE.WHITE, IFE.EMPTY, IFE.EMPTY, IFE.EMPTY, IFE.EMPTY, IFE.EMPTY},
            {IFE.EMPTY, IFE.EMPTY, IFE.EMPTY, IFE.EMPTY, IFE.WHITE, IFE.EMPTY, IFE.EMPTY, IFE.EMPTY, IFE.BLACK, IFE.EMPTY},
            {IFE.EMPTY, IFE.EMPTY, IFE.BLACK, IFE.EMPTY, IFE.BLACK, IFE.EMPTY, IFE.WHITE, IFE.EMPTY, IFE.EMPTY, IFE.EMPTY},
            {IFE.EMPTY, IFE.EMPTY, IFE.EMPTY, IFE.WHITE, IFE.EMPTY, IFE.EMPTY, IFE.WHITE, IFE.EMPTY, IFE.EMPTY, IFE.EMPTY},
            {IFE.BLACK, IFE.EMPTY, IFE.EMPTY, IFE.EMPTY, IFE.EMPTY, IFE.WHITE, IFE.EMPTY, IFE.EMPTY, IFE.EMPTY, IFE.WHITE},
            {IFE.EMPTY, IFE.EMPTY, IFE.WHITE, IFE.EMPTY, IFE.EMPTY, IFE.EMPTY, IFE.EMPTY, IFE.WHITE, IFE.EMPTY, IFE.EMPTY},
            {IFE.EMPTY, IFE.EMPTY, IFE.BLACK, IFE.EMPTY, IFE.EMPTY, IFE.EMPTY, IFE.EMPTY, IFE.EMPTY, IFE.EMPTY, IFE.EMPTY},
            {IFE.WHITE, IFE.EMPTY, IFE.EMPTY, IFE.EMPTY, IFE.BLACK, IFE.EMPTY, IFE.EMPTY, IFE.EMPTY, IFE.EMPTY, IFE.WHITE},
            {IFE.EMPTY, IFE.EMPTY, IFE.EMPTY, IFE.EMPTY, IFE.EMPTY, IFE.EMPTY, IFE.WHITE, IFE.WHITE, IFE.EMPTY, IFE.EMPTY},
            {IFE.EMPTY, IFE.EMPTY, IFE.BLACK, IFE.EMPTY, IFE.EMPTY, IFE.EMPTY, IFE.EMPTY, IFE.EMPTY, IFE.EMPTY, IFE.BLACK},
        };
        result.setContent(in);
        return result;
    }

    //stub
    // 0611156115640614315231526115220022261422614342064226115202642261431435222061115234202611420031431114
    public static Variant getVariantStub(){
        Variant v = new Variant();
        VFE[][] in;
        in = new VFE[][]{
            {VFE.EMPTY, VFE.ANG_4, VFE.STR_H, VFE.STR_H, VFE.STR_H, VFE.ANG_3, VFE.ANG_4, VFE.STR_H, VFE.STR_H, VFE.ANG_3},
            {VFE.ANG_4, VFE.ANG_2, VFE.EMPTY, VFE.ANG_4, VFE.STR_H, VFE.ANG_2, VFE.ANG_1, VFE.STR_H, VFE.ANG_3, VFE.STR_V},
            {VFE.ANG_1, VFE.STR_H, VFE.ANG_3, VFE.STR_V, VFE.ANG_4, VFE.STR_H, VFE.STR_H, VFE.ANG_3, VFE.STR_V, VFE.STR_V},
            {VFE.EMPTY, VFE.EMPTY, VFE.STR_V, VFE.STR_V, VFE.STR_V, VFE.ANG_4, VFE.STR_H, VFE.ANG_2, VFE.STR_V, VFE.STR_V},
            {VFE.ANG_4, VFE.STR_H, VFE.ANG_2, VFE.ANG_1, VFE.ANG_2, VFE.STR_V, VFE.EMPTY, VFE.ANG_4, VFE.ANG_2, VFE.STR_V},
            {VFE.STR_V, VFE.ANG_4, VFE.STR_H, VFE.STR_H, VFE.ANG_3, VFE.STR_V, VFE.EMPTY, VFE.STR_V, VFE.ANG_4, VFE.ANG_2},
            {VFE.STR_V, VFE.STR_V, VFE.ANG_4, VFE.STR_H, VFE.ANG_2, VFE.ANG_1, VFE.STR_H, VFE.ANG_2, VFE.ANG_1, VFE.ANG_3},
            {VFE.STR_V, VFE.STR_V, VFE.STR_V, VFE.EMPTY, VFE.ANG_4, VFE.STR_H, VFE.STR_H, VFE.STR_H, VFE.ANG_3, VFE.STR_V},
            {VFE.ANG_1, VFE.ANG_2, VFE.STR_V, VFE.EMPTY, VFE.STR_V, VFE.ANG_4, VFE.STR_H, VFE.STR_H, VFE.ANG_2, VFE.STR_V},
            {VFE.EMPTY, VFE.EMPTY, VFE.ANG_1, VFE.STR_H, VFE.ANG_2, VFE.ANG_1, VFE.STR_H, VFE.STR_H, VFE.STR_H, VFE.ANG_2},
        };
        v.setFields(in);
        return v;
    }

    public static String getVariantStub(Variant v){
        return Arrays.stream(v.getFields())
                .flatMap(x -> Arrays.stream(x))
                .map(x -> Arrays.asList(VFE.values()).indexOf(x))
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

    public static String getTaskStub(InputField t){
        return Arrays.stream(t.getContent())
                .flatMap(x -> Arrays.stream(x))
                .map(x -> Arrays.asList(IFE.values()).indexOf(x))
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }
}
