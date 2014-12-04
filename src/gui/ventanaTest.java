package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import carga.AdDatos;
import carga.MateriaSiiau;


class CU{
	String clave;
	String etiqueta;
	public String toString(){
		return etiqueta;
	}
	
	public CU(String clv, String etiq){
		clave=clv;
		etiqueta=etiq;
	}
}

public class ventanaTest extends JInternalFrame{
	static final long serialVersionUID=1;
	JButton btnGo;
	JTextArea texto;
	JTextField ciclo;
	JTextField clave;
	JComboBox centro;
	AdDatos datos;
	JLabel estado;
	
	public ventanaTest(AdDatos d){
		super();
		Object centros[]={new CU("A","C.U. DE ARTE, ARQ. Y DISEÑO"),
				new CU("B","C.U. DE CS. BIOLOGICO Y AGR."),
				new CU("C","C.U. DE CS. ECONOMICO-ADMVAS."),
				new CU("D","C.U. DE CS. EXACTAS E ING."),
				new CU("E","C.U. DE CS. DE LA SALUD"),
				new CU("F","C.U. DE CS. SOCIALES Y HUM."),
				new CU("G","C.U. DE LOS ALTOS"),
				new CU("H","C.U. DE LA CIENEGA"),
				new CU("I","C.U. DE LA COSTA"),
				new CU("J","C.U. DE LA COSTA SUR"),
				new CU("K","C.U. DEL SUR"),
				new CU("M","C.U. DE LOS VALLES"),
				new CU("N","C.U. DEL NORTE"),
				new CU("O","CUCEI SEDE VALLES"),
				new CU("P","CUCSUR SEDE VALLES"),
				new CU("Q","CUCEI SEDE NORTE"),
				new CU("R","CUALTOS SEDE NORTE"),
				new CU("S","CUCOSTA SEDE NORTE"),
				new CU("T","CUSUR SEDE NORTE"),
				new CU("C","C.U. DE LOS LAGOS"),
				new CU("V","CURSOS INTERD. DE VERANO"),
				new CU("W","CUCEA SEDE VALLE"),
				new CU("X","SIST. DE UNIVERSIDAD VIRTUAL"),
				
				};
		
		
		setTitle("Lector de html de SIIAU");
		setResizable(true);
		setMaximizable(true);
		setClosable(true);
		setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
		
		datos=d;
		texto=new JTextArea();
		centro=new JComboBox(centros);
		ciclo=new JTextField();
		clave=new JTextField();
		btnGo=new JButton();
		estado=new JLabel();
		JPanel menu= new JPanel();
		JLabel lciclo= new JLabel();
		JLabel lclave= new JLabel();
		JLabel lcentro= new JLabel();
							
		clave.setColumns(7);
		ciclo.setColumns(7);
		ciclo.setText("200520");
		lciclo.setText("Ciclo:");
		lclave.setText("Clave:");
		lcentro.setText("Centro:");
		btnGo.setText("Go!");
		estado.setHorizontalAlignment(JLabel.RIGHT);
		
			
		menu.add(lciclo);
		menu.add(ciclo);
		menu.add(lcentro);
		menu.add(centro);
		menu.add(lclave);
		menu.add(clave);
		menu.add(btnGo);
				
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(new JScrollPane(texto),"Center");
		getContentPane().add(menu,"North");
		getContentPane().add(estado,"South");
		pack();
		setSize(getWidth(),300);
		
		 
				
		btnGo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				CU tmp=(CU)centro.getSelectedItem();
				
				datos.cargar(new MateriaSiiau(clave.getText(),tmp.clave,ciclo.getText()));
				//btnGo.setEnabled(false);
			}
		});
		
		
	}
}
