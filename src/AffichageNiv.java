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
		for (int i = 0; i < hauteur; i++) {
			for (int j = 0; j < largeur; j++) {
				JButton bouton = new JButton(Integer.toString(i+j));
				//à changer selon la case i j du plateau (setIcon, addActionListener(modele.jouer(i, j))

				Case current= modele.currentPlat.grid[i][j];
				switch(current.k) {

				case 0:
					bouton.setEnabled(false);
					break;
				case 1:
					bouton.setBackground(Color.RED);
					bouton.addActionListener((event) -> {modele.currentPlat.supprimer(bouton.getX(), bouton.getY(), true);

					actualiser();});

					//je<3
					break;
				case 2:
					bouton.setBackground(Color.GREEN);
					break;
				case 3:
					bouton.setBackground(Color.BLUE);
					break;
				case 4:
					bouton.setEnabled(false);
					bouton.setBackground(Color.BLACK);
					break;

				}
				affichagePlateau.add(bouton);

			}
		}
		public void actualiser(){
			Component [] component = affichagePlateau.getComponents();
			//if !alien
			Plateau plato = modele.currentPlat;
			for (int i=0; i<component.length; i++) {
				JButton bouton = (JButton)component[i];
				if(bouton.setEnabled(false)) {
					bouton.setEnabled(true);
				}
			}
		}
		this.add(affichagePlateau, BorderLayout.CENTER);


		
	}
}


