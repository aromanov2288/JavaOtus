package ru.romanov.hw07patternsdesign.bank.department.atm.atm;

import ru.romanov.hw07patternsdesign.bank.common.Banknote;
import ru.romanov.hw07patternsdesign.bank.common.SelfStorable;

import java.util.Map;

public interface ATM extends SelfStorable, BalanceOReportable, ChangeBalancePublisher {

    Integer putMoney(Map<Banknote, Integer> banknotePack);

    Map<Banknote, Integer> getMoney(Integer amount);
}
