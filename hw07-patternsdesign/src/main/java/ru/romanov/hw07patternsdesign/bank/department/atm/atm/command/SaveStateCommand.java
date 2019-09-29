package ru.romanov.hw07patternsdesign.bank.department.atm.atm.command;

import ru.romanov.hw07patternsdesign.bank.department.atm.atm.ATM;

public class SaveStateCommand implements Command {

    private ATM atm;

    public SaveStateCommand(ATM atm) {
        this.atm = atm;
    }

    @Override
    public void execute() {
        atm.saveState();
    }
}
