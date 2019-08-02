package com.example.yibo;

import java.io.Serializable;

public class ParkingLot implements Serializable {

    private String parkingLotName;

    private int parkingLotSize;

    private double parkingLotPrice;

    private int distance;

    public ParkingLot(String name, int size, double price, int dis){
        this.parkingLotName = name;
        this.parkingLotSize = size;
        this.parkingLotPrice = price;
        this.distance = dis;
    }

    public String getParkingLotName(){
        return parkingLotName;
    }
    public int getParkingLotSize(){
        return parkingLotSize;
    }
    public double getParkingLotPrice(){
        return parkingLotPrice;
    }
    public int getDistance(){
        return distance;
    }
}
