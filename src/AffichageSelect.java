import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

public class AffichageSelect extends JPanel {
	private static final long serialVersionUID = 1L;
	Environnement modele;
	ArrayList<Bouton> niveaux = new ArrayList<>();
	JButton play;
	private Image bgImage = null;
	
	public AffichageSelect (Environnement e) {
		modele = e;
		this.setLayout(new GridLayout(6, 1));
		File f = new File("images/Planets.jpg");
		if (f.exists()) {
			try {
				bgImage = ImageIO.read(f);
				bgImage = new ImageIcon("images/Planets.jpg").getImage();
				System.out.println("Supposed to be painted...");
			} catch (IOException except) {
				except.printStackTrace();
			}
		} else {
			System.out.println("BackgroundImg, bad link.");
		}
		for (int i = 1; i < 6; i++)
		{
			Bouton niv = new Bouton(i);
			niv.setText(String.valueOf(i));
			niv.setOpaque(false);
			niv.setContentAreaFilled(false);
			niv.setFont(new Font("Arial", Font.BOLD, 40));
			if ( i > e.maxNiv) {
				niv.setEnabled(false);
				niv.setBorderPainted(false);
			}
			if (i == e.cursorNiv) {
				niv.setEnabled(false);
			}
			if ( i <= e.maxNiv)
				niv.setBorder(BorderFactory.createLineBorder((Color.green)));
			niv.addActionListener((event) -> changeNiv(niv.num));
			niveaux.add(niv);
			this.add(niv);
		}
		play = new JButton("Jouer au niveau 1 !");
		play.setHorizontalTextPosition(JButton.CENTER);
		play.setVerticalTextPosition(JButton.CENTER);
		play.setOpaque(false);
		play.setContentAreaFilled(false);
		play.setFont(new Font("Arial", Font.BOLD, 20));
		Image g = null;
		if (g != null)
			play.setIcon(new ImageIcon(g));
		else
			play.setBackground(Color.cyan);
		play.addActionListener((event) -> modele.chargerNiveau());
		this.add(play);
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
	
	// Le niveau a été quitté donc le score ou nombre de niveau disponibles ont peut être changer
	public void actualiser() {
		for (int i = 1; i < 6; i++)
		{
			if ( i <= modele.maxNiv && i != modele.cursorNiv)
				this.niveaux.get(i - 1).setEnabled(true);
			if ( i <= modele.maxNiv) {
				this.niveaux.get(i - 1).setBorderPainted(true);
				this.niveaux.get(i - 1).setBorder(BorderFactory.createLineBorder(Color.green));
			}
		}
	}
	
	// Click sur un bouton de séléction
	private void changeNiv(int cursor) {
		this.niveaux.get(modele.cursorNiv - 1).setEnabled(true);
		modele.cursorNiv = cursor;
		this.niveaux.get(cursor - 1).setEnabled(false);
		this.play.setText("Jouer au niveau " + cursor + " !");
	}
	
	private BufferedImage loadImage(String src) {
		BufferedImage img;
		File f = new File(src);
		if (!f.exists()) {
			System.out.println("L'image n'existe pas.");
			return null;
		}
		try {
			img = ImageIO.read(f);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return img;
	}
	
	// Pour pouvoir numéroter les boutons (c'est utile vraiment)
	private class Bouton extends JButton {
		private static final long serialVersionUID = 1L;
		public final int num;
		
		public Bouton (int i) {
			super();
			num = i;
		}
	}

}
