package combinacion;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Vector;

import carga.AdDatos;
import carga.Grupo;

public class PrimeroElMejor extends ModeloCombinatorio {

    private static Comparator<Grupo> comparadorDeGrupos = new Comparator<Grupo>() {
        public int compare(Grupo grupo1, Grupo grupo2) {
            if (grupo1.huecos > grupo2.huecos) {
                return 1;
            } else if (grupo1.huecos == grupo2.huecos) {
                return 0;
            } else {
                return -1;
            }
        }
    };

    public PrimeroElMejor(AdDatos datos) {
        super(datos);
    }

    private void ponerMateria(Solucion s, int indiceMat) {

        if (indiceMat >= materias.length) {
            if (materias.length > 0) {
                progreso++;
                solCount++;

                if (total > 0)
                    fireProgreso("[" + Integer.toString(solCount) + "] " + Long.toString(progreso) + "/"
                            + Long.toString(total), Math.round(progreso * 100 / total));
                else
                    fireProgreso("Generando horarios [" + Integer.toString(solCount) + "]", -2);

                int huecos = s.getHuecos();
                assert huecos <= maxHuecos;

                Solucion sc = (Solucion) s.clone();

                if (hashHuecos[huecos] == null) {
                    hashHuecos[huecos] = new Vector();
                }
                hashHuecos[huecos].add(sc);

                fireNuevaSolucion(sc);
            }
        } else {

            Grupo grupos[] = materias[indiceMat];

            // Calcular huecos
            for (int x = 0; x < grupos.length; x++) {
                if (s.compatible(grupos[x].horario)) {
                    grupos[x].huecos = s.getHuecos(grupos[x]);
                } else {
                    grupos[x].huecos = 1000; // infinito
                }
            }

            Arrays.sort(grupos, comparadorDeGrupos);

            // Empieza la combinacion
            for (int x = 0; x < grupos.length; x++) {

                if (abortar)
                    break;

                boolean salir = false;

                if (grupos[x].huecos >= 1000) // Empiezan los no compatibles
                    salir = true;

                if (indiceMat < materias.length - 1) { // no es la ultima materia
                    if (maxHuecosInt > -1 && grupos[x].huecos > maxHuecosInt) // Condicion de no aceptacion
                        salir = true;

                } else {
                    if (maxHuecos > -1 && grupos[x].huecos > maxHuecos) // Condicion de no aceptacion
                        salir = true;
                }

                if (maxHorarios > -1 && solCount >= maxHorarios) // Se supero el limite
                    salir = true;

                if (salir) {
                    if (maxHorarios == -1) {
                        int tmp = grupos.length - x;
                        for (int m = indiceMat + 1; m < materias.length; m++)
                            tmp *= materias[m].length;
                        progreso += tmp;

                        if (total > 0)
                            fireProgreso("[" + Integer.toString(solCount) + "] " + Long.toString(progreso) + "/"
                                    + Long.toString(total), Math.round(progreso * 100 / total));
                        else
                            fireProgreso("Generando horarios [" + Integer.toString(solCount) + "]", -2);
                    }

                    break;
                }

                s.agregar(grupos[x]);
                ponerMateria(s, indiceMat + 1);
                s.quitar(grupos[x]);

            }
        }
    }

    protected void combinar() {
        int comb = 1;
        for (int z = 0; z < materias.length; z++) {
            comb *= materias[z].length;
            if (comb < 0)// Se desbordo
                break;
        }
        if (maxHorarios > -1 && (maxHorarios < comb || comb < 0))
            total = maxHorarios;
        else
            total = comb;
        progreso = 0;

        if (datos.evaluarPeriodos) {
            ponerMateria(new SolucionConPeriodo(), 0);
        } else {
            ponerMateria(new Solucion(), 0);
        }

        if (abortar)
            fireProgreso("Combinación cancelada", -1);
        else
            fireProgreso("Combinación terminada", 100);

    }

}
