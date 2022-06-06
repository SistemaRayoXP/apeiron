package carga;

import gui.Descarga;
import gui.Descargas;

import java.util.Vector;

public class AdDatos implements LectorSiiauListener {
    public boolean conCupo;
    public int maxHorarios;
    public int maxHuecos;
    public int maxHuecosInt;
    public int prHora;
    public int prDemanda;
    public boolean evaluarPeriodos;

    public Vector materias;
    public Horario horarioUsuario;
    private Descargas descargas;

    private Vector eventos;
    private Vector lectoresSiiau;

    public Descargas getDescargas() {
        return descargas;
    }

    public void advertencia(String descripcion) {
    }

    public void error(String descripcion) {
    }

    public void progreso(String estado, int porcentaje) {
    }

    public void terminado(Vector reg, MateriaSiiau f) {
        synchronized (this) {
            for (int x = 0; x < reg.size(); x++) {
                inserta((Registro) reg.get(x), f);
            }

            for (int x = 0; x < eventos.size(); x++)
                ((AdDatosListener) eventos.get(x)).nuevosDatos(this);
        }
    }

    public void cancelar() {
        for (int x = 0; x < lectoresSiiau.size(); x++) {
            ((LectorSiiau) (lectoresSiiau.get(x))).cancelar();
        }
    }

    public void addAdDatosListener(AdDatosListener e) {
        eventos.add(e);
    }
    /*
     * Metodos para acceder a los registros
     */

    /*
     * Constructor
     */
    public AdDatos() {
        descargas = new Descargas();
        lectoresSiiau = new Vector(10, 10);
        eventos = new Vector(10, 10);
        materias = new Vector(10, 10);
        horarioUsuario = Horario.getHorarioLleno();
        conCupo = true;
        maxHorarios = 50000;
        maxHuecos = 2;
        maxHuecosInt = -1;
        prDemanda = -1;
        prHora = 2;
        evaluarPeriodos = false;
    }

    /*
     * Si la materia se encuentra regresa un valor positivo
     * indicando el indice del vector, de lo contrario regresa un
     * valor negativo indicando donde se encuentra el siguiente lugar de
     * donde deberia estar
     */
    public int buscarMateria(Object obj) {
        for (int x = 0; x < materias.size(); x++) {
            int res = materias.get(x).toString().compareTo(obj.toString());
            if (res >= 0) {
                if (res == 0)
                    return x;
                else
                    return -(x + 1);
            }
        }
        return -(materias.size() + 1);
    }

    /*
     * Inserta un registro, si ya existe lo sobreescribe
     */
    public synchronized void inserta(Registro reg, MateriaSiiau f) {

        int tmp;
        Maestro tmpMaestro;
        Grupo tmpGrupo;
        Materia tmpMateria;

        tmp = buscarMateria(reg.materia);
        if (tmp < 0) { // No existe la materia
            // Ajustar el indice
            tmp *= -1;
            tmp--;

            tmpMateria = new Materia();
            tmpMateria.fuente = f;
            tmpMateria.creditos = reg.creditos;
            tmpMateria.nombre = reg.materia;
            materias.add(tmp, tmpMateria);
        } else // La materia ya existe
            tmpMateria = ((Materia) materias.get(tmp));

        tmp = tmpMateria.buscarMaestro(reg.profesor);
        if (tmp < 0) {
            tmp *= -1;
            tmp--;
            tmpMaestro = new Maestro(tmpMateria);
            tmpMaestro.nombre = reg.profesor;
            tmpMateria.maestros.add(tmp, tmpMaestro);
        } else
            tmpMaestro = ((Maestro) tmpMateria.maestros.get(tmp));

        tmpGrupo = tmpMaestro.buscarGrupo(reg.nrc);
        if (tmpGrupo == null) {
            tmpGrupo = new Grupo(tmpMaestro);
            tmpMaestro.grupos.add(tmpGrupo);
        }
        tmpGrupo.cup = Integer.parseInt(reg.cupo);
        tmpGrupo.dis = Integer.parseInt(reg.disponible);
        tmpGrupo.nrc = reg.nrc;
        tmpGrupo.sec = reg.seccion;
        tmpGrupo.horario = reg.horario;
    }

    /*
     * Obtiene el html de SIIAU a determinado ciclo, cientro y clave
     */
    public void cargar(MateriaSiiau ms) {

        LectorSiiau ls = new LectorSiiau(ms);
        Descarga d = new Descarga(ls);
        ls.addLectorSiiauListener(d);
        ls.addLectorSiiauListener(this);
        descargas.agregar(d);

        lectoresSiiau.add(ls);
        ls.start();
        descargas.setVisible(true);
        try {
            descargas.setSelected(true);
        } catch (Exception e) {
        }
    }
}
