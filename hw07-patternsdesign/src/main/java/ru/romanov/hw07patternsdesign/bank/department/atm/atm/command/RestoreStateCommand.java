package ru.romanov.hw07patternsdesign.bank.department.atm.atm.command;

import ru.romanov.hw07patternsdesign.bank.department.atm.atm.ATM;

public class RestoreStateCommand implements Command {

    private ATM atm;

    public RestoreStateCommand(ATM atm) {
        this.atm = atm;
    }

    @Override
    public void execute() {
        atm.restoreState();
    }
}
