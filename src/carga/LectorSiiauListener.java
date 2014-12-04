package carga;

import java.util.Vector;

public interface LectorSiiauListener{
	void progreso(String estado,int porcentaje);
	void error(String descripcion);
	void terminado(Vector reg,MateriaSiiau f);
	void advertencia(String descripcion);
}

