import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.Painter;
import javax.swing.SwingConstants;
import javax.swing.UIDefaults;

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
	LinkedList<JButton> powers = new LinkedList<>();

	public AffichageNiv (Niveau n) {
		this.setLayout(null);//new BorderLayout());
		modele = n;

		// Affichage des informations du niveau
		JPanel hud = new JPanel();
		hud.setLayout(null);
		hud.setOpaque(false);
		JButton exit = new JButton(new ImageIcon("images/return.png"));
		exit.addActionListener((event) -> modele.retour());
		exit.setBounds(0, 0, 60, 60);
		exit.setOpaque(false);
		exit.setContentAreaFilled(false);
		exit.setBorderPainted(false);
		this.add(exit);
		score = new JLabel("0", SwingConstants.RIGHT);
		score.setFont(new Font("Arial", Font.BOLD, 30));
		score.setBounds(60, 10, 120, 50);
		this.add(score);
		goal = new JProgressBar(0, modele.totalAlien*10 + modele.totalCase);
		Dictionary<Integer, JLabel> labels = new Hashtable<>();
		for (int star : modele.starScore)
			labels.put(star / 100, new JLabel(new ImageIcon("images/star.png")));
		goal.setOpaque(false);
		goal.setBounds(190, 10, 300, 50);
		this.add(goal);
		aliens = new JLabel("0/" + modele.totalAlien, alien, SwingConstants.RIGHT);
		aliens.setFont(new Font("Arial", Font.BOLD, 30));
		aliens.setBounds(490, 10, 100, 60);
		this.add(aliens);
		victory = new JLabel();
		victory.setFont(new Font("Arial", Font.BOLD, 40));
		victory.setForeground(Color.WHITE);
//		victory.setBackground(Color.BLACK);
		victory.setBounds(0, 150, 550, 80);
		this.add(victory);
		hud.setBounds(0, 0, 600, 90);
		//this.add(hud);
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
		int hauteur = n.currentPlat.hauteur; //à remplacer avec les valeurs de plateau
		int largeur = n.currentPlat.largeur;
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
		affichagePlateau.setBounds(0, 50, 600, 700);
		this.add(affichagePlateau);
		
		//affichage des pouvoirs
		int height = 900;
		int width = 600;
		if (n.acid > 0) {
			JButton acid = new JButton("Arme Biochimique");
			acid.addActionListener((event) -> choosePower(0));
			powers.add(acid);
		}if (n.laser > 0) {
			JButton laser = new JButton("Laser linéaire");
			laser.addActionListener((event) -> choosePower(1));
			powers.add(laser);
		}
		for (int i = 0; i < powers.size(); i++) {
			JButton b = powers.get(i);
			b.setBounds(40 + width * i / powers.size() ,height - 100, (width - 100 )/ powers.size(), 50);
			b.setFont(new Font("Arial", Font.BOLD, 30));
			b.setBackground(Color.GRAY);
			b.setForeground(Color.white);
			b.setEnabled(true);
			this.add(b);
			System.out.println(b);
		}
		this.refreshPlat();
	}
	
	public void choosePower(int p) {
		JButton activated = powers.get(p);
		for (int i = 0; i < powers.size(); i++)
			powers.get(i).setBackground(Color.GRAY);
		activated.setBackground(Color.GREEN);
		modele.acidG = false;
		modele.laserS = false;
		switch (p) {
		case 0 :
			modele.chooseAcid();
		case 1 :
			modele.chooseLaser();
		}
		
	}
	
	public void actualiser(){
		//Actualisation du Plateau
		refreshPlat();
		//Divers
		score.setText(Integer.toString(modele.currentScore));
		aliens.setText(modele.savedAlien + "/" + modele.totalAlien);
		goal.setValue(modele.currentScore/100);
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



