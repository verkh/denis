package org.example.spring.beans;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@Component
class AnotherComponent {
    public void hello() {
        System.out.println("Hello");
    }
}

@Component
@SpringBootApplication
public class SpringBean {

    @Autowired
    private AnotherComponent anotherComponent;

//    public void setAnotherComponent(@Autowired AnotherComponent anotherComponent) {
//        this.anotherComponent = anotherComponent;
//    }

//    public SpringBean(AnotherComponent anotherComponent) { // VALID
//        this.anotherComponent = anotherComponent;
//        anotherComponent.hello();
//    }

//    public SpringBean() {
//        anotherComponent.hello(); // INVALID: Null Pointer Exception
//    }

    public SpringBean() {
        System.out.println("1-st phase constructor");
    }

    @PostConstruct
    void initMySpringBean() {
        System.out.println("2-nd phase constructor");
        anotherComponent.hello();
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBean.class, args);
    }
}

/*
    @RestController и @Controller - спринг понимает, что это именно котроллеры, которые умеют работать с запросами.
                                    поэтому если поменить другой аннотацией, то например для веб запроса, спринг вернет 404
                                    потому что он не знает ни одного класса, который бы предоставлял такой ендпоинт

    @Component                    - воспринимается как просто обычный бин, который менеджится контейнером
    @Service                      - обрабытывается так же как и @Component. Разница только в названии (пока что)
    @Repository                   - предназначен в т.ч. для обработки SQLException, которые чекед и будут обернуты в RuntimeException
 */

/*
    Как работает конструктор
    - идет выделение памяти: (8 байт одно поле, 4 байта другое поле, 16 байт метаинформация о классе....)
      [][][][][][][0][0][0][0][0][0]
    - записывает исходную информацию о классе в этот массив байт
    - отрабатывает код коструктора
    - возвращается созданные объект (ссылка на него)
 */

@Component
class SettInjectionClass {
    private AnotherComponent anotherComponent;
    private SpringBean springBean;

    public SettInjectionClass() {

    }

    public void setAnotherComponent(@Autowired AnotherComponent anotherComponent) {
        this.anotherComponent = anotherComponent;
    }

    public void setSpringBean(@Autowired SpringBean springBean) {
        this.springBean = springBean;
    }

    public void process() {
        if(springBean != null && anotherComponent != null) {
            springBean.initMySpringBean();
            anotherComponent.hello();
            //...
        }
        else if(springBean != null) {
            springBean.initMySpringBean();
        } else if (anotherComponent != null) {
            anotherComponent.hello();
        }
    }

}

class Processor {
    void doMyStuff() {
        var processor = new SettInjectionClass();
        processor.process();
    }
}


/**
 *  Внедрение зависимостей:
 *
 *  - Через Поля
 *    Важно понмить, что внедрение произойдет уже после конструктора
 *    Плюс:
 *    1) легко написать
 *    Минусы:
 *    1) неявные зависимости.
 *    2) невозможность управлением зависимостями
 *
 *
 *  - Через сеттер
 *    Важно понмить, что внедрение произойдет уже после конструктора
 *    Минусы:
 *    1) Неявные зависимости
 *    Плюсы:
 *    1) возможность управления зависимостями
 *
 *  - Через конструктора
 *    Минусы:
 *    1) невозможность управления зависимости после иниициализации
 *    Плюсы:
 *    1) Явные зависимости
 *   Предпочтительнее потому, что в большинстве случаев зависимости не меняются
 *
 *  Самый предпочтительный вариант - конструктор или конструктор + сеттер
 *  -
 */