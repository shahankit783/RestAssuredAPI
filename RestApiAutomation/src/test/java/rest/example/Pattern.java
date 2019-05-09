package rest.example;

import java.util.ArrayList;

public class Pattern {

    public static void main(String args[]){


        for(int i = 1;i<6;i++){

            ArrayList<Integer> numbers = new ArrayList<Integer>();
            numbers.add(i);
            for (int k = numbers.size()-1;k>=0;k--){
                for (int m=1;m< numbers.size();m++) {
                    System.out.println((numbers.get(k)-m));
                }
            }

        }
        }

    }

