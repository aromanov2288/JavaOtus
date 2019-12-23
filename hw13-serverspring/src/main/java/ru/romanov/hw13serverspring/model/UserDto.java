package ru.romanov.hw13serverspring.model;

public class UserDto {

    private String name;
    private Integer age;

    public UserDto() {
    }

    public UserDto(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
