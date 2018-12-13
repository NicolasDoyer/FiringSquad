package helpers; /**
 * 
 */

import problem.Neighbour;

import java.util.Random;
import java.util.Vector;

/**
 * @author verel
 *
 */
public class Initializer {
	
	Random generator;
	
	public Initializer() {
		generator = new Random();
	}

	// Init only usefull rules
	public void init(int [] rules) {

		for(int g = 0; g < 4; g++){
			for(int c = 0; c < 4; c++){
				for(int d = 0; d < 4; d++){
					if( !(g == 0 && c == 0 && d == 0) && !(g == 1 && c == 1 && d == 1) )
						setRule(rules,g,c,d,generator.nextInt(4));
				}
			}
		}

		for(int c = 0; c < 4; c++){
			for(int d = 0; d < 4; d++){
				if( !(c == 0 && d == 0) && !(c == 1 && d == 1) && !(c == 1 && d == 0) )
				setRule(rules,5,c,d,generator.nextInt(4));
			}
		}


		for(int g = 0; g < 4; g++){
			for(int c = 0; c < 4; c++){
				if( !(g == 0 && c == 0) && !(g == 1 && c == 1) && !(g == 1 && c == 0) )
				setRule(rules,g,c,5,generator.nextInt(4));
			}
		}

		// les regles repos (obligatoires)
		setRule(rules, 0, 0, 0, 0);
		setRule(rules, 5, 0, 0, 0);
		setRule(rules, 0, 0, 5, 0);
		
		// les regles feu (trés conseillés)
		setRule(rules, 1, 1, 1, 4);
		setRule(rules, 5, 1, 1, 4);
		setRule(rules, 1, 1, 5, 4);
		
		// les regles a priori (signal de période 2 vers la droite)
		setRule(rules, 1, 0, 0, 2);
		setRule(rules, 2, 0, 0, 1);

		// a priori bord droit
		setRule(rules, 1, 0, 5, 1); // pour taille 2
		setRule(rules, 2, 0, 5, 2);
		
		// a priori bord gauche (pour la taille 2)
		setRule(rules, 5, 1, 0, 1);
	}

    /*
     * Ecrit la regle 
     *
     * g : etat de la cellule de gauche
     * c : etat de la cellule centrale
     * d : etat de la cellule de droite
     * r : etat de la cellule centale à t+1
     */
    public void setRule(int [] rules, int g, int c, int d, int r) {
		rules[g * 36 + c * 6 + d] = r;
    }

    public void setRule(int[] rules, int index, int r){
    	rules[index] = r;
	}

}