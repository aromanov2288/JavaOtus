package ru.romanov.hw07patternsdesign.bank.department.atm;

import ru.romanov.hw07patternsdesign.bank.department.atm.atm.ATM;
import ru.romanov.hw07patternsdesign.bank.department.atm.atm.BalanceOReportable;
import ru.romanov.hw07patternsdesign.bank.department.atm.atm.command.RestoreStateCommand;
import ru.romanov.hw07patternsdesign.bank.department.atm.atm.command.SaveStateCommand;
import ru.romanov.hw07patternsdesign.bank.department.atm.atm.SimpleATM;

import java.util.ArrayList;
import java.util.List;

public class SimpleATMDepartment implements LowLevelDepartment, ChangeBalanceListener {

    private List<ATM> atmList = new ArrayList<>();

    private Integer totalBalance = 0;

    public SimpleATMDepartment() {
        for (int i = 0; i < 1; i++) {
            SimpleATM simpleATM = new SimpleATM();
            simpleATM.subscribe(this);
            atmList.add(simpleATM);
            totalBalance += simpleATM.getBalance();
        }
        saveState();
    }

    @Override
    public void saveState() {
        atmList.forEach(atm -> new SaveStateCommand(atm).execute());
    }

    @Override
    public void restoreState() {
        atmList.forEach(atm -> new RestoreStateCommand(atm).execute());
        totalBalance = atmList.stream()
                .map(BalanceOReportable::getBalance)
                .reduce(0, Integer::sum);
    }

    @Override
    public Integer getBalance() {
        return totalBalance;
    }

    @Override
    public void changeBalance(Integer delta) {
        totalBalance += delta;
    }

    @Override
    public List<ATM> getAtmList() {
        return atmList;
    }
}
