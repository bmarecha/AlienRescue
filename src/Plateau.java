import java.io.Serializable;

public class Plateau implements Serializable, Cloneable{
	private static final long serialVersionUID = 1L;
	Case[][] grid;
	int hauteur;
	int largeur;

	public Plateau (int h, int l) {
		hauteur=h;
		largeur=l;
		grid = new Case[hauteur][largeur];
		//Niveau 1
		String s=
				"bbaxxx" +
				"bbbvvr" +
				"rrvbrr"+
				"vvvbbr"+
				"bbbvvb"+
				"bbrvvv";
		//Niveau 2
		s =		"xxxxmx" +
				"xxxxxx" +
				"xvmxax"+
				"rbxbrb"+
				"bbvbvb"+
				"rrrbvb";
		//Niveau 3
		s =		"arrrxx" +
				"brbbab" +
				"vvbbrb"+
				"vbmvmr"+
				"vbbvvr"+
				"bvvvrr";
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
				case 'm':
					grid[i][j] = new Case (false, 5);
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
				if(grid[i][j].k!=0 && grid[i][j].k != 5 && i != this.hauteur-1) {
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
	
	public int suppLigne(int a) {
		int res = 0;
		for (Case c : grid[a])
		{
			if (c.k != 0 && c.k != 4)
				res += 100;
			c.supp();
		}
		return res;
	}
	public int suppZone(int a, int b, int radius) {
		int res = 0;
		for (int i= -radius; i<=radius; i++) {
			for (int j= -radius; j<=radius; j++) {
			if(a+i>=0 && a+i<this.hauteur && b+j>=0 && b+j<this.largeur) {
				grid[a+i][b+j].supp();
				res += 100;
			}
			}
		}
		return res;
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
					if (grid[i][z].k != 0) {
						while (i > 0 && grid [i][z].k != 0 && grid [i][z].k != 5) {
							int l = z - 1;
							while ( l > j) {
								if (grid[i][l].k != 0) {
									l++;
									break;
								}
								l--;
							}
							int d = grid[i][l].k;
							if (d == 0) {
								grid[i][l].k=grid[i][z].k;
								grid[i][z].k=d;
							}
							i--;
						}
						break;
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
