package model;

public class TableData {

    int no;
    String getterName;
    String getterValue;

    public TableData(int no, String getterName, String getterValue) {
        this.no = no;
        this.getterName = getterName;
        this.getterValue = getterValue;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getGetterName() {
        return getterName;
    }

    public void setGetterName(String getterName) {
        this.getterName = getterName;
    }

    public String getGetterValue() {
        return getterValue;
    }

    public void setGetterValue(String getterValue) {
        this.getterValue = getterValue;
    }
}
