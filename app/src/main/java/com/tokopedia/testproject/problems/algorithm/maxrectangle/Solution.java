package com.tokopedia.testproject.problems.algorithm.maxrectangle;


import java.util.ArrayList;

public class Solution {
    public static int maxRect(int[][] matrix) {
        // TODO, return the largest area containing 1's, given the 2D array of 0s and 1s
        // below is stub

        int totalSizeFinal =0;
        int total = 0;
        int posisiAwal = 0;

        int [][] position = new int[matrix.length][matrix.length];

        int loopI = 0;
        int loopJ = 0;

        for (int i=0; i < matrix.length; i ++){
            for (int j=0; j < matrix[i].length; j++) {
               if (matrix[i][j] == 1){
                   position[loopI][loopJ] = j;
               }
               loopJ++;
            }
            loopI++;
        }

        boolean isFindOut = false, isTwice = false;
        ArrayList<Integer> posMathc = new ArrayList<>();

        outerLoop:
        for (int i=0; i<position.length; ){
            posisiAwal = 0;
            total = 0;

            if (!isFindOut) {
                for (int j = 0; j < position[i].length; j++) {
                    if (j == 0) {
                        posisiAwal = position[i][j];
                        total += 1;
                        posMathc.add(posisiAwal);

                    } else if (position[i][j] == posisiAwal + 1) {
                        posisiAwal = position[i][j];
                        total += 1;
                        isFindOut = true;
                        posMathc.add(posisiAwal);

                    } else if (total < 2) {
                        posisiAwal = position[i][j];
                        total = 1;
                        posMathc.clear();
                        posMathc.add(posisiAwal);
                    }
                }

                if (posMathc.size() < 2){
                    posMathc.clear();
                }

                totalSizeFinal =posMathc.size();
                i++;

            }else {
                int loopArrayList = 0 ;
                for (int j = 0; j < position[i].length; j++) {
                    if (position[i][j] == posMathc.get(loopArrayList)){
                        isFindOut = true;
                        loopArrayList++;
                    }else {
                        isFindOut = false;
                        loopArrayList = 0;
                    }
                }


                if (isFindOut){
                    totalSizeFinal += posMathc.size();
                    isTwice = true;
                    i++;
                }else if (isTwice){
                    i++;
                    break outerLoop;
                }
            }


        }

        return isFindOut ? totalSizeFinal : 0;
    }
}
