package com.moklyak.enums;

/** Variant Field Element
 *
 */
public enum VFE {
    EMPTY(0, 0, 0, 0, false),
    STR_H(1, 0, -1, 0, false),
    STR_V(0, 1, 0, -1, false),
    ANG_1(0, 1, 1, 0, true), // like L
    ANG_2(0, 1, -1, 0, true), // like ⅃
    ANG_3(0, -1, -1, 0, true), // like ⅂
    ANG_4(1, 0, 0, -1, true); // like Г

    //one then two clockwise
    //one and two just neighbor fields with continuous line? not the amount
    private final int oneShiftX, oneShiftY, twoShiftX, twoShiftY;
    private final boolean isAngle;

    private static boolean isInit = false;
    VFE(int oneShiftX, int oneShiftY, int twoShiftX, int twoShiftY, boolean isAngle) {
        this.oneShiftX = oneShiftX;
        this.oneShiftY = oneShiftY;
        this.twoShiftX = twoShiftX;
        this.twoShiftY = twoShiftY;
        this.isAngle = isAngle;
    }

    private VFE[] downAllowed;
    private VFE[] rightAllowed;

    public void setDownAllowed(VFE[] downAllowed) {
        if (this.downAllowed == null) {
            this.downAllowed = downAllowed;
        }
    }

    public void setRightAllowed(VFE[] rightAllowed) {
        if (this.rightAllowed == null) {
            this.rightAllowed = rightAllowed;
        }
    }

    public boolean checkDown(VFE down){
        if (!isInit) configureClasses();
        for (VFE vfe : downAllowed) {
            if (down == vfe) {
                return true;
            }
        }
        return false;
    }
    public boolean checkRight(VFE right){
        if (!isInit) configureClasses();
        for (VFE vfe : rightAllowed) {
            if (right == vfe) {
                return true;
            }
        }
        return false;
    }

    //-shift because I'm going from up to down
    public VFE getOne(VFE[][] variants, int i, int j) {
        return variants[i-oneShiftY][j+oneShiftX];
    }
    //-shift because I'm going from up to down
    public VFE getTwo(VFE[][] variants, int i, int j) {
        return variants[i-twoShiftY][j+twoShiftX];
    }

    public boolean isAngle() {
        return isAngle;
    }

    private static void configureClasses(){
        VFE[] rightEmpty, downEmpty, rightBusy, downBusy;
        rightEmpty = new VFE[]{VFE.EMPTY, VFE.ANG_4, VFE.ANG_1, VFE.STR_V};
        downEmpty = new VFE[]{VFE.EMPTY, VFE.STR_H, VFE.ANG_3, VFE.ANG_4};
        rightBusy = new VFE[]{VFE.STR_H, VFE.ANG_2, VFE.ANG_3};
        downBusy = new VFE[]{VFE.STR_V, VFE.ANG_1, VFE.ANG_2};

        VFE.EMPTY.setRightAllowed(rightEmpty);
        VFE.EMPTY.setDownAllowed(downEmpty);

        VFE.STR_V.setRightAllowed(rightEmpty);
        VFE.STR_V.setDownAllowed(downBusy);

        VFE.STR_H.setRightAllowed(rightBusy);
        VFE.STR_H.setDownAllowed(downEmpty);

        VFE.ANG_1.setRightAllowed(rightBusy);
        VFE.ANG_1.setDownAllowed(downEmpty);

        VFE.ANG_2.setRightAllowed(rightEmpty);
        VFE.ANG_2.setDownAllowed(downEmpty);

        VFE.ANG_3.setRightAllowed(rightEmpty);
        VFE.ANG_3.setDownAllowed(downBusy);

        VFE.ANG_4.setRightAllowed(rightBusy);
        VFE.ANG_4.setDownAllowed(downBusy);

        isInit = true;
    }
}
