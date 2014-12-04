package carga;

import java.util.Date;

public class Periodo
{
	Date inicio;
	Date fin;
	
	public Periodo(Date inicio, Date fin)
   {
	   this.inicio=inicio;
	   this.fin=fin;
   }

	public Date getInicio()
   {
   	return inicio;
   }

	public Date getFin()
   {
   	return fin;
   }
	
	public boolean compatible(Periodo p)
	{
		if (getInicio()!=null && getFin()!=null && p.getInicio()!=null && p.getFin()!=null)
		{
			if (getInicio().after(p.getFin()) || p.getInicio().after(getFin()))
			{
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean equals(Object obj)
	{
	   if (obj instanceof Periodo)
	   {
	   	return ((Periodo)obj).inicio.equals(inicio) && ((Periodo)obj).fin.equals(fin); 
	   }
	   return false;
	}
	
	public boolean estaDentroDe(Periodo p)
	{
		return estaDentroDe(p.getInicio(),p.getFin());
	}
	
	public boolean estaDentroDe(Date i, Date f)
	{
		if (inicio!=null && fin!=null && inicio!=null && fin!=null)
		{
			 if (((inicio.equals(i) || inicio.before(i)) && fin.after(i)) ||
			     ((inicio.equals(i) || inicio.after(i)) && inicio.before(f)))
			 {
				 return true;
			 }
			 
			 return false;
		}
		return true;
	}
}
