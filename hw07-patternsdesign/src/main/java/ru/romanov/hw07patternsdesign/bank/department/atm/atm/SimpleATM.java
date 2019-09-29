package ru.romanov.hw07patternsdesign.bank.department.atm.atm;

import ru.romanov.hw07patternsdesign.bank.common.Banknote;
import ru.romanov.hw07patternsdesign.bank.department.atm.atm.box.SimpleBox;
import ru.romanov.hw07patternsdesign.bank.department.atm.ChangeBalanceListener;
import ru.romanov.hw07patternsdesign.bank.department.atm.atm.box.storage.InitialStateStorage;
import ru.romanov.hw07patternsdesign.bank.department.atm.atm.box.storage.StateStorage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class SimpleATM implements ATM {

    private StateStorage stateStorage = new InitialStateStorage();

    private Map<Banknote, SimpleBox> storageMap = new HashMap<>();

    private Map<Banknote, SimpleBox> transitoryMap = new HashMap<>();

    private List<ChangeBalanceListener> changeBalanceListenerList = new ArrayList<>();

    public SimpleATM() {
        Arrays.stream(Banknote.values()).forEach(banknote -> {
            storageMap.put(banknote, new SimpleBox(banknote, true));
            transitoryMap.put(banknote, new SimpleBox(banknote, false));
        });
    }

    @Override
    public Integer putMoney(Map<Banknote, Integer> banknotePack) {
        Integer banknotePackSum = banknotePack.keySet().stream()
                .map(banknote -> storageMap.get(banknote).putBanknotes(banknotePack.get(banknote)))
                .reduce(0, Integer::sum);
        signal(banknotePackSum);
        return banknotePackSum;
    }

    @Override
    public Map<Banknote, Integer> getMoney(Integer amount) {
        Map<Banknote, Integer> banknotePack = fillTransitoryMap(amount).keySet().stream()
                .filter(banknote -> transitoryMap.get(banknote).getBalance() != 0)
                .collect(Collectors.toMap(banknote -> banknote, banknote -> transitoryMap.get(banknote).getAllBanknotes()));
        if (!banknotePack.isEmpty()) {
            signal(-amount);
        }
        return banknotePack;
    }

    @Override
    public Integer getBalance() {
        return storageMap.keySet().stream()
                .map(banknote -> storageMap.get(banknote).getBalance())
                .reduce(0,Integer::sum);
    }

    @Override
    public void saveState() {
        stateStorage.setState(storageMap);
    }

    @Override
    public void restoreState() {
        storageMap = stateStorage.getState();
    }

    private Map<Banknote, SimpleBox> fillTransitoryMap(Integer amount) {
        storageMap.keySet().stream()
                .filter(banknote -> storageMap.get(banknote).getBalance() != 0)
                .filter(banknote -> (amount / banknote.getNominal() > 0))
                .max(Comparator.comparingInt(Banknote::getNominal))
                .ifPresentOrElse(banknote -> {
                            storageMap.get(banknote).getBanknote();
                            transitoryMap.get(banknote).putBanknote();
                            int newAmount = amount - banknote.getNominal();
                            if (newAmount != 0) {
                                fillTransitoryMap(newAmount);
                            }
                        }, () -> transitoryMap.keySet().stream()
                                .filter(banknote -> transitoryMap.get(banknote).getBalance() != 0)
                                .forEach(banknote -> storageMap.get(banknote).putBanknotes(transitoryMap.get(banknote).getAllBanknotes()))
                );
        return transitoryMap;
    }

    @Override
    public void subscribe(ChangeBalanceListener changeBalanceListener) {
        changeBalanceListenerList.add(changeBalanceListener);
    }

    @Override
    public void signal(Integer delta) {
        changeBalanceListenerList.forEach(changeBalanceListener -> changeBalanceListener.changeBalance(delta));
    }
}
