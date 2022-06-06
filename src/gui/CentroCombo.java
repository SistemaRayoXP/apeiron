package gui;

import javax.swing.JComboBox;

public class CentroCombo extends JComboBox {
    private static final long serialVersionUID = -3380286777287163273L;

    public static final CUN centros[] = {
            new CUN("D", "CUCEI"), new CUN("A", "CUAAD"), new CUN("B", "CUCBA"), new CUN("C", "CUCEA")
            // , new CU("D","C.U. DE CS. EXACTAS E ING.")
            , new CUN("E", "CUCS"), new CUN("F", "CUCSH"), new CUN("G", "CUALTOS"), new CUN("H", "CUCIENEGA"),
            new CUN("I", "CUCOSTA"), new CUN("J", "CUCSUR"), new CUN("K", "CUSUR"), new CUN("M", "CUVALLES"),
            new CUN("N", "CUNORTE")
            // , new CU("O","CUVALLES")
            // , new CU("P","CUCSUR SEDE VALLES")
            // , new CU("Q","CUCEI SEDE NORTE")
            // , new CU("R","CUALTOS SEDE NORTE")
            // , new CU("S","CUCOSTA SEDE NORTE")
            // , new CU("T","CUSUR SEDE NORTE")
            // , new CU("C","C.U. DE LOS LAGOS")
            // , new CU("V","CURSOS INTERD. DE VERANO")
            // , new CU("W","CUCEA SEDE VALLE")
            , new CUN("X", "UDG VIRTUAL")
    };

    public CentroCombo() {
        super(centros);
    }

}
