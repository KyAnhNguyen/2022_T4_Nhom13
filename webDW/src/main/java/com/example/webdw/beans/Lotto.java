package com.example.webdw.beans;

import java.sql.Date;
import java.util.Collections;

public class Lotto {
    private String natural_key;
    private String id_province;
    private String id_prize;
    private String number;
    private String status;
    private int created_date;
    private Date updated_date;

    public Lotto(String natural_key, String id_province, String id_prize, String number, String status, int created_date, Date updated_date) {
        this.natural_key = natural_key;
        this.id_province = id_province;
        this.id_prize = id_prize;
        this.number = number;
        this.status = status;
        this.created_date = created_date;
        this.updated_date = updated_date;
    }

    public Lotto() {
    }

    public String getNatural_key() {
        return natural_key;
    }

    public void setNatural_key(String natural_key) {
        this.natural_key = natural_key;
    }

    public String getId_province() {
        return id_province;
    }

    public void setId_province(String id_province) {
        this.id_province = id_province;
    }

    public String getId_prize() {
        return id_prize;
    }

    public void setId_prize(String id_prize) {
        this.id_prize = id_prize;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCreated_date() {
        return created_date;
    }

    public void setCreated_date(int created_date) {
        this.created_date = created_date;
    }

    public Date getUpdated_date() {
        return updated_date;
    }

    public void setUpdated_date(Date updated_date) {
        this.updated_date = updated_date;
    }

    public int numberSizeById_prize() {
        switch (this.id_prize) {
            case "giai8":
                return 2;
            case "giai7":
                return 3;
            case "giai6":
                return 4;
            case "giai5":
                return 4;
            case "giai4":
                return 5;
            case "giai3":
                return 5;
            case "giai2":
                return 5;
            case "giai1":
                return 5;
            case "giaidb":
                return 6;
            default:
                return 0;
        }
    }

    public void adjustNumber() {
        int sizeNumber = numberSizeById_prize();
        int remainder = sizeNumber - this.number.length();
        if (remainder > 0) {
            this.number = String.join("", Collections.nCopies(remainder, "0")) + this.number;
        }
    }

    @Override
    public String toString() {
        return "Lotto{" +
                "natural_key='" + natural_key + '\'' +
                ", id_province='" + id_province + '\'' +
                ", id_prize='" + id_prize + '\'' +
                ", number=" + number +
                ", status='" + status + '\'' +
                ", created_date=" + created_date +
                ", updated_date=" + updated_date +
                '}';
    }
}
