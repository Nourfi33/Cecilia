package Controller;

import Model.JeuCarte;
import Model.Player;
import javafx.event.EventHandler;

import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;


public class Controller {

    JeuCarte cardGame = new JeuCarte();

    Player me = new Player(1);
    Player player2 = new Player(2);
    Player player3 = new Player(3);
    Player player4 = new Player(4);
    Player dog = new Player(5);

    public JeuCarte getCardGame() {
        return cardGame;
    }

    public void setCardGame(JeuCarte cardGame) {
        this.cardGame = cardGame;
    }

    public Player getMe() {
        return me;
    }

    public void setMe(Player me) {
        this.me = me;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public Player getPlayer3() {
        return player3;
    }

    public void setPlayer3(Player player3) {
        this.player3 = player3;
    }

    public Player getPlayer4() {
        return player4;
    }

    public void setPlayer4(Player player4) {
        this.player4 = player4;
    }

    public Player getDog() {
        return dog;
    }

    public void setDog(Player dog) {
        this.dog = dog;
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
}