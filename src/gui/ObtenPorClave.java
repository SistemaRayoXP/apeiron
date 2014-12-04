package gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import carga.AdDatos;
import carga.MateriaSiiau;

public class ObtenPorClave extends JInternalFrame{
	private static final long serialVersionUID = -6839515434020487533L;
	
	private AdDatos datos;
	private JButton btnOk;
	private JTextField txtClave;
	private JComboBox cmbCen;
	private JComboBox cmbCal;
	private JLabel lblEstado;
	
	protected Vector obtenerClaves(String s){
		Vector claves=new Vector();
		int pos=0;
		StringBuffer sb=new StringBuffer();
		
		while (pos<s.length()){
			if (!Character.isWhitespace(s.charAt(pos)) && !(s.charAt(pos)==',') && !(s.charAt(pos)==';')){
				sb.append(s.charAt(pos));
			}else{
				if (sb.length()>0){
					claves.add(sb.toString().toUpperCase());
					sb=new StringBuffer();
				}
			}
			pos++;
		}
		
		if (sb.length()>0)
			claves.add(sb.toString().toUpperCase());
				
		if (claves.size()>0)
			return claves;
		
		return null;
	}
	
	public ObtenPorClave(AdDatos dat){
		super("Descarga por Clave");
		
		this.datos=dat;
		
		setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
	    setResizable(true);
	    setMaximizable(true);
		setClosable(true);
		  
		lblEstado=new JLabel("Separe las claves con comas o espacios");
		
		cmbCen=new CentroCombo();
		cmbCal=new CalenCombo();
		txtClave=new JTextField(15);
		btnOk=new JButton("Descargar");
		btnOk.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				//Validar txtClave
				if (txtClave.getText().equals("")){
					lblEstado.setText("* Debe llenar todos los campos");
				}else{
					Vector claves=obtenerClaves(txtClave.getText());
					
					for (int x=0;x<claves.size();x++){
						MateriaSiiau ms=new MateriaSiiau(claves.get(x)+"",
						                        ((CUN)cmbCen.getSelectedItem()).clave,
												((Cal)cmbCal.getSelectedItem()).calendario);
						datos.cargar(ms);
					}
					hide();
				}
			}
		});
		JPanel panel=new JPanel(new FlowLayout());
		
		JPanel panelClaves=new JPanel(new FlowLayout());
		panelClaves.add(new JLabel(" Claves: "));
		panelClaves.add(txtClave);
		panelClaves.add(btnOk);
		
		panel.add(cmbCen);
		panel.add(cmbCal);
		panel.add(panelClaves);
		
		
		getContentPane().add(panel,"Center");
		getContentPane().add(lblEstado,"South");
		setSize(370,120);		
	}
}
