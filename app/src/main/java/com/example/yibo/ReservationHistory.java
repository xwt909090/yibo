package com.example.yibo;

public class ReservationHistory {

    private String parkingLotName;
    private int parkingLotNum;
    private int parkingSpotNum;
    private String carNum;
    private String time;

    public ReservationHistory(String plname, int plnum, int psnum, String cnum, String t){
        this.parkingLotName = plname;
        this.parkingLotNum = plnum;
        this.parkingSpotNum = psnum;
        this.carNum = cnum;
        this.time = t;
    }

    public int getParkingSpotNum() {
        return parkingSpotNum;
    }

    public int getParkingLotNum() {
        return parkingLotNum;
    }

    public String getCarNum() {
        return carNum;
    }

    public String getParkingLotName() {
        return parkingLotName;
    }

    public String getTime() {
        return time;
    }
}
