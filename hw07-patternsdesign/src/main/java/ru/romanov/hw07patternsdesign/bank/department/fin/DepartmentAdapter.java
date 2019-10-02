package ru.romanov.hw07patternsdesign.bank.department.fin;

import ru.romanov.hw07patternsdesign.bank.department.atm.LowLevelDepartment;
import ru.romanov.hw07patternsdesign.bank.department.atm.atm.ATM;

import java.util.List;

public class DepartmentAdapter implements HighLevelDepartment {

    private LowLevelDepartment lowLevelDepartment;

    public DepartmentAdapter(LowLevelDepartment lowLevelDepartment) {
        this.lowLevelDepartment = lowLevelDepartment;
    }

    @Override
    public void saveState() {
        lowLevelDepartment.saveState();
    }

    @Override
    public void restoreState() {
        lowLevelDepartment.restoreState();
    }

    @Override
    public String getBalance() {
        return lowLevelDepartment.getBalance().toString();
    }

    @Override
    public List<ATM> getAtmList() {
        return lowLevelDepartment.getAtmList();
    }
}
