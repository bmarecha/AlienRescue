import java.io.Serializable;

public class Plateau implements Serializable, Cloneable{
	private static final long serialVersionUID = 1L;
	Case[][] grid;
	int hauteur;
	int largeur;
	/*
	Case rouge = new Case(false, 1);
	Case vert = new Case(false, 2);
	Case bleu = new Case(false, 3);
	Case vide= new Case(false,0);
	Case[] normal= {rouge, vert, bleu};
	*/

	public Plateau (int h, int l) {
		hauteur=h;
		largeur=l;
		grid = new Case[hauteur][largeur];
		String s=
				"arrxxx" +
				"rrbbrx" +
				"vvbarr"+
				"bbbvvv"+
				"vvrrbb"+
				"brvvrr";
/*
		s =		"xxxxxx" +
				"xxaxxr" +
				"bvrbvr"+
				"vrbvrb"+
				"bvrbvr"+
				"vrbvrb";
				*//*
		s =		"arrrxx" +
				"brbbxb" +
				"vvbaxr"+
				"rbbvrv"+
				"vrrrbb"+
				"bvvvrr";
				//*/
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
	
	public Plateau clone() throws CloneNotSupportedException {
		Plateau copy = (Plateau)super.clone();
		copy.grid = this.grid.clone();
		return copy;
		
	}
	
	public int supprimer(int a, int b, boolean first) {
		boolean neighbour=false;
		int res = 0;
		int color = grid[a][b].k;
		grid[a][b].checked= true;
		System.out.println("checked " + a + " " + b);

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
			System.out.println("supp " + a + " " + b + "n°" + res);
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

	public boolean jouable() {
		for (int a = 0; a <this.hauteur; a++)
			for (int b = 0; b < this.largeur ; b++) {
				int color = grid[a][b].k;
				if (color != 0 && color != 4) {
					if (a-1>=0 && grid[a-1][b].k== color)
						return true;
					if (a+1<this.hauteur && grid[a+1][b].k== color)
						return true;
					if (b-1>=0 && grid[a][b-1].k== color)
						return true;
					if (b+1<this.largeur && grid[a][b+1].k== color)
						return true;
				}
			}
		return false;
	}
}