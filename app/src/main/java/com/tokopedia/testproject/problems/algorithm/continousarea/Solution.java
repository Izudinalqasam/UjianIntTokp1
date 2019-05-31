package com.tokopedia.testproject.problems.algorithm.continousarea;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by hendry on 18/01/19.
 */
public class Solution {

    public static int maxContinuousArea(int[][] matrix) {
        // TODO, return the largest continuous area containing the same integer, given the 2D array with integers
        // below is

        HashMap<Integer, Integer> resultNumber = new HashMap<>();
        ArrayList<Integer> angkaTampung = new ArrayList<>();

        for (int p=0; p<matrix.length; p++){
            for (int y=0; y< matrix[p].length; y++){

                if (!angkaTampung.contains(matrix[p][y])){
                    angkaTampung.add(matrix[p][y]);

                    int outherIncr = 0;
                    for (int i=0; i < matrix.length; i ++){
                        int innerIncr = 1;

                        innerBreak:
                        for (int j=0; j< matrix[i].length; j++){

                            if (matrix[p][y] == matrix[i][j]){

                                if (((p+outherIncr) == i && y == j) || ((p+outherIncr) == i && (y+innerIncr) == j)){
                                    if (resultNumber.get(matrix[p][y]) != null){
                                        int tampungNumber = resultNumber.get(matrix[p][y]);
                                        tampungNumber += 1;
                                        resultNumber.put(matrix[p][y], tampungNumber);
                                    }else {
                                        resultNumber.put(matrix[p][y], 1);
                                    }

                                    innerIncr += 1;

                                }else {
                                    break innerBreak;
                                }
                            }

                        }

                        outherIncr += 1;
                    }
                }
            }
        }

        int theHighestResult =0;
        for (Integer i : resultNumber.keySet()){
            if (theHighestResult < resultNumber.get(i)){
                theHighestResult = resultNumber.get(i);
            }
        }

        return theHighestResult;
    }
}
