
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

public class AffichageNiv extends JPanel {
	private static final long serialVersionUID = 1L;
	Niveau modele;
	JLabel score, aliens, victory;
	JProgressBar goal;
	JPanel affichagePlateau;
	private Image bgImage = null;
	ImageIcon aster2 = new ImageIcon("images/asteroid1.png");
	ImageIcon aster1 = new ImageIcon("images/asteroid2.png");
	ImageIcon aster3 = new ImageIcon("images/asteroid3.png");
	ImageIcon alien = new ImageIcon("images/alien2.png");
	ImageIcon star1 = new ImageIcon("images/star1.png");
	JLabel[] labels;

	public AffichageNiv (Niveau n) {
		this.setLayout(null);//new BorderLayout());
		modele = n;
		int height = n.envi.screen.height;
		int width = n.envi.screen.width;

		// Affichage des informations du niveau
		JButton exit = new JButton(new ImageIcon("images/return.png"));
		exit.addActionListener((event) -> modele.retour());
		exit.setBounds(0, 0, 60, 60);
		exit.setOpaque(false);
		exit.setContentAreaFilled(false);
		exit.setBorderPainted(false);
		this.add(exit);
		score = new JLabel("0", SwingConstants.RIGHT);
		score.setFont(new Font("Arial", Font.BOLD, 30));
		score.setBounds(60, 10, 100, 50);
		this.add(score);
		goal = new JProgressBar(0, modele.totalAlien*10 + modele.totalCase);
		goal.setOpaque(false);
		goal.setBounds(170, 10, width/2, 50);
		labels = new JLabel[modele.starScore.length];
		for (int i = 0; i < modele.starScore.length; i++) {
			labels[i] = new JLabel(star1);
			labels[i].setBounds(140 + (width/2 * modele.starScore[i]) / (goal.getMaximum() * 100 ), 10, 50, 50);
			labels[i].setOpaque(false);
			this.add(labels[i]);
			System.out.println("add star : "+labels[i]+i);
		}
		this.add(goal);
		aliens = new JLabel("0/" + modele.totalAlien, alien, SwingConstants.RIGHT);
		aliens.setFont(new Font("Arial", Font.BOLD, 30));
		aliens.setBounds(width-100, 10, 100, 60);
		this.add(aliens);
		victory = new JLabel();
		victory.setFont(new Font("Arial", Font.BOLD, 40));
		victory.setForeground(Color.WHITE);
		victory.setBounds(0, 150, 550, 80);
		this.add(victory);
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
				bouton.addActionListener((event) -> {modele.jouer(bouton.x, bouton.y);actualiser();});
				affichagePlateau.add(bouton);

			}
		affichagePlateau.setBounds(0, 80, width, height - 180);
		this.add(affichagePlateau);
		this.refreshPlat();
	}
	
	public void actualiser(){
		//Actualisation du Plateau
		refreshPlat();
		//Divers
		score.setText(Integer.toString(modele.currentScore));
		aliens.setText(modele.savedAlien + "/" + modele.totalAlien);
		goal.setValue(modele.currentScore/100);
		Color bar;
		if (modele.currentScore >= modele.starScore[0])
			if (modele.currentScore >= modele.starScore[1])
				if (modele.currentScore >= modele.starScore[2])
					bar = Color.GREEN;
				else
					bar = Color.YELLOW;
			else 
				bar = Color.ORANGE;
		else
			bar = Color.RED;
		goal.setForeground(bar);
		if (modele.gameState != 0) {
			if (modele.gameState == -1)
				victory.setText("Vous avez perdu !");
			else
				victory.setText("Vous avez gagné ! " + modele.gameState + "*");
		}
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



