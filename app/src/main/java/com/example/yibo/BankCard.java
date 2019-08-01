package com.example.yibo;

public class BankCard {

    private String name;

    private String type;

    private String number;

    private int imageId;

    private int backgroundId;

    public BankCard(String name, String type, String number, int imageId, int backgroundId){
        this.name = name;
        this.type = type;
        this.number = number;
        this.imageId = imageId;
        this.backgroundId = backgroundId;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getNumber() {
        return number;
    }

    public int getImageId() {
        return imageId;
    }

    public int getBackgroundId() {
        return backgroundId;
    }
}
