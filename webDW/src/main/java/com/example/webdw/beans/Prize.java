package com.example.webdw.beans;

public class Prize {
    private String id_prize;
    private String name_prize;
    private int value_prize;

    public Prize(String id_prize, String name_prize, int value_prize) {
        this.id_prize = id_prize;
        this.name_prize = name_prize;
        this.value_prize = value_prize;
    }

    public Prize() {
    }

    public String getId_prize() {
        return id_prize;
    }

    public void setId_prize(String id_prize) {
        this.id_prize = id_prize;
    }

    public String getName_prize() {
        return name_prize;
    }

    public void setName_prize(String name_prize) {
        this.name_prize = name_prize;
    }

    public int getValue_prize() {
        return value_prize;
    }

    public void setValue_prize(int value_prize) {
        this.value_prize = value_prize;
    }

    @Override
    public String toString() {
        return "Prize{" +
                "id_prize='" + id_prize + '\'' +
                ", name_prize='" + name_prize + '\'' +
                ", value_prize=" + value_prize +
                '}';
    }
}
