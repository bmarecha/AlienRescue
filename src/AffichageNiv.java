import javax.swing.JButton;
import javax.swing.JPanel;

public class AffichageNiv extends JPanel {
	private static final long serialVersionUID = 1L;
	Niveau modele;
	
	public AffichageNiv (Niveau n) {
		modele = n;
		JButton exit = new JButton();
		exit.addActionListener((event) -> modele.retour());
		exit.setText("Quitter le niveau " + modele.num);
		this.add(exit);
	}

}
