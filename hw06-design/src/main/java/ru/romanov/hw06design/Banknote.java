package ru.romanov.hw06design;

public enum  Banknote {
    TYPE_10(10),
    TYPE_50(50),
    TYPE_100(100),
    TYPE_500(500),
    TYPE_1000(1000),
    TYPE_2000(2000),
    TYPE_5000(5000);

    private int nominal;

    Banknote(int nominal) {
        this.nominal = nominal;
    }

    public int getNominal() {
        return nominal;
    }
}
