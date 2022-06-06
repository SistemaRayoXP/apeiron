package carga;

public class SubHora implements Marcable {
    private Horario h;
    private int idx1;
    private int idx2;
    private String label;

    public boolean getMarca() {
        return h.getHora(idx1, idx2);
    }

    public void setMarca(boolean marca) {
        h.setHora(idx1, idx2, marca);
    }

    public SubHora(Horario h, int idx1, int idx2, String label) {
        this.h = h;
        this.idx1 = idx1;
        this.idx2 = idx2;
        this.label = label;
    }

    public String toString() {
        return label;
    }
}
