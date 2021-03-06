package model;

import java.util.Date;

public class Person {

    private String fieldString;
    private int fieldInt;
    private byte fieldByte;
    private short fieldShort;
    private long fieldLong;
    private float fieldFloat;
    private double fieldDouble;
    private boolean fieldBoolean;
    private char fieldChar;
    private Date fieldDate;
    private ColorEnum fieldEnum;

    public String getFieldString() {
        return fieldString;
    }

    public void setFieldString(String fieldString) {
        this.fieldString = fieldString;
    }

    public int getFieldInt() {
        return fieldInt;
    }

    public void setFieldInt(int fieldInt) {
        this.fieldInt = fieldInt;
    }

    public byte getFieldByte() {
        return fieldByte;
    }

    public void setFieldByte(byte fieldByte) {
        this.fieldByte = fieldByte;
    }

    public short getFieldShort() {
        return fieldShort;
    }

    public void setFieldShort(short fieldShort) {
        this.fieldShort = fieldShort;
    }

    public long getFieldLong() {
        return fieldLong;
    }

    public void setFieldLong(long fieldLong) {
        this.fieldLong = fieldLong;
    }

    public float getFieldFloat() {
        return fieldFloat;
    }

    public void setFieldFloat(float fieldFloat) {
        this.fieldFloat = fieldFloat;
    }

    public double getFieldDouble() {
        return fieldDouble;
    }

    public void setFieldDouble(double fieldDouble) {
        this.fieldDouble = fieldDouble;
    }

    public boolean isFieldBoolean() {
        return fieldBoolean;
    }

    public void setFieldBoolean(boolean fieldBoolean) {
        this.fieldBoolean = fieldBoolean;
    }

    public char getFieldChar() {
        return fieldChar;
    }

    public void setFieldChar(char fieldChar) {
        this.fieldChar = fieldChar;
    }

    public Date getFieldDate() {
        return fieldDate;
    }

    public void setFieldDate(Date fieldDte) {
        this.fieldDate = fieldDte;
    }

    public Enum<ColorEnum> getFieldEnum() {
        return fieldEnum;
    }

    public void setFieldEnum(ColorEnum fieldEnum) {
        this.fieldEnum = fieldEnum;
    }

    public Person() {
    }

    public Person(String fieldString, int fieldInt, byte fieldByte, short fieldShort, long fieldLong, float fieldFloat, double fieldDouble, boolean fieldBoolean, char fieldChar, Date fieldDate, ColorEnum fieldEnum) {
        this.fieldString = fieldString;
        this.fieldInt = fieldInt;
        this.fieldByte = fieldByte;
        this.fieldShort = fieldShort;
        this.fieldLong = fieldLong;
        this.fieldFloat = fieldFloat;
        this.fieldDouble = fieldDouble;
        this.fieldBoolean = fieldBoolean;
        this.fieldChar = fieldChar;
        this.fieldDate = fieldDate;
        this.fieldEnum = fieldEnum;
    }

    @Override
    public String toString() {
        return "Person{" +
                "fieldString='" + fieldString + '\'' +
                ", fieldInt=" + fieldInt +
                ", fieldByte=" + fieldByte +
                ", fieldShort=" + fieldShort +
                ", fieldLong=" + fieldLong +
                ", fieldFloat=" + fieldFloat +
                ", fieldDouble=" + fieldDouble +
                ", fieldBoolean=" + fieldBoolean +
                ", fieldChar=" + fieldChar +
                ", fieldDte=" + fieldDate +
                ", fieldEnum=" + fieldEnum +
                '}';
    }
}
