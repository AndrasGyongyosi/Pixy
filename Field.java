package src;

import java.awt.Color;

public class Field {
	private Color PaintingColor;
	private int x;
	private int y;
	
	public Field(int xi, int yi, Color BasicColor){
		x = xi;
		y = yi;
		PaintingColor = BasicColor;
	}
	public void Drawing(Color color){
		PaintingColor = color;
	}
}
