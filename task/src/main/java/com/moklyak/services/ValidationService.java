package com.moklyak.services;

import com.moklyak.enums.IFE;
import com.moklyak.enums.VFE;
import com.moklyak.others.InputField;
import com.moklyak.others.Variant;

public class ValidationService {

    public  static  boolean validate(Variant variant, InputField task){
        return validateLine(variant) && validateCircles(variant, task);
    }
    private static boolean validateLine(Variant variant) {
        VFE[][] variants = variant.getFields();
        int rows = variants.length;
        int columns = variants[0].length;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (j != columns - 1) {
                    if (!variants[i][j].checkRight(variants[i][j + 1])) {
                        return false;
                    }
                } else {
                    if (!variants[i][j].checkRight(VFE.EMPTY)) {
                        return false;
                    }
                }
                if (i != rows - 1) {
                    if (!variants[i][j].checkDown(variants[i + 1][j])) {
                        return false;
                    }
                } else {
                    if (!variants[i][j].checkDown(VFE.EMPTY)) {
                        return false;
                    }
                }
            }
        }
        for (int i = 0 ; i < rows; i++){
            if (!VFE.EMPTY.checkRight(variants[i][0])){
                return false;
            }
        }
        for (int j = 0; j < columns; j++){
            if (!VFE.EMPTY.checkDown(variants[0][j])){
                return false;
            }
        }
        return true;
    }

    private static boolean validateCircles(Variant variant, InputField input) {
        VFE[][] variants = variant.getFields();
        IFE[][] tasks = input.getContent();
        int rows = variants.length;
        int columns = variants[0].length;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                IFE task = tasks[i][j];
                if (task == IFE.WHITE) {
                    if (!whiteCheck(variants, i, j)){
                        return false;
                    }
                } else if (task == IFE.BLACK) {
                    if (!blackCheck(variants, i, j)){
                        return false;
                    }
                } else if (task == IFE.GREY_) {
                    if (!greyCheck(variants, i, j)){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private static boolean whiteCheck(VFE[][] variants, int i, int j){
        VFE curr, one, two; //one and two just neighbor fields with continuous line? not the amount
        curr = variants[i][j];
        if (curr.isAngle()){
            return false;
        }
        one = curr.getOne(variants, i, j);
        two = curr.getTwo(variants, i, j);
        if (!(one.isAngle() || two.isAngle())){
            return false;
        }
        return true;
    }

    private static boolean blackCheck(VFE[][] variants, int i, int j){
        VFE curr, one, two; //one and two just neighbor fields with continuous line? not the amount
        curr = variants[i][j];
        if (!(curr.isAngle())){
            return false;
        }
        one = curr.getOne(variants, i, j);
        two = curr.getTwo(variants, i, j);
        if (one.isAngle() || two.isAngle()){
            return false;
        }
        return true;
    }

    private static boolean greyCheck(VFE[][] variants, int i, int j){
        return whiteCheck(variants, i, j) || blackCheck(variants, i, j);
    }
}
