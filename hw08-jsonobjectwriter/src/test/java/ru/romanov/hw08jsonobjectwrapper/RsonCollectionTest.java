package ru.romanov.hw08jsonobjectwrapper;

import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.romanov.hw08jsonobjectwriter.SimpleObject;
import ru.romanov.hw08jsonobjectwriter.Rson;

import java.util.Arrays;
import java.util.List;

public class RsonCollectionTest {

    private Gson gson;

    private Rson rson;

    @BeforeEach
    void init() {
        gson = new Gson();
        rson = new Rson();
    }

    @Test
    void byteCollectionTest() throws IllegalAccessException {
        List<Byte> collection = Arrays.asList((byte) 1, null, (byte) 3);
        Assertions.assertEquals(gson.toJson(collection), rson.toJson(collection));
    }

    @Test
    void shortCollectionTest() throws IllegalAccessException {
        List<Short> collection = Arrays.asList((short) 1, null, (short) 3);
        Assertions.assertEquals(gson.toJson(collection), rson.toJson(collection));
    }

    @Test
    void integerCollectionTest() throws IllegalAccessException {
        List<Integer> collection = Arrays.asList(1, null, 3);
        Assertions.assertEquals(gson.toJson(collection), rson.toJson(collection));
    }

    @Test
    void longCollectionTest() throws IllegalAccessException {
        List<Long> collection = Arrays.asList(1L, null, 3L);
        Assertions.assertEquals(gson.toJson(collection), rson.toJson(collection));
    }

    @Test
    void booleanCollectionTest() throws IllegalAccessException {
        List<Boolean> collection = Arrays.asList(true, null, false);
        Assertions.assertEquals(gson.toJson(collection), rson.toJson(collection));
    }

    @Test
    void charCollectionTest() throws IllegalAccessException {
        List<Character> collection = Arrays.asList('a', null, 'c');
        Assertions.assertEquals(gson.toJson(collection), rson.toJson(collection));
    }

    @Test
    void floatCollectionTest() throws IllegalAccessException {
        List<Float> collection = Arrays.asList(1.1f, null, 3.3f);
        Assertions.assertEquals(gson.toJson(collection), rson.toJson(collection));
    }

    @Test
    void doubleCollectionTest() throws IllegalAccessException {
        List<Double> collection = Arrays.asList(1.1, null, 3.3);
        Assertions.assertEquals(gson.toJson(collection), rson.toJson(collection));
    }

    @Test
    void stringCollectionTest() throws IllegalAccessException {
        List<String> collection = Arrays.asList("AAA", null, "CCC");
        Assertions.assertEquals(gson.toJson(collection), rson.toJson(collection));
    }

    @Test
    void simpleObjectCollectionTest() throws IllegalAccessException {
        SimpleObject simpleObject1 = new SimpleObject();
        SimpleObject simpleObject2 = null;
        List<SimpleObject> collection = Arrays.asList(simpleObject1, null, simpleObject2);
        Assertions.assertEquals(gson.toJson(collection), rson.toJson(collection));
    }
}
