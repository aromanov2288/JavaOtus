package ru.romanov.hw01maven;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class HelloOtus {
    public static void main(String[] args) {
        Multimap<String, String> multimap = ArrayListMultimap.create();
        multimap.put("Группа 1", "Петров");
        multimap.put("Группа 2", "Иванов");
        multimap.put("Группа 1", "Сидоров");

        System.out.println(multimap);
    }
}
