package View;

import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.input.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.transform.Rotate;
import javafx.scene.effect.DropShadow;
import javafx.scene.control.ButtonBar;
import javafx.animation.KeyFrame;
import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;


import Model.Carte;
import Model.JeuCarte;
import Model.Player;
import Controller.Controller;


public class View extends Application {


    //main timeline
    private Timeline timeline, timelineFlip, timelineSort;

    //variable for storing actual frame
    private Integer cptF = 0;

    int x = 1800;
    int y = 600;

    // Create the ButtonBar instance
    Font font = new Font("Arial", 28);

    public void createButton(Button btn, String title, int x, int y){
        btn.setText(title);
        btn.setMinSize(400,120);
        btn.setMaxSize(400,120);
        btn.setTranslateX(x);
        btn.setTranslateY(y);
        btn.setFont(font);
        btn.setVisible(true);
    }

    @Override public void start(Stage stage) {


        Controller controller = new Controller();

        stage = controller.stage;


        Player me = controller.getMe();
        Player player2 = controller.getPlayer2();
        Player player3 = controller.getPlayer3();
        Player player4 = controller.getPlayer4();
        Player dog = controller.getDog();


        Group groupCard = controller.getGroupCard();
        Group groupButton = controller.getGroupButton();
        Group group = new Group(groupButton, groupCard);

        Scene scene = new Scene(group);
        scene.getStylesheets().add(View.class.getResource("DarkTheme.css").toExternalForm());

        createButton(controller.PButton, "PRISE DU CHIEN", -300, -300);
        createButton(controller.GButton, "GARDE", 100, -300);
        createButton(controller.GACButton, "GARDE SANS CHIEN", 500, -300);
        createButton(controller.GSCButton, "GARDE CONTRE CHIEN", 900, -300);

        // Create the ButtonBar instance
        Font font = new Font("Arial", 28);

        // Create the buttons to go into the ButtonBar
        Button PButton = new Button("PRISE DU CHIEN");
        PButton.setMinSize(400,120);
        PButton.setMaxSize(400,120);
        PButton.setTranslateX(-300);
        PButton.setTranslateY(-300);
        PButton.setFont(font);
        PButton.setVisible(true);

        Button GButton = new Button("GARDE");
        GButton.setMinSize(400,120);
        GButton.setMaxSize(400,120);
        GButton.setTranslateX(100);
        GButton.setTranslateY(-300);
        GButton.setFont(font);
        GButton.setVisible(true);

        Button GSCButton = new Button("GARDE SANS CHIEN");
        GSCButton.setMinSize(400,120);
        GSCButton.setMaxSize(400,120);
        GSCButton.setTranslateX(500);
        GSCButton.setTranslateY(-300);
        GSCButton.setFont(font);
        GSCButton.setVisible(true);

        Button GACButton = new Button("GARDE CONTRE CHIEN");
        GACButton.setMinSize(400,120);
        GACButton.setMaxSize(400,120);
        GACButton.setTranslateX(900);
        GACButton.setTranslateY(-300);
        GACButton.setFont(font);
        GACButton.setVisible(true);


        Camera camera = new PerspectiveCamera(true);
        camera.setTranslateZ(-1700);
        camera.setTranslateX(200);
        camera.setTranslateY(900);
        camera.setNearClip(0.2);
        camera.setFarClip(2000.0);
        camera.setRotationAxis(Rotate.X_AXIS);
        camera.setRotate(20);
        scene.setCamera(camera);


        stage.setScene(scene);
        stage.setWidth(x);
        stage.setHeight(y);
        stage.setTitle("Let's play Tarot!");

        JeuCarte cardGame = controller.getCardGame();

        //g.getChildren().addAll(board.getNodes());

        for(Carte e : cardGame)
        {
            groupCard.getChildren().addAll(e.getNodes());
        }

        stage.show();

        timeline = new Timeline();
        timeline.setCycleCount(30);

        //create a keyFrame, the keyValue is reached at time 2s
        Duration duration = Duration.millis(400);
        //one can add a specific action when the keyframe is reached


        EventHandler onFinished = controller.distribute;

        timelineFlip = new Timeline();
        timelineFlip.setCycleCount(24);

        //create a keyFrame, the keyValue is reached at time 2s
        Duration durationFlip = Duration.millis(100);
        //one can add a specific action when the keyframe is reached

        EventHandler onFinishedFlip = controller.flip;

        timelineSort = new Timeline();
        timelineSort.setCycleCount(1);


        //create a keyFrame, the keyValue is reached at time 2s
        Duration durationSort = Duration.millis(500);
        //one can add a specific action when the keyframe is reached


        EventHandler onFinishedSort = controller.sort;

        controller.PButton.setOnAction(controller.actionButtonPrise);

        /*for(int i=0; i<g.getChildren().size(); i++)
        {
            g.getChildren().get(i).setOnMousePressed(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent mouseE) {

                }
            });
        }*/

        KeyFrame keyFrame = new KeyFrame(duration, onFinished);
        KeyFrame keyFrameFlip = new KeyFrame(durationFlip, onFinishedFlip);
        KeyFrame keyFrameSort = new KeyFrame(durationSort, onFinishedSort);


        //add the keyframe to the timeline
        timeline.getKeyFrames().add(keyFrame);
        timelineFlip.getKeyFrames().add(keyFrameFlip);
        timelineSort.getKeyFrames().add(keyFrameSort);


        SequentialTransition sequence = new SequentialTransition(timeline, timelineFlip, timelineSort);
        sequence.play();

    }


    public static void main(String[] args) {
        Application.launch(args);
    }
}