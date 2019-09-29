package ru.romanov.hw07patternsdesign.bank;

import ru.romanov.hw07patternsdesign.bank.department.fin.AbstractFINDepartment;
import ru.romanov.hw07patternsdesign.bank.department.fin.SberFINDepartment;

public class SberBank extends AbstractBank {

    @Override
    public AbstractFINDepartment createFINDepartment() {
        return new SberFINDepartment();
    }
}
