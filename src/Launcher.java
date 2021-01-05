import java.io.*;

public class Launcher{

	public static void main(String[] args) {
		Environnement envi = null;
		File save = new File("ser/Environ.ser");
		if (args.length == 1)
		{ 
			save = new File(args[0]);
			if (!save.exists()) {
				System.out.println("Le fichier entré en argument n'existe pas.");
				return;
			}
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
		} else if (save.exists()) {
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
		} else {
			// création de la fenêtre sans sauvegarde
			envi = new Environnement();
			// sauvegarde
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
