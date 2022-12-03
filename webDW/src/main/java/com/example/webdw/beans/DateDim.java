package com.example.webdw.beans;

public class DateDim {
    private String full_date;
    private String day_of_week;

    public DateDim(String full_date, String day_of_week) {
        this.full_date = full_date;
        this.day_of_week = day_of_week;
    }

    public DateDim() {
    }

    public String getFull_date() {
        return full_date;
    }

    public void setFull_date(String full_date) {
        this.full_date = full_date;
    }

    public String getDay_of_week() {
        return day_of_week;
    }

    public void setDay_of_week(String day_of_week) {
        this.day_of_week = day_of_week;
    }

    public void translateDay_of_week() {
        switch(this.day_of_week) {
            case "Saturday":
                this.day_of_week = "Thứ bảy";
                break;
            case "Sunday":
                this.day_of_week = "Chủ nhật";
                break;
            case "Monday":
                this.day_of_week = "Thứ hai";
                break;
            case "Tuesday":
                this.day_of_week = "Thứ ba";
                break;
            case "Wednesday":
                this.day_of_week = "Thứ tư";
                break;
            case "Thursday":
                this.day_of_week = "Thứ năm";
                break;
            case "Friday":
                this.day_of_week = "Thứ sáu";
                break;
            default:
                break;
        }
    }



    @Override
    public String toString() {
        return "DataDim{" +
                "full_date='" + full_date + '\'' +
                ", day_of_week='" + day_of_week + '\'' +
                '}';
    }
}
