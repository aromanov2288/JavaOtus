package ru.romanov.hw04logging;

import ru.romanov.hw04logging.proxy.IoC;

import java.lang.reflect.InvocationTargetException;

public class App {

    public static void main(String[] args)
            throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        CalculatorInterface myClass = IoC.newInstance(CalculatorInterface.class, CalculatorImpl.class);
        myClass.sum(1,2);
        myClass.sum(1, 2, 3);
    }
}
