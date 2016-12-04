package View;

import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.transform.Rotate;
import javafx.animation.KeyFrame;
import javafx.animation.SequentialTransition;
import javafx.event.EventHandler;


import Model.Carte;
import Model.JeuCarte;
import Controller.Controller;


public class View extends Application {


    //main timeline
    private Timeline timeline, timelineFlip, timelineSort;

    int x = 1800;
    int y = 900;

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


        Group groupCard = controller.getGroupCard();
        Group groupButton = controller.getGroupButton();
        Group groupMyCards = controller.getGroupMyCards();
        Group group = new Group(groupButton, groupCard, groupMyCards);

        Scene scene = new Scene(group);
        scene.getStylesheets().add(View.class.getResource("DarkTheme.css").toExternalForm());


        createButton(controller.getPriseButton(), "PRISE DU CHIEN", -300, -300);
        createButton(controller.getPasseButton(), "PASSE", 100, -300);
        createButton(controller.getFiniButton(), "DISTRIBUTION FINI", 500, -300);



        Camera camera = new PerspectiveCamera(true);
        camera.setTranslateZ(-1700);
        camera.setTranslateX(400);
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


        controller.getPriseButton().setOnAction(controller.actionButtonPrise);
        controller.getPasseButton().setOnAction(controller.actionButtonPasse);
        controller.getFiniButton().setOnAction(controller.actionButtonFini);


        stage.show();

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