package ru.romanov.hw07patternsdesign;

import ru.romanov.hw07patternsdesign.bank.AbstractBank;
import ru.romanov.hw07patternsdesign.bank.SberBank;
import ru.romanov.hw07patternsdesign.bank.common.Banknote;
import ru.romanov.hw07patternsdesign.bank.department.atm.atm.ATM;

import java.util.HashMap;
import java.util.Map;

public class App {

    public static void main(String[] args) {
        AbstractBank sberBank = new SberBank();
        ATM atm = sberBank.getAtmList().get(0);
        System.out.println(sberBank.getBalance());
        Map<Banknote, Integer> putMap = new HashMap<>();
        putMap.put(Banknote.TYPE_10, 1);
        atm.putMoney(putMap);
        System.out.println(sberBank.getBalance());
        sberBank.restoreState();
        System.out.println(sberBank.getBalance());
    }
}
