package Model;

import java.util.ArrayList;
import java.util.Arrays;

/*
 * Nom de classe : Player
 *
 * Description   : La classe contient les cartes de chaque joueur et de les mélanger
 *
 * Version       : 2.6
 *
 * Date          : 15/11/2016
 *
 * Copyright     : Julien Germanaud et Nicolas Gras
 */

public class Player {

    public int player;

    private ArrayList<Carte> mesCartes = new ArrayList<Carte>();

    public Player(int player){
        this.player = player;
    }

    public void ajoutCarte(Carte carte){
        getMesCartes().add(carte);
    }

    /**
     *  La méhode permet de trier en passant par un tableau
     */
    public void sort(ArrayList<Carte> mesCartes){
        int i=0;
        int tab[] = new int[mesCartes.size()];
        double tabX[] = new double[mesCartes.size()];
        double tabY[] = new double[mesCartes.size()];


        for(Carte e: mesCartes){
            tab[i] = Integer.valueOf(e.id);
            tabX[i] = e.x;
            tabY[i] = e.y;
            i++;
        }


        Arrays.sort(tab);


        mesCartes.clear();

        for(int j=0; j<i; j++){
            mesCartes.add(j, new Carte(tabX[j], tabY[j], 0, String.valueOf(tab[j]))); // problÃ¨me Ã  rÃ©gler pour finir le tri
        }
    }

    public ArrayList<Carte> getMesCartes() {
        return mesCartes;
    }


}
