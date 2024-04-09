package org.example.spring.beans.postprocessing;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.example.spring.beans.BeanScopesExample;
import org.example.spring.beans.MyLogger;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@SpringBootApplication
public class BeanPostProcessorExample {

    @Service
    public static class UserService {
        @MyLogger
        private CustromLogger custromLogger;

        @PostConstruct
        void init() {
            custromLogger.log("2-nd phase constructor, logger test");
        }
    }

    @Service
    public static class OrderService {
        @MyLogger
        private CustromLogger custromLogger;

        @PostConstruct
        void init() {
            custromLogger.log("2-nd phase constructor, logger test");
        }
    }

    @AllArgsConstructor
    public static class CustromLogger {
        private Class<?> clazz;

        void log(String message) {
            System.out.println(clazz + ": " + message);
        }
    }

    @Component
    public static class MyLoggerAnnotationBeanPostProcessor implements BeanPostProcessor {

        @Override
        public Object postProcessBeforeInitialization(Object bean, String beanName) {

            var fields = bean.getClass().getDeclaredFields();
            for(var field : fields) {
                var annotation = field.getAnnotation(MyLogger.class);
                if(annotation != null) {
                    try {
                        field.setAccessible(true); // make private to public
                        field.set(bean, new CustromLogger(bean.getClass()));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }


            return bean;
        }
    }

//    @Component
//    public static class BeanConfigurator {
//        private List<BeanPostProcessor> processorList;
//
//
//        void configureBean(Map<String, Object> beans) {
//            for(var bean : beans.entrySet()) {
//                for(var processonr: processorList) {
//                    bean = processonr.postProcessBeforeInitialization(bean.getKey(), bean.getValue());
//
//                    // ... initializing
//
//                    bean = processonr.postProcessAfterInitialization(bean.getKey(), bean.getValue());
//
//                    beans.put(bean.getKey(), bean);
//                }
//            }
//        }
//    }

    public static void main(String[] args) {
        SpringApplication.run(BeanScopesExample.class, args);
    }
}

