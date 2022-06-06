package carga;

public class Registro {
    public String nrc;
    public String clave;
    public String materia;
    public String seccion;
    public String creditos;
    public String cupo;
    public String disponible;
    public String profesor;
    public String periodo;

    Horario horario;

    public Registro() {
        horario = new Horario();
    }

    public String toString() {
        return "NRC: " + nrc + "\n" +
                "Clave: " + clave + "\n" +
                "Materia: " + materia + "\n" +
                "Seccion: " + seccion + "\n" +
                "Creditos: " + creditos + "\n" +
                "Cupo: " + cupo + "\n" +
                "Disponible: " + disponible + "\n" +
                "Periodo: " + periodo + "\n" +
                "Horario: " + horario.toString() + "\n" +

                "Profesor: " + profesor + "\n\n";
    }
}
