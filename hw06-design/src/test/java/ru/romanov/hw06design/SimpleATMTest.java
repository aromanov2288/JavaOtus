package ru.romanov.hw06design;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SimpleATMTest {

    private ATM atm;

    @BeforeEach
    public void init() {
        atm = new SimpleATM();
    }

    @Test
    public void putMoneyTest() {
        Map<Banknote, Integer>  banknotePack = new HashMap<>();
        banknotePack.put(Banknote.TYPE_500, 1);
        banknotePack.put(Banknote.TYPE_100, 2);
        banknotePack.put(Banknote.TYPE_50, 3);
        atm.putMoney(banknotePack);
        assertTrue(atm.getBalance().isPresent());
        assertEquals(850, atm.getBalance().get());
    }

    @Test
    public void getMoney1Test() {
        Map<Banknote, Integer>  banknotePack = new HashMap<>();
        banknotePack.put(Banknote.TYPE_5000, 1);
        atm.putMoney(banknotePack);

        Map<Banknote, Integer> banknotePackFromAtm = atm.getMoney(4900);

        assertNotNull(banknotePackFromAtm);
        assertTrue(banknotePackFromAtm.isEmpty());
    }

    @Test
    public void getMoney2Test() {
        Map<Banknote, Integer>  banknotePack = new HashMap<>();
        banknotePack.put(Banknote.TYPE_5000, 1);
        atm.putMoney(banknotePack);

        Map<Banknote, Integer> banknotePackFromAtm = atm.getMoney(5100);

        assertNotNull(banknotePackFromAtm);
        assertTrue(banknotePackFromAtm.isEmpty());
    }

    @Test
    public void getMoney3Test() {
        Map<Banknote, Integer>  banknotePack = new HashMap<>();
        banknotePack.put(Banknote.TYPE_1000, 2);
        banknotePack.put(Banknote.TYPE_500, 2);
        banknotePack.put(Banknote.TYPE_100, 10);
        atm.putMoney(banknotePack);

        Map<Banknote, Integer> banknotePackFromAtm = atm.getMoney(1500);

        assertNotNull(banknotePackFromAtm);
        assertEquals(2, banknotePackFromAtm.size());
        assertTrue(banknotePackFromAtm.containsKey(Banknote.TYPE_1000));
        assertTrue(banknotePackFromAtm.containsKey(Banknote.TYPE_500));
        assertEquals(1, banknotePackFromAtm.get(Banknote.TYPE_1000));
        assertEquals(1, banknotePackFromAtm.get(Banknote.TYPE_500));
        assertTrue(atm.getBalance().isPresent());
        assertEquals(2500, atm.getBalance().get());
    }
}
