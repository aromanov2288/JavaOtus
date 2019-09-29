package ru.romanov.hw07patternsdesign.bank.department.atm.atm.box;

import ru.romanov.hw07patternsdesign.bank.common.Banknote;

import java.util.Objects;

public class SimpleBox implements Box {

    private Banknote banknote;

    private Integer count;

    public SimpleBox(SimpleBox simpleBox) {
        this.banknote = simpleBox.banknote;
        this.count = simpleBox.count;
    }

    public SimpleBox(Banknote banknote, Integer count) {
        this.banknote = banknote;
        this.count = count;
    }

    public SimpleBox(Banknote banknote, boolean initCount) {
        this.banknote = banknote;
        count = initCount ? (int) (Math.random() * 10) : 0;
    }

    @Override
    public Integer putBanknote() {
        return putBanknotes(1);
    }

    @Override
    public Integer putBanknotes(Integer count) {
        this.count += count;
        return banknote.getNominal() * count;
    }

    @Override
    public Integer getBanknote() {
        this.count -= 1;
        return 1;
    }

    @Override
    public Integer getAllBanknotes() {
        int count = this.count;
        this.count = 0;
        return count;
    }

    @Override
    public Integer getBalance() {
        return banknote.getNominal() * count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleBox simpleBox = (SimpleBox) o;
        return banknote == simpleBox.banknote &&
                Objects.equals(count, simpleBox.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(banknote, count);
    }
}
