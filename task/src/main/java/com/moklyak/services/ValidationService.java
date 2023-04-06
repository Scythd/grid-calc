package com.moklyak.services;

import com.moklyak.enums.IFE;
import com.moklyak.enums.VFE;
import com.moklyak.others.InputField;
import com.moklyak.others.Variant;

import java.util.Arrays;

public class ValidationService {

    public static int validateLine(Variant variant) {
        VFE curr;

        VFE[][] variants = variant.getFields();
        int rows = variants.length;
        int columns = variants[0].length;
        int tempIndex = rows * columns;

        for (int j = 0; j < columns; j++){
            if (!VFE.EMPTY.checkDown(variants[0][j])){
                return j;
            }
        }

        for (int i = 0 ; i < rows; i++){
            if (!VFE.EMPTY.checkRight(variants[i][0])){
                tempIndex = i * columns;
                break;
            }
        }

        checker : for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
               curr = variants[i][j];

                if (j != columns - 1) {
                    if (!curr.checkRight(variants[i][j + 1])) {
                        // here i = 8 ; j = 7
                        tempIndex = Math.min(tempIndex, i * columns + j + 1);
                    }
                } else {
                    if (!curr.checkRight(VFE.EMPTY)) {
                        tempIndex = Math.min(tempIndex, i * columns + j);
                    }
                }
                if (i != rows - 1) {
                    if (!curr.checkDown(variants[i + 1][j])) {
                        tempIndex = Math.min(tempIndex, (i + 1) * columns + j);
                    }
                } else {
                    if (!curr.checkDown(VFE.EMPTY)) {
                        tempIndex = Math.min(tempIndex, i * columns + j);
                    }
                }
                if (tempIndex <= i * j){
                    break checker;
                }
            }
        }


        return tempIndex;
    }

    public static boolean validateSingleLine(Variant variant){
        VFE[][] var = variant.getFields();
        boolean[][] res = new boolean[var.length][var[0].length];
        int si = 0, sj = 0;
        for (int i = 0; i < var.length; i++) {
            for (int j = 0; j < var[0].length; j++) {
                if (var[i][j] == VFE.EMPTY){
                    res[i][j] = true;
                }
            }
        }
        for (int i = 0; i < var.length; i++) {
            for (int j = 0; j < var[0].length; j++) {
                if (var[i][j] != VFE.EMPTY){
                    si = i; sj = j;
                    res[i][j] = true;
                    break;
                }
            }
        }
        boolean isRunning = true;
        int oneI, oneJ, twoI, twoJ, currI, currJ, prevI, prevJ, pprevI, pprevJ;
        prevI = si;
        prevJ = sj;
        currI = var[prevI][prevJ].getOneI(var, prevI, prevJ);
        currJ = var[prevI][prevJ].getOneJ(var, prevI, prevJ);
        res[currI][currJ] = true;
        while(isRunning){
            pprevI = prevI;
            pprevJ = prevJ;
            prevI = currI;
            prevJ = currJ;
            oneI = var[prevI][prevJ].getOneI(var, prevI, prevJ);
            oneJ = var[prevI][prevJ].getOneJ(var, prevI, prevJ);
            twoI = var[prevI][prevJ].getTwoI(var, prevI, prevJ);
            twoJ = var[prevI][prevJ].getTwoJ(var, prevI, prevJ);
            if (pprevJ == oneJ && pprevI == oneI){
                currI = twoI;
                currJ = twoJ;
            } else {
                currI = oneI;
                currJ = oneJ;
            }

            if (currI == si && currJ == sj){
                isRunning = false;
            }

            res[currI][currJ] = true;
        }
        boolean acu = true;
        for (int i = 0; i < var.length; i++) {
            for (int j = 0; j < var[0].length; j++) {
                if (var[i][j] != VFE.EMPTY){
                    acu = acu && res[i][j];
                }
            }
        }
        return acu;
    }
    public static boolean validateCircles(Variant variant, InputField input) {
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
