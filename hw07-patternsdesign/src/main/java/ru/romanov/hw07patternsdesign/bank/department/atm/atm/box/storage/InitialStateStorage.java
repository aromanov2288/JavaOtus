package ru.romanov.hw07patternsdesign.bank.department.atm.atm.box.storage;

import ru.romanov.hw07patternsdesign.bank.common.Banknote;
import ru.romanov.hw07patternsdesign.bank.department.atm.atm.box.SimpleBox;

import java.util.Map;
import java.util.stream.Collectors;

public class InitialStateStorage implements StateStorage {

    private Map<Banknote, SimpleBox> state;

    @Override
    public Map<Banknote, SimpleBox> getState() {
        return copy(state);
    }

    @Override
    public void setState(Map<Banknote, SimpleBox> state) {
        this.state = copy(state);
    }

    private Map<Banknote, SimpleBox> copy(Map<Banknote, SimpleBox> src) {
        if (src != null) {
            return src.keySet().stream()
                    .collect(Collectors.toMap(banknote -> banknote, banknote -> new SimpleBox(src.get(banknote))));
        } else {
            return null;
        }
    }
}
