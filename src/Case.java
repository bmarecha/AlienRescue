import java.io.Serializable;

public class Case implements Serializable{
	private static final long serialVersionUID = 1L;
	
	boolean isAlien;
	int k;
	boolean checked;
	public Case(boolean b, int a) {
		isAlien= b;
		k=a;
	}

	public void supp() {
		isAlien = false;
		k = 0;
	}
}
