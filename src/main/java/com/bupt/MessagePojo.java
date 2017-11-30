package com.bupt;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 张 成 on 2017/8/6.
 */
public class MessagePojo implements Serializable{

    private String strObj ;
    private int int32Obj ;
    private long int64Obj ;
    private int uint32Obj ;
    private long uint64Obj ;
    private int sint32Obj ;
    private long sint64Obj ;
    private int fixed32Obj ;
    private long fixed64Obj ;
    private int sfixed32Obj;
    private long sfixed64Obj;
    private boolean boolObj ;
    private byte[] bytesObj;
    private float folatObj ;
    private List<Double> doubleObj ; //
    private InnerMessagePojo innerMessage;


    public String getStrObj() {
        return strObj;
    }

    public void setStrObj(String strObj) {
        this.strObj = strObj;
    }

    public int getInt32Obj() {
        return int32Obj;
    }

    public void setInt32Obj(int int32Obj) {
        this.int32Obj = int32Obj;
    }

    public long getInt64Obj() {
        return int64Obj;
    }

    public void setInt64Obj(long int64Obj) {
        this.int64Obj = int64Obj;
    }

    public int getUint32Obj() {
        return uint32Obj;
    }

    public void setUint32Obj(int uint32Obj) {
        this.uint32Obj = uint32Obj;
    }

    public long getUint64Obj() {
        return uint64Obj;
    }

    public void setUint64Obj(long uint64Obj) {
        this.uint64Obj = uint64Obj;
    }

    public int getSint32Obj() {
        return sint32Obj;
    }

    public void setSint32Obj(int sint32Obj) {
        this.sint32Obj = sint32Obj;
    }

    public long getSint64Obj() {
        return sint64Obj;
    }

    public void setSint64Obj(long sint64Obj) {
        this.sint64Obj = sint64Obj;
    }

    public int getFixed32Obj() {
        return fixed32Obj;
    }

    public void setFixed32Obj(int fixed32Obj) {
        this.fixed32Obj = fixed32Obj;
    }

    public long getFixed64Obj() {
        return fixed64Obj;
    }

    public void setFixed64Obj(long fixed64Obj) {
        this.fixed64Obj = fixed64Obj;
    }

    public int getSfixed32Obj() {
        return sfixed32Obj;
    }

    public void setSfixed32Obj(int sfixed32Obj) {
        this.sfixed32Obj = sfixed32Obj;
    }

    public long getSfixed64Obj() {
        return sfixed64Obj;
    }

    public void setSfixed64Obj(long sfixed64Obj) {
        this.sfixed64Obj = sfixed64Obj;
    }

    public boolean isBoolObj() {
        return boolObj;
    }

    public void setBoolObj(boolean boolObj) {
        this.boolObj = boolObj;
    }

    public byte[] getBytesObj() {
        return bytesObj;
    }

    public void setBytesObj(byte[] bytesObj) {
        this.bytesObj = bytesObj;
    }

    public float getFolatObj() {
        return folatObj;
    }

    public void setFolatObj(float folatObj) {
        this.folatObj = folatObj;
    }

    public List<Double> getDoubleObj() {
        return doubleObj;
    }

    public void setDoubleObj(List<Double> doubleObj) {
        this.doubleObj = doubleObj;
    }

    public InnerMessagePojo getInnerMessage() {
        return innerMessage;
    }

    public void setInnerMessage(InnerMessagePojo innerMessage) {
        this.innerMessage = innerMessage;
    }

    @Override
    public String toString() {
        return "MessagePojo{" +
                "strObj='" + strObj + '\'' +
                ", int32Obj=" + int32Obj +
                ", int64Obj=" + int64Obj +
                ", uint32Obj=" + uint32Obj +
                ", uint64Obj=" + uint64Obj +
                ", sint32Obj=" + sint32Obj +
                ", sint64Obj=" + sint64Obj +
                ", fixed32Obj=" + fixed32Obj +
                ", fixed64Obj=" + fixed64Obj +
                ", sfixed32Obj=" + sfixed32Obj +
                ", sfixed64Obj=" + sfixed64Obj +
                ", boolObj=" + boolObj +
                ", bytesObj=" + Arrays.toString(bytesObj) +
                ", folatObj=" + folatObj +
                ", doubleObj=" + doubleObj +
                ", innerMessage=" + innerMessage +
                '}';
    }
}
