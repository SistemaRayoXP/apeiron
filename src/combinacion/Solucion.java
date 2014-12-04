package combinacion;

import java.util.Vector;

import carga.Grupo;
import carga.Horario;

public class Solucion
{
	protected Vector<Grupo> grupos;
	protected int huecos;
	protected Horario horarioGlobal;

	public Solucion()
	{
		horarioGlobal = new Horario();
		grupos = new Vector<Grupo>();
	}
	
	public int getHuecos()
	{
		return horarioGlobal.getHuecos();
	}

	public int getHuecos(Grupo g)
	{
		horarioGlobal.agregar(g.horario);
		int huecos = horarioGlobal.getHuecos();
		horarioGlobal.quitar(g.horario);
		return huecos;
	}

	public boolean compatible(Horario h)
	{
		return horarioGlobal.compatible(h);
	}

	public void agregar(Grupo g)
	{
		horarioGlobal.agregar(g.horario);
		grupos.add(g);
	}

	public void quitar(Grupo g)
	{
		horarioGlobal.quitar(g.horario);
		grupos.remove(g);
	}

	@SuppressWarnings("unchecked")
   public Object clone()
	{
		Solucion s = crearSolucion();
		s.grupos = (Vector<Grupo>) grupos.clone();
		s.huecos = huecos;
		s.horarioGlobal = (Horario) horarioGlobal.clone();
		return s;
	}

	protected Solucion crearSolucion()
	{
		return new Solucion();
	}
	
	public String getDescripcionGrupo(int idx1,int idx2)
	{
		if (horarioGlobal.getHora(idx1, idx2))
		{
			for(int x=0;x<grupos.size();x++)
			{
				Grupo g=(Grupo)grupos.get(x);
				if (g.horario.getHora(idx1,idx2))
				{
					return g.getPadre().getPadre().getMateriaSiiau().getClave();
				}
			}
		}
		return null;
	}
	
	public int getNumGrupos()
	{
		return grupos.size();
	}
	
	public Grupo getGrupo(int i)
	{
		return grupos.get(i);
	}
}
