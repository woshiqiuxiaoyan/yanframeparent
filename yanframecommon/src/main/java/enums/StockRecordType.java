package enums;

import java.util.ArrayList;
import java.util.List;

public enum StockRecordType {
    INSTOCK("入库清单",0),OUTSTOCK("出库清单",1);

    StockRecordType(String name, int value) {
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
        List<StockRecordType> list  = new ArrayList<>();
        for(StockRecordType stockRecordType: StockRecordType.values()){
            list.add(stockRecordType);
        }
        return list;
    }

}
