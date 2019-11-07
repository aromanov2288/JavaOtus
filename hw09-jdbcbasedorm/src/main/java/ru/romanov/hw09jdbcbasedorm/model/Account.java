package ru.romanov.hw09jdbcbasedorm.model;

import ru.romanov.hw09jdbcbasedorm.api.annotation.Entity;
import ru.romanov.hw09jdbcbasedorm.api.annotation.Id;

import java.util.Objects;

@Entity
public class Account {

    @Id
    private long id;

    private String type;

    private Float balance;

    private Boolean isActive;

    public Account() {
    }

    public Account(String type, Float balance, Boolean isActive) {
        this.type = type;
        this.balance = balance;
        this.isActive = isActive;
    }

    public Account(long id, String type, Float balance, Boolean isActive) {
        this.id = id;
        this.type = type;
        this.balance = balance;
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", balance=" + balance +
                ", isActive=" + isActive +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Account account = (Account) object;
        return Objects.equals(type, account.type) &&
                Objects.equals(balance, account.balance) &&
                Objects.equals(isActive, account.isActive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, balance, isActive);
    }
}
