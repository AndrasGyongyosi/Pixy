package src;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import java.awt.List;

public class drawing {

	private JFrame frame;
	public static ArrayList<Color> ColorPalette = new ArrayList<Color>();
	private int pressedX;
	private int pressedY;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					drawing window = new drawing();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public drawing() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void PaletteDrawing(JPanel palette) {
		palette.setBounds(1100, 60, 75, 30 * ColorPalette.size() + 6);
		Graphics g = palette.getGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(3, 3, 30, drawing.ColorPalette.size() * 30);
		for (int i = 0; i < drawing.ColorPalette.size(); i++) {
			g.setColor(drawing.ColorPalette.get(i));
			g.fillRect(4, 30 * i + 4, 28, 28);
			g.setColor(Color.WHITE);
			g.fillRect(60, 30 * i + 13, 10, 10);
			if (drawing.ColorPalette.get(i).equals(PaintingSpace.getFirstColor()))
				g.setColor(Color.RED);
			g.fillRect(40, 30 * i + 13, 10, 10);
		}
	}
	public void AreaFills (Graphics g, Color FieldColor, Color NewColor, int i, int j){
			g.setColor(NewColor);
			g.fillRect(i*PaintingSpace.getSizeX()+6, j*PaintingSpace.getSizeY()+6, 29, 29);
			PaintingSpace.DrawField(i, j, 1);
			PaintingSpace.setModified(i, j, true);
			if (i<PaintingSpace.getCountX()-1 && !PaintingSpace.getModified(i+1, j) && PaintingSpace.getFieldColor(i+1, j).equals(FieldColor)) AreaFills(g, FieldColor, NewColor, i+1, j);
			if (j<PaintingSpace.getCountY()-1 && !PaintingSpace.getModified(i, j+1) &&PaintingSpace.getFieldColor(i, j+1).equals(FieldColor))AreaFills(g, FieldColor, NewColor, i, j+1);
			if (i>0 && !PaintingSpace.getModified(i-1, j) &&PaintingSpace.getFieldColor(i-1, j).equals(FieldColor))AreaFills(g, FieldColor, NewColor, i-1, j);
			if (j>0 && !PaintingSpace.getModified(i, j-1) &&PaintingSpace.getFieldColor(i, j-1).equals(FieldColor))AreaFills(g, FieldColor, NewColor, i, j-1);
	}

	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(0, 0, 1604, 1040);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		PaintingSpace ps = new PaintingSpace(30, 30, 32, 32, Color.BLACK);

		/* Néhány alapszínt hozzáadunk a palettánkhoz. */
		ColorPalette.add(Color.RED);
		ColorPalette.add(Color.BLACK);
		ColorPalette.add(Color.BLUE);
		ColorPalette.add(Color.CYAN);
		ColorPalette.add(Color.MAGENTA);
		ColorPalette.add(Color.GREEN);
		ColorPalette.add(Color.YELLOW);
		ColorPalette.add(Color.LIGHT_GRAY);
		ColorPalette.add(Color.ORANGE);
		ColorPalette.add(Color.WHITE);

		JPanel palette = new Palette();

		JPanel addColor = new JPanel();
		addColor.setBounds(1221, 60, 304, 188);
		frame.getContentPane().add(addColor);
		addColor.setLayout(null);

		JSlider redslider = new JSlider();
		redslider.setValue(0);
		redslider.setMaximum(255);
		redslider.setBounds(56, 18, 140, 26);
		addColor.add(redslider);

		JSlider greenslider = new JSlider();
		greenslider.setValue(0);
		greenslider.setMaximum(255);
		greenslider.setBounds(56, 65, 140, 26);
		addColor.add(greenslider);

		JSlider blueslider = new JSlider();
		blueslider.setValue(0);
		blueslider.setMaximum(255);
		blueslider.setBounds(56, 110, 140, 26);
		addColor.add(blueslider);

		palette.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent mouse) {
				if (mouse.getX() < 30 && mouse.getX() > 0 && mouse.getY() % 30 != 3 && mouse.getY() > 3) {
					/*
					 * Csak akkor lépünk be, ha a megfelelõ tartományban van az
					 * egér, illetve ha már nem elsõdleges illetve másodlagos
					 * szín a választott.
					 */
					if (!ColorPalette.get((mouse.getY() - 3 - (mouse.getY() - 3) % 30) / 30)
							.equals(PaintingSpace.getSecondColor())
							&& !ColorPalette.get((mouse.getY() - 3 - (mouse.getY() - 3) % 30) / 30)
									.equals(PaintingSpace.getFirstColor())) {
						Graphics g = palette.getGraphics();
						g.setColor(Color.WHITE);
						for (int i = 0; i < ColorPalette.size(); i++) {
							/*
							 * Ez az egyenlet teszi lehetõvé, hogy amelyiket nem
							 * változtatjuk meg (elsõdleges, vagy másodlagos) ne
							 * legyen fehér
							 */
							if ((mouse.getButton() != 1 || !PaintingSpace.getSecondColor().equals(ColorPalette.get(i)))
									&& (mouse.getButton() != 3
											|| !PaintingSpace.getFirstColor().equals(ColorPalette.get(i))))
								g.fillRect(40, 30 * i + 13, 10, 10);
						}
						/*
						 * redslider.setValue(ColorPalette.get((mouse.getY() - 3
						 * - (mouse.getY() - 3) % 30) / 30).getRed());
						 * greenslider.setValue(ColorPalette.get((mouse.getY() -
						 * 3 - (mouse.getY() - 3) % 30) / 30).getGreen());
						 * blueslider.setValue(ColorPalette.get((mouse.getY() -
						 * 3 - (mouse.getY() - 3) % 30) / 30).getBlue());
						 */
						if (mouse.getButton() == 1
								&& !ColorPalette.get((mouse.getY() - 3 - (mouse.getY() - 3) % 30) / 30)
										.equals(PaintingSpace.getSecondColor())) {
							PaintingSpace
									.Change1Color(ColorPalette.get((mouse.getY() - 3 - (mouse.getY() - 3) % 30) / 30));
							g.setColor(Color.RED);
						} else if (mouse.getButton() == 3
								&& !ColorPalette.get((mouse.getY() - 3 - (mouse.getY() - 3) % 30) / 30)
										.equals(PaintingSpace.getFirstColor())) {
							PaintingSpace
									.Change2Color(ColorPalette.get((mouse.getY() - 3 - (mouse.getY() - 3) % 30) / 30));
							g.setColor(Color.BLUE);
						}
						g.fillRect(40, mouse.getY() - 3 - (mouse.getY() - 3) % 30 + 13, 10, 10);
					}
				}
			}
		});
		palette.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent mouse) {
				Graphics g = palette.getGraphics();
				g.setColor(Color.WHITE);
				for (int i = 0; i < ColorPalette.size(); i++) {
					g.fillRect(60, 30 * i + 13, 10, 10);
				}
				if (mouse.getX() < 30 && mouse.getX() > 0 && mouse.getY() % 30 != 3 && mouse.getY() > 3) {
					g.setColor(Color.GREEN);
					g.fillRect(60, mouse.getY() - 3 - (mouse.getY() - 3) % 30 + 13, 10, 10);
				}
			}
		});
		palette.setBounds(1100, 60, 75, 30 * ColorPalette.size() + 6);
		frame.getContentPane().add(palette);

		JPanel canvas = new Canvas();
		canvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent mouse) {
				int FieldX = (mouse.getX() - 5 - (mouse.getX() - 5) % 30) / 30;
				int FieldY = (mouse.getY() - 5 - (mouse.getY() - 5) % 30) / 30;
				/* Beállítjuk, hogy tároljuk a mezõ színét. */
				Graphics g = canvas.getGraphics();
				if (mouse.getButton() == 1)
					g.setColor(PaintingSpace.getFirstColor());
				if (mouse.getButton() == 3)
					g.setColor(PaintingSpace.getSecondColor());
				/*if (mouse.getButton() == 2) {
					g.setColor(PaintingSpace.getSecondColor());
					PaintingSpace.ChangeFColor(PaintingSpace.getSecondColor());
						for (int i=0 ; i<=PaintingSpace.getCountX(); i++)
							g.drawLine(i*PaintingSpace.getSizeX()+5, 5, i*PaintingSpace.getSizeX()+5, PaintingSpace.getCountY()*PaintingSpace.getSizeY()+5);
					//	vízszintes rácsok
					for (int j=0 ; j<=PaintingSpace.getCountY();j++)
							g.drawLine(5, j*PaintingSpace.getSizeY()+5, PaintingSpace.getCountX()*PaintingSpace.getSizeX()+5, j*PaintingSpace.getSizeY()+5);
					//	függõleges rácsok
				}
				else{*/
				if (mouse.getButton()==2){
					AreaFills(g, PaintingSpace.getFieldColor(FieldX, FieldY), PaintingSpace.getFirstColor(), FieldX, FieldY);
					for (int i=0;i<PaintingSpace.getCountX();i++)
						for (int j=0; j<PaintingSpace.getCountY();j++)
							PaintingSpace.setModified(i, j, false);
				} else {
				for (int i = Math.min(pressedX,FieldX); i <= Math.max(pressedX,FieldX); i++)
					for (int j = Math.min(pressedY,FieldY); j <= Math.max(pressedY,FieldY); j++){
						g.fillRect(i * 30 + 6, j * 30 + 6, PaintingSpace.getSizeX() - 1, PaintingSpace.getSizeY() - 1);
						PaintingSpace.DrawField(i, j, mouse.getButton());
					}
				}
			}

			@Override
			public void mousePressed(MouseEvent mouse) {
				Graphics g = canvas.getGraphics();
				pressedX = (mouse.getX() - 5 - (mouse.getX() - 5) % 30) / 30;
				pressedY = (mouse.getY() - 5 - (mouse.getY() - 5) % 30) / 30;
				}
		});
		canvas.setBounds(20, 20, PaintingSpace.getCountX() * PaintingSpace.getSizeX() + 10,
				PaintingSpace.getCountY() * PaintingSpace.getSizeY() + 10);
		frame.getContentPane().add(canvas);

		redslider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				Graphics g = addColor.getGraphics();
				g.setColor(Color.BLACK);
				g.fillRect(212, 57, 56, 56);
				g.setColor(new Color(redslider.getValue(), greenslider.getValue(), blueslider.getValue()));
				g.fillRect(215, 60, 50, 50);
			}
		});
		blueslider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				Graphics g = addColor.getGraphics();
				g.setColor(Color.BLACK);
				g.fillRect(212, 57, 56, 56);
				g.setColor(new Color(redslider.getValue(), greenslider.getValue(), blueslider.getValue()));
				g.fillRect(215, 60, 50, 50);
			}
		});
		greenslider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				Graphics g = addColor.getGraphics();
				g.setColor(Color.BLACK);
				g.fillRect(212, 57, 56, 56);
				g.setColor(new Color(redslider.getValue(), greenslider.getValue(), blueslider.getValue()));
				g.fillRect(215, 60, 50, 50);
			}
		});

		JButton Random = new JButton("Random");
		Random.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Random rand = new Random();
				int redi = rand.nextInt(256);
				int greeni = rand.nextInt(256);
				int bluei = rand.nextInt(256);
				redslider.setValue(redi);
				greenslider.setValue(greeni);
				blueslider.setValue(bluei);
				Graphics g = addColor.getGraphics();
				g.setColor(Color.BLACK);
				g.fillRect(212, 57, 56, 56);
				g.setColor(new Color(redi, greeni, bluei));
				g.fillRect(215, 60, 50, 50);
			}
		});
		Random.setBounds(205, 154, 89, 23);
		addColor.add(Random);
		JLabel lblRed = new JLabel("Red");
		lblRed.setFont(new Font("Arial", Font.BOLD, 14));
		lblRed.setBounds(12, 24, 28, 14);
		addColor.add(lblRed);

		JLabel lblGreen = new JLabel("Green");
		lblGreen.setFont(new Font("Arial", Font.BOLD, 14));
		lblGreen.setBounds(5, 70, 46, 14);
		addColor.add(lblGreen);

		JLabel lblBlue = new JLabel("Blue");
		lblBlue.setFont(new Font("Arial", Font.BOLD, 14));
		lblBlue.setBounds(12, 116, 32, 14);
		addColor.add(lblBlue);

		JButton btnRandompicture = new JButton("RandPicture");
		btnRandompicture.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color OriginalColor = PaintingSpace.getFirstColor();
				Random rand = new Random();
				int red, green, blue;
				for (int i = 0; i < PaintingSpace.getCountX(); i++) {
					for (int j = 0; j < PaintingSpace.getCountY(); j++) {
						red = rand.nextInt(256);
						green = rand.nextInt(256);
						blue = rand.nextInt(256);
						PaintingSpace.Change1Color(new Color(red, green, blue));
						PaintingSpace.DrawField(i, j, 1);
						/* Beállítjuk, hogy tároljuk a mezõ színét. */
						Graphics g = canvas.getGraphics();
						g.setColor(PaintingSpace.getFirstColor());
						g.fillRect(i * 30 + 6, j * 30 + 6, PaintingSpace.getSizeX() - 1, PaintingSpace.getSizeY() - 1);
					}
				}
				PaintingSpace.Change1Color(OriginalColor);
			}
		});
		btnRandompicture.setBounds(14, 154, 110, 23);
		addColor.add(btnRandompicture);

		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean exist = false;
				Color newColor = new Color(redslider.getValue(), greenslider.getValue(), blueslider.getValue());
				for (int i = 0; i < ColorPalette.size(); i++)
					if (newColor.equals(ColorPalette.get(i)))
						exist = true;
				if (!exist) {
					ColorPalette.add(newColor);
					PaletteDrawing(palette);
				}
			}
		});
		btnAdd.setBounds(134, 154, 60, 23);
		addColor.add(btnAdd);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, frame.getWidth(), 21);
		frame.getContentPane().add(menuBar);

		JMenu mnGame = new JMenu("Frame");
		menuBar.add(mnGame);
		
		JRadioButtonMenuItem rdbtnmntmBlue = new JRadioButtonMenuItem("Blue");
		mnGame.add(rdbtnmntmBlue);
		
		JRadioButtonMenuItem rdbtnmntmBlack = new JRadioButtonMenuItem("Black");
		mnGame.add(rdbtnmntmBlack);
		
		JRadioButtonMenuItem rdbtnmntmGreen = new JRadioButtonMenuItem("Green");
		mnGame.add(rdbtnmntmGreen);
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
