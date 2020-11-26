import java.io.*;

public class Launcher{
	//static Environnement envi;
	
	public static void main(String[] args) {
		Environnement envi = null;
		if (args.length != 1)
		{ // création de la fenêtre sans sauvegarde
			envi = new Environnement();
			// sauvegarde
			File save = new File("Firstsave.ser");
			ObjectOutputStream oos;
			try {
				oos = new ObjectOutputStream(new FileOutputStream(save));
				oos.writeObject(envi);
				oos.close();
				afficher(envi);
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		} else {
			File save = new File(args[0]);
			try { //chargement du fichier sauvegardé
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(save));
				envi = (Environnement)ois.readObject();
				ois.close();
				afficher(envi);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ReflectiveOperationException e) {
				e.printStackTrace();
			}
		}
	}
	
	static void afficher (Environnement e) {
		Ecran window = new Ecran(e);
		java.awt.EventQueue.invokeLater(new Runnable() {
	        public void run() {
	        	window.setVisible(true);
	        }
		});
	}
	
	

}
