import java.io.Serializable;

public class Niveau implements Serializable {
	private static final long serialVersionUID = 1L;
	public transient Environnement envi;
	
	//Constantes à sauvegarder
	public final int num;
	public final Plateau startPlateau;
	public final int totalAlien;
	
	//Variables propre à la partie en cours
	public transient Plateau currentPlat;
	public transient int savedAlien;
	public transient int currentScore;
	
	// n'hésite pas à rajouter des choses au constructeur
	public Niveau(int i) {
		num = i;
		startPlateau = new Plateau(6, 6); 
		currentPlat =  new Plateau(6, 6);
		totalAlien = countAlien(startPlateau);
		savedAlien = 0;
		currentScore = 0;
	}

	public void jouer(int a, int b) {
		currentScore += currentPlat.supprimer(a, b, true);
		currentPlat.tomber();
		int saved = currentPlat.suppAlien();
		currentScore += saved*10000;
		currentPlat.glisser();
		savedAlien += saved;
	}
	
	public int countAlien(Plateau p) {
		int res = 0;
		for (Case[] ligne : p.grid)
			for (Case c : ligne)
				if (c.isAlien)
					res++;
		return res;
	}
	public void setEnvironnement(Environnement e) {
		envi = e;
	}
	
	// à appeler quand le niveau est fini et qu'on veux envoyer des informations
	// à l'environnement, n'hésite pas à mettre des arguments dans niveauFini si t'as besoin
	// mais n'oublie pas non plus que les informations de niveau se trouvent dans l'environnement
	// dans le Niveau current
	public void retour() {
		this.envi.niveauFini(savedAlien == totalAlien);
	}
}
