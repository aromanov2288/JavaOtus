package ru.romanov.hw08jsonobjectwrapper;

import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.romanov.hw08jsonobjectwriter.SimpleObject;
import ru.romanov.hw08jsonobjectwriter.Rson;

public class RsonArraySerializerTest {

    private Gson gson;

    private Rson rson;

    @BeforeEach
    void init() {
        gson = new Gson();
        rson = new Rson();
    }

    @Test
    void byteArrayTest() throws IllegalAccessException {
        byte[] array = {1, 2, 3};
        Assertions.assertEquals(gson.toJson(array), rson.toJson(array));
    }

    @Test
    void byteOArrayTest() throws IllegalAccessException {
        Byte[] array = {1, null, 3};
        Assertions.assertEquals(gson.toJson(array), rson.toJson(array));
    }

    @Test
    void shortArrayTest() throws IllegalAccessException {
        short[] array = {1, 2, 3};
        Assertions.assertEquals(gson.toJson(array), rson.toJson(array));
    }

    @Test
    void shortOArrayTest() throws IllegalAccessException {
        Short[] array = {1, null, 3};
        Assertions.assertEquals(gson.toJson(array), rson.toJson(array));
    }

    @Test
    void intArrayTest() throws IllegalAccessException {
        int[] array = {1, 2, 3};
        Assertions.assertEquals(gson.toJson(array), rson.toJson(array));
    }

    @Test
    void integerArrayTest() throws IllegalAccessException {
        Integer[] array = {1, null, 3};
        Assertions.assertEquals(gson.toJson(array), rson.toJson(array));
    }

    @Test
    void longArrayTest() throws IllegalAccessException {
        long[] array = {1L, 2L, 3L};
        Assertions.assertEquals(gson.toJson(array), rson.toJson(array));
    }

    @Test
    void longOArrayTest() throws IllegalAccessException {
        Long[] array = {1L, null, 3L};
        Assertions.assertEquals(gson.toJson(array), rson.toJson(array));
    }

    @Test
    void booleanArrayTest() throws IllegalAccessException {
        boolean[] array = {true, false, false};
        Assertions.assertEquals(gson.toJson(array), rson.toJson(array));
    }

    @Test
    void booleanOArrayTest() throws IllegalAccessException {
        Boolean[] array = {true, null, false};
        Assertions.assertEquals(gson.toJson(array), rson.toJson(array));
    }

    @Test
    void charArrayTest() throws IllegalAccessException {
        char[] array = {'a', 'b', 'c'};
        Assertions.assertEquals(gson.toJson(array), rson.toJson(array));
    }

    @Test
    void characterArrayTest() throws IllegalAccessException {
        Character[] array = {'a', null, 'c'};
        Assertions.assertEquals(gson.toJson(array), rson.toJson(array));
    }

    @Test
    void floatArrayTest() throws IllegalAccessException {
        float[] array = {1.1f, 2.2f, 3.3f};
        Assertions.assertEquals(gson.toJson(array), rson.toJson(array));
    }

    @Test
    void floatOArrayTest() throws IllegalAccessException {
        Float[] array = {1.1f, null, 3.3f};
        Assertions.assertEquals(gson.toJson(array), rson.toJson(array));
    }

    @Test
    void doubleArrayTest() throws IllegalAccessException {
        double[] array = {1.1, 2.2, 3.3};
        Assertions.assertEquals(gson.toJson(array), rson.toJson(array));
    }

    @Test
    void doubleOArrayTest() throws IllegalAccessException {
        Double[] array = {1.1, null, 3.3};
        Assertions.assertEquals(gson.toJson(array), rson.toJson(array));
    }

    @Test
    void stringOArrayTest() throws IllegalAccessException {
        String[] array = {"aaa", null, "ccc"};
        Assertions.assertEquals(gson.toJson(array), rson.toJson(array));
    }

    @Test
    void simpleObjectArrayTest() throws IllegalAccessException {
        SimpleObject simpleObject1 = new SimpleObject();
        SimpleObject simpleObject2 = null;
        SimpleObject[] array = {simpleObject1, simpleObject2};
        Assertions.assertEquals(gson.toJson(array), rson.toJson(array));
    }
}
