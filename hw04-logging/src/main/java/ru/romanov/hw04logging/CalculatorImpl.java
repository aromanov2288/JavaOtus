package ru.romanov.hw04logging;


import ru.romanov.hw04logging.proxy.LogProxy;

public class CalculatorImpl implements CalculatorInterface {

    @LogProxy
    @Override
    public int sum(int a, int b) {
        return a + b;
    }

    @Override
    public int sum(int a, int b, int c) {
        return a + b + c;
    }

    @Override
    public int diff(int a, int b) {
        return a - b;
    }

    @Override
    public int division(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("Division by zero is not allowed");
        }
        return a / b;
    }

    @Override
    public int multiplication(int a, int b) {
        return a * b;
    }
}
