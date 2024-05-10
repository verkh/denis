package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HitsFromTask {
    Map<String, List<String>> get() {
        return Collections.emptyMap();
    }

    void exampleUsingVar() {
        List<String> list2 = new ArrayList();

        var list1 = new ArrayList<String>();
        var map = get();
    }

    void stringFormating() {
        var str = 1 + " Hello " + ": " + 1000;

        var str2 = "1 Hello : %d".formatted(1000);

        var str3 = String.format("1 Hello : %d", 1000);

        var str4 = """
                 1 Hello : %d
                 1 Hello : %d
                 1 Hello : %d
                """.formatted(1, 2, 3);
    }

    @lombok.Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Data{
        private String name;
        private Integer value;
    }

    static void jsonParsing() throws JsonProcessingException {
        var data = new Data("Test", 10);
        var string = new ObjectMapper().writeValueAsString(data);

        var dataParsed = new ObjectMapper().readValue(string, Data.class);
        System.out.println(dataParsed);

        var map = new HashMap<String, List<Data>>();
        map.put("Data", List.of(data));

        var mapJson = new ObjectMapper().writeValueAsString(map);

        var parsedMap = new ObjectMapper().readValue(mapJson, new TypeReference<HashMap<String,List<Data>>>() {});
        System.out.println(parsedMap); 
    }

    public static int main(String[] args) {
        try {
            jsonParsing();

            System.out.println(args[0]);
            return 0;
        } catch (Throwable e) {
            System.out.printf("Unexpected error: %s%n", e.getMessage());
            return 10;
        }
    }
}
