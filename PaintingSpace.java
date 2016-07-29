package src;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class PaintingSpace {
	private static int FieldSizeX = 30;
	private static int FieldSizeY = 30;
	private static int FieldCountX = 32;
	private static int FieldCountY = 32;
	/* Menet közben nem csökkenthetõ.
	 * 30-as default-érték esetén kb 32x32-es tábla jön létre.
	 */
	private static List<Field> fields = new ArrayList<Field>();
	private static Color FrameColor = Color.BLACK;
	/*Keretszín
	 * Menet közben ugyanúgy változtatható.
	 */
	private static Color FirstColor = Color.GREEN;
	private static Color SecondColor = Color.WHITE;
	
	public PaintingSpace(int sizex, int sizey, int countx, int county, Color fColor){
		FieldSizeX = sizex;
		FieldSizeY = sizey;
		FieldCountX = countx;
		FieldCountY = county;
		FrameColor = fColor;
		/* 965-ös táblaméretünk lesz.
		 * Feltöltjük a maximummal ami 960-ig elfér a mezõkbõl, a többi a keret.
		 */
		for (int i=0; i < FieldCountX ; i++){
			for (int j=0 ; j<FieldCountY; j++){
				fields.add(new Field(i,j,FirstColor));
			}
		}
	}
	public static Color getFrameColor(){
		return FrameColor;
	}
	public static Color getFirstColor(){
		return FirstColor;
	}
	public static Color getSecondColor(){
		return SecondColor;
	}
	public static int getSizeX(){
		return FieldSizeX;
	}
	public static int getSizeY(){
		return FieldSizeY;
	}
	public static int getCountX(){
		return FieldCountX;
	}
	public static int getCountY(){
		return FieldCountY;
	}
	public static void Change1Color(Color c){
		FirstColor = c;
	}
	public static void Change2Color(Color c){
		SecondColor = c;
	}
	public static void DrawField(int i, int j, int Buttonid){
		if (Buttonid == 1)	fields.get(i*FieldCountX+j).Drawing(FirstColor);
		if (Buttonid == 3) fields.get(i*FieldCountX+j).Drawing(SecondColor);
	}
}
