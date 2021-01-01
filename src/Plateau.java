
public class Plateau {
	Case[][] grid;
	int hauteur;
	int largeur;
	Case rouge = new Case(false, 1);
	Case vert = new Case(false, 2);
	Case bleu = new Case(false, 3);
	Case vide= new Case(false,0);
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
					grid[i][j]= new Case(false, 1) ;
					break;
				case 'v':
					grid[i][j]= new Case(false, 2);
					break;
				case 'b':
					grid[i][j]= new Case(false, 3);
					break;

				}
			}
		}
	}
	boolean first;
	
	public void supprimer(int a, int b, boolean first) {
		int color = grid[a][b].k;
		grid[a][b].checked= true;

		for (int i=-1; i<=1; i++) {
			if(grid[a-i][b].k== color) {
				supprimer(a-1, b, false);
			}
		}
		for (int i=-1; i<=1; i++) {
			if(grid[a][b-1].k== color) {
				supprimer(a, b-1, false);
			}
		}
		grid[a][b].k=0;

	}
	
	
	
}
