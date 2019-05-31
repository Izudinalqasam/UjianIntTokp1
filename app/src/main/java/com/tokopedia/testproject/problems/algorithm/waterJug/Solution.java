package com.tokopedia.testproject.problems.algorithm.waterJug;

public class Solution {

    public static int minimalPourWaterJug(int jug1, int jug2, int target) {
        // TODO, return the smallest number of POUR action to do the water jug problem
        // below is stub, replace with your implementation!

        int minimumFromFront = 0;
        int minimumFromEnd =0;

        int tampungJug2 =0;
        int tampungJug1 =0;

        while (tampungJug2 != target){

            if (tampungJug2 == 0 && tampungJug1 == 0) {
                tampungJug1 = jug1;

                if (tampungJug1 <= target || tampungJug1 < jug2){
                    tampungJug2 = tampungJug1;
                    minimumFromFront +=1;
                }

            }else {

                if (tampungJug1 == 0) {
                    tampungJug1 += jug1;

                    int minus = jug2 - tampungJug2;

                    tampungJug2 += minus;
                    tampungJug1 -= minus;
                    minimumFromFront +=1;

                    if (tampungJug2 == jug2 && tampungJug2 != target){
                        tampungJug2 = 0;

                        if (tampungJug1 != 0) {
                            tampungJug2 += tampungJug1;
                            minimumFromFront += 1;
                        }

                        tampungJug1 = 0;
                    }

                } else {

                    int minus = jug2 - tampungJug2;

                    if (tampungJug2 == jug2 && tampungJug2 != target){
                        tampungJug2 = 0;

                        if (tampungJug1 != 0) {
                            tampungJug2 += tampungJug1;
                            minimumFromFront += 1;
                        }

                        tampungJug1 = 0;
                    }

                    if (tampungJug1 <= minus){
                        tampungJug2 += tampungJug1;
                        minimumFromFront += 1;
                        tampungJug1 = 0;
                    }else {
                        int jmlhKekurangan = tampungJug1 - minus;
                        tampungJug2 += jmlhKekurangan;
                        tampungJug1 -= minus;
                        minimumFromFront += 1;
                    }

                }
            }
        }

        tampungJug1 = 0;
        tampungJug2 = 0;

        while (tampungJug2 != target){
            if (tampungJug2 == 0){
                tampungJug2 = jug2;
            }

            int minus = tampungJug2 - target;

            if (minus <= jug1){

                if (tampungJug1 == 0) {

                    tampungJug1 += minus;
                    tampungJug2 -= minus;
                    minimumFromEnd += 1;

                    if (tampungJug1 == jug1 && tampungJug2 != target){
                        tampungJug1 = 0;

                        if (tampungJug2 <= jug1 && tampungJug2 != 0){

                            tampungJug1 += tampungJug2;
                            minimumFromEnd += 1;
                            tampungJug2 = 0;

                        }
                    }

                }else {
                    int jmlhKekuranganJug1 = jug1 - tampungJug1;

                    tampungJug1 += jmlhKekuranganJug1;
                    tampungJug2 -= jmlhKekuranganJug1;

                    if (tampungJug1 == jug1 && tampungJug2 != target){
                        tampungJug1 = 0;

                        if (tampungJug2 <= jug1 && tampungJug2 != 0){

                            tampungJug1 += tampungJug2;
                            minimumFromEnd += 1;
                            tampungJug2 = 0;

                        }
                    }
                }

            }else {

                int jmlhKekuranganJug1 = minus - jug1;

                tampungJug1 += jmlhKekuranganJug1;
                tampungJug2 -= jmlhKekuranganJug1;

                if (tampungJug1 == jug1 && tampungJug2 != target){
                    tampungJug1 = 0;

                    if (tampungJug2 <= jug1 && tampungJug2 != 0){

                        tampungJug1 += tampungJug2;
                        minimumFromEnd += 1;
                        tampungJug2 = 0;

                    }
                }
            }
        }


        if (minimumFromEnd < minimumFromFront){
            return minimumFromEnd;
        }else {
            return minimumFromFront;
        }
    }
}
