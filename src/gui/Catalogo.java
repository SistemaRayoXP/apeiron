package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public class Catalogo extends JInternalFrame {
    private static final long serialVersionUID = 1;
    private JComboBox cuadroComb, cal;
    private Vector eventos;

    public void addCatalogoListener(CatalogoListener cl) {
        eventos.add(cl);
    }

    public void fireSeleccion(Vector v) {
        for (int x = 0; x < eventos.size(); x++) {
            ((CatalogoListener) eventos.get(x)).selecciones(v);
        }
    }

    private Vector strs, strs2, seleccion;
    private JList list, lstSeleccion;
    private DefaultListModel lmSeleccion;
    private JLabel eCentro, lcal;
    private JTextField buscaText;

    private JButton enviaButton, busca, resetButton;
    private JPanel panelSur, panelNorte;

    // Para Paco
    public String resCal;// Calendario
    public Vector resMat;// Vector con las materias

    public Catalogo() {

        // Frame Principal
        super("Selecciona tus materias");

        eventos = new Vector();
        cuadroComb = new CentroCombo();
        cal = new CalenCombo();
        list = new JList();
        enviaButton = new JButton("Descargar");
        busca = new JButton("Filtrar");
        resetButton = new JButton("Limpiar");
        eCentro = new JLabel("Centro Unversitario:", JLabel.CENTER);
        lcal = new JLabel("Calendario: ", JLabel.CENTER);
        buscaText = new JTextField("", 15);
        seleccion = new Vector(15, 5);
        lmSeleccion = new DefaultListModel();
        lstSeleccion = new JList(lmSeleccion);

        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
        setResizable(true);
        setMaximizable(true);
        setClosable(true);

        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameActivated(InternalFrameEvent e) {
                buscaText.grabFocus();
            }
        });

        // Manejador de Acciones
        ManejaAction manejador = new ManejaAction();

        enviaButton.addActionListener(manejador);
        busca.addActionListener(manejador);
        resetButton.addActionListener(manejador);
        buscaText.addActionListener(manejador);

        // Manejador Eventos Mouse
        ManejaRaton manejador1 = new ManejaRaton();

        list.addMouseListener(manejador1);

        // Manejador de Cambio de Centros
        ManejaItem manejador3 = new ManejaItem();

        cuadroComb.addItemListener(manejador3);

        // Funcion para cargar centros, en este caso default cucei
        cargaCentros("CUCEI");

        list.setCellRenderer(new CheckListRenderer());
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setBorder(new EmptyBorder(0, 4, 0, 0));

        launchFrame();
    }

    public void launchFrame() {

        panelSur = new JPanel(new FlowLayout());
        panelNorte = new JPanel(new FlowLayout());

        // Parte Sur
        panelSur.add(new JLabel("Busca: "));
        panelSur.add(buscaText);
        panelSur.add(busca);
        panelSur.add(resetButton);
        panelSur.add(enviaButton);

        // Parte Norte
        panelNorte.add(eCentro);
        panelNorte.add(cuadroComb);
        panelNorte.add(lcal);
        panelNorte.add(cal);

        JSplitPane sp = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                new PanelTitulado("Lista de Materias", new JScrollPane(list)),
                new PanelTitulado("Lista de Materias Seleccionadas", new JScrollPane(lstSeleccion)));

        sp.setDividerLocation(200);
        sp.setOneTouchExpandable(true);

        getContentPane().add(sp, BorderLayout.CENTER);
        getContentPane().add(panelSur, BorderLayout.SOUTH);
        getContentPane().add(panelNorte, BorderLayout.NORTH);
        setSize(600, 450);

    }

    private void cargaCentros(Object centro) {

        try {
            InputStream is = Catalogo.class.getResourceAsStream(centro.toString() + ".dat");

            String cad = null;
            BufferedReader ent = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            strs = new Vector(3000, 20);
            int i = 0;

            while ((cad = ent.readLine()) != null) {

                strs.addElement(cad);
                // System.out.println(strs.elementAt(i));
                i++;
            }
            ent.close();
        }

        catch (FileNotFoundException e1) {
            strs.removeAllElements();
            System.out.println("Archivo no encontrado");
            strs.addElement("Archivo no encontrado");
        } catch (IOException e2) {
            strs.removeAllElements();
            System.out.println("Algo Paso");
            strs.addElement("Algo Raro Paso");
            e2.printStackTrace();
        }

        list.setListData(createData(strs));
    }

    private Vector createData(Vector strs) {

        int n = strs.size();
        Vector items = new Vector();

        for (int i = 0; i < n; i++) {
            String a = (String) strs.elementAt(i);
            items.addElement(new CheckableItem(a, CentroCombo.centros[cuadroComb.getSelectedIndex()]));
        }

        return items;
    }

    private void ponCheck(JList list) {
        ListModel model = list.getModel();
        int n = model.getSize();

        for (int i = 0; i < n; i++) {
            CheckableItem item = (CheckableItem) model.getElementAt(i);
            item.setSelected(false);

            if (seleccion.contains(item.toCentro() + " " + item.toString())) {
                item.setSelected(true);
            }
            list.repaint();
        }
    }

    private void buscaMat() {

        strs2 = new Vector(30, 10);

        String cadena = buscaText.getText();
        cadena = cadena.toUpperCase();
        int n = strs.size();

        for (int i = 0; i < n; i++) {
            String a = (String) strs.elementAt(i);

            // Busqueda de subcadenas en el vector str
            if (a.indexOf(cadena) > 0 || a.startsWith(cadena)) {
                // System.out.println(a);
                strs2.addElement(a);
            }
        }

        list.setListData(createData(strs2));
        // ACtualiza(si hay nuevos checkbox marcados o si se hizo reset)
        ponCheck(list);
    }

    public void enviar() {

        Vector resultado = new Vector(10, 5);
        int t = seleccion.size();

        for (int i = 0; i < t; i++) {
            ResCat rc = new ResCat();

            String res = (String) seleccion.elementAt(i);
            StringTokenizer a = new StringTokenizer(res);
            rc.centro = a.nextToken();
            rc.clave = a.nextToken();
            rc.cal = ((Cal) cal.getSelectedItem()).calendario;
            resultado.addElement(rc);
        }

        fireSeleccion(resultado);
        hide();
    }

    private class ManejaRaton extends MouseAdapter {

        public void mouseClicked(MouseEvent e) {

            if (e.getSource() == list) {

                int index = list.getSelectedIndex();

                if (index > -1) {
                    CheckableItem item = (CheckableItem) list.getSelectedValue();
                    item.setSelected(!item.isSelected());
                    if (!item.isSelected() == false) {
                        if (seleccion.indexOf(item.toString()) < 0) {
                            seleccion.addElement(item.toCentro() + " " + item.toString());
                            lmSeleccion.addElement(item + "");
                        }
                    } else {
                        seleccion.remove(item.toCentro() + " " + item.toString());
                        lmSeleccion.removeElement(item + "");
                    }
                }
                list.repaint();
            }

        }

    }

    private class ManejaAction implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            // Si se presiona enviar
            if (e.getSource() == enviaButton) {
                enviar();
            }

            // Si se presiona Filtra
            else if (e.getSource() == busca) {
                buscaMat();
            }

            // Si se presiona Reset
            else if (e.getSource() == resetButton) {
                seleccion.removeAllElements();
                lmSeleccion.removeAllElements();
                ponCheck(list);
            }

            // Si se da enter en le TextField
            else if (e.getSource() == buscaText) {
                buscaMat();
            }
        }
    }

    private class ManejaItem implements ItemListener {

        public void itemStateChanged(ItemEvent evento) {

            if (evento.getSource() == cuadroComb) {
                cargaCentros(CentroCombo.centros[cuadroComb.getSelectedIndex()]);
                buscaMat();
                ponCheck(list);

            }
        }
    }

    class CheckableItem {
        private String str;
        private String clave;
        // private String centro;
        private boolean isSelected;

        public CheckableItem(String str, CUN centros) {
            isSelected = false;
            this.str = str;
            // this.centro=centros.toString();
            this.clave = centros.toClave();
        }

        public void setSelected(boolean b) {
            isSelected = b;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public String toCentro() {
            // return centro.toString();
            return clave;
        }

        public String toString() {
            return str;
        }
    }

    class CheckListRenderer extends JCheckBox implements ListCellRenderer {
        private static final long serialVersionUID = 1;

        public CheckListRenderer() {
            setBackground(UIManager.getColor("List.textBackground"));
            setForeground(UIManager.getColor("List.textForeground"));
        }

        public Component getListCellRendererComponent(JList list, Object value,
                int index, boolean isSelected, boolean hasFocus) {
            setEnabled(list.isEnabled());
            setSelected(((CheckableItem) value).isSelected());
            setFont(list.getFont());
            setText(value.toString());
            return this;
        }
    }

    public static void main(String args[]) {
        Catalogo frame = new Catalogo();
        frame.setVisible(true);

    }

}

class Cal {
    String calendario;
    String etiqueta;

    public Cal(String cal, String lbl) {
        calendario = cal;
        etiqueta = lbl;
    }

    public String toString() {
        return etiqueta;
    }
}

class CUN {
    String clave;
    String etiqueta;

    public String toString() {
        return etiqueta;
    }

    public String toClave() {
        return clave;
    }

    public CUN(String clv, String etiq) {
        clave = clv;
        etiqueta = etiq;
    }
}

class PanelTitulado extends JPanel {
    private static final long serialVersionUID = 9067270384166640706L;

    public PanelTitulado(String titulo, Component centro) {
        super(new BorderLayout());
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel(titulo));
        add(top, "North");
        add(centro, "Center");
    }
}
