package Model;


import View.Carte;

import java.util.ArrayList;
import java.util.Collections;

/*
 * Nom de classe : JeuCarte
 *
 * Description   : La classe permet de créer le paquet de cartes et de le distribuer
 *
 * Version       : 2.0
 *
 * Date          : 15/11/2016
 *
 * Copyright     : Julien Germanaud et Nicolas Gras
 */

public class JeuCarte extends ArrayList<Carte> {


    private int x=500, y=-300;

    private int transX = 110;
    private int transY = 400;
    private int player = 1;
    private int pos = 0;

    private Integer cpt = 1;
    private Integer cptC = 1;
    private Integer cptF = 0;

    public int getTransX() {
        return transX;
    }

    public int getTransY() {
        return transY;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public Integer getCptF() {
        return cptF;
    }

    public void setCptF(Integer cptF) {
        this.cptF = cptF;
    }

    /**
     *  Le constructeur de la classe, il permet de créer
     *  le jeu de carte et de les mélanger
     */
    public JeuCarte(){
        for(int i=0; i<78; i++){
            this.add(i, new Carte(x, y, 0, String.valueOf(i+1)));
        }
        Collections.shuffle(this);
    }

    /**
     *  La méhode permet de distribuer des cartes
     */
    public void distribute(Player player, int x, int y){
        if(player.player != 5) {
            for (int j = pos; j < pos + 3; j++) {
                this.get(j).move(x * cpt, y).play();
                player.ajoutCarte(this.get(j));
                if (player.player == 1)
                    cpt++;
            }

            pos += 3;

            //reset counter
            if (player.player == 1 && cpt > 9) {
                cpt = 1;
                transY += 200;
            }
        }
        else{
            this.get(pos).move(transX+transX*cptC, 0).play();
            player.ajoutCarte(this.get(pos));
            cptC += 1;
            this.player = 2;
            pos += 1;
        }
    }

    /**
     *  La méhode permet de prendre le chien et de l'ajouter au jeu du joueur
     */
    public void TakeDog(ArrayList<Carte> cardGame, Player player, int x, int y){
            for (int j = 0; j < 6; j++) {
                cardGame.get(j).flip().play();
                cardGame.get(j).move(x * (j+1), y).play();
                player.ajoutCarte(cardGame.get(j));
            }
            cardGame.clear();

            player.sort(player.getMesCartes());
    }



}
