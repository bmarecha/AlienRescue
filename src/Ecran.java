import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Ecran extends JFrame{
	private static final long serialVersionUID = 1L;
	Environnement envi;
	AffichageSelect selection = null;
	HomeMenu menu = null;
	
	public Ecran (Environnement e) {
		super();
		this.setTitle("AlienRescue");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		envi = e;
		this.setSize(600, 900);
		envi.setScreen(this);
		menu();
	}


	// Retour à l'écran de selection, plein de choses doivent changer
	public void select() {
		if (selection == null)
			selection = new AffichageSelect(envi);
		selection.actualiser();
		this.setContentPane(selection);
		this.setVisible(true);
	}
	
	public void menu() {
		if (menu == null)
			menu = new HomeMenu();
		this.setContentPane(menu);
		this.setVisible(true);
	}
	
	private class HomeMenu extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private Image bgImage = null;
		
		public HomeMenu() {
			this.setLayout(null);//new GridLayout(6, 1));
			File f = new File("images/ufo-600900.jpg");
			if (f.exists()) {
				try {
					bgImage = ImageIO.read(f);
					//bgImage = new ImageIcon("images/ufo-600900.jpg").getImage();
					System.out.println("Supposed to be painted...");
				} catch (IOException except) {
					except.printStackTrace();
				}
			} else {
				System.out.println("BackgroundImg, bad link.");
			}
			JLabel name = new JLabel("Alien Rescue");
			name.setFont(new Font("Arial", Font.BOLD, 60));
			name.setForeground(Color.WHITE);
			name.setBackground(Color.BLACK);
			name.setBounds(70, 355, 440, 70);
			this.add(name);
			JButton play = new JButton();
			play.setOpaque(false);
			play.setContentAreaFilled(false);
			play.setIcon(new ImageIcon("images/right.png"));
			play.setBounds(255, 440, 70, 70);
			play.setBorderPainted(false);
			play.addActionListener((event) -> select());
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
	}
}
