import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Ecran extends JFrame{
	private static final long serialVersionUID = 1L;
	Environnement envi;
	AffichageSelect selection= null;
	HomeMenu menu = null;
	HelpPage help= null;
	public int width = 600;
	public int height = 900;

	
	

	public Ecran (Environnement e) {
		super();
		this.setTitle("AlienRescue");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		envi = e;
		Rectangle tailleEnvi = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		if (height > tailleEnvi.height)
			if (tailleEnvi.height >= 600) {
				height = tailleEnvi.height;
				width = tailleEnvi.height * 2 / 3;
			} else {
				height = 600;
				width = 400;
			}
		this.setSize(width, height);
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

	public void help() {
		if (help == null)
			help = new HelpPage();
		this.setContentPane(help);
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
			

			JButton aide = new JButton(); 
			aide.setOpaque(false);
			aide.setContentAreaFilled(false);
			aide.setIcon(new ImageIcon("images/question.png"));
			aide.setBounds(0, height - 120, 70, 70);
			aide.setBorderPainted(false);
			aide.addActionListener((event) -> help());

			this.add(aide);



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

	private class HelpPage extends JPanel{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private Image bgImage = null;

		public HelpPage() {
			//this.setLayout(null);//new GridLayout(6, 1));
			File f = new File("images/Planets.jpg");
			if (f.exists()) {
				try {
					bgImage = ImageIO.read(f);
					bgImage = new ImageIcon("images/ufo-600900.jpg").getImage();
				} catch (IOException except) {
					except.printStackTrace();
				}
			} else {
				System.out.println("BackgroundImg, bad link.");
			}
			Scanner scan;
			JTextArea j = null;
			try {
				File f1 = new File("howToPlay.md");
				scan = new Scanner(f1);
				
				String text = "";
				while(scan.hasNext()) {
					String mot=scan.next();
					if(mot.length()+text.length()-text.lastIndexOf("\n")>25) {
						text+="\n";	
					}
					text+= mot+" ";	

				}
				j = new JTextArea(text);
				j.setEditable(false);
				j.setOpaque(false);
				j.setFont(new Font("Arial", Font.BOLD, 40));
				j.setForeground(Color.white);
				j.setBounds(30, 0, 150, height - 150);
				scan.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.add(j);
			JButton home = new JButton();
			home.setContentAreaFilled(false);
			home.setOpaque(false);
			home.setBorderPainted(false);
			home.setIcon(new ImageIcon("images/home.png"));
			home.setBounds(300, height - 140, 70, 70);
			home.addActionListener((event)-> menu());
			this.add(home);
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

