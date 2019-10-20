package ru.romanov.hw09jdbcbasedorm.model;

import ru.romanov.hw09jdbcbasedorm.api.annotation.Entity;
import ru.romanov.hw09jdbcbasedorm.api.annotation.Id;

import java.util.Objects;

@Entity
public class Person {

    @Id
    private long id;

    private String name;

    private int age;

    private boolean isActive;

    public Person() {
    }

    public Person(String name, int age, boolean isActive) {
        this.name = name;
        this.age = age;
        this.isActive = isActive;
    }

    public Person(long id, String name, int age, boolean isActive) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", isActive=" + isActive +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Person person = (Person) object;
        return age == person.age &&
                isActive == person.isActive &&
                Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, isActive);
    }
}
