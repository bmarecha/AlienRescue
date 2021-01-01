import java.io.Serializable;

public class Niveau implements Serializable {
	private static final long serialVersionUID = 6756182802449785285L;
	public final int num;
	public transient Environnement envi;
	public final Plateau startPlateau;
	public transient Plateau currentPlat;
	
	// n'hésite pas à rajouter des choses au constructeur
	public Niveau(int i) {
		num = i;
		startPlateau = new Plateau(6, 6); 
		currentPlat =  new Plateau(6, 6);
	}
	
	
	public void setEnvironnement(Environnement e) {
		envi = e;
	}
	
	// à appeler quand le niveau est fini et qu'on veux envoyer des informations
	// à l'environnement, n'hésite pas à mettre des arguments dans niveauFini si t'as besoin
	// mais n'oublie pas non plus que les informations de niveau se trouvent dans l'environnement
	// dans le Niveau current
	public void retour() {
		this.envi.niveauFini();
	}
}
