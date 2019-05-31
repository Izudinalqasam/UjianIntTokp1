package com.tokopedia.testproject.problems.androidView.waterJugSimulation;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    public static List<WaterJugAction> simulateWaterJug(int jug1, int jug2, int target) {
        // TODO, simulate the smallest number of action to do the water jug problem
        // below is stub, replace with your implementation!
        List<WaterJugAction> listAwal = new ArrayList<>();
//        List<WaterJugAction> listAckhir = new ArrayList<>();


        int minimumFromFront = 0;
        int minimumFromEnd =0;

        int tampungJug2 =0;
        int tampungJug1 =0;

        while (tampungJug2 != target){

            if (tampungJug2 == 0 && tampungJug1 == 0) {
                tampungJug1 = jug1;
                listAwal.add(new WaterJugAction(WaterJugActionEnum.FILL, 1));

                if (tampungJug1 <= target || tampungJug1 < jug2){
                    tampungJug2 = tampungJug1;
                    listAwal.add(new WaterJugAction(WaterJugActionEnum.POUR, 2));
                    minimumFromFront +=1;
                }

            }else {

                if (tampungJug1 == 0) {
                    tampungJug1 += jug1;
                    listAwal.add(new WaterJugAction(WaterJugActionEnum.FILL, 1));

                    int minus = jug2 - tampungJug2;

                    tampungJug2 += minus;
                    tampungJug1 -= minus;
                    listAwal.add(new WaterJugAction(WaterJugActionEnum.POUR, 1));
                    minimumFromFront +=1;

                    if (tampungJug2 == jug2 && tampungJug2 != target){
                        tampungJug2 = 0;
                        listAwal.add(new WaterJugAction(WaterJugActionEnum.EMPTY, 2));

                        if (tampungJug1 != 0) {
                            tampungJug2 += tampungJug1;
                            minimumFromFront += 1;
                            listAwal.add(new WaterJugAction(WaterJugActionEnum.POUR, 2));
                        }

                        tampungJug1 = 0;
                    }

                } else {

                    int minus = jug2 - tampungJug2;

                    if (tampungJug2 == jug2 && tampungJug2 != target){
                        tampungJug2 = 0;
                        listAwal.add(new WaterJugAction(WaterJugActionEnum.EMPTY, 2));

                        if (tampungJug1 != 0) {
                            tampungJug2 += tampungJug1;
                            minimumFromFront += 1;
                            listAwal.add(new WaterJugAction(WaterJugActionEnum.POUR, 2));
                        }

                        tampungJug1 = 0;
                    }

                    if (tampungJug1 <= minus){
                        tampungJug2 += tampungJug1;
                        minimumFromFront += 1;
                        tampungJug1 = 0;
                        listAwal.add(new WaterJugAction(WaterJugActionEnum.POUR, 2));
                    }else {
                        int jmlhKekurangan = tampungJug1 - minus;
                        tampungJug2 += jmlhKekurangan;
                        tampungJug1 -= minus;
                        minimumFromFront += 1;
                        listAwal.add(new WaterJugAction(WaterJugActionEnum.POUR, 2));
                    }

                }
            }
        }

        tampungJug1 = 0;
        tampungJug2 = 0;

//        while (tampungJug2 != target){
//            if (tampungJug2 == 0){
//                tampungJug2 = jug2;
//                listAckhir.add(new WaterJugAction(WaterJugActionEnum.FILL, 2));
//            }
//
//            int minus = tampungJug2 - target;
//
//            if (minus <= jug1){
//
//                if (tampungJug1 == 0) {
//
//                    tampungJug1 += minus;
//                    tampungJug2 -= minus;
//                    minimumFromEnd += 1;
//                    listAckhir.add(new WaterJugAction(WaterJugActionEnum.POUR, 1));
//
//                    if (tampungJug1 == jug1 && tampungJug2 != target){
//                        tampungJug1 = 0;
//                        listAckhir.add(new WaterJugAction(WaterJugActionEnum.EMPTY, 1));
//
//                        if (tampungJug2 <= jug1 && tampungJug2 != 0){
//
//                            tampungJug1 += tampungJug2;
//                            minimumFromEnd += 1;
//                            tampungJug2 = 0;
//                            listAckhir.add(new WaterJugAction(WaterJugActionEnum.POUR, 1));
//                            listAckhir.add(new WaterJugAction(WaterJugActionEnum.EMPTY, 2));
//                        }
//                    }
//
//                }else {
//                    int jmlhKekuranganJug1 = jug1 - tampungJug1;
//
//                    tampungJug1 += jmlhKekuranganJug1;
//                    tampungJug2 -= jmlhKekuranganJug1;
//                    listAckhir.add(new WaterJugAction(WaterJugActionEnum.POUR, 1));
//
//                    if (tampungJug1 == jug1 && tampungJug2 != target){
//                        tampungJug1 = 0;
//                        listAckhir.add(new WaterJugAction(WaterJugActionEnum.EMPTY, 1));
//
//                        if (tampungJug2 <= jug1 && tampungJug2 != 0){
//
//                            tampungJug1 += tampungJug2;
//                            minimumFromEnd += 1;
//                            tampungJug2 = 0;
//                            listAckhir.add(new WaterJugAction(WaterJugActionEnum.POUR, 1));
//                            listAckhir.add(new WaterJugAction(WaterJugActionEnum.EMPTY, 2));
//                        }
//                    }
//                }
//
//            }else {
//
//                int jmlhKekuranganJug1 = minus - jug1;
//
//                tampungJug1 += jmlhKekuranganJug1;
//                tampungJug2 -= jmlhKekuranganJug1;
//                listAckhir.add(new WaterJugAction(WaterJugActionEnum.POUR, 1));
//
//                if (tampungJug1 == jug1 && tampungJug2 != target){
//                    tampungJug1 = 0;
//                    listAckhir.add(new WaterJugAction(WaterJugActionEnum.EMPTY, 1));
//
//                    if (tampungJug2 <= jug1 && tampungJug2 != 0){
//
//                        tampungJug1 += tampungJug2;
//                        minimumFromEnd += 1;
//                        tampungJug2 = 0;
//                        listAckhir.add(new WaterJugAction(WaterJugActionEnum.POUR, 1));
//                        listAckhir.add(new WaterJugAction(WaterJugActionEnum.EMPTY, 2));
//                    }
//                }
//            }
//        }


//        if (minimumFromEnd < minimumFromFront){
//            return listAckhir;
//        }else {
//            return listAwal;
//        }

        return listAwal;
    }
}
