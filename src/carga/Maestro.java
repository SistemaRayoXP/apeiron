package carga;

import java.util.Vector;

public class Maestro implements Marcable {
    Materia materia;
    String nombre;
    public Vector grupos;

    private boolean marca;

    public String getNombre() {
        return nombre;
    }

    public Materia getPadre() {
        return materia;
    }

    public Object clone() {
        Maestro m = new Maestro(materia);
        m.nombre = nombre;
        m.marca = marca;
        for (int x = 0; x < grupos.size(); x++)
            m.grupos.add(((Grupo) grupos.get(x)).clone());
        return m;
    }

    public boolean getMarca() {
        return marca;
    }

    public void setMarca(boolean marca) {
        this.marca = marca;
    }

    public Maestro(Materia materia) {
        super();
        this.materia = materia;
        grupos = new Vector(10, 10);
        marca = true;
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

    public Grupo buscarGrupo(Object obj) {
        Grupo tmpGrupo;
        for (int x = 0; x < grupos.size(); x++) {
            tmpGrupo = (Grupo) grupos.get(x);
            if (tmpGrupo.equals(obj))
                return tmpGrupo;
        }
        return null;
    }
}
