package Controller;

import Model.JeuCarte;
import Model.Player;
import View.View;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import View.Carte;


public class Controller {

    Group groupCard = new Group();
    Group groupButton = new Group();
    Group groupMyCards = new Group();

    Stage stage;

    Scene scene;

    Button PriseButton = new Button();
    Button GardeButton = new Button();
    Button GardeSansButton = new Button();
    Button GardeContreButton = new Button();
    Button PassButton = new Button();
    Button FiniButton = new Button();

    public Button getGardeSansButton() {
        return GardeSansButton;
    }

    public Button getGardeContreButton() {
        return GardeContreButton;
    }

    public Group getGroupCard() {
        return groupCard;
    }

    public Group getGroupButton() {
        return groupButton;
    }

    public Group getGroupMyCards() {
        return groupMyCards;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public Button getPriseButton() {
        return PriseButton;
    }

    public Button getGardeButton() {
        return GardeButton;
    }

    public Button getPassButton() {
        return PassButton;
    }

    public Button getFiniButton() {
        return FiniButton;
    }

    int cptDog = 0;
    int dogX=110;

    JeuCarte cardGame = new JeuCarte();

    Player me = new Player(1);
    Player player2 = new Player(2);
    Player player3 = new Player(3);
    Player player4 = new Player(4);
    Player dog = new Player(5);


    public JeuCarte getCardGame() {
        return cardGame;
    }

    /**
     * Evenement de distribution. Distribution
     */
    public EventHandler distribute = new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
            switch(cardGame.getPlayer()){
                case 1:
                    cardGame.distribute(me, cardGame.getTransX(), cardGame.getTransY());
                    cardGame.setPlayer(5);
                    break;
                case 2:
                    cardGame.distribute(player2, cardGame.getTransX()+2000, cardGame.getTransY());
                    cardGame.setPlayer(3);
                    break;
                case 3:
                    cardGame.distribute(player3, cardGame.getTransX(), cardGame.getTransY()-2000);
                    cardGame.setPlayer(4);
                    break;
                case 4:
                    cardGame.distribute(player4, cardGame.getTransX()-2000, cardGame.getTransY());
                    cardGame.setPlayer(1);
                    break;
                case 5:
                    cardGame.distribute(dog, cardGame.getTransX(), 0);
                    cardGame.setPlayer(2);
                    break;
            }
        }
    };

    /**
     * Evenement pour retourner les cartes
     */
    public EventHandler flip =  new EventHandler<ActionEvent>() {
        public void handle(ActionEvent t) {
            if(cardGame.getCptF()<18){
                me.getMesCartes().get(cardGame.getCptF()).flip().play();
                cardGame.setCptF(cardGame.getCptF()+1);
            }
        }
    };

    /**
     * Evenement de trie et ajout des boutons à la fin
     */
    public EventHandler sort = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent t) {

            me.sort(me.getMesCartes());

            int j = 0;

            for(Carte e: cardGame){
                for(int i=0; i<me.getMesCartes().size(); i++){
                    if(e.id() == me.getMesCartes().get(i).id()){
                        cardGame.get(j).move((int) me.getMesCartes().get(i).x, (int) me.getMesCartes().get(i).y).play();
                    }
                }
                j++;
            }
            groupButton.getChildren().add(PriseButton);
            groupButton.getChildren().add(GardeButton);
            groupButton.getChildren().add(GardeSansButton);
            groupButton.getChildren().add(GardeContreButton);
            groupButton.getChildren().add(PassButton);
            groupButton.getChildren().add(FiniButton);
            stage.show();
        }
    };

    /**
     * Evenement du bouton Prise
     */
    public EventHandler actionButtonPrise = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {

            groupButton.getChildren().clear();
            stage.show();

            cardGame.TakeDog(dog.getMesCartes(), me, cardGame.getTransX(), cardGame.getTransY());

            for (Carte e : cardGame) {
                for (int i = 0; i < me.getMesCartes().size(); i++) {
                    if (e.id() == me.getMesCartes().get(i).id()) {
                        e.move((int) me.getMesCartes().get(i).x, (int) me.getMesCartes().get(i).y-200).play();
                    }
                }
            }

            scene.addEventHandler(MouseEvent.MOUSE_PRESSED, ridOf);

        }
    };

    /**
     * Evenement pour écarter les cartes
     */
    public EventHandler ridOf = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseE) {

            for (Carte e : cardGame) {
                if (cptDog < 6) {
                    if ((mouseE.getX() >= e.x-e.w/2) && (mouseE.getX() <= e.x-e.w/2 + e.w) &&
                            (mouseE.getY() >= e.y-e.h/2) && (mouseE.getY() <= e.y-e.h/2 + e.h)) {
                        if(e.id()!=14 && e.id()!=28 && e.id()!=29 && e.id()!=49 && e.id()!=63 && e.id()!=77 && e.id()!=78){
                            e.move(dogX, 0).play();
                            dogX += 110;
                            cptDog++;
                            for (int i = 0; i < me.getMesCartes().size(); i++) {
                                if (e.id() == me.getMesCartes().get(i).id()) {
                                    me.getMesCartes().remove(i);
                                    dog.getMesCartes().add(e);
                                }
                            }
                        }

                    }
                }
            }

            if(cptDog==6){
                groupButton.getChildren().add(FiniButton);
                mouseE.consume();
            }
            stage.show();
        }
    };

    /**
     * Evenement pour recommencer le trie
     */
    public EventHandler actionButtonPass =  new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event){
            stage.close();
            Platform.runLater(() -> new View().start(new Stage()));
        }
    };

    /**
     * Evenement de Garde sans chien
     */
    public EventHandler actionButtonGardeSans =  new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event){

            groupButton.getChildren().clear();

            for (Carte e : cardGame) {
                for (int i = 0; i < dog.getMesCartes().size(); i++) {
                    if (e.id() == dog.getMesCartes().get(i).id()) {
                        e.move(1200, 200).play();
                    }
                }
            }

            groupButton.getChildren().add(FiniButton);
            stage.show();
        }
    };

    /**
     * Evenement de Garde contre chien
     */
    public EventHandler actionButtonGardeContre =  new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event){

            groupButton.getChildren().clear();

            for (Carte e : cardGame) {
                for (int i = 0; i < dog.getMesCartes().size(); i++) {
                    if (e.id() == dog.getMesCartes().get(i).id()) {
                        e.move(-300, -300).play();
                    }
                }
            }

            groupButton.getChildren().add(FiniButton);
            stage.show();
        }
    };

    /**
     * Evenement pour terminer la distribution
     */
    public EventHandler actionButtonFini = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            Platform.exit();
        }
    };
}