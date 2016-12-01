package sample;

import java.util.ArrayList;
import java.util.Collections;

public class JeuCarte extends ArrayList<Carte>{

    private int x, y;

    JeuCarte(){
        for(int i=0; i<78; i++){
            this.add(i, new Carte(x, y, 0, String.valueOf(i+1)));
        }

        Collections.shuffle(this);
    }

    void Shuffle(){
        Collections.shuffle(this);
    }

}
