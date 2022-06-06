package gui;

import javax.swing.JFrame;

import carga.Horario;

public class ChkListTest extends JFrame {
    static final long serialVersionUID = 1;

    public ChkListTest() {
        super();
        setContentPane(new SeleccionHorario(new Horario()));
        setSize(300, 400);
        setVisible(true);

    }

    public static void main(String[] args) {
        new ChkListTest();

    }

}
