package enums;

import java.util.ArrayList;
import java.util.List;

public enum GoodsSize {
    S_SIZE("S码",0),M_SIZE("M码",1),L_SIZE("L码",2),XL_SIZE("XL码",3),XXL_SIZE("XXL码",4),XXXL_SIZE("XXXL码",5),NO_SIZE("无码",6);

    GoodsSize(String name, int value) {
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
        List<GoodsSize> list  = new ArrayList<>();
        for(GoodsSize goodsSize: GoodsSize.values()){
            list.add(goodsSize);
        }
        return list;
    }

    public static String getName(Integer goods_size) {
        for(GoodsSize goodsSize: GoodsSize.values()){
            if(goodsSize.value==goods_size.intValue()){
                return goodsSize.getName();
            }
        }
        return "";
    }
}
