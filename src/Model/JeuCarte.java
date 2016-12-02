package Model;

import java.util.ArrayList;
import java.util.Collections;

public class JeuCarte extends ArrayList<Carte>{

    private int x, y;

    private int transX = 110;
    private int transY = 400;
    private int player = 1;
    private int pos = 0;

    private Integer cpt = 1;
    private Integer cptC = 1;
    private Integer cptF = 0;
    private Integer cptFC = 0;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getTransX() {
        return transX;
    }

    public void setTransX(int transX) {
        this.transX = transX;
    }

    public int getTransY() {
        return transY;
    }

    public void setTransY(int transY) {
        this.transY = transY;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public Integer getCpt() {
        return cpt;
    }

    public void setCpt(Integer cpt) {
        this.cpt = cpt;
    }

    public Integer getCptC() {
        return cptC;
    }

    public void setCptC(Integer cptC) {
        this.cptC = cptC;
    }

    public Integer getCptF() {
        return cptF;
    }

    public void setCptF(Integer cptF) {
        this.cptF = cptF;
    }

    public Integer getCptFC() {
        return cptFC;
    }

    public void setCptFC(Integer cptFC) {
        this.cptFC = cptFC;
    }


    public JeuCarte(){
        for(int i=0; i<78; i++){
            this.add(i, new Carte(x, y, 0, String.valueOf(i+1)));
        }

        Collections.shuffle(this);
    }


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


}
