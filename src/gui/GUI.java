package gui;

import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Elevator;
import controller.Controller;
import model.Request;

import java.util.ArrayList;

public class GUI extends Application {


    static Line elevatorPath = new Line();
    static PathTransition transition = new PathTransition();

    private static Elevator elevator1 = new Elevator();
    VBox vBoxDown = new VBox();
    VBox vBoxUp = new VBox();

    private static ArrayList<CheckBox> DownButtons = new ArrayList<>();
    private static ArrayList<CheckBox> upButtons = new ArrayList<>();


    static Label lblQueue = new Label("Queue: ");


    private static Rectangle elevator = new Rectangle(60, 20);

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Elevator Sim");
        GridPane pane = new GridPane();
        this.initContent(pane);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    private void initContent(GridPane pane) {
        for (int i = 0; i < 8; i++) {
            CheckBox chbDown = new CheckBox("↓");
            chbDown.setFocusTraversable(false);
            CheckBox chbUp = new CheckBox("↑");
            chbUp.setFocusTraversable(false);
            upButtons.add(chbUp);
            DownButtons.add(chbDown);
        }

        for (CheckBox chb : DownButtons) {
            chb.setOnAction(e -> btnAction((CheckBox) e.getSource()));
        }
        for (CheckBox chb : upButtons) {
            chb.setOnAction(e -> btnAction((CheckBox) e.getSource()));
        }

        pane.setMinSize(420, 820);
        pane.setMaxSize(420, 820);
        pane.setPadding(new Insets(10));
        pane.setGridLinesVisible(false);

        Pane elevatorPane = new Pane();
        GridPane buttonPane = new GridPane();
        GridPane numPlane = new GridPane();
        pane.add(elevatorPane, 1, 0);
        pane.add(buttonPane, 2, 0);
        pane.add(numPlane, 0, 0);

        Label lbl0 = new Label("0");
        Label lbl1 = new Label("1");
        Label lbl2 = new Label("2");
        Label lbl3 = new Label("3");
        Label lbl4 = new Label("4");
        Label lbl5 = new Label("5");
        Label lbl6 = new Label("6");
        Label lbl7 = new Label("7");

        VBox vboxNums = new VBox();
        numPlane.setVgap(10);
        numPlane.setHgap(10);
        numPlane.add(vboxNums, 0, 0);
        numPlane.setMinHeight(800);
        numPlane.setMaxHeight(800);
        vboxNums.setAlignment(Pos.CENTER);
        vboxNums.setPrefHeight(800);
        vboxNums.getChildren().setAll(lbl7, lbl6, lbl5, lbl4, lbl3, lbl2, lbl1, lbl0);
        vboxNums.setSpacing(82);
        numPlane.setPadding(new Insets(10, 10, 0, 10));

        buttonPane.setGridLinesVisible(false);
        buttonPane.setHgap(10);
        buttonPane.setVgap(10);
        buttonPane.setMinHeight(800);
        buttonPane.setMaxHeight(800);
        buttonPane.setPadding(new Insets(10, 10, 0, 10));

        buttonPane.add(vBoxDown, 0, 0);
        vBoxDown.getChildren().setAll(DownButtons);
        vBoxDown.setPrefHeight(800);
        vBoxDown.setAlignment(Pos.CENTER);
        vBoxDown.setSpacing(82);

        buttonPane.add(vBoxUp, 1, 0);
        vBoxUp.getChildren().setAll(upButtons);
        vBoxUp.setPrefHeight(800);
        vBoxUp.setAlignment(Pos.CENTER);
        vBoxUp.setSpacing(82);

        elevatorPane.getChildren().add(elevator);
        elevator.setFill(Color.AQUAMARINE);
        elevator.setStroke(Color.BLACK);

        elevator.setY(745);

        pane.add(lblQueue, 4, 0);
    }


    private void btnAction(CheckBox chb) {
        // Checking on which floor the button was pressed and whether they want to go up or down and sending request
        int floorPressed = 7;
        boolean upPressed = false;
        chb.setDisable(true);
        // When pressing "UP"
        if (chb.getParent().equals(vBoxUp)) {
            upPressed = true;
            for (CheckBox c : upButtons) {
                if (chb.equals(c)) {
                    break;
                }
                floorPressed--;
            }
            // When pressing "DOWN"
        } else {
            for (CheckBox c : DownButtons) {
                if (chb.equals(c)) {
                    break;
                }
                floorPressed--;
            }
        }
        Controller.requestElevator(upPressed, floorPressed, elevator1);
        updateQueue(floorPressed);
    }

    public static void moveElevator(int fromFloor, int toFloor, Elevator ele) {
        transition.setNode(elevator);
        transition.setDuration(Duration.seconds(3));
        elevatorPath.setStartX(30);
        elevatorPath.setEndX(30);
        elevatorPath.setStartY(800 - (fromFloor * 100) - 45);
        elevatorPath.setEndY(800 - (toFloor * 100) - 45);
        transition.setPath(elevatorPath);
        transition.play();
        transition.setOnFinished(e -> {
            Controller.refreshQueue(toFloor, ele);
            clearButtons(toFloor);
        });
    }

    public static EventHandler<ActionEvent> clearButtons(int floorNum) {
        upButtons.get(7 - floorNum).setDisable(false);
        upButtons.get(7 - floorNum).setSelected(false);
        DownButtons.get(7 - floorNum).setDisable(false);
        DownButtons.get(7 - floorNum).setSelected(false);
        updateQueue(floorNum);
        return null;
    }

    public static void updateQueue(int floorNum) {
        StringBuilder sb = new StringBuilder();
        for (Request r : elevator1.getRequests()) {
            sb.append(r.getFloorNumber());
        }
        lblQueue.setText("Queue: " + sb);

    }
}
