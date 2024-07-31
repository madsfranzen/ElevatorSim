package model;

import gui.GUI;

import java.util.ArrayList;

public class Elevator {
    private int currentFloor;
    private ArrayList<Request> requests = new ArrayList<>();
    private boolean isMoving = false;

    public Elevator() {
        currentFloor = 0;
    }

    public void sendElevator(int floorNum) {
        GUI.moveElevator(currentFloor, floorNum, this);
        currentFloor = floorNum; // CHANGE: Should be set at the time of arrival, not departure (fix in animation handler)
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void addRequestToQueue(Request request) {
        if (requests.size() > 1) {
            Request nextFloor = requests.getFirst();
            // GOING DOWN
            if (nextFloor.getFloorNumber() < currentFloor) {
                System.out.println("Going Down");
                if (request.getFloorNumber() < currentFloor && request.getFloorNumber() > nextFloor.getFloorNumber()) {
                    requests.addFirst(request);
                }
            } else {
                System.out.println("Going Up");
                requests.addLast(request);
                // GOING UP
            }
        } else {
            requests.add(request);
            sendElevator(request.getFloorNumber());
        }
    }

    public ArrayList<Request> getRequests() {
        return requests;
    }

    public void refreshRequests(ArrayList<Request> newRequests) {
        requests.clear();
        requests.addAll(newRequests);
    }
}
