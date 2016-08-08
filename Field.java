package src;

import java.awt.Color;

public class Field {
	private Color PaintingColor;
	private int x;
	private int y;
	private boolean modified;
	
	public Field(int xi, int yi, Color BasicColor){
		x = xi;
		y = yi;
		PaintingColor = BasicColor;
		modified=false;
	}
	public void Drawing(Color color){
		PaintingColor = color;
	}
	public Color getColor(){
		return PaintingColor;
	}
	public void setModified(boolean b){
		modified=b;
	}
	public boolean getModified(){
		return modified;
	}
}
