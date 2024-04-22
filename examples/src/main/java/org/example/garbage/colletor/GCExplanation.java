package org.example.garbage.colletor;

public class GCExplanation {

    long refCounter = 2;

    void myFunction( ) {
       Object data = new Object();
       Object data2 = data;
       //.. do something here

        System.gc(); // вот так можно вызывать сборку мусора, но не рекомендовано так делать
    }

    /*
        - new object -> JVM -> [memory space]

        когда мы создаем новый объект, он не просто хранит информацию,
        которые мы поместили, но так же хранит кучу разной дополнительной информации.

        Reference Counter - счетчик ссылок

     */
}
