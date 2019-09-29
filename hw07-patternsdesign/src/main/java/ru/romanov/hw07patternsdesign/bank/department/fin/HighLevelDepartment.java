package ru.romanov.hw07patternsdesign.bank.department.fin;

import ru.romanov.hw07patternsdesign.bank.common.SelfStorable;
import ru.romanov.hw07patternsdesign.bank.department.atm.atm.ATM;

import java.util.List;

public interface HighLevelDepartment extends SelfStorable, BalanceSReportable {

    List<ATM> getAtmList();
}
