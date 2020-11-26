import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.*;

public class AffichageSelect extends JPanel {
	private static final long serialVersionUID = 1L;
	Environnement modele;
	ArrayList<Bouton> niveaux = new ArrayList<>();
	JButton play;
	
	public AffichageSelect (Environnement e) {
		modele = e;
		this.setLayout(new GridLayout(6, 1));
		for (int i = 1; i < 6; i++)
		{
			Bouton niv = new Bouton(i);
			niv.setText(String.valueOf(i));
			if ( i > e.maxNiv || i == e.cursorNiv)
				niv.setEnabled(false);
			if ( i <= e.maxNiv)
				niv.setBackground(Color.green);
			niv.addActionListener((event) -> changeNiv(niv.num));
			niveaux.add(niv);
			this.add(niv);
		}
		play = new JButton("Jouer au niveau 1 !");
		play.setBackground(Color.cyan);
		play.addActionListener((event) -> modele.chargerNiveau());
		this.add(play);
	}
	
	// Le niveau a été quitté donc le score ou nombre de niveau disponibles ont peut être changer
	public void actualiser() {
		for (int i = 1; i < 6; i++)
		{
			if ( i <= modele.maxNiv && i != modele.cursorNiv)
				this.niveaux.get(i - 1).setEnabled(true);
			if ( i <= modele.maxNiv)
				this.niveaux.get(i - 1).setBackground(Color.green);
		}
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
