package com.cotiviti.springapp.utility;

public class DebugTest {

    public static void main(String[] args) {
        System.out.println("*************Before breakpoint**************");
        int a = 10, b = 100;
        System.out.println("a is: "+a +" b is: "+b);
        int total = sum(a, b);
        System.out.println("Total sum  is: "+total);
        System.out.println("After addition");
        System.out.println("Before multiplication");
        int mul = mul(a,b);
        System.out.println("Multiplication  is: "+mul);
    }

    public static int sum(int x, int y){
        return x+y;
    }

    public static int mul(int m, int n){
        return m*n;
    }


}
