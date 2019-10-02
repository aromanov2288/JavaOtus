package ru.romanov.hw07patternsdesign.bank.department.atm.atm.box;

public interface Box {

    Integer putBanknote();

    Integer putBanknotes(Integer count);

    Integer getBanknote();

    Integer getAllBanknotes();

    Integer getBalance();

}
