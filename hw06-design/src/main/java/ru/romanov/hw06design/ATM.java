package ru.romanov.hw06design;

import java.util.Map;
import java.util.Optional;

public interface ATM {

    Optional<Integer> putMoney(Map<Banknote, Integer> banknotePack);

    Map<Banknote, Integer> getMoney(Integer amount);

    Optional<Integer> getBalance();
}
