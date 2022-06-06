package carga;

public interface LectorHttpListener {
    void progreso(String estado, int porcentaje);

    void error(String descripcion);

    void terminado(String html);
}