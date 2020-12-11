import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class Environnement implements Serializable{
	private static final long serialVersionUID = 3968155942580492870L;
	public int maxNiv;
	public int cursorNiv;
	public Niveau current;
	private Ecran screen;
	//

	public Environnement () {
		maxNiv = 2;
		cursorNiv = 1;
	}
	
	// Pour avoir accès à l'écran à partir de l'environnement
	public void setScreen(Ecran e) {
		screen = e;
	}
	
	//format des fichiers niveaux : NiveauX.niv avec X = son numéro, exemple : Niveau4.niv
	public void chargerNiveau() {
		File niveau = new File("Niveau"+cursorNiv+".niv");
		System.out.println("Chargement du niveau");
		if (niveau.exists()) {
			try { //chargement du fichier de niveau sauvegardé
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(niveau));
				current = (Niveau)ois.readObject();
				ois.close();
				current.setEnvironnement(this);
				AffichageNiv panelNiv= new AffichageNiv(current);
				this.screen.setContentPane(panelNiv);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ReflectiveOperationException e) {
				e.printStackTrace();
			}
		} else { // Tant qu'on a pas de fichier sauvegardés pour les niveaux
			System.out.println("Raté ce fichier n'existe pas.");
			current = new Niveau(cursorNiv);
			current.setEnvironnement(this);
			AffichageNiv panelNiv= new AffichageNiv(current);
			this.screen.setContentPane(panelNiv);
			this.screen.setVisible(true);
		}
	}
	
	// Le niveau a été quitté donc le score ou nombre de niveau disponibles ont peut être changer
	public void niveauFini() {
		if (this.cursorNiv == maxNiv)
			maxNiv++;
		System.out.println(cursorNiv + " " + maxNiv);
		this.screen.select();
	}
}
