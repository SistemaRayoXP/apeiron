package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.AbstractTableModel;

import carga.AdDatos;
import carga.AdDatosListener;
import carga.Grupo;
import carga.Maestro;
import carga.Materia;

import combinacion.PrimeroElMejor;

public class Preferencias extends JInternalFrame implements AdDatosListener {
    public static final long serialVersionUID = 3;
    AdDatos datos;
    Materia mat;
    Maestro maestro;
    // DefaultListModel maestros;
    DefaultListModel materias;
    AbstractTableModel tmMaestros;
    AbstractTableModel tmGrupos;
    JList lstMaterias;
    Principal principal;
    // ChkList lstMaestros;
    JTable lstMaestros;
    JTable tblGrupos;

    JLabel est;
    JLabel detalle;

    private void cerrar() {
        setVisible(false);
    }

    public void nuevosDatos(AdDatos datos) {
        refrescar();
    }

    public void refrescar() {
        materias.clear();

        for (int x = 0; x < datos.materias.size(); x++) {
            materias.addElement(datos.materias.get(x));
        }
    }

    public Preferencias(AdDatos d, Principal prin) {
        this.principal = prin;
        datos = d;
        datos.addAdDatosListener(this);
        detalle = new JLabel("Detalles");

        setTitle("Apeiron [Preferencias]");
        setResizable(true);
        setMaximizable(true);
        setClosable(true);
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
        est = new JLabel("Estado");
        materias = new DefaultListModel();

        getContentPane().setLayout(null);

        lstMaterias = new JList(materias);
        lstMaterias.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (lstMaterias.getSelectedIndex() > -1)
                    mat = (Materia) lstMaterias.getSelectedValue();
                else
                    mat = null;
                tmMaestros.fireTableDataChanged();
            }
        });

        JTabbedPane tp = new JTabbedPane();

        JPanel p1 = new JPanel(new BorderLayout());
        JPanel p2 = new JPanel(new BorderLayout());
        JPanel p3 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel p4 = new JPanel(new BorderLayout());
        JPanel p5 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel p6 = new JPanel(new BorderLayout());

        // lstMaestros=new ChkList(maestros);

        tmMaestros = new AbstractTableModel() {
            static final long serialVersionUID = 1;

            public String getColumnName(int column) {
                switch (column) {
                    case 0:
                        return "Seleccionado";
                    case 1:
                        return "Profesor";

                }
                return "";
            }

            public Class getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 0:
                        return Boolean.class;
                    case 1:
                        return String.class;
                }
                return String.class;
            }

            public int getColumnCount() {
                return 2;
            }

            public int getRowCount() {
                if (mat != null)
                    return mat.maestros.size();
                else
                    return 0;

            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                if (columnIndex == 0)
                    return true;
                else
                    return false;
            }

            public Object getValueAt(int rowIndex, int columnIndex) {

                if (mat != null) {
                    if (columnIndex == 0)
                        return new Boolean(((Maestro) mat.maestros.get(rowIndex)).getMarca());
                    else
                        return ((Maestro) mat.maestros.get(rowIndex)).toString();
                }
                return "";
            }

            public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
                if (mat != null)
                    if (columnIndex == 0)
                        ((Maestro) mat.maestros.get(rowIndex)).setMarca(((Boolean) aValue).booleanValue());
            }

        };

        lstMaestros = new JTable(tmMaestros);
        lstMaestros.getColumnModel().getColumn(0).setPreferredWidth(60);
        lstMaestros.getColumnModel().getColumn(1).setPreferredWidth(300);
        lstMaestros.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        lstMaestros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lstMaestros.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                // Ignore extra messages.
                if (e.getValueIsAdjusting())
                    return;

                ListSelectionModel lsm = (ListSelectionModel) e.getSource();
                if (lsm.isSelectionEmpty()) {
                    maestro = null;
                } else {
                    maestro = ((Maestro) mat.maestros.get(lsm.getMinSelectionIndex()));
                }
                tmGrupos.fireTableDataChanged();
            }
        });

        tmGrupos = new AbstractTableModel() {
            static final long serialVersionUID = 1;

            public int getColumnCount() {
                return 6;
            };

            public int getRowCount() {
                if (maestro != null)
                    return maestro.grupos.size();
                else
                    return 0;
            };

            public Object getValueAt(int rowIndex, int columnIndex) {
                if (maestro != null) {
                    Grupo grp = (Grupo) maestro.grupos.get(rowIndex);
                    switch (columnIndex) {
                        case 0:
                            return grp.getNrc();
                        case 1:
                            return grp.getSec();
                        case 2:
                            return Integer.toString(grp.getCup());
                        case 3:
                            return Integer.toString(grp.getDis());
                        case 4:
                            return grp.getHor();
                        case 5:
                            return grp.horario.getPeriodoString();
                    }
                    return "";
                } else
                    return "";

            };

            public String getColumnName(int column) {
                switch (column) {
                    case 0:
                        return "NRC";
                    case 1:
                        return "Sección";
                    case 2:
                        return "Cupo";
                    case 3:
                        return "Disponible";
                    case 4:
                        return "Horario";
                    case 5:
                        return "Periodo";
                }
                return "";
            };

            public Class getColumnClass(int columnIndex) {
                return String.class;
            };
        };

        tblGrupos = new JTable(tmGrupos);
        tblGrupos.getColumnModel().getColumn(0).setPreferredWidth(50);
        tblGrupos.getColumnModel().getColumn(1).setPreferredWidth(50);
        tblGrupos.getColumnModel().getColumn(2).setPreferredWidth(50);
        tblGrupos.getColumnModel().getColumn(3).setPreferredWidth(70);
        tblGrupos.getColumnModel().getColumn(4).setPreferredWidth(300);
        tblGrupos.getColumnModel().getColumn(5).setPreferredWidth(150);
        tblGrupos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        p6.add(new JLabel("Grupos"), "North");
        p6.add(new JScrollPane(tblGrupos), "Center");
        p1.add(new JLabel("Materias"), "North");
        p1.add(new JScrollPane(lstMaterias), "Center");
        p2.add(new JLabel("Profesores"), "North");
        p2.add(new JScrollPane(lstMaestros), "Center");

        JButton btnAddMat = new JButton("Catálogo");
        JButton btnDelMat = new JButton("Borrar");
        JButton btnRefMat = new JButton("Actualizar Materias");
        JButton btnAddClave = new JButton("Agregar por Clave");

        btnDelMat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int indice;
                if ((indice = lstMaterias.getSelectedIndex()) > -1) {
                    datos.materias.remove(indice);
                    refrescar();
                }
            }
        });

        btnRefMat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (int indice = 0; indice < datos.materias.size(); indice++) {
                    datos.cargar(((Materia) datos.materias.get(indice)).getMateriaSiiau());
                }

            }
        });

        btnAddClave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                principal.porClave.setVisible(true);
            }
        });

        btnAddMat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                principal.carga.setVisible(true);
            }
        });

        p5.add(btnAddMat);
        p5.add(btnAddClave);
        p5.add(btnDelMat);
        p5.add(btnRefMat);

        p1.add(p5, "South");
        JSplitPane spl = new JSplitPane(JSplitPane.VERTICAL_SPLIT, p1, p2);
        spl.setDividerLocation(125);
        spl.setOneTouchExpandable(true);

        JSplitPane spl2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, spl, p6);
        spl2.setDividerLocation(300);
        spl2.setOneTouchExpandable(true);

        tp.addTab("Materias, Profesores y Grupos", spl2);

        SeleccionHorario sh = new SeleccionHorario(datos.horarioUsuario);

        tp.addTab("Horario del Estudiante", sh);

        tp.addTab("Combinaciones", new CombPref(datos));

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cerrar();
            }
        });

        JButton btnCombinar = new JButton("Buscar Horario");
        btnCombinar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Combinador comb = new Combinador(new PrimeroElMejor(datos));
                principal.dp.add(comb);
                comb.setVisible(true);
                comb.empezar();
            }
        });

        JButton btnImportar = new JButton("Importar");
        btnImportar.setToolTipText("Importar de un archivo XML todas las preferencias");
        btnImportar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();

                FileFilter ff = new FileFilter() {
                    public boolean accept(File f) {
                        String nombre = f.getName();
                        if (f.isDirectory())
                            return true;
                        else
                            return nombre.endsWith(".xml") || nombre.endsWith(".XML");
                    }

                    public String getDescription() {
                        return "Archivos XML";
                    }
                };

                fc.setFileFilter(ff);

                int ret = fc.showOpenDialog(null);

                if (ret == JFileChooser.APPROVE_OPTION) {
                    JInternalFrame tmp = principal.preferencias;
                    try {
                        new PreferenciasXLM(datos).leer(fc.getSelectedFile().getAbsolutePath());
                        principal.preferencias = new Preferencias(datos, principal);
                        principal.dp.add(principal.preferencias);
                        principal.preferencias.setVisible(true);
                        principal.preferencias.refrescar();
                        tmp.dispose();
                    } catch (Exception e2) {
                        JOptionPane.showInternalMessageDialog(principal.preferencias, e2 + "", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }

            }
        });

        JButton btnExportar = new JButton("Exportar");
        btnExportar.setToolTipText("Exportar a un archivo XML todas las preferencias");
        btnExportar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();

                FileFilter ff = new FileFilter() {
                    public boolean accept(File f) {
                        String nombre = f.getName();
                        if (f.isDirectory())
                            return true;
                        else
                            return nombre.endsWith(".xml") || nombre.endsWith(".XML");
                    }

                    public String getDescription() {
                        return "Archivos XML";
                    }
                };

                fc.setFileFilter(ff);

                int ret = fc.showSaveDialog(null);

                if (ret == JFileChooser.APPROVE_OPTION) {
                    if (fc.getSelectedFile().getName().endsWith(".xml")
                            || fc.getSelectedFile().getName().endsWith(".xml"))
                        new PreferenciasXLM(datos).escribir(fc.getSelectedFile().getAbsolutePath());
                    else
                        new PreferenciasXLM(datos).escribir(fc.getSelectedFile().getAbsolutePath() + ".xml");
                }

            }
        });

        p3.add(btnExportar);
        p3.add(btnImportar);
        p3.add(btnCombinar);
        p3.add(btnCerrar);
        p4.add(p3, "South");
        p4.add(tp, "Center");
        setContentPane(p4);

        setSize(600, 500);
        setLocation(0, 0);

        // setVisible(true);
    }

}
