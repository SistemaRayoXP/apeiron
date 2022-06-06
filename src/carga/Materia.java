package carga;

import java.util.Vector;

public class Materia implements Marcable {

    MateriaSiiau fuente;
    String nombre;
    String creditos;
    public Vector maestros;
    private boolean marca;

    public String getNombre() {
        return nombre;
    }

    public Object clone() {
        Materia m = new Materia();
        m.fuente = (MateriaSiiau) fuente.clone();
        m.nombre = nombre;
        m.creditos = creditos;
        m.marca = marca;
        for (int x = 0; x < maestros.size(); x++)
            m.maestros.add(((Maestro) maestros.get(x)).clone());
        return m;
    }

    public MateriaSiiau getMateriaSiiau() {
        return fuente;
    }

    public boolean getMarca() {
        return marca;
    }

    public void setMarca(boolean marca) {
        this.marca = marca;
    }

    public Materia() {
        super();
        maestros = new Vector(10, 10);
    }

    public String toString() {
        return nombre;
    }

    public boolean equals(Object obj) {
        if (obj.toString().equals(nombre))
            return true;
        else
            return false;

    }

    public int buscarMaestro(Object obj) {
        for (int x = 0; x < maestros.size(); x++) {
            int res = maestros.get(x).toString().compareTo(obj.toString());
            if (res >= 0) {
                if (res == 0)
                    return x;
                else
                    return -(x + 1);
            }
        }
        return -(maestros.size() + 1);
    }

    /*
     * public Maestro buscarMaestro(Object obj){
     * Maestro tmpMaestro;
     * for(int x=0;x<maestros.size();x++){
     * tmpMaestro=(Maestro)maestros.get(x);
     * if (tmpMaestro.equals(obj))
     * return tmpMaestro;
     * }
     * return null;
     * }
     */

}