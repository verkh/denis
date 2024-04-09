package org.example;

// JMM - Java Memory Model
public class Volatile {

    private int names = 0;

    // FIXME: Volitile не спасает от гонки потоков, не подходит для синхронизации
    private volatile int counter = 0;

    public void reordering() {
        names = 1;          // non-volatile
        int x = names;      // non-volatile
        int y = 10;         // non-volatile

        // after reodering: real execution
        // int y = 10;
        // int x = names;
        // names = 1;
    }

    public void reordering2() {
        names = 1;          // non-volatile
        int x = names;      // non-volatile
        int y = 10;         // non-volatile

        counter = names;    // volatile

        int z = 9;          // non-volatile
        int v = names;      // non-volatile

        // after reodering: real execution
        // int y = 10;
        // int x = names;
        // names = 1;

        // counter = names; // keeps its place

        // int v = names;
        // int z = 9;
    }


    private static int number;
    private static boolean ready;

    private static class Reader extends Thread {

        @Override
        public void run() {
            while (!ready) {
                Thread.yield();
            }

            System.out.println(number);
        }
    }

    static int getSomething() { //
        return 10 + 15;
    }

    public static void main(String[] args) {
        new Reader().start();
        number = 42;
        ready = true;

        for(int i =0; i< 1000; i++) {
            int value = getSomething();
            // after optimization:
            // int value = 25;
        }
    }
}


/*
    counter


    [ CORE 1 ]
    | Cache  |
    | counter|


    [ CORE 2 ]
    | Cache  |
    | counter|

 */

/*
 |--------|--------|---------|-------
 T2       T1       -         T2
 read    write           read (cache)

 */