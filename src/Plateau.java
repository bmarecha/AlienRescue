
public class Plateau {
	Case[][] grid;
	int hauteur;
	int largeur;
	Case rouge = new Case(false, 1);
	Case vert = new Case(false, 2);
	Case bleu = new Case(false, 3);
	Case[] normal= {rouge, vert, bleu};


	public Plateau (int h, int l) {
		hauteur=h;
		largeur=l;
		grid = new Case[hauteur][largeur];

		String s = "rrrrvb"+"rrbbvv"+"vvbbbr"+"bbbrrv"+"vvrrbb"+"bvvvrr";
		for(int i=0; i<hauteur; i++) {
			for(int j=0; j<largeur; j++) {
				switch(s.charAt((i*6 + j)% s.length())){
				case 'r':
					grid[i][j]= rouge;
					break;
				case 'v':
					grid[i][j]= vert;
					break;
				case 'b':
					grid[i][j]= bleu;
					break;

				}
			}
		}
	}
}
