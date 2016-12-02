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
import javafx.scene.effect.DropShadow;

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

    @Override public void start(Stage stage) {

        Controller controller = new Controller();


        Player me = controller.getMe();
        Player player2 = controller.getPlayer2();
        Player player3 = controller.getPlayer3();
        Player player4 = controller.getPlayer4();
        Player dog = controller.getDog();


        Group p = new Group();
        Scene scene = new Scene(p);

        // Create the ButtonBar instance
        Font font = new Font("Arial", 28);

        // Create the buttons to go into the ButtonBar
        Button PButton = new Button("PRISE DU CHIEN");
        PButton.setMinSize(200,120);
        PButton.setMaxSize(200,120);
        PButton.setTranslateX(-200);
        PButton.setTranslateY(-300);
        PButton.setFont(font);
        PButton.setVisible(true);


        Button GButton = new Button("GARDE");
        GButton.setMinSize(200,120);
        GButton.setMaxSize(200,120);
        GButton.setTranslateX(0);
        GButton.setTranslateY(-300);
        GButton.setFont(font);
        GButton.setVisible(true);

        Button GSCButton = new Button("GARDE SANS CHIEN");
        GSCButton.setMinSize(200,120);
        GSCButton.setMaxSize(200,120);
        GSCButton.setTranslateX(200);
        GSCButton.setTranslateY(-300);
        GSCButton.setFont(font);
        GSCButton.setVisible(true);

        Button GACButton = new Button("GARDE CONTRE CHIEN");
        GACButton.setMinSize(200,120);
        GACButton.setMaxSize(200,120);
        GACButton.setTranslateX(400);
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

        //p.getChildren().addAll(board.getNodes());

        for(Carte e : cardGame)
        {
            p.getChildren().addAll(e.getNodes());
        }

        stage.show();

        timeline = new Timeline();
        timeline.setCycleCount(30);

        //create a keyFrame, the keyValue is reached at time 2s
        Duration duration = Duration.millis(300);
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


        EventHandler onFinishedSort = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {

                me.sort(me.getMesCartes());

                int j = 0;

                for(Model.Carte e: cardGame){
                    for(int i=0; i<me.getMesCartes().size(); i++){

                        if(e.id() == me.getMesCartes().get(i).id()){
                            cardGame.get(j).move((int) me.getMesCartes().get(i).x, (int) me.getMesCartes().get(i).y).play();
                        }
                    }
                    j++;
                }
                p.getChildren().add(PButton);
                p.getChildren().add(GButton);
                p.getChildren().add(GACButton);
                p.getChildren().add(GSCButton);
                stage.show();
            }

        };

        PButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                for(int i=0; i<6; i++){
                    dog.getMesCartes().get(i).flip().play();
                }
                p.getChildren().removeAll(PButton, GButton);
                p.getChildren().removeAll(GACButton, GSCButton);
                stage.show();
            }
        });

        for(int i=0; i<p.getChildren().size(); i++){
            p.getChildren().get(i).setOnMousePressed(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent me) {

                }
            });
        }



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