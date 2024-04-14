package org.example.spring.beans.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Arrays;


/*
    AOP Framework
    https://www.baeldung.com/aspectj
 */


/*
    Два варианта встраивания в систему спринга:
    1) Через наследование от определенного класс + @Component или другая аннотация (например BeanPostProcessor, или старый вариант настройки Security)
    2) Через аннотации (например аспекты)
 */

@Aspect
@Component
public class ProfilerAspect {

    @Around("@annotation(profiler)")
    public Object wrap(ProceedingJoinPoint pjp, Profiler profiler) throws Throwable {

        var args = Arrays.stream(pjp.getArgs()).map(o -> {
            System.out.println("Passed argument: " + o);
            if(o instanceof String) {
                return "Wrapped message: <%s>".formatted((String)o);
            }
            return o;
        }).toList();
        MethodSignature signature = (MethodSignature) pjp.getStaticPart().getSignature();
        for(int i = 0; i < signature.getParameterTypes().length; i++) {
            System.out.println("Argument Type: " + signature.getParameterTypes()[i]);
        }
        long start = System.currentTimeMillis();
        var result = pjp.proceed(args.toArray());
        long elapsed = System.currentTimeMillis() - start;
        System.out.printf("Execution of %s took %d ms%n", pjp.getSignature(), elapsed);
        return result;
    }
}

/*
     Написать аспект, который тоже профайлит функциюю на предмет времени ее исполнения.
     Выводит на консоль все аргументы вместе с типами, которые были переданы в профилируемую функциюю.
     И если какой-то аргумент аннотирван аннотацией @CriticalArgument, отдельно выводить на консоль его значение.

     @Profiler
     void doSomething(String data, int x, @CriticalArgument String name) {

     }

     Expected Log:
     - Profiled method: SomeClass::doSomething(), used argument: String data="Some value", int x="10", String name="NAME"
     - CRITICAL ARGUMENT: String name="NAME"
     - Execution of SomeClass::doSomething() took 1000ms
 */