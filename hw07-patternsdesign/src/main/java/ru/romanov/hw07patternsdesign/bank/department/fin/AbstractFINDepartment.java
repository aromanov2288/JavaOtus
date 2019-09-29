package ru.romanov.hw07patternsdesign.bank.department.fin;

import ru.romanov.hw07patternsdesign.bank.department.atm.atm.ATM;

import java.util.List;

public abstract class AbstractFINDepartment implements HighLevelDepartment {

    private DepartmentAdapter departmentAdapter = createDepartmentAdapter();

    public abstract String bankName();

    public abstract DepartmentAdapter createDepartmentAdapter();

    @Override
    public void saveState() {
        departmentAdapter.saveState();
    }

    @Override
    public void restoreState() {
        departmentAdapter.restoreState();
    }

    @Override
    public String getBalance() {
        return bankName() + " " + departmentAdapter.getBalance();
    }

    @Override
    public List<ATM> getAtmList() {
        return departmentAdapter.getAtmList();
    }
}
