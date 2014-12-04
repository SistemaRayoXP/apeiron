package gui;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import carga.AdDatos;

public class CombPref extends JPanel {
	static final long serialVersionUID=1;
	AdDatos datos;
	public CombPref(AdDatos dat){
		super(null);
		this.datos=dat;
		
		JPanel p1=new JPanel(null);
		p1.setSize(390,140);
		p1.setLocation(10,10);
		p1.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("General"),BorderFactory.createEmptyBorder(5,5,5,5)));
		
		JCheckBox cupos=new JCheckBox("Solo grupos con cupo");
		cupos.setSize(cupos.getPreferredSize());
		cupos.setLocation(10,90);
		cupos.setSelected(datos.conCupo);
		
		cupos.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e) {
				datos.conCupo=((JCheckBox)e.getSource()).isSelected();
			}
		});
		
		JCheckBox chkEvaluarPeriodos=new JCheckBox("Tomar periodos en cuenta (Materias Espejo)");
		chkEvaluarPeriodos.setSize(chkEvaluarPeriodos.getPreferredSize());
		chkEvaluarPeriodos.setLocation(10,110);
		chkEvaluarPeriodos.setSelected(datos.evaluarPeriodos);
		
		chkEvaluarPeriodos.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e) {
				datos.evaluarPeriodos=((JCheckBox)e.getSource()).isSelected();
			}
		});
		
		JLabel lblmax=new JLabel("Número máximo de horarios a generar: ");
		lblmax.setSize(lblmax.getPreferredSize());
		lblmax.setLocation(10,20);
		
		
		JTextField txtmax=new JTextField(10);
		txtmax.setSize(txtmax.getPreferredSize());
		txtmax.setLocation(lblmax.getX()+lblmax.getWidth()+10,20);
		txtmax.setText(Integer.toString(datos.maxHorarios));
		txtmax.addKeyListener(new KeyListener(){
			
			public void keyReleased(KeyEvent e) {
				JTextField tf=(JTextField)e.getSource();
				int val;
				try{
					val=Integer.parseInt(tf.getText());
				}catch(RuntimeException ex){
					val=1000;//default
				}
				
				datos.maxHorarios=val;
			}
			public void keyTyped(KeyEvent e) {}
			public void keyPressed(KeyEvent e) {}
		});
		
		
		JLabel lblhueInt=new JLabel("Huecos intermedios permisibles: ");
		lblhueInt.setSize(lblhueInt.getPreferredSize());
		lblhueInt.setLocation(10,60);
		
		JTextField txthueInt=new JTextField(10);
		txthueInt.setSize(txthueInt.getPreferredSize());
		txthueInt.setLocation(lblmax.getX()+lblmax.getWidth()+10,60);
		txthueInt.setText(Integer.toString(datos.maxHuecosInt));
		txthueInt.addKeyListener(new KeyListener(){
			public void keyReleased(KeyEvent e) {
				JTextField tf=(JTextField)e.getSource();
				int val;
				try{
					val=Integer.parseInt(tf.getText());
				}catch(RuntimeException ex){
					val=5;
				}
				datos.maxHuecosInt=val;
			}
			public void keyTyped(KeyEvent e) {}
			public void keyPressed(KeyEvent e) {}
		});
		
		JLabel lblhue=new JLabel("Huecos permisibles: ");
		lblhue.setSize(lblhue.getPreferredSize());
		lblhue.setLocation(10,40);
		
		JTextField txthue=new JTextField(10);
		txthue.setSize(txthue.getPreferredSize());
		txthue.setLocation(lblmax.getX()+lblmax.getWidth()+10,40);
		txthue.setText(Integer.toString(datos.maxHuecos));
		
		txthue.addKeyListener(new KeyListener(){
			public void keyReleased(KeyEvent e) {
				JTextField tf=(JTextField)e.getSource();
				int val;
				try{
					val=Integer.parseInt(tf.getText());
				}catch(RuntimeException ex){
					val=2;
				}
				
				datos.maxHuecos=val;				
			}
			public void keyTyped(KeyEvent e) {}
			public void keyPressed(KeyEvent e) {}
		});		
				
		JLabel instr1=new JLabel("-1 Significa infinito");
		
		
		instr1.setForeground(Color.BLUE);
		instr1.setSize(380,instr1.getPreferredSize().height);
		instr1.setHorizontalAlignment(JLabel.RIGHT);
		instr1.setLocation(0,90);
		
		p1.add(instr1);
		p1.add(txthueInt);
		p1.add(lblhueInt);
		p1.add(txthue);
		p1.add(lblhue);
		p1.add(txtmax);
		p1.add(lblmax);
		p1.add(cupos);
		p1.add(chkEvaluarPeriodos);
		
		JPanel p2=new JPanel(null);
		
		p2.setSize(390,120);
		p2.setLocation(10,150);
		p2.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Orden de los grupos"),BorderFactory.createEmptyBorder(5,5,5,5)));
		
		JLabel instr2=new JLabel("Coloque los numeros del 1 al 2 en las casillas,");
		JLabel instr3=new JLabel("positivos (menor-mayor) negativos (mayor-menor)");
		
		instr2.setForeground(Color.BLUE);
		instr2.setSize(instr2.getPreferredSize());
		instr2.setLocation(10,20);
		
		instr3.setForeground(Color.BLUE);
		instr3.setSize(instr3.getPreferredSize());
		instr3.setLocation(10,35);
		
		JLabel lblHora=new JLabel("Hora del día:");
		lblHora.setSize(lblHora.getPreferredSize());
		lblHora.setLocation(10,65);
		
		
		JTextField txtHora=new JTextField(2);
		txtHora.setText(Integer.toString(datos.prHora));
		txtHora.setSize(txtHora.getPreferredSize());
		txtHora.setLocation(lblHora.getWidth()+20,65);
		txtHora.addKeyListener(new KeyListener(){
			public void keyReleased(KeyEvent e) {
				JTextField tf=(JTextField)e.getSource();
				int val;
				try{
					val=Integer.parseInt(tf.getText());
				}catch(RuntimeException ex){
					val=2;
				}
				
				datos.prHora=val;	
			}
			public void keyTyped(KeyEvent e) {}
			public void keyPressed(KeyEvent e) {}
		});
		
		
		JLabel lblDem=new JLabel("Demanda:");
		lblDem.setSize(lblDem.getPreferredSize());
		lblDem.setLocation(10,85);
		
		
		JTextField txtDem=new JTextField(2);
		txtDem.setText(Integer.toString(datos.prDemanda));
		txtDem.setSize(txtDem.getPreferredSize());
		txtDem.setLocation(lblHora.getWidth()+20,85);
		txtDem.addKeyListener(new KeyListener(){
			public void keyReleased(KeyEvent e) {
				JTextField tf=(JTextField)e.getSource();
				int val;
				try{
					val=Integer.parseInt(tf.getText());
				}catch(RuntimeException ex){
					val=-1;
				}
				
				datos.prDemanda=val;	
			}
			public void keyTyped(KeyEvent e) {}
			public void keyPressed(KeyEvent e) {}
		});
		p2.add(instr3);
		p2.add(instr2);
		p2.add(lblDem);
		p2.add(txtDem);
		p2.add(lblHora);
		p2.add(txtHora);
		
		add(p2);
		add(p1);
	}
}
