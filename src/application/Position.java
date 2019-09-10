package application;

/**
 * 
 * this class is used to define the two object of the position
 * 
 * @author Zibo Wang
 * @version 10.09.2019
 *
 */
public class Position {

	private int r;// row of the game board
	private int c;// column of the game board

	public Position(int r, int c) {
		// TODO Auto-generated constructor stub
		this.r = r;
		this.c = c;
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public int getC() {
		return c;
	}

	public void setC(int c) {
		this.c = c;
	}

}
