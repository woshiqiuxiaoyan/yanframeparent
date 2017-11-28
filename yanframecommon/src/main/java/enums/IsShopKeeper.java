package enums;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public enum IsShopKeeper implements Serializable {
    ADMIN("管理员",1),SHOP_KEEPER("店长",2),STAFF("普通员工",3);

    IsShopKeeper(String name, int value) {
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
        List<IsShopKeeper> list  = new ArrayList<>();
        for(IsShopKeeper isShopKeeper: IsShopKeeper.values()){
            list.add(isShopKeeper);
        }
        return list;
    }

}
