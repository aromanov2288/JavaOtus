package ru.romanov.hw08jsonobjectwriter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComplexObject {

    private String stringField = "AAA";

    private byte byteField = 1;

    private Byte byteOField = 2;

    private short shortField = 3;

    private Short shortOField = 4;

    private int intField = 5;

    private Integer integerOField = 6;

    private long longField = 7;

    private Long longOField = 8L;

    private boolean booleanField = false;

    private Boolean booleanOField = true;

    private char charField = 'a';

    private Character characterOField = 'b';

    private float floatField = 1.1f;

    private Float floatOField = 2.2f;

    private double doubleField = 3.3;

    private Double doubleOField = 4.4;

    private SimpleObject simpleObjectField = new SimpleObject();
}
