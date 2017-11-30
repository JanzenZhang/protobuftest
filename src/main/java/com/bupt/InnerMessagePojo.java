package com.bupt;

import java.io.Serializable;

/**
 * Created by 张 成 on 2017/8/6.
 */
public class InnerMessagePojo implements Serializable {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EnumTypePojo getType() {
        return type;
    }

    public void setType(EnumTypePojo type) {
        this.type = type;
    }

    private String name;
    private int id;
    private EnumTypePojo type;


    @Override
    public String toString() {
        return "InnerMessagePojo{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", type=" + type +
                '}';
    }
}
