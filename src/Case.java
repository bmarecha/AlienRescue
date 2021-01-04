import java.io.Serializable;

public class Case implements Serializable, Cloneable{
	private static final long serialVersionUID = 1L;
	
	boolean isAlien;
	int k;
	boolean checked;
	
	public Case(boolean b, int a) {
		isAlien= b;
		k=a;
		checked = false;
	}

	public void supp() {
		isAlien = false;
		k = 0;
	}
	
	public Case clone() throws CloneNotSupportedException {
		Case c = (Case)super.clone();
		return c;
	}
}
