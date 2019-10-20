package ru.romanov.hw08jsonobjectwrapper;

import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.romanov.hw08jsonobjectwriter.ComplexObject;
import ru.romanov.hw08jsonobjectwriter.Rson;

public class OblectHelperTest {

    private Gson gson;

    private Rson rson;

    @BeforeEach
    void init() {
        gson = new Gson();
        rson = new Rson();
    }

    @Test
    void complexObjectTest() throws IllegalAccessException {
        ComplexObject complexObject = new ComplexObject();
        Assertions.assertEquals(gson.toJson(complexObject), rson.toJson(complexObject));
    }

    @Test
    void complexObjectWithSomeNullsTest() throws IllegalAccessException {
        ComplexObject complexObject = new ComplexObject();
        complexObject.setStringField(null);
        complexObject.setIntegerOField(null);
        complexObject.setSimpleObjectField(null);
        Assertions.assertEquals(gson.toJson(complexObject), rson.toJson(complexObject));
    }
}
