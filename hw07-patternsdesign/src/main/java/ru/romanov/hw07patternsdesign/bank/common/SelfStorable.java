package ru.romanov.hw07patternsdesign.bank.common;

public interface SelfStorable {

    void saveState();

    void restoreState();
}
