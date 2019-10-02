package ru.romanov.hw07patternsdesign.bank.department.fin;

import ru.romanov.hw07patternsdesign.bank.department.atm.SimpleATMDepartment;

public class SberFINDepartment extends AbstractFINDepartment {

    @Override
    public String bankName() {
        return "Сбербанк";
    }

    @Override
    public DepartmentAdapter createDepartmentAdapter() {
        return new DepartmentAdapter(new SimpleATMDepartment());
    }
}
