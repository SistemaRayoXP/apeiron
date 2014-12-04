package gui;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;

import javax.swing.JDialog;
import javax.swing.JLabel;

class Imagen extends Container
{
	static final long serialVersionUID = 1;
	Image img;

	public Imagen(String filename)
	{
		img = getToolkit().getImage(Imagen.class.getResource(filename));
		MediaTracker mt = new MediaTracker(this);
		mt.addImage(img, 0);
		try
		{
			mt.waitForID(0);
		}
		catch (InterruptedException ie)
		{
		}
	}

	public void paint(Graphics g)
	{
		g.drawImage(img, 0, 0, this);
		super.paint(g);
	}

}

public class Splash extends JDialog
{
	static final long serialVersionUID = 1;

	public Splash()
	{
		super();
		setTitle("Apeiron");
		Imagen logo = new Imagen("apeiron.jpg");
		JLabel lbl = new JLabel("Dudas y sugerencias:");
		lbl.setBounds(0, 340, 485, lbl.getPreferredSize().height);
		lbl.setHorizontalAlignment(JLabel.RIGHT);
		logo.add(lbl);

		lbl = new JLabel("rieztra@users.sourceforge.net");
		lbl.setBounds(0, 355, 485, lbl.getPreferredSize().height);
		lbl.setHorizontalAlignment(JLabel.RIGHT);
		logo.add(lbl);

		lbl = new JLabel("Apeiron " + Principal.version + " es FREEWARE. Úselo bajo su propia responsabilidad,");
		lbl.setBounds(0, 5, 485, lbl.getPreferredSize().height);
		lbl.setHorizontalAlignment(JLabel.RIGHT);
		logo.add(lbl);

		lbl = new JLabel("los autores no se hacen responsables de");
		lbl.setBounds(0, 20, 485, lbl.getPreferredSize().height);
		lbl.setHorizontalAlignment(JLabel.RIGHT);
		logo.add(lbl);

		lbl = new JLabel("cualquier daño o perjuicio");
		lbl.setBounds(0, 35, 485, lbl.getPreferredSize().height);
		lbl.setHorizontalAlignment(JLabel.RIGHT);
		logo.add(lbl);

		lbl = new JLabel("que pueda causar");
		lbl.setBounds(0, 50, 485, lbl.getPreferredSize().height);
		lbl.setHorizontalAlignment(JLabel.RIGHT);
		logo.add(lbl);

		setContentPane(logo);
		setSize(491, 381);
		setUndecorated(true);
		setLocationRelativeTo(null);
		repaint();
		setVisible(true);
	}

	public void close(int ms)
	{
		try
		{
			Thread.sleep(ms);
		}
		catch (InterruptedException ie)
		{
		}

		setVisible(false);

	}

	public static void main(String[] args)
	{
		new Splash();
	}
}
