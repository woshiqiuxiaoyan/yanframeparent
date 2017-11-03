package enums;

import java.util.ArrayList;
import java.util.List;

public enum StockType {
    INSTOCK("入库",0),OUTSTOCK("出库",1),ADDSTOCK("新增",2);

    StockType(String name, int value) {
        this.name = name;
        this.value = value;
    }

    private String name;

    private int value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }


    public static List toList(){
        List<StockType> list  = new ArrayList<>();
        for(StockType stockRecordType: StockType.values()){
            list.add(stockRecordType);
        }
        return list;
    }

}
