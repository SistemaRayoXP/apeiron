package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.BevelBorder;

import carga.LectorSiiau;
import carga.LectorSiiauListener;
import carga.MateriaSiiau;

public class Descarga extends JPanel implements LectorSiiauListener, ActionListener {
    public static final long serialVersionUID = 3;
    JProgressBar pbProgreso;
    JLabel lEstado;
    JButton btnCancelar;
    public LectorSiiau ls;

    public void actionPerformed(ActionEvent e) {
        ls.cancelar();
        btnCancelar.setEnabled(false);
        btnCancelar.setText("Cancelado");
    }

    public Descarga(LectorSiiau lectorSiiau) {
        super(new BorderLayout());
        ls = lectorSiiau;
        setBorder(new BevelBorder(BevelBorder.RAISED));
        JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        pbProgreso = new JProgressBar(0, 100);
        pbProgreso.setStringPainted(true);
        lEstado = new JLabel("Descargando..." + lectorSiiau.getMateriaSiiau().getClave());
        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(this);
        p.add(btnCancelar);
        add(p, "South");

        p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p.add(lEstado);
        add(p, "North");

        Box b = Box.createHorizontalBox();
        b.add(pbProgreso);
        add(b, "Center");

        pbProgreso.setBounds(10, 30, 100, 10);
        lEstado.setBounds(10, 10, 100, 15);

        setSize(200, 200);

    }

    public void advertencia(String descripcion) {
    }

    public void error(String descripcion) {
        lEstado.setText("[" + ls.getMateriaSiiau().getClave() + "] " + descripcion);
    }

    public void progreso(String estado, int porcentaje) {
        pbProgreso.setValue(porcentaje);
        pbProgreso.setString(Integer.toString(porcentaje) + "%");
        lEstado.setText(estado);
    }

    public void terminado(Vector reg, MateriaSiiau f) {
        pbProgreso.setValue(100);
        pbProgreso.setString("100%");
        lEstado.setText("Terminada la descarga de " + f.getClave());
        btnCancelar.setEnabled(false);

    }
}
