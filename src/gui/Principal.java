package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;

import actualizacion.ChkVersion;

import carga.AdDatos;
import carga.MateriaSiiau;

import combinacion.PrimeroElMejor;

public class Principal extends JFrame implements CatalogoListener
{
	static final long serialVersionUID = 1;
	public static final double version = 2.94;

	AdDatos datos;
	Descargas descargas;
	Preferencias preferencias;
	Catalogo carga;
	ObtenPorClave porClave;
	ChkVersion chkVersion;
	Acerca acerca;

	public JDesktopPane dp;

	public void selecciones(Vector v)
	{
		for (int x = 0; x < v.size(); x++)
		{
			ResCat i = (ResCat) v.get(x);
			datos.cargar(new MateriaSiiau(i.clave, i.centro, i.cal));
		}
	}

	public Principal()
	{
		super("Apeiron " + version + "   [http://apeiron.sourceforge.net]");

		Splash sp = new Splash();
		datos = new AdDatos();
		descargas = datos.getDescargas();
		preferencias = new Preferencias(datos, this);
		carga = new Catalogo();
		carga.addCatalogoListener(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		porClave = new ObtenPorClave(datos);
		acerca = new Acerca();

		JMenuBar mb = new JMenuBar();
		setJMenuBar(mb);

		JMenu ventanas = new JMenu("Horarios");
		JMenu archivo = new JMenu("Archivo");
		JMenu mAyuda = new JMenu("Ayuda");

		mb.add(archivo);
		mb.add(ventanas);
		mb.add(mAyuda);

		JMenuItem miAcerca = new JMenuItem("Acerca de...");
		miAcerca.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				acerca.setVisible(true);
			}
		});

		JMenuItem miConfComb = new JMenuItem("Como configurar la generación de horarios");
		miConfComb.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					BrowserLauncher.openURL("http://apeiron.sourceforge.net/configurar.htm");
				}
				catch (IOException e1)
				{
					e1.printStackTrace();
				}
			}
		});

		mAyuda.add(miAcerca);
		mAyuda.add(miConfComb);

		JMenuItem miCarga = new JMenuItem("1 Escoger Materias");
		ventanas.add(miCarga);

		miCarga.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				carga.setVisible(true);

			}
		});

		JMenuItem miPreferencias = new JMenuItem("2 Seleccionar Preferencias");
		ventanas.add(miPreferencias);

		miPreferencias.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				preferencias.setVisible(true);
			}
		});

		JMenuItem miTest = new JMenuItem("3 Generar horario");
		ventanas.add(miTest);

		miTest.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Combinador comb = new Combinador(new PrimeroElMejor(datos));
				dp.add(comb);
				comb.setVisible(true);
				comb.empezar();
			}
		});

		JMenuItem miDescargas = new JMenuItem("Administrador de Descargas");
		ventanas.add(miDescargas);

		miDescargas.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				descargas.setVisible(true);
			}
		});

		JMenuItem miSalir = new JMenuItem("Salir");
		JMenuItem miAct = new JMenuItem("Buscar Actualizaciones...");

		miAct.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				chkVersion.setVisible(true);
			}
		});

		miSalir.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});

		archivo.add(miAct);
		archivo.add(miSalir);

		dp = new JDesktopPane();
		setContentPane(new JScrollPane(dp));
		dp.add(descargas);
		dp.add(preferencias);
		dp.add(carga);
		dp.add(porClave);
		dp.add(acerca);

		setSize(600, 400);

		setLocationRelativeTo(null);
		sp.close(1000);
		setVisible(true);

		setExtendedState(JFrame.MAXIMIZED_BOTH);

		sp.dispose();
		preferencias.setVisible(true);

		chkVersion = new ChkVersion();
		dp.add(chkVersion);
	}

	public static void main(String[] args)
	{
		new Principal();
	}

}
