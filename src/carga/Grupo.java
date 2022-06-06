package carga;

public class Grupo implements Marcable {
    Maestro padre;
    String nrc;
    String sec;
    int cup;
    int dis;
    public Horario horario;
    public int huecos;

    private boolean marca;

    public Maestro getPadre() {
        return padre;
    }

    public String getNrc() {
        return nrc;
    }

    public String getSec() {
        return sec;
    }

    public int getCup() {
        return cup;
    }

    public int getDis() {
        return dis;
    }

    public boolean subHorario(Horario h) {
        return horario.subHorario(h);
    }

    public String getHor() {
        return horario.toString();
    }

    public boolean getMarca() {
        return marca;
    }

    public void setMarca(boolean marca) {
        this.marca = marca;
    }

    public String toString() {
        return nrc + ' ' + horario.toString();
    }

    public boolean equals(Object obj) {
        if (obj instanceof Grupo) {
            return ((Grupo) obj).nrc.equals(nrc);
        } else {
            if (obj.toString().equals(nrc))
                return true;
            else
                return false;
        }
    }

    public Object clone() {
        Grupo g = new Grupo(padre);
        g.nrc = nrc;
        g.sec = sec;
        g.cup = cup;
        g.dis = dis;
        g.horario = (Horario) horario.clone();
        g.marca = marca;
        return g;
    }

    public Grupo(Maestro padre) {
        this.padre = padre;
    }
}
