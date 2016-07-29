package src;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Canvas extends JPanel{
	
	public void paintComponent(Graphics g){
		g.setColor(PaintingSpace.getFrameColor());
		g.fillRect(0, 0, PaintingSpace.getCountX()*PaintingSpace.getSizeX()+10, PaintingSpace.getCountY()*PaintingSpace.getSizeY()+10);
		g.setColor(Color.WHITE);
		g.fillRect(5, 5, PaintingSpace.getCountX()*PaintingSpace.getSizeX(), PaintingSpace.getCountY()*PaintingSpace.getSizeY());
		g.setColor(PaintingSpace.getFrameColor());
		for (int i=0 ; i<=PaintingSpace.getCountX(); i++)
				g.drawLine(i*PaintingSpace.getSizeX()+5, 5, i*PaintingSpace.getSizeX()+5, PaintingSpace.getCountY()*PaintingSpace.getSizeY()+5);
		//	v�zszintes r�csok
		for (int j=0 ; j<=PaintingSpace.getCountY();j++)
				g.drawLine(5, j*PaintingSpace.getSizeY()+5, PaintingSpace.getCountX()*PaintingSpace.getSizeX()+5, j*PaintingSpace.getSizeY()+5);
		//	f�gg�leges r�csok
	}
}
