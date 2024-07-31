package model;

public class Request {
    private boolean goingUp;
    private int floorNumber;

    public Request(boolean goingUp, int floorNumber) {
        this.floorNumber = floorNumber;
        this.goingUp = goingUp;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    @Override
    public String toString() {
        return "" + floorNumber;
    }
}
