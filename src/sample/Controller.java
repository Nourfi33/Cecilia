package sample;

import javafx.animation.KeyFrame;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.util.Duration;

public class Controller {

    private Model model;
    /**
     * Create a controller. *
     * @param model
     * The model. */
    public Controller(Model model) {
        this.model = model;
    }
/*
    public void distribution(Timeline timeline) {
    	//create a timeline for moving the circle
        timeline = new Timeline();
        timeline.setCycleCount(30);

        //create a keyFrame, the keyValue is reached at time 2s
        Duration duration = Duration.millis(1000);
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
    }

    public void flip(Timeline timelineFlip){

        //create a timeline for moving the circle
        timelineFlip = new Timeline();
        timelineFlip.setCycleCount(24);

        //create a keyFrame, the keyValue is reached at time 2s
        Duration durationFlip = Duration.millis(500);
        //one can add a specific action when the keyframe is reached
        EventHandler onFinishedFlip = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
            	if(cptF<18){
                	me.getMesCartes().get(cptF).flip().play();
            		cptF++;
            	}
            	else{
            		chien.getMesCartes().get(cptFC).flip().play();
            		cptFC++;
            	}

            }
        };
    }

    public void sort(Timeline timelineSort){

        //create a timeline for moving the circle
        timelineSort = new Timeline();
        timelineSort.setCycleCount(24);

        //create a keyFrame, the keyValue is reached at time 2s
        Duration durationSort = Duration.millis(500);
        //one can add a specific action when the keyframe is reached
        EventHandler onFinishedSort = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
            	me.sort(me.getMesCartes());
            }
        };
    }

    public void animation(){

        KeyFrame keyFrame = new KeyFrame(duration, onFinished);
        KeyFrame keyFrameFlip = new KeyFrame(duration, onFinishedFlip);
        KeyFrame keyFrameSort = new KeyFrame(duration, onFinishedSort);



        //add the keyframe to the timeline
        timeline.getKeyFrames().add(keyFrame);
        timelineFlip.getKeyFrames().add(keyFrameFlip);
        timelineSort.getKeyFrames().add(keyFrameSort);

        SequentialTransition sequence = new SequentialTransition(timeline, timelineFlip, timelineSort);
        sequence.play();
    }
    */

}