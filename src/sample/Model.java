package sample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;

public class Model extends Observable {

    private ArrayList<Carte> cards;

    public Model(ArrayList<Carte> cards) {
        this.cards = cards;
        Collections.shuffle(cards);
    }

    public ArrayList<Carte> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Carte> cards) {
        this.cards = cards;
        setChanged();
        notifyObservers();
    }

    public void distribution(JeuCarte jeu, Player player, int x, int y, int pos, int cpt){
        for(int j=pos; j<pos+3; j++){
            jeu.get(j).move(x*cpt, y).play();
            player.ajoutCarte(jeu.get(j));
            if(player.player == 1)
                cpt++;
        }

        pos += 3;

        //reset counter
        if(player.player == 1 && cpt>5)
        {
            cpt = 1;
            y += 100;
        }
    }

}