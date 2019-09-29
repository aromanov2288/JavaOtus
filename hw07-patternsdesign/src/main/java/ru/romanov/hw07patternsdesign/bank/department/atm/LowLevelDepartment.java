package ru.romanov.hw07patternsdesign.bank.department.atm;

import ru.romanov.hw07patternsdesign.bank.common.SelfStorable;
import ru.romanov.hw07patternsdesign.bank.department.atm.atm.ATM;

import java.util.List;

public interface LowLevelDepartment extends SelfStorable, BalanceIReportable {

    List<ATM> getAtmList();
}
