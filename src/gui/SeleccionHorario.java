package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import carga.Horario;
import carga.SubHora;

class Dia {
    private String etiqueta;
    private int indice;

    public Dia(String etiqueta, int indice) {
        this.etiqueta = etiqueta;
        this.indice = indice;
    }

    public int getIndice() {
        return indice;
    }

    public String toString() {
        return etiqueta;
    }
}

public class SeleccionHorario extends JPanel {
    static final long serialVersionUID = 1;

    private Vector dias[];
    private JComboBox cb;
    private DefaultListModel lm;

    public SeleccionHorario(Horario h) {
        super(new BorderLayout());
        Object d[] = {
                new Dia("Lunes", 0),
                new Dia("Martes", 1),
                new Dia("Miércoles", 2),
                new Dia("Jueves", 3),
                new Dia("Viernes", 4),
                new Dia("Sábado", 5),
        };

        cb = new JComboBox(d);
        lm = new DefaultListModel();
        ChkList lista = new ChkList(lm);

        dias = new Vector[6];
        for (int y = 0; y < 6; y++) {
            dias[y] = new Vector(10, 10);
            for (int x = 7; x <= 21; x++) {
                if (x < 13)
                    if (x == 12)
                        dias[y].add(new SubHora(h, y, x, Integer.toString(x) + ":00 pm"));
                    else
                        dias[y].add(new SubHora(h, y, x, Integer.toString(x) + ":00 am"));
                else
                    dias[y].add(new SubHora(h, y, x, Integer.toString(x - 12) + ":00 pm"));
            }
        }

        for (int x = 0; x < dias[0].size(); x++)
            lm.addElement(dias[0].get(x));

        cb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int indice = ((Dia) cb.getSelectedItem()).getIndice();
                lm.clear();
                for (int x = 0; x < dias[indice].size(); x++)
                    lm.addElement(dias[indice].get(x));

            }
        });

        JButton btnManana = new JButton("Mañana");
        btnManana.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                int indice = ((Dia) cb.getSelectedItem()).getIndice();
                lm.clear();
                for (int x = 0; x < dias[indice].size(); x++) {
                    SubHora sh = (SubHora) dias[indice].get(x);
                    if (x < 6)
                        sh.setMarca(true);
                    else
                        sh.setMarca(false);
                    lm.addElement(sh);
                }
            }
        });

        JButton btnTarde = new JButton("Tarde");
        btnTarde.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                int indice = ((Dia) cb.getSelectedItem()).getIndice();
                lm.clear();
                for (int x = 0; x < dias[indice].size(); x++) {
                    SubHora sh = (SubHora) dias[indice].get(x);
                    if (x < 9)
                        sh.setMarca(false);
                    else
                        sh.setMarca(true);
                    lm.addElement(sh);
                }
            }
        });

        JButton btnNinguna = new JButton("Ninguna");
        btnNinguna.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                int indice = ((Dia) cb.getSelectedItem()).getIndice();
                lm.clear();
                for (int x = 0; x < dias[indice].size(); x++) {
                    SubHora sh = (SubHora) dias[indice].get(x);
                    sh.setMarca(false);
                    lm.addElement(sh);
                }
            }
        });

        JButton btnAplTodos = new JButton("Aplicar a Todos");
        btnAplTodos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                int indice = ((Dia) cb.getSelectedItem()).getIndice();

                for (int x = 0; x < dias[indice].size(); x++) {
                    SubHora sh = (SubHora) dias[indice].get(x);
                    for (int y = 0; y < 6; y++) {
                        if (y != indice) {
                            SubHora sh2 = (SubHora) dias[y].get(x);
                            sh2.setMarca(sh.getMarca());
                        }
                    }

                }
            }
        });

        JPanel pmenu = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pmenu.add(btnManana);
        pmenu.add(btnTarde);
        pmenu.add(btnNinguna);
        pmenu.add(btnAplTodos);
        pmenu.add(new JLabel("Dia:"));
        pmenu.add(cb);
        add(pmenu, "North");

        JPanel plista = new JPanel(new BorderLayout());
        plista.add(new JLabel("Horas disponibles"), "North");
        plista.add(new JScrollPane(lista), "Center");
        add(plista, "Center");
    }
}
