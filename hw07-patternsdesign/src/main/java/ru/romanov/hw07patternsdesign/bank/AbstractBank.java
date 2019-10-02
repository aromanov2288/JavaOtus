package ru.romanov.hw07patternsdesign.bank;

import ru.romanov.hw07patternsdesign.bank.department.atm.atm.ATM;
import ru.romanov.hw07patternsdesign.bank.department.fin.AbstractFINDepartment;
import ru.romanov.hw07patternsdesign.bank.department.fin.HighLevelDepartment;

import java.util.List;

public abstract class AbstractBank implements HighLevelDepartment {

    private AbstractFINDepartment finDepartment;

    public AbstractBank() {
        finDepartment = createFINDepartment();
    }

    public abstract AbstractFINDepartment createFINDepartment();

    @Override
    public void saveState() {
        finDepartment.saveState();
    }

    @Override
    public void restoreState() {
        finDepartment.restoreState();
    }

    @Override
    public String getBalance() {
        return finDepartment.getBalance();
    }

    @Override
    public List<ATM> getAtmList() {
        return finDepartment.getAtmList();
    }
}
