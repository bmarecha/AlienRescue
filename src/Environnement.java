import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Environnement implements Serializable{
	private static final long serialVersionUID = 3968155942580492870L;
	public int maxNiv;
	public int cursorNiv;
	public int[] Scores = {0, 0, 0, 0, 0};
	transient Niveau current;
	transient Ecran screen;
	//

	public Environnement () {
		maxNiv = 1;
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
				current.initTransients(this);
				AffichageNiv panelNiv= new AffichageNiv(current);
				this.screen.setContentPane(panelNiv);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ReflectiveOperationException e) {
				e.printStackTrace();
			}
		} else { // Tant qu'on a pas de fichier sauvegardés pour les niveaux
			System.out.println("Fichier pas encore créé. Chargement du niveau par défaut.");
			current = new Niveau(cursorNiv);
			current.initTransients(this);
			AffichageNiv panelNiv= new AffichageNiv(current);
			this.screen.setContentPane(panelNiv);
			this.screen.setVisible(true);
		}
	}
	
	public void save() {
		File save = new File("ser/Environ.ser");
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(save));
			oos.writeObject(this);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}

	
	public static void play(File f, boolean music) {
		
	}

	
	
	// Le niveau a été quitté donc le score ou nombre de niveau disponibles ont peut être changer
	public void niveauFini(boolean gagner, boolean stop, int etoiles) {
		if (this.cursorNiv == maxNiv && gagner) {
			maxNiv++;
		}
		if (stop)
			this.screen.select();
		else if (gagner) {
			cursorNiv++;
			chargerNiveau();
		} else
			chargerNiveau();
	}
}
