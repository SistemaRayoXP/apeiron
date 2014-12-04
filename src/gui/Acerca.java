package gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Acerca extends JInternalFrame {
	private static final long serialVersionUID = 3981528613124927775L;
	
	JTextArea txtTexto;
	StringBuffer texto;
	JButton btnIrSitio;
		
	public Acerca(){
		super("Acerca de Apeiron...");
		setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
	    setResizable(true);
	    setMaximizable(true);
		setClosable(true);
		
		btnIrSitio=new JButton("Ir a la página del proyecto");
		btnIrSitio.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				try {
					BrowserLauncher.openURL("http://apeiron.sourceforge.net");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		texto=new StringBuffer();
		txtTexto=new JTextArea();
		txtTexto.setEditable(false);
		txtTexto.setLineWrap(true);
		txtTexto.setWrapStyleWord(true);
		
		JPanel panel=new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panel.add(btnIrSitio);
				
		getContentPane().add(new JScrollPane(txtTexto));
		getContentPane().add(panel,"South");
		
		setSize(270,300);
		
		escribir("\n    AGRADECIMIENTOS\n");
		
		escribir("               Emma: Gracias por todo...");
		escribir("               Gerardo Flores Correa");
		escribir("               Dulce Espinoza"); 
		escribir("               Faby Galindo"); 
		escribir("               Ahgue Figueroa");
		escribir("               Anabell Saldaña"); 
		escribir("               Daniel Rodríguez"); 
		escribir("               Ing. Luis F. Rodríguez Acosta\n");
		
		escribir("    A TODOS ELLOS MIL GRACIAS!");
		
		escribir("\nCONTACTO\n");
		
		escribir("Si encuentra algún problema, tiene un comentario o incluso una felicitación escriba a:");
		escribir("\n               rieztra@users.sourceforge.net");
		
	}
		
	protected void escribir(String s){
		texto.append(s+"\n");
		txtTexto.setText(texto+"");
		txtTexto.setSelectionStart(texto.length());
	}
		
}
