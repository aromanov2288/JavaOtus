package ru.romanov.hw07patternsdesign.bank.department.atm.atm;

import ru.romanov.hw07patternsdesign.bank.department.atm.ChangeBalanceListener;

public interface ChangeBalancePublisher {

    void subscribe(ChangeBalanceListener changeBalanceListener);

    void signal(Integer delta);
}
