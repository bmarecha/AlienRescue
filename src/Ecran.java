import javax.swing.JFrame;

public class Ecran extends JFrame{
	private static final long serialVersionUID = 1L;
	Environnement envi;
	AffichageSelect selection;
	
	public Ecran (Environnement e) {
		super();
		this.setTitle("AlienRescue");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		envi = e;
		selection = new AffichageSelect(envi);
		this.setContentPane(selection);
		this.setSize(600, 800);
		envi.setScreen(this);
	}
	
	// Retour à l'écran de selection, plein de choses doivent changer
	public void select() {
		selection.actualiser();
		this.setContentPane(selection);
		this.setVisible(true);
	}
}
