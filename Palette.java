package src;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Palette extends JPanel{
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		super.setBackground(Color.GRAY);
		g.setColor(Color.BLACK);
		g.fillRect(3, 3, 30, drawing.ColorPalette.size()*30);
		for (int i=0; i<drawing.ColorPalette.size();i++){
			g.setColor(drawing.ColorPalette.get(i));
			g.fillRect(4, 30*i+4, 28, 28);
			g.setColor(Color.WHITE);
			g.fillRect(60, 30*i+13, 10, 10);
			if (drawing.ColorPalette.get(i).equals(PaintingSpace.getFirstColor())) g.setColor(Color.RED);
			else if (drawing.ColorPalette.get(i).equals(PaintingSpace.getSecondColor())) g.setColor(Color.BLUE);
			g.fillRect(40, 30*i+13, 10, 10);
		}
		
	}
}
