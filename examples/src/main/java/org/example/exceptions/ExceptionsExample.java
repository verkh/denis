package org.example.exceptions;

import java.io.IOException;

public class ExceptionsExample {
/*
                    Object
                      |
                   Throwable
                  /         \
               Error     Exceptions  -> Unchecked (All RuntimeExceptions)
               (JVM)         |
                           Checked
                              |
                IOException, ArithmeticException,
                 IllegalAccessException etc
 */

    void throwCheckedException() throws IOException {
        throw new IOException();
    }

    void callThrowCheckedException() {
        try {
            throwCheckedException();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void throwUncheckedException()  {
        throw new RuntimeException();
    }

    void callThrowUncheckedException() {
        throwUncheckedException(); // обработка опциональна
    }

    // !!!!! WARNING: Никогда не используй стандартные исключения, создавай свои!!!!

    void getDomainException() {
        try {
            callSomeFunction("");
        } catch (NameIsAbsentException ex) {
            // processing
        } catch (InterruptedException ex) {
            // processing
        }
    }

    void getDomainException2BadPractice() {
        try {
            callSomeFunction("");
        } catch (Exception ex) {
            // processing: not really good
            // Допустипо, но лучше задать вопрос к бизнес логике, почему все исключения обрабытваются одинаково
        }
    }

    private void callSomeFunction(String name) throws InterruptedException {
        if(name == null || name.isEmpty()) {
            throw new NameIsAbsentException();
        }
        Thread.sleep(1000);
    }
}

// authentication -> unauthenticated=401
// authorization  -> unauthorized=403/Forbidden
