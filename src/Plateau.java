import java.io.Serializable;

public class Plateau implements Serializable{
	private static final long serialVersionUID = 1L;
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

		String s = "arrrvx"+"rrbbvv"+"vvbbbr"+"bbbrrv"+"vvrrbb"+"bvvvrr";
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
				case 'x':
					grid[i][j] = new Case (false, 0);
					break;
				case 'a':
					grid[i][j] = new Case (true, 4);					
					break;
				default:
					break;
				}
			}
		}
	}
	boolean first;
	boolean neighbour;
	
	public int supprimer(int a, int b, boolean first) {
		int res = 0;
		int color = grid[a][b].k;
		grid[a][b].checked= true;
		neighbour=false;

		for (int i=-1; i<=1; i++) {
			if(a-i>=0 && a-i<this.hauteur && i!=0 && !grid[a-i][b].checked && grid[a-i][b].k== color) {
				res += supprimer(a-i, b, false);
				neighbour=true;
				
			}
		}
		for (int i=-1; i<=1; i++) {
			if(b-i>=0 && b-i<this.largeur && i!=0 && !grid[a][b-i].checked && grid[a][b-i].k== color) {
				res += supprimer(a, b-i, false);
				neighbour=true;

			}
		}
		if(!first || neighbour) {
			grid[a][b].k=0;
			res+= 100;
		}
		return res;
	}
	
	public void tomber() {
		for(int i=this.hauteur - 1; i >= 0; i--) {
			for(int j=0; j<=this.largeur-1; j++) {
				grid[i][j].checked=false;
				if(grid[i][j].k!=0 && i != this.hauteur-1) {
					int z;
					for(z=i+1; z<this.hauteur; z++) {
						if (grid[z][j].k != 0) {
							break;
						}
					}
					z--;
					if (z != i) {
						int d = grid[z][j].k;
						grid[z][j].k=grid[i][j].k;
						grid[i][j].k=d;
					}
				}
			}
		}
	}
	
	public int suppAlien() {
		int res = 0;
		for (Case c : grid[this.hauteur - 1])
			if (c.k == 4) {
				c.supp();
				res++;
			}
		return res;
	}
	
	public void glisser() {
		for (int j = 0; j <=this.largeur - 2; j++) {
			int i = this.hauteur - 1;
			if (grid[i][j].k == 0) {
				int z = j;
				while (++z < this.largeur)
					if (grid[i][z].k != 0)
						while (i > 0) {
							int d = grid[i][z].k;
							grid[i][z].k=grid[i][j].k;
							grid[i][j].k=d;
							i--;
						}
			}
		}
	}
}
/*
o
o
x
x
o
o
*/