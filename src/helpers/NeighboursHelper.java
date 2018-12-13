package helpers;

import problem.Neighbour;

import java.util.Vector;

public class NeighboursHelper {

    public static Vector<Neighbour> getNeightbours(int[] rules){
        Vector<Neighbour> neightbours = new Vector<>();

        for(int g = 0; g < 4; g++){
            for(int c = 0; c < 4; c++){
                for(int d = 0; d < 4; d++){
                    if( !(g == 0 && c == 0 && d == 0) && !(g == 1 && c == 1 && d == 1) ) {
                        for (int i = 0; i <= 3; i++) {
                            if (rules[g * 36 + c * 6 + d] != i) {
                                neightbours.add(new Neighbour(i, g * 36 + c * 6 + d));
                            }
                        }
                    }
                }
            }
        }

        for(int c = 0; c < 4; c++){
            for(int d = 0; d < 4; d++){
                if( !(c == 0 && d == 0) && !(c == 1 && d == 1) && !(c == 1 && d == 0) )
                    for(int i = 0; i <=3; i++) {
                        if(rules[5*36 + c*6 + d] != i) {
                            neightbours.add(new Neighbour(i, 5*36 + c*6 + d));
                        }
                    }
            }
        }


        for(int g = 0; g < 4; g++){
            for(int c = 0; c < 4; c++){
                if( !(g == 0 && c == 0) && !(g == 1 && c == 1) && !(g == 1 && c == 0) )
                    for(int i = 0; i <=3; i++) {
                        if(rules[5*36 + c*6 + 5] != i) {
                            neightbours.add(new Neighbour(i, 5*36 + c*6 + 5));
                        }
                    }
            }
        }

        return neightbours;
    }
}
