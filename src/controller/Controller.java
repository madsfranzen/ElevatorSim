package controller;

import model.Elevator;
import model.Request;

import java.util.ArrayList;

public class Controller {

    public static void requestElevator(boolean upPressed, int floorNumber, Elevator elevator) {
        Request request = new Request(upPressed, floorNumber);
        elevator.addRequestToQueue(request);
    }

    public static void refreshQueue(int floorNum, Elevator ele) {
        ArrayList<Request> newRequests = new ArrayList<>();
        for (Request r : ele.getRequests()) {
            if (r.getFloorNumber() != floorNum) {
                newRequests.add(r);
            }
        }
        ele.refreshRequests(newRequests);
    }
}
