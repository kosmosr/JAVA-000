package main.java;

/**
 * @author kosmosr
 * @date 2020/10/19 11:32
 * 题目1
 */
public class Hello {
    public static void main(String[] args) {
        int a = 1;
        int b = 2;
        int c = a + b;
        if (c == 3) {
            System.out.println("SUCCESS");
        }
        for (int i = 0; i < c; i++) {
            System.out.println(i);
        }
    }
}
