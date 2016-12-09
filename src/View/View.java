package View;

import Model.Player;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.paint.Color;
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


import Model.JeuCarte;
import Controller.Controller;


/*
 * Nom de classe : View
 *
 * Description   : La classe gère tous les événements visuel de la distribution
 *
 * Version       : 4.2
 *
 * Date          : 15/11/2016
 *
 * Copyright     : Julien Germanaud et Nicolas Gras
 */

public class View extends Application {


    //main timeline
    private Timeline timeline, timelineFlip, timelineSort;

    int x = 1800;
    int y = 900;

    /**
     *  La méhode permet de créer un bouton
     */
    public void createButton(Button btn, String title, int x, int y){
        btn.setText(title);
        btn.setMinSize(300,100);
        btn.setMaxSize(300,100);
        btn.setTranslateX(x);
        btn.setTranslateY(y);
        btn.setTranslateZ(-2);
        btn.setFont(new Font("Arial", 12));
        btn.setVisible(true);
    }

    /**
     *  La méthode start permet de lancer le jeu, c'est la méthode qui contient
     *  toutes les actions du jeu
     */
    @Override public void start(Stage stage) {

        Controller controller = new Controller();

        controller.setStage(stage);

        Group group = new Group(controller.getGroupButton(), controller.getGroupCard(), controller.getGroupMyCards());

        // set les propriétés de la scène
        controller.setScene(new Scene(group));
        controller.getScene().setFill(Color.GREEN);
        controller.getScene().getStylesheets().add(View.class.getResource("DarkTheme.css").toExternalForm());


        // création des boutons
        createButton(controller.getPriseButton(), "PRISE", -300, -300);
        createButton(controller.getGardeButton(), "GARDE", 0, -300);
        createButton(controller.getPassButton(), "PASSE", 300, -300);
        createButton(controller.getGardeSansButton(), "GARDE SANS CHIEN", 600, -300);
        createButton(controller.getGardeContreButton(), "GARDE CONTRE CHIEN", 900, -300);
        createButton(controller.getFiniButton(), "DISTRIBUTION FINI", 1200, -300);


        // ajout des événements aux boutons
        controller.getPriseButton().setOnAction(controller.actionButtonPrise);
        controller.getGardeButton().setOnAction(controller.actionButtonPrise);
        controller.getPassButton().setOnAction(controller.actionButtonPass);
        controller.getGardeSansButton().setOnAction(controller.actionButtonGardeSans);
        controller.getGardeContreButton().setOnAction(controller.actionButtonGardeContre);
        controller.getFiniButton().setOnAction(controller.actionButtonFini);

        // set la caméra
        Camera camera = new PerspectiveCamera(true);
        camera.setTranslateZ(-2000);
        camera.setTranslateX(550);
        camera.setTranslateY(1000);
        camera.setNearClip(1.0);
        camera.setFarClip(5000.0);
        camera.setRotationAxis(Rotate.X_AXIS);
        camera.setRotate(20);
        controller.getScene().setCamera(camera);

        // set la scène
        stage.setScene(controller.getScene());
        stage.setWidth(x);
        stage.setHeight(y);
        stage.setTitle("Let's play Tarot!");
        stage.setFullScreen(true);

        JeuCarte cardGame = controller.getCardGame();


        // ajout des Carte au groupe
        for(Carte e : cardGame)
        {
            controller.getGroupCard().getChildren().addAll(e.getNodes());
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

        stage.show();

        KeyFrame keyFrame = new KeyFrame(duration, onFinished);
        KeyFrame keyFrameFlip = new KeyFrame(durationFlip, onFinishedFlip);
        KeyFrame keyFrameSort = new KeyFrame(durationSort, onFinishedSort);




        //add the keyframe to the timeline
        timeline.getKeyFrames().add(keyFrame);
        timelineFlip.getKeyFrames().add(keyFrameFlip);
        timelineSort.getKeyFrames().add(keyFrameSort);

        // séquence des timelines
        SequentialTransition sequence = new SequentialTransition(timeline, timelineFlip, timelineSort);
        sequence.play();

    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}