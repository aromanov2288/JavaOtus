package ru.romanov.hw08jsonobjectwrapper;

import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.romanov.hw08jsonobjectwriter.SimpleObject;
import ru.romanov.hw08jsonobjectwriter.Rson;

import java.util.HashMap;
import java.util.Map;

public class RsonMapHelperTest {

    private Gson gson;

    private Rson rson;

    @BeforeEach
    void init() {
        gson = new Gson();
        rson = new Rson();
    }

    @Test
    void emptyMapTest() throws IllegalAccessException {
        Map<Byte, Short> map = new HashMap<>();
        Assertions.assertEquals(gson.toJson(map), rson.toJson(map));
    }

    @Test
    void byteShortMapTest() throws IllegalAccessException {
        Map<Byte, Short> map1 = new HashMap<>();
        map1.put((byte) 1, (short) 2);
        map1.put((byte) 3, (short) 4);
        Assertions.assertEquals(gson.toJson(map1), rson.toJson(map1));

        Map<Byte, Short> map2 = new HashMap<>();
        map2.put((byte) 1, (short) 2);
        map2.put((byte) 3, null);
        Assertions.assertEquals(gson.toJson(map2), rson.toJson(map2));

        Map<Byte, Short> map3 = new HashMap<>();
        map3.put((byte) 1, (short) 2);
        map3.put(null, (short) 4);
        Assertions.assertEquals(gson.toJson(map3), rson.toJson(map3));

        Map<Byte, Short> map4 = new HashMap<>();
        map4.put((byte) 1, (short) 2);
        map4.put(null, null);
        Assertions.assertEquals(gson.toJson(map4), rson.toJson(map4));
    }

    @Test
    void shortByteMapTest() throws IllegalAccessException {
        Map<Short, Byte> map1 = new HashMap<>();
        map1.put((short) 1, (byte) 2);
        map1.put((short) 3, (byte) 4);
        Assertions.assertEquals(gson.toJson(map1), rson.toJson(map1));

        Map<Short, Byte> map2 = new HashMap<>();
        map2.put((short) 1, (byte) 2);
        map2.put((short) 3, null);
        Assertions.assertEquals(gson.toJson(map2), rson.toJson(map2));

        Map<Short, Byte> map3 = new HashMap<>();
        map3.put((short) 1, (byte) 2);
        map3.put(null, (byte) 4);
        Assertions.assertEquals(gson.toJson(map3), rson.toJson(map3));

        Map<Short, Byte> map4 = new HashMap<>();
        map4.put((short) 1, (byte) 2);
        map4.put(null, null);
        Assertions.assertEquals(gson.toJson(map4), rson.toJson(map4));
    }

    @Test
    void integerLongMapTest() throws IllegalAccessException {
        Map<Integer, Long> map1 = new HashMap<>();
        map1.put(1, 2L);
        map1.put(3, 4L);
        Assertions.assertEquals(gson.toJson(map1), rson.toJson(map1));

        Map<Integer, Long> map2 = new HashMap<>();
        map2.put(1, 2L);
        map2.put(3, null);
        Assertions.assertEquals(gson.toJson(map2), rson.toJson(map2));

        Map<Integer, Long> map3 = new HashMap<>();
        map3.put(1, 2L);
        map3.put(null, 4L);
        Assertions.assertEquals(gson.toJson(map3), rson.toJson(map3));

        Map<Integer, Long> map4 = new HashMap<>();
        map4.put(1, 2L);
        map4.put(null, null);
        Assertions.assertEquals(gson.toJson(map4), rson.toJson(map4));
    }

    @Test
    void longIntegerMapTest() throws IllegalAccessException {
        Map<Long, Integer> map1 = new HashMap<>();
        map1.put(1L, 2);
        map1.put(3L, 4);
        Assertions.assertEquals(gson.toJson(map1), rson.toJson(map1));

        Map<Long, Integer> map2 = new HashMap<>();
        map2.put(1L, 2);
        map2.put(3L, null);
        Assertions.assertEquals(gson.toJson(map2), rson.toJson(map2));

        Map<Long, Integer> map3 = new HashMap<>();
        map3.put(1L, 2);
        map3.put(null, 4);
        Assertions.assertEquals(gson.toJson(map3), rson.toJson(map3));

        Map<Long, Integer> map4 = new HashMap<>();
        map4.put(1L, 2);
        map4.put(null, null);
        Assertions.assertEquals(gson.toJson(map4), rson.toJson(map4));
    }

    @Test
    void booleanCharacterMapTest() throws IllegalAccessException {
        Map<Boolean, Character> map1 = new HashMap<>();
        map1.put(false, 'a');
        map1.put(true, 'b');
        Assertions.assertEquals(gson.toJson(map1), rson.toJson(map1));

        Map<Boolean, Character> map2 = new HashMap<>();
        map2.put(false, 'a');
        map2.put(true, null);
        Assertions.assertEquals(gson.toJson(map2), rson.toJson(map2));

        Map<Boolean, Character> map3 = new HashMap<>();
        map3.put(false, 'a');
        map3.put(null, 'b');
        Assertions.assertEquals(gson.toJson(map3), rson.toJson(map3));

        Map<Boolean, Character> map4 = new HashMap<>();
        map4.put(false, 'a');
        map4.put(null, null);
        Assertions.assertEquals(gson.toJson(map4), rson.toJson(map4));
    }

    @Test
    void characterBooleanMapTest() throws IllegalAccessException {
        Map<Character, Boolean> map1 = new HashMap<>();
        map1.put('a', false);
        map1.put('b', true);
        Assertions.assertEquals(gson.toJson(map1), rson.toJson(map1));

        Map<Character, Boolean> map2 = new HashMap<>();
        map2.put('a', false);
        map2.put('b', null);
        Assertions.assertEquals(gson.toJson(map2), rson.toJson(map2));

        Map<Character, Boolean> map3 = new HashMap<>();
        map3.put('a', false);
        map3.put(null, true);
        Assertions.assertEquals(gson.toJson(map3), rson.toJson(map3));

        Map<Character, Boolean> map4 = new HashMap<>();
        map4.put('a', false);
        map4.put(null, null);
        Assertions.assertEquals(gson.toJson(map4), rson.toJson(map4));
    }

    @Test
    void floatDoubleMapTest() throws IllegalAccessException {
        Map<Float, Double> map1 = new HashMap<>();
        map1.put(1.1f, 2.2);
        map1.put(3.3f, 4.4);
        Assertions.assertEquals(gson.toJson(map1), rson.toJson(map1));

        Map<Float, Double> map2 = new HashMap<>();
        map2.put(1.1f, 2.2);
        map2.put(3.3f, null);
        Assertions.assertEquals(gson.toJson(map2), rson.toJson(map2));

        Map<Float, Double> map3 = new HashMap<>();
        map3.put(1.1f, 2.2);
        map3.put(null, 4.4);
        Assertions.assertEquals(gson.toJson(map3), rson.toJson(map3));

        Map<Float, Double> map4 = new HashMap<>();
        map4.put(1.1f, 2.2);
        map4.put(null, null);
        Assertions.assertEquals(gson.toJson(map4), rson.toJson(map4));
    }

    @Test
    void doubleFloatMapTest() throws IllegalAccessException {
        Map<Double, Float> map1 = new HashMap<>();
        map1.put(1.1, 2.f);
        map1.put(3.3, 4.4f);
        Assertions.assertEquals(gson.toJson(map1), rson.toJson(map1));

        Map<Double, Float> map2 = new HashMap<>();
        map2.put(1.1, 2.2f);
        map2.put(3.3, null);
        Assertions.assertEquals(gson.toJson(map2), rson.toJson(map2));

        Map<Double, Float> map3 = new HashMap<>();
        map3.put(1.1, 2.2f);
        map3.put(null, 4.4f);
        Assertions.assertEquals(gson.toJson(map3), rson.toJson(map3));

        Map<Double, Float> map4 = new HashMap<>();
        map4.put(1.1, 2.2f);
        map4.put(null, null);
        Assertions.assertEquals(gson.toJson(map4), rson.toJson(map4));
    }

    @Test
    void stringSimpleObjectMapTest() throws IllegalAccessException {
        Map<String, SimpleObject> map1 = new HashMap<>();
        map1.put("AAA", new SimpleObject());
        map1.put("BBB", new SimpleObject());
        Assertions.assertEquals(gson.toJson(map1), rson.toJson(map1));

        Map<String, SimpleObject> map2 = new HashMap<>();
        map2.put("AAA", new SimpleObject());
        map2.put("BBB", null);
        Assertions.assertEquals(gson.toJson(map2), rson.toJson(map2));

        Map<String, SimpleObject> map3 = new HashMap<>();
        map3.put("AAA", new SimpleObject());
        map3.put(null, new SimpleObject());
        Assertions.assertEquals(gson.toJson(map3), rson.toJson(map3));

        Map<String, SimpleObject> map4 = new HashMap<>();
        map4.put("AAA", new SimpleObject());
        map4.put(null, null);
        Assertions.assertEquals(gson.toJson(map4), rson.toJson(map4));
    }

    @Test
    void simpleObjectStringMapTest() throws IllegalAccessException {
        Map<SimpleObject, String> map1 = new HashMap<>();
        map1.put(new SimpleObject(), "AAA");
        map1.put(new SimpleObject(), "BBB");
        Assertions.assertEquals(gson.toJson(map1), rson.toJson(map1));

        Map<SimpleObject, String> map2 = new HashMap<>();
        map2.put(new SimpleObject(), "AAA");
        map2.put(new SimpleObject(), null);
        Assertions.assertEquals(gson.toJson(map2), rson.toJson(map2));

        Map<SimpleObject, String> map3 = new HashMap<>();
        map3.put(new SimpleObject(), "AAA");
        map3.put(null, "BBB");
        Assertions.assertEquals(gson.toJson(map3), rson.toJson(map3));

        Map<SimpleObject, String> map4 = new HashMap<>();
        map4.put(new SimpleObject(), "AAA");
        map4.put(null, null);
        Assertions.assertEquals(gson.toJson(map4), rson.toJson(map4));
    }
}
