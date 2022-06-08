package com.example.approbotica;

import android.os.Parcel;
import android.os.Parcelable;

public class Controller implements Parcelable {
    private boolean connection;
    private int battery = 69;
    private int speed = 0;
    private int weight = 0;
    private int[] decibel;
    private int currentGrid;
    private String cameraFeed;
    private String currentAction = "controller";

    public Controller(){}

    protected Controller(Parcel in) {
        connection = in.readByte() != 0;
        battery = in.readInt();
        speed = in.readInt();
        weight = in.readInt();
        decibel = in.createIntArray();
        currentGrid = in.readInt();
        cameraFeed = in.readString();
        currentAction = in.readString();
    }

    public static final Creator<Controller> CREATOR = new Creator<Controller>() {
        @Override
        public Controller createFromParcel(Parcel in) {
            return new Controller(in);
        }

        @Override
        public Controller[] newArray(int size) {
            return new Controller[size];
        }
    };

    public void sendSignal(String action){
        //sendsomething
    }

    public boolean setConnection(){
        connection = true;
        //do connection stuff here
        return connection;
    }

    public boolean getConnection(){ return connection; }
    public int getBattery(){ return battery; }
    public int getSpeed(){ return speed; }
    public int getWeight(){ return weight; }
    public String getCurrentAction() { return currentAction; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (connection ? 1 : 0));
        dest.writeInt(battery);
        dest.writeInt(speed);
        dest.writeInt(weight);
        dest.writeIntArray(decibel);
        dest.writeInt(currentGrid);
        dest.writeString(cameraFeed);
        dest.writeString(currentAction);
    }
}
