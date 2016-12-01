package sample;

import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;


public class View extends Application {

    //main timeline
    private Timeline timeline, timelineFlip, timelineSort;

    //variable for storing actual frame
    private Integer cpt = 1;
    private Integer cptC = 1;
    private Integer cptF = 0;
    private Integer cptFC = 0;
    int x = 600-100/2;
    int y = 300-170/2;
    int transX = 110;
    int transY = 400;
    int player = 1;
    int pos = 0;

    public void distribution(JeuCarte jeu, Player player, int x, int y){
        for(int j=pos; j<pos+3; j++){
            jeu.get(j).move(x*cpt, y).play();
            player.ajoutCarte(jeu.get(j));
            if(player.player == 1)
                cpt++;
        }

        pos += 3;

        //reset counter
        if(player.player == 1 && cpt>9)
        {
            cpt = 1;
            transY += 200;
        }
    }


    @Override public void start(Stage stage) {


        Player me = new Player(1);
        Player player2 = new Player(2);
        Player player3 = new Player(3);
        Player player4 = new Player(4);
        Player chien = new Player(5);


        Group p = new Group();
        Scene scene = new Scene(p);

        // Create the ButtonBar instance
        ButtonBar buttonBar = new ButtonBar();

        // Create the buttons to go into the ButtonBar
        Button PButton = new Button("PRISE");
        ButtonBar.setButtonData(PButton, ButtonData.YES);

        Button GButton = new Button("GARDE");
        ButtonBar.setButtonData(GButton, ButtonData.YES);

        Button GSCButton = new Button("GARDE SANS CHIEN");
        ButtonBar.setButtonData(GSCButton, ButtonData.YES);

        Button GACButton = new Button("GARDE CONTRE CHIEN");
        ButtonBar.setButtonData(GACButton, ButtonData.YES);

        // Add buttons to the ButtonBar
        buttonBar.getButtons().addAll(PButton, GButton);
        buttonBar.getButtons().addAll(GSCButton, GACButton);



        Camera camera = new PerspectiveCamera(true);
        camera.setTranslateZ(-2000);
        camera.setTranslateX(200);
        camera.setTranslateY(1000);
        camera.setNearClip(0.2);
        camera.setFarClip(3000.0);
        camera.setRotationAxis(Rotate.X_AXIS);
        camera.setRotate(20);
        scene.setCamera(camera);


        stage.setScene(scene);
        stage.setWidth(1800);
        stage.setHeight(600);
        stage.setTitle("Let's play Tarot!");

        JeuCarte jeu = new JeuCarte();
        GameBoard board = new GameBoard();



        //p.getChildren().addAll(board.getNodes());

        for(Carte e : jeu)
        {
            p.getChildren().addAll(e.getNodes());
        }

        p.getChildren().addAll(buttonBar);



        stage.show();

        //create a timeline for moving the circle
        timeline = new Timeline();
        timeline.setCycleCount(30);

        //create a keyFrame, the keyValue is reached at time 2s
        Duration duration = Duration.millis(300);
        //one can add a specific action when the keyframe is reached
        EventHandler onFinished = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                switch(player){
                    case 1:
                        distribution(jeu, me, transX, transY);
                        player = 5;
                        break;
                    case 2:
                        distribution(jeu, player2, transX+2000, transY);
                        player = 3;
                        break;
                    case 3:
                        distribution(jeu, player3, transX, transY-2000);
                        player = 4;
                        break;
                    case 4:
                        distribution(jeu, player4, transX-2000, transY);
                        player = 1;
                        break;
                    case 5:
                        jeu.get(pos).move(transX+transX*cptC, 0).play();
                        chien.ajoutCarte(jeu.get(pos));
                        cptC += 1;
                        player = 2;
                        pos += 1;
                        break;
                }
            }
        };

        //create a timeline for moving the circle
        timelineFlip = new Timeline();
        timelineFlip.setCycleCount(24);

        //create a keyFrame, the keyValue is reached at time 2s
        Duration durationFlip = Duration.millis(100);
        //one can add a specific action when the keyframe is reached
        EventHandler onFinishedFlip = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                if(cptF<18){
                    me.getMesCartes().get(cptF).flip().play();
                    cptF++;
                }
            }
        };

        //create a timeline for moving the circle
        timelineSort = new Timeline();
        timelineSort.setCycleCount(1);


        //create a keyFrame, the keyValue is reached at time 2s
        Duration durationSort = Duration.millis(500);
        //one can add a specific action when the keyframe is reached
        EventHandler onFinishedSort = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {

                me.sort(me.getMesCartes());

                int j = 0;

                for(Carte e: jeu){
                    System.out.println(e.id);
                    for(int i=0; i<me.getMesCartes().size(); i++){
                        //System.out.println(me.getMesCartes().get(i).id());

                        if(e.id() == me.getMesCartes().get(i).id()){
                            jeu.get(j).move((int) me.getMesCartes().get(i).x, (int) me.getMesCartes().get(i).y).play();
                        }
                    }
                    j++;
                }
            }

        };



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