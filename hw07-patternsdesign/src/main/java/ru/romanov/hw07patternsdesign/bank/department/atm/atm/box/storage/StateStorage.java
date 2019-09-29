package ru.romanov.hw07patternsdesign.bank.department.atm.atm.box.storage;

import ru.romanov.hw07patternsdesign.bank.common.Banknote;
import ru.romanov.hw07patternsdesign.bank.department.atm.atm.box.SimpleBox;

import java.util.Map;

public interface StateStorage {

    Map<Banknote, SimpleBox> getState();

    void setState(Map<Banknote, SimpleBox> state);
}
