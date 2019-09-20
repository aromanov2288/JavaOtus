package ru.romanov.hw06design;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class SimpleATM implements ATM {

    private Map<Banknote, SimpleBox<Banknote>> storageMap = new HashMap<>();

    private Map<Banknote, SimpleBox<Banknote>> transitoryMap = new HashMap<>();

    public SimpleATM() {
        Arrays.stream(Banknote.values()).forEach(banknote -> {
            storageMap.put(banknote, new SimpleBox<>(banknote));
            transitoryMap.put(banknote, new SimpleBox<>(banknote));
        });
    }

    @Override
    public Optional<Integer> putMoney(Map<Banknote, Integer> banknotePack) {
        return banknotePack.keySet().stream()
                .map(banknote -> storageMap.get(banknote).putBanknotes(banknotePack.get(banknote)))
                .reduce(Integer::sum);
    }

    @Override
    public Map<Banknote, Integer> getMoney(Integer amount) {
        return fillTransitoryMap(amount).keySet().stream()
                .filter(banknote -> transitoryMap.get(banknote).getBalance() != 0)
                .collect(Collectors.toMap(banknote -> banknote, banknote -> transitoryMap.get(banknote).getAllBanknotes()));
    }

    @Override
    public Optional<Integer> getBalance() {
        return storageMap.keySet().stream()
                .map(banknote -> storageMap.get(banknote).getBalance())
                .reduce(Integer::sum);
    }

    private Map<Banknote, SimpleBox<Banknote>> fillTransitoryMap(Integer amount) {
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
}
