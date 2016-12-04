package Controller;

import Model.JeuCarte;
import Model.Player;
import View.View;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import Model.Carte;


public class Controller {

    Group groupCard = new Group();
    Group groupButton = new Group();

    Group groupMyCards = new Group();

    public Stage stage = new Stage();

    int cptDog = 0;

    JeuCarte cardGame = new JeuCarte();

    Player me = new Player(1);
    Player player2 = new Player(2);
    Player player3 = new Player(3);
    Player player4 = new Player(4);
    Player dog = new Player(5);

    Button PriseButton = new Button();
    Button PasseButton = new Button();
    Button FiniButton = new Button();

    public Group getGroupMyCards() {
        return groupMyCards;
    }

    public Group getGroupButton() {
        return groupButton;
    }

    public Group getGroupCard() {
        return groupCard;
    }

    public JeuCarte getCardGame() {
        return cardGame;
    }

    public Button getPriseButton() {
        return PriseButton;
    }

    public Button getPasseButton() {
        return PasseButton;
    }

    public Button getFiniButton() {
        return FiniButton;
    }


    /**
     * Create a controller. *
     * @param model
     * The model. */
    public Controller() {

    }

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

    public EventHandler flip =  new EventHandler<ActionEvent>() {
        public void handle(ActionEvent t) {
            if(cardGame.getCptF()<18){
                me.getMesCartes().get(cardGame.getCptF()).flip().play();
                cardGame.setCptF(cardGame.getCptF()+1);
            }
        }
    };

    public EventHandler sort = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent t) {

            me.sort(me.getMesCartes());

            int j = 0;

            for(Carte e: cardGame){
                for(int i=0; i<me.getMesCartes().size(); i++){

                    if(e.id() == me.getMesCartes().get(i).id()){
                        cardGame.get(j).move((int) me.getMesCartes().get(i).x-me.getMesCartes().get(i).w, (int) me.getMesCartes().get(i).y-me.getMesCartes().get(i).h).play();
                    }
                }
                j++;
            }
            groupButton.getChildren().add(PriseButton);
            groupButton.getChildren().add(PasseButton);
            groupButton.getChildren().add(FiniButton);
            stage.show();
        }
    };

    public EventHandler actionButtonPrise = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {

            groupButton.getChildren().clear();
            stage.show();

            cardGame.TakeDog(dog.getMesCartes(), me, cardGame.getTransX(), cardGame.getTransY());
            stage.show();

            int j = 0;

            for (Carte e : cardGame) {
                for (int i = 0; i < me.getMesCartes().size(); i++) {

                    if (e.id() == me.getMesCartes().get(i).id()) {
                        cardGame.get(j).move((int) me.getMesCartes().get(i).x-100, (int) me.getMesCartes().get(i).y-200).play();
                    }
                }
                j++;
            }

            me.getMesCartes().clear();


            for(int i=1; i<groupCard.getChildren().size(); i++)
            {
                if(i!=29 && i!=49 && i!=78)
                    groupCard.getChildren().get(i).addEventHandler(MouseEvent.MOUSE_CLICKED, chose);
            }

        }
    };

    public EventHandler chose = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseE) {

            if(cptDog < 6) {
                groupCard.getChildren().remove(mouseE.getSource());
                cptDog++;
            }

            stage.show();

        }
    };


    public EventHandler actionButtonPasse =  new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            stage.close();
            Platform.runLater( () -> new View().start( new Stage() ));
        }
    };

    public EventHandler actionButtonFini =  new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            Platform.exit();
        }
    };
}