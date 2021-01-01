import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
		exit.setText("Quitter le n"
				+ "iveau " + modele.num);
		this.add(exit, BorderLayout.SOUTH);


		//Affichage du plateau central
		affichagePlateau = new JPanel();
		int hauteur = 6; //à remplacer avec les valeurs de plateau
		int largeur = 6;
		affichagePlateau.setLayout(new GridLayout(hauteur, largeur));
		for (int i = 0; i < hauteur; i++) 
			for (int j = 0; j < largeur; j++) {
				Bouton bouton = new Bouton(i,j);
				//à changer selon la case i j du plateau (setIcon, addActionListener(modele.jouer(i, j))

				Case current= modele.currentPlat.grid[i][j];
				switch(current.k) {

				case 0:
					bouton.setBackground(Color.WHITE);
					bouton.setEnabled(false);
					break;
				case 1:
					bouton.setBackground(Color.RED);
					bouton.addActionListener((event) -> {modele.currentPlat.supprimer(bouton.x, bouton.y, true);
					actualiser();});

					//je<3
					break;
				case 2:
					bouton.setBackground(Color.GREEN);
					bouton.addActionListener((event) -> {modele.currentPlat.supprimer(bouton.x, bouton.y, true);
					actualiser();});
					break;
				case 3:
					bouton.setBackground(Color.BLUE);
					bouton.addActionListener((event) -> {modele.currentPlat.supprimer(bouton.x, bouton.y, true);

					actualiser();});
					break;
				case 4:
					bouton.setEnabled(false);
					bouton.setBackground(Color.BLACK);
					bouton.addActionListener((event) -> {modele.currentPlat.supprimer(bouton.x, bouton.y, true);
					actualiser();});
					break;

				}
				affichagePlateau.add(bouton);

			}
		this.add(affichagePlateau, BorderLayout.CENTER);

		

	}
	public void actualiser(){
		Component [] component = affichagePlateau.getComponents();
		//if !alien
		Plateau plato = modele.currentPlat;
		for (int i=0; i<component.length; i++) {
			Bouton bouton = (Bouton)component[i];
			if(bouton.isEnabled()) {
				bouton.setEnabled(true);
			}

			switch(plato.grid[bouton.x][bouton.y].k) {

			case 0:
				bouton.setEnabled(false);
				bouton.setBackground(Color.WHITE);

				break;
			case 1:
				bouton.setBackground(Color.RED);
				bouton.addActionListener((event) -> {modele.currentPlat.supprimer(bouton.x, bouton.y, true);
				actualiser();});

				break;
			case 2:
				bouton.setBackground(Color.GREEN);
				bouton.addActionListener((event) -> {modele.currentPlat.supprimer(bouton.x, bouton.y, true);
				actualiser();});
				break;
			case 3:
				bouton.setBackground(Color.BLUE);
				bouton.addActionListener((event) -> {modele.currentPlat.supprimer(bouton.x, bouton.y, true);

				actualiser();});
				break;
			case 4:
				bouton.setEnabled(false);
				bouton.setBackground(Color.BLACK);
				bouton.addActionListener((event) -> {modele.currentPlat.supprimer(bouton.x, bouton.y, true);
				actualiser();});
				break;

			}

		}
	}
	private class Bouton extends JButton {
		private static final long serialVersionUID = 1L;
		public final int x;
		public final int y;

		
		public Bouton (int i, int j) {
			super();
			x = i;
			y=j;
		}
	}

}



