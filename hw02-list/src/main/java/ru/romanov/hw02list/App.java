package ru.romanov.hw02list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class App {
    public static void main(String[] args) {
        List<Person> firstList = new DIYArrayList<>();

        Collections.addAll(firstList, new Person("Peta", 22), new Person("Nikita", 18));
        Collections.addAll(firstList, new Person("Sasha", 25), new Person("Anton", 20));
        Collections.addAll(firstList, new Person("Zhenya", 28), new Person("Filipp", 30));
        Collections.addAll(firstList, new Person("Maxim", 29), new Person("Lenya", 21));
        Collections.addAll(firstList, new Person("Vera", 18), new Person("Stepa", 26));
        Collections.addAll(firstList, new Person("Ira", 29), new Person("Kira", 25));
        Collections.addAll(firstList, new Person("Kolya", 19), new Person("Nastya", 17));
        Collections.addAll(firstList, new Person("Semen", 25), new Person("Natasha", 21));
        Collections.addAll(firstList, new Person("Viktor", 25), new Person("Aleksey", 21));
        Collections.addAll(firstList, new Person("Roman", 32), new Person("Volodya", 23));
        Collections.addAll(firstList, new Person("Vitya", 22), new Person("Masha", 28));

        List<Person> secondList = new ArrayList<>(Arrays.asList(new Person[firstList.size()]));
        Collections.copy(secondList, firstList);

        Collections.sort(secondList, Comparator.comparingInt(Person::getAge));

        for(Person s : secondList) {
            System.out.println(s);
        }
    }
}
