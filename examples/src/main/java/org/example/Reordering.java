package org.example;

public class Reordering {
    private static int x = 0, y = 0;
    private static boolean flag = false;

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            synchronized (Reordering.class) {
                x = 1;
                flag = true;
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (Reordering.class) {
                if (flag) {
                    y = x;
                }
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("y: " + y);
    }
}
