package com.example.webdw.beans;

public class Province {
    private String id_province;
    private String name_province;

    public Province(String id_province, String name_province) {
        this.id_province = id_province;
        this.name_province = name_province;
    }

    public Province() {
    }

    public String getId_province() {
        return id_province;
    }

    public void setId_province(String id_province) {
        this.id_province = id_province;
    }

    public String getName_province() {
        return name_province;
    }

    public void setName_province(String name_province) {
        this.name_province = name_province;
    }

    @Override
    public String toString() {
        return "Province{" +
                "id_province='" + id_province + '\'' +
                ", name_province='" + name_province + '\'' +
                '}';
    }
}
