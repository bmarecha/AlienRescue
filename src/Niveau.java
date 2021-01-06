import java.io.Serializable;
import java.util.function.BiFunction;

public class Niveau implements Serializable {
	private static final long serialVersionUID = 1L;
	public transient Environnement envi;
	
	//Constantes à sauvegarder
	public final int num;
	public final Plateau startPlateau;
	public final int totalAlien;
	public final int totalCase;
	public final BiFunction<Integer, Integer, Integer> stars;
	public final int[] starScore = {2000, 4000, 5000};
	public int laser;
	public int acid;
	
	//Variables propre à la partie en cours
	public transient Plateau currentPlat;
	public transient int savedAlien;
	public transient int currentScore;
	public transient int gameState;
	public transient boolean laserS = false;
	public transient boolean acidG = false;
	
	// n'hésite pas à rajouter des choses au constructeur
	public Niveau(int i) {
		num = i;
		startPlateau = new Plateau(6, 6); 
		totalAlien = countAlien(startPlateau);
		totalCase = countCase(startPlateau);
		stars = ((score, alien) -> {
			int res = 0;
			if (score >= starScore[0] && alien == totalAlien) {
				res++;
				if (score >= starScore[1]) {
					res++;
					if (score >= starScore[2])
						res++;
				}
			}
			return res;
		});
		if (i > 3)
			laser = 1;
		if (i > 2)
			acid = 1;
	}
	
	public void initTransients(Environnement e) {
		envi = e;
		try {
			currentPlat =  startPlateau.clone();
		} catch (CloneNotSupportedException e1) {
			currentPlat =  new Plateau(6, 6);
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		savedAlien = 0;
		currentScore = 0;
		gameState = 1;
	}

	public void chooseAcid() {
		if (acid > 0)
			acidG = true;
	}
	
	public void chooseLaser() {
		if (laser > 0)
			laserS = true;
	}
	
	public void jouer(int a, int b) {
		if (acidG) {
			currentScore += currentPlat.suppZone(a, b, 1);
			acid--;
			acidG = false;
		} else if (laserS) {
			currentScore += currentPlat.suppLigne(a);
			laser--;
			laserS = false;
		} else {
			currentScore += currentPlat.supprimer(a, b, true);
		}
		currentPlat.tomber();
		int saved = currentPlat.suppAlien();
		currentPlat.tomber();
		currentScore += saved*1000;
		currentPlat.glisser();
		savedAlien += saved;
		gameState = stars.apply(currentScore, savedAlien);
		if (gameState == 0 && !currentPlat.jouable())
			gameState = -1;
	}
	
	public int countAlien(Plateau p) {
		int res = 0;
		for (Case[] ligne : p.grid)
			for (Case c : ligne)
				if (c.isAlien)
					res++;
		return res;
	}
	
	public int countCase(Plateau p) {
		int res = 0;
		for (Case[] ligne : p.grid)
			for (Case c : ligne)
				if (c.k == 1 || c.k == 2 || c.k == 3)
					res++;
		return res;
	}
	
	// à appeler quand le niveau est fini et qu'on veux envoyer des informations
	// à l'environnement, n'hésite pas à mettre des arguments dans niveauFini si t'as besoin
	// mais n'oublie pas non plus que les informations de niveau se trouvent dans l'environnement
	// dans le Niveau current
	public void retour(boolean stop) {
		this.envi.niveauFini(gameState > 0, stop, gameState);
	}
}
