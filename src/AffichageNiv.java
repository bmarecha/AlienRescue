import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

public class AffichageNiv extends JPanel {
	private static final long serialVersionUID = 1L;
	Niveau modele;
	JLabel score, aliens;
	JSlider goal;
	JPanel affichagePlateau;
	private Image bgImage = null;
	ImageIcon aster1 = new ImageIcon("images/asteroid1.png");
	ImageIcon aster2 = new ImageIcon("images/asteroid2.png");
	ImageIcon aster3 = new ImageIcon("images/asteroid3.png");
	ImageIcon alien = new ImageIcon("images/alien2.png");

	public AffichageNiv (Niveau n) {
		this.setLayout(new BorderLayout());
		modele = n;

		// Affichage des informations du niveau
		JButton exit = new JButton();
		exit.addActionListener((event) -> modele.retour());
		exit.setText("Quitter le niveau " + modele.num);
		exit.setFont(new Font("Arial", Font.BOLD, 15));
		this.add(exit, BorderLayout.SOUTH);
		JPanel hud = new JPanel();
		hud.setLayout(new GridLayout(1,3));
		hud.setOpaque(false);
		score = new JLabel("0", SwingConstants.LEFT);
		score.setFont(new Font("Arial", Font.BOLD, 30));
		hud.add(score);
		goal = new JSlider(0, modele.totalAlien*50 + modele.totalCase);
		Dictionary<Integer, JLabel> labels = new Hashtable<>();
		for (int star : modele.starScore)
			labels.put(star, new JLabel(new ImageIcon("images/star.png")));
		goal.setLabelTable(labels);
		hud.add(goal);
		aliens = new JLabel("0/" + modele.totalAlien, alien, SwingConstants.RIGHT);
		aliens.setFont(new Font("Arial", Font.BOLD, 30));
		hud.add(aliens);
		this.add(hud, BorderLayout.NORTH);
		File f;
		switch (modele.num) {
		case 1:
			f = new File("images/galaxy1.jpg");
			break;
		case 2:
			f = new File("images/galaxy2.jpg");
			break;
		case 3:
			f = new File("images/moon1.jpg");
			break;
		default:
			f = new File("images/moon2.jpg");
			break;

		}
		if (f.exists()) {
			try {
				bgImage = ImageIO.read(f);
				System.out.println("Supposed to be painted...");
			} catch (IOException except) {
				except.printStackTrace();
			}
		} else {
			System.out.println("BackgroundImg, bad link.");
		}

		//Affichage du plateau central
		affichagePlateau = new JPanel();
		int hauteur = 6; //à remplacer avec les valeurs de plateau
		int largeur = 6;
		affichagePlateau.setLayout(new GridLayout(hauteur, largeur));
		affichagePlateau.setOpaque(false);
		for (int i = 0; i < hauteur; i++) 
			for (int j = 0; j < largeur; j++) {
				Bouton bouton = new Bouton(i,j);
				bouton.setBorderPainted(false);
				bouton.setContentAreaFilled(false);
				bouton.setOpaque(false);
				bouton.setIcon(null);
				//à changer selon la case i j du plateau (setIcon, addActionListener(modele.jouer(i, j))
				bouton.addActionListener((event) -> {modele.jouer(bouton.x, bouton.y);actualiser();});
				
				affichagePlateau.add(bouton);

			}
		this.add(affichagePlateau, BorderLayout.CENTER);
		this.refreshPlat();
	}
	
	public void actualiser(){
		//Divers
		score.setText(Integer.toString(modele.currentScore));
		aliens.setText(modele.savedAlien + "/" + modele.totalAlien);
		if (modele.gameState != 0) {
			JLabel victory = new JLabel();
			if (modele.gameState == -1)
				victory.setText("Vous avez perdu !");
			else
				victory.setText("Vous avez gagné !");
			victory.setFont(new Font("Arial", Font.BOLD, 50));
			victory.setForeground(Color.WHITE);
			victory.setBackground(Color.BLACK);
			this.add(victory, BorderLayout.CENTER);
		}
		//Actualisation du Plateau
		refreshPlat();
	}
	
	public void refreshPlat() {
		Component [] component = affichagePlateau.getComponents();
		Plateau plato = modele.currentPlat;
		for (int i=0; i<component.length; i++) {
			Bouton bouton = (Bouton)component[i];
			switch(plato.grid[bouton.x][bouton.y].k) {
			case 0:
				bouton.setEnabled(false);
				bouton.setIcon(null);
				bouton.setDisabledIcon(null);
				break;
			case 1:
				bouton.setEnabled(true);
				bouton.setIcon(aster1);
				break;
			case 2:
				bouton.setEnabled(true);
				bouton.setIcon(aster2);
				break;
			case 3:
				bouton.setEnabled(true);
				bouton.setIcon(aster3);
				break;
			case 4:
				bouton.setEnabled(false);
				bouton.setIcon(alien);
				bouton.setDisabledIcon(alien);
				break;

			}

		}
	}
	
	
	@Override
	  protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    Graphics2D g2 = (Graphics2D) g.create();
	    if (bgImage != null) {
	        g2.drawImage(bgImage, 0, 0, null);
	        g2.dispose();
	    }
	    else
	    	System.out.println("no backgroundimage");
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



