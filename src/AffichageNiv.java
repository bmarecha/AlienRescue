import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class AffichageNiv extends JPanel {
	private static final long serialVersionUID = 1L;
	Niveau modele;
	JPanel affichagePlateau;
	
	public AffichageNiv (Niveau n) {
		this.setLayout(new BorderLayout());
		
		// Affichage des informations du niveau
		modele = n;
		JButton exit = new JButton();
		exit.addActionListener((event) -> modele.retour());
		exit.setText("Quitter le niveau " + modele.num);
		this.add(exit, BorderLayout.SOUTH);
		
		
		//Affichage du plateau central
		affichagePlateau = new JPanel();
		int hauteur = 6; //à remplacer avec les valeurs de plateau
		int largeur = 6;
		affichagePlateau.setLayout(new GridLayout(hauteur, largeur));
		for (int i = 0; i < hauteur; i++)
			for (int j = 0; j < largeur; j++) {
				JButton bouton = new JButton(Integer.toString(i+j));
				//à changer selon la case i j du plateau (setIcon, addActionListener(modele.jouer(i, j))
				affichagePlateau.add(bouton);
			}
		this.add(affichagePlateau, BorderLayout.CENTER);
	}

}
