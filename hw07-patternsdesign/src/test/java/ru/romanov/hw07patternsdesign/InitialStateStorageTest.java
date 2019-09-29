package ru.romanov.hw07patternsdesign;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.romanov.hw07patternsdesign.bank.common.Banknote;
import ru.romanov.hw07patternsdesign.bank.department.atm.atm.box.SimpleBox;
import ru.romanov.hw07patternsdesign.bank.department.atm.atm.box.storage.InitialStateStorage;
import ru.romanov.hw07patternsdesign.bank.department.atm.atm.box.storage.StateStorage;

import java.util.HashMap;
import java.util.Map;

public class InitialStateStorageTest {

    private StateStorage stateStorage;

    @BeforeEach
    void init() {
        stateStorage = new InitialStateStorage();
    }

    @Test
    void getStateTest() {
        Map<Banknote, SimpleBox> srcMap = new HashMap<>();
        SimpleBox sb1 = new SimpleBox(Banknote.TYPE_10, 2);
        srcMap.put(Banknote.TYPE_10, sb1);
        SimpleBox sb2 = new SimpleBox(Banknote.TYPE_50, 5);
        srcMap.put(Banknote.TYPE_50, sb2);
        SimpleBox sb3 = new SimpleBox(Banknote.TYPE_100, 8);
        srcMap.put(Banknote.TYPE_100, sb3);

        stateStorage.setState(srcMap);
        Map<Banknote, SimpleBox> dstMap = stateStorage.getState();
        SimpleBox dstSb1 = dstMap.get(Banknote.TYPE_10);
        SimpleBox dstSb2 = dstMap.get(Banknote.TYPE_50);
        SimpleBox dstSb3 = dstMap.get(Banknote.TYPE_100);

        Assertions.assertEquals(dstMap.size(), 3);

        Assertions.assertTrue(dstMap.containsKey(Banknote.TYPE_10));
        Assertions.assertTrue(dstMap.containsKey(Banknote.TYPE_50));
        Assertions.assertTrue(dstMap.containsKey(Banknote.TYPE_100));

        Assertions.assertEquals(sb1, dstSb1);
        Assertions.assertEquals(sb2, dstSb2);
        Assertions.assertEquals(sb3, dstSb3);

        Assertions.assertNotSame(sb1, dstSb1);
        Assertions.assertNotSame(sb3, dstSb2);
        Assertions.assertNotSame(sb1, dstSb3);
    }
}
