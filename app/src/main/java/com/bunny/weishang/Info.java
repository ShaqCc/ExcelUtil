package com.bunny.weishang;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
/**
 * Created by Administrator on 2017/11/19.
 */
@DatabaseTable(tableName = "info_table")
public class Info implements Serializable {
        @DatabaseField(generatedId = true)//id为主键 
        private int id;
        @DatabaseField(columnName = "date")
        private String date;
        @DatabaseField(columnName = "size")
        private String size;
        @DatabaseField(columnName = "address")
        private String address;
        @DatabaseField(columnName = "tel")
        private String tel;
        @DatabaseField(columnName = "sender")
        private String sender;
        @DatabaseField(columnName = "number")
        private String number;
        @DatabaseField(columnName = "remark")
        private String remark;
        @DatabaseField(columnName = "express")
        private String express;
        @DatabaseField(columnName = "image",dataType = DataType.BYTE_ARRAY)
        private byte[] image;

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Info{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", size='" + size + '\'' +
                ", address='" + address + '\'' +
                ", tel='" + tel + '\'' +
                ", sender='" + sender + '\'' +
                ", number='" + number + '\'' +
                ", remark='" + remark + '\'' +
                ", express='" + express + '\'' +
                '}';
    }
    
        public Info() {
        }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSize() {
        return size;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getTel() {
        return tel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getExpress() {
        return express;
    }

    public void setExpress(String express) {
        this.express = express;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

}
