package com.example;

public class Demo {


    public static void main(String[] args) {
        int array[] = {4, 7, 6, 9, 1, 5, 8, 2, 0, 3};
        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[i] > array[j]) {
                    int temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                } else {
                    continue;
                }
            }

            System.out.print(array[i] + "、");
        }
    }

    public void synDemo() {
        synchronized (this) {

        }

        synchronized (new Demo()) {

        }
    }
}
