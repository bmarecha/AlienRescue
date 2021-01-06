import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class AffichageSelect extends JPanel {
	private static final long serialVersionUID = 1L;
	Environnement modele;
	ArrayList<Bouton> niveaux = new ArrayList<>();
	ArrayList<JLabel> Scores = new ArrayList<>();
	JButton play, home, save, music;
	boolean noSound= true;
	private Image bgImage = null;

	ImageIcon la = new ImageIcon("images/LaunchPad.png");
	ImageIcon la2 = new ImageIcon("images/LaunchPad2.png");
	ImageIcon ship = new ImageIcon("images/Ship.png");
	ImageIcon shipland = new ImageIcon("images/ShipLand.png");
	ImageIcon star1 = new ImageIcon("images/star1.png");
	ImageIcon star2 = new ImageIcon("images/star2.png");
	ImageIcon star3 = new ImageIcon("images/star3.png");

	Clip clip;
  
	public AffichageSelect (Environnement e) {
		modele = e;
		int height = e.screen.height;
		int width = e.screen.width;
		this.setLayout(null);
		File f = new File("images/Planets.jpg");
		if (f.exists()) {
			try {
				bgImage = ImageIO.read(f);
				bgImage = new ImageIcon("images/Planets.jpg").getImage();
			} catch (IOException except) {
				except.printStackTrace();
			}
			
		}
		else{
		
			System.out.println("BackgroundImg, bad link.");
		}
		
		for (int i = 1; i < 6; i++)
		{
			int x =  (width - 60) / 2;
			int y =  i * (height / 6) - 110;
			Bouton niv = new Bouton(i);
			niv.setOpaque(false);
			niv.setContentAreaFilled(false);
			niv.setBorderPainted(false);
			niv.setFont(new Font("Arial", Font.BOLD, 50));
			niv.setBounds(x, y, 60, 60);
			if ( i > e.maxNiv) {
				niv.setEnabled(false);
				niv.setIcon(la);
				niv.setDisabledIcon(la2);	
			}
			if (i == e.cursorNiv) {
				niv.setEnabled(false);
			}
			if ( i <= e.maxNiv){
				niv.setIcon(la);
				niv.setDisabledIcon(shipland);
			}
			afficherEtoile(i - 1, x, y);
			this.add(Scores.get(i - 1));
			niv.addActionListener((event) -> changeNiv(niv.num));
			niveaux.add(niv);
			this.add(niv);
		}
		play = new JButton("Jouer au niveau "+ this.modele.cursorNiv +" !");
		play.setOpaque(false);
		play.setContentAreaFilled(false);
		play.setFont(new Font("Arial", Font.BOLD, 40));
		play.setBounds(80, height - 90, 480, 50);
		play.setBorderPainted(false);
		play.addActionListener((event) -> modele.chargerNiveau());
		this.add(play);
		home = new JButton();
		home.setContentAreaFilled(false);
		home.setOpaque(false);
		home.setBorderPainted(false);
		home.setIcon(new ImageIcon("images/home.png"));
		home.setBounds(0, 0, 70, 70);
		home.addActionListener((event)-> modele.screen.menu());
		this.add(home);
		save = new JButton();
		save.setContentAreaFilled(false);
		save.setOpaque(false);
		save.setBorderPainted(false);
		save.setIcon(new ImageIcon("images/save.png"));
		save.setBounds(width - 70, 0, 70, 70);
		save.addActionListener((event)-> {modele.save();save.setIcon(new ImageIcon("images/checkmark.png"));save.setEnabled(false);});
		this.add(save);
		music = new JButton();
		music.setContentAreaFilled(false);
		music.setOpaque(false);
		music.setBorderPainted(false);
		music.setBounds(0, height - 120, 80, 80);
		switchMusic();
		music.addActionListener((event)-> {switchMusic();});
		this.add(music);

	}

	public void switchMusic() {
		File f= new File("music/soar-noisy_oyster.wav");
		if(f.exists()){
			try {
				if (clip == null)
					clip = AudioSystem.getClip();
				if (noSound) {
					AudioInputStream audioInput= AudioSystem.getAudioInputStream(f);
					clip.open(audioInput);
					clip.start();
					clip.loop(Clip.LOOP_CONTINUOUSLY);
					music.setIcon(new ImageIcon("images/musicOn.png"));
					noSound = false;
				}
				else {
					clip.stop();
					clip.close();
					noSound = true;
					music.setIcon(new ImageIcon("images/musicOff.png"));
				}
			} catch (UnsupportedAudioFileException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (LineUnavailableException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			} else {
				System.out.println("Music File Not Found");
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
	
	// Le niveau a été quitté donc le score ou nombre de niveau disponibles ont peut être changer
	public void actualiser() {
		for (int i = 1; i < 6; i++)
		{
			if ( i <= modele.maxNiv && i != modele.cursorNiv)
				this.niveaux.get(i - 1).setEnabled(true);
			if ( i <= modele.maxNiv) {
				this.niveaux.get(i - 1).setDisabledIcon(shipland);
			}
			switch (modele.Scores[i - 1]) {
			case 1 :
				Scores.get(i - 1).setIcon(star1);
				break;
			case 2 :
				Scores.get(i - 1).setIcon(star2);
				break;
			case 3 :
				Scores.get(i - 1).setIcon(star3);
				break;
			}
		}
		play.setText("Jouer au niveau " + modele.cursorNiv + " !");
		save.setIcon(new ImageIcon("images/save.png"));
		save.setEnabled(true);
		
	}
	
	public void afficherEtoile(int n, int x, int y) {
		JLabel etoiles = new JLabel();
		switch (modele.Scores[n]) {
		case 1 :
			etoiles.setIcon(star1);
			break;
		case 2 :
			etoiles.setIcon(star2);
			break;
		case 3 :
			etoiles.setIcon(star3);
			break;
		}
		etoiles.setOpaque(false);
		etoiles.setBounds(x - 8, y + 23, 80, 80);
		this.Scores.add(etoiles);
	}

	// Click sur un bouton de séléction
	private void changeNiv(int cursor) {
		this.niveaux.get(modele.cursorNiv - 1).setEnabled(true);
		modele.cursorNiv = cursor;
		this.niveaux.get(cursor - 1).setEnabled(false);
		this.play.setText("Jouer au niveau " + cursor + " !");
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
