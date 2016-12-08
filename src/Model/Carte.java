package Model;

import java.util.ArrayList;
import java.util.Collection;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;


/*
 * Nom de classe : Carte
 *
 * Description   : La classe Carte permet la création de carte celon certain attributs
 *
 * Version       : 2.6
 *
 * Date          : 15/11/2016
 *
 * Copyright     : Julien Germanaud et Nicolas Gras
 */
public class Carte {
    public double x, y, z;
    public int w, h;
    public String id = "";
    ImageView devant = new ImageView();
    ImageView dos = new ImageView();
    static Image imageDos = new Image("file:./resources/backside.jpg");
    static long halfFlipDuration = 500;

    public Carte(double x, double y, double z, String fichier){
        Image imageDevant = new Image("file:./resources/"+fichier+".jpg");
        devant.setImage(imageDevant);
        devant.setTranslateX(x);
        devant.setTranslateY(y);
        devant.setTranslateZ(z);
        dos.setImage(imageDos);
        dos.setTranslateX(x);
        dos.setTranslateY(y);
        dos.setTranslateZ(z);

        this.x = devant.getTranslateX();
        this.y = devant.getTranslateY();
        this.z = devant.getTranslateZ();
        this.w = 100;
        this.h = 180;
        this.id = fichier;
    }

    /**
     *  La méthode retourne l'id de la carte
     */
    public int id(){
        return Integer.valueOf(id);
    }

    public Collection<Node> getNodes(){
        ArrayList<Node> al = new ArrayList<>();
        al.add(devant);
        al.add(dos);
        return al;
    }

    public Node getFront(){
        return devant;
    }

    /**
     *  La méthode permet de retourner les cartes du joueur
     *  FACE CACHE ==> FACE RETOURNE
     */
    public Transition flip() {

        final RotateTransition rotateOutFront = new RotateTransition(Duration.millis(halfFlipDuration), dos);
        rotateOutFront.setInterpolator(Interpolator.LINEAR);
        rotateOutFront.setAxis(Rotate.Y_AXIS);
        rotateOutFront.setFromAngle(0);
        rotateOutFront.setToAngle(90);
        devant.setVisible(false);

        rotateOutFront.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dos.setVisible(false);
                devant.setVisible(true);
            }
        });

        //
        final RotateTransition rotateInBack = new RotateTransition(Duration.millis(halfFlipDuration), devant);
        rotateInBack.setInterpolator(Interpolator.LINEAR);
        rotateInBack.setAxis(Rotate.Y_AXIS);
        rotateInBack.setFromAngle(-90);
        rotateInBack.setToAngle(0);
        //

        return new SequentialTransition(rotateOutFront, rotateInBack);
    }

    /**
     *  La méthode permet de retourner les cartes du joueur
     *  FACE RETOURNE ==> FACE CACHE
     */
    public Transition flipInv() {

        final RotateTransition rotateOutFront = new RotateTransition(Duration.millis(halfFlipDuration), devant);
        rotateOutFront.setInterpolator(Interpolator.LINEAR);
        rotateOutFront.setAxis(Rotate.Y_AXIS);
        rotateOutFront.setFromAngle(0);
        rotateOutFront.setToAngle(90);
        dos.setVisible(false);

        rotateOutFront.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                devant.setVisible(false);
                dos.setVisible(true);
            }
        });

        //
        final RotateTransition rotateInBack = new RotateTransition(Duration.millis(halfFlipDuration), dos);
        rotateInBack.setInterpolator(Interpolator.LINEAR);
        rotateInBack.setAxis(Rotate.Y_AXIS);
        rotateInBack.setFromAngle(-90);
        rotateInBack.setToAngle(0);
        //

        return new SequentialTransition(rotateOutFront, rotateInBack);
    }

    /**
     *  La méthode permet le déplacement des cartes
     */
    public Transition move(int a, int b) {

        Path path1 = new Path();
        path1.getElements().add (new MoveTo (x+w, y+h));
        path1.getElements().add (new LineTo (a, b));

        PathTransition pathTransition1 = new PathTransition();
        pathTransition1.setDuration(Duration.millis(2000));
        pathTransition1.setNode(dos);
        pathTransition1.setPath(path1);

        Path path2 = new Path();
        path2.getElements().add (new MoveTo (x+w, y+h));
        path2.getElements().add (new LineTo (a, b));

        PathTransition pathTransition2 = new PathTransition();
        pathTransition2.setDuration(Duration.millis(2000));
        pathTransition2.setNode(devant);
        pathTransition2.setPath(path2);

        this.x = a;
        this.y = b;

        return new ParallelTransition(pathTransition1, pathTransition2);
    }


}
