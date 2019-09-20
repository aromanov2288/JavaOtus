package ru.romanov.hw06design;

public class SimpleBox<T extends Banknote> implements Box {

    private T banknote;

    private Integer count = 0;

    public SimpleBox(T banknote) {
        this.banknote = banknote;
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
}
