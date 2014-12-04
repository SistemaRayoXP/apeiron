package carga;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Horario{
	protected static SimpleDateFormat dateFormatter=new SimpleDateFormat("dd/MM/yy");
	protected static final int bit[]={1,2,4,8,16,32,64,128,256,512,1024,2048,4096,8192,16384};
	protected int total;
	protected int ultimosHuecos;
	protected String periodoString;
	protected Periodo periodo;
	
	
	String modulos[];
	private int horario[];
	String aulas[];
		
	public int[] getHorario()
   {
   	return horario;
   }
	
	public Periodo getPeriodo()
	{
		return periodo;
	}

	public int getHorario(int i){
		return horario[i];
	}
		
	public void setPeriodo(String periodo)
	{
		this.periodoString=periodo;
		
		try
		{
			String fechas[]=periodo.split("-");
			synchronized (dateFormatter)
			{
				Date inicio=dateFormatter.parse(fechas[0]);
				Date fin=dateFormatter.parse(fechas[1]);
				
				// Error
				if (!inicio.before(fin))
				{
					throw new Exception("Error: "+fechas[0]+" no es menor que"+ fechas[1]);
				}
				
				this.periodo=new Periodo(inicio,fin);
			}
		}
		catch (Exception e)
		{
			// Nada que hacer, quiza algun problema de SIIAU
			e.printStackTrace();
		}
	}
	
	public String getPeriodoString(){
		return periodoString;
	}
	
	public static Horario getHorarioLleno(){
		Horario tmp=new Horario();
		for (int x=0;x<bit.length;x++)
			tmp.horario[0]+=bit[x];
		
		tmp.horario[1]=tmp.horario[0];
		tmp.horario[2]=tmp.horario[0];
		tmp.horario[3]=tmp.horario[0];
		tmp.horario[4]=tmp.horario[0];
		tmp.horario[5]=tmp.horario[0];
		return tmp;
	}
	
	public int getHorarioTotal(){
		if (total==0)
			total=	horario[0]+horario[1]+horario[2]+
					horario[3]+horario[4]+horario[5];
		return total;	
	}
	
	private int getHuecos(int h){
		int tmp=0;
		int inicio=-1;
		int fin=0;
		
		//Calcular inicio
		for(int x=0;x<bit.length;x++)
			if ((h & bit[x])!=0){ 
				inicio=x;
				break; 
			}
		
		if (inicio>-1){
			//Calcular fin
			for (int x=bit.length-1;x>=0;x--)
				if ((h & bit[x])!=0){ 
					fin=x;
					break; 
				}
			//Calcular huecos
			for (int x=inicio;x<=fin;x++){
				if ((h & bit[x])==0)
					tmp++;
			}
		}
		else
			return 0;
		
		return tmp;
	}
	public int getHuecos(){
		if (total==0){
			
			total=	horario[0]+horario[1]+horario[2]+
					horario[3]+horario[4]+horario[5];
			
			ultimosHuecos=getHuecos(horario[0])+getHuecos(horario[1])+
				getHuecos(horario[2])+getHuecos(horario[3])+
				getHuecos(horario[4])+getHuecos(horario[5]);
		
		}
		return ultimosHuecos;
	}
	
	public void agregar(Horario h){
		total=0;
		for(int x=0;x<6;x++)
			horario[x]+=h.horario[x];
	}
	
	public void quitar(Horario h){
		total=0;
		for(int x=0;x<6;x++)
			horario[x]-=h.horario[x];
	}
		
	public Object clone(){
		Horario h=new Horario();
		h.periodoString=periodoString;
		h.total=total;
		h.ultimosHuecos=ultimosHuecos;
		h.periodo=periodo;
		
		for(int x=0;x<6;x++){
			h.aulas[x]=aulas[x];
			h.modulos[x]=modulos[x];
			h.horario[x]=horario[x];
		}
		return h;
	}
	public boolean getHora(int idx1,int idx2){
		idx2-=7;
		return (horario[idx1] & bit[idx2])!=0;
	}
	
	public void setHora(int idx1,int idx2,boolean marca) {
		total=0;
		
		idx2-=7;
			
		if (marca)
			horario[idx1]=horario[idx1] | bit[idx2];
		else
			if ((horario[idx1] & bit[idx2]) != 0)
				horario[idx1]=horario[idx1] ^ bit[idx2];
		
	}
	
	public boolean compatible(Horario h){
		for (int x=0;x<6;x++){
			if ((h.horario[x] & horario[x])!=0)
				return false;
		}
		return true;
	}
	
	public boolean subHorario(Horario h){
		for (int x=0;x<6;x++){
			if ((h.horario[x] & horario[x])!=horario[x])
				return false;
		}
		return true;
	}
	
	public Horario(){
		modulos=new String[6];
		horario=new int[6];
		aulas=new String[6];
		horario[0]=0;
		horario[1]=0;
		horario[2]=0;
		horario[3]=0;
		horario[4]=0;
		horario[5]=0;
		total=0;
	}
	
	public void limpiar()
	{
		horario[0]=0;
		horario[1]=0;
		horario[2]=0;
		horario[3]=0;
		horario[4]=0;
		horario[5]=0;
		total=0;
	}
	
	public Horario(int horas[]){
		modulos=new String[6];
		horario=new int[6];
		aulas=new String[6];
		horario[0]=horas[0];
		horario[1]=horas[1];
		horario[2]=horas[2];
		horario[3]=horas[3];
		horario[4]=horas[4];
		horario[5]=horas[5];
		total=0;
	}
	public String toString(){
		String tmp="";
		for (int x=0;x<6;x++){
			if (horario[x]!=0){
				switch (x){
					case 0: 
						tmp+="Lunes: "+int2hora(horario[x])+' ';
						break;
					case 1: 
						tmp+="Martes: "+int2hora(horario[x])+' ';
						break;
					case 2: 
						tmp+="Miércoles: "+int2hora(horario[x])+' ';
						break;
					case 3: 
						tmp+="Jueves: "+int2hora(horario[x])+' ';
						break;
					case 4: 
						tmp+="Viernes: "+int2hora(horario[x])+' ';
						break;
					case 5: 
						tmp+="Sábado: "+int2hora(horario[x])+' ';
						break;
					
				}
			}
		}
		return tmp;
	}
	private String int2hora(int h){
		int desde=0,hasta=0;
		int x;
		if (h==0)
			return "";
		
		for(x=0;x<bit.length;x++)
			if ((h & bit[x])!=0){
				desde=x;
				break;
			}
			
		while (x<bit.length && (h & bit[x])!=0){
			hasta=x++;
		}

		if (hasta==desde)
			return Integer.toString(desde+7)+":00";
		else
			return Integer.toString(desde+7)+":00-"+Integer.toString(hasta+7)+":55";
	}
	private int hora2int(String hora){
		
		int sep=hora.indexOf('-');
		/*
		 * El -7 es para que las horas queden relativas a 0
		 */
		
		int desde=Integer.parseInt(hora.substring(0,sep).substring(0,2))-7;
		int hasta=Integer.parseInt(hora.substring(sep+1).substring(0,2))-7;
		int tmp=0;
		
		for (int x=desde;x<=hasta;x++)
			tmp+=bit[x];
		
		
		return tmp;
	}
	
	public void setDatos(String dias,String modulo,String aula,String horas){
		total=0;
		
		int h=hora2int(horas);
		
		for(int x=0;x<dias.length();x++){
			switch (dias.charAt(x)){
			case 'L':
				modulos[0]=modulo;
				aulas[0]=aula;
				horario[0]+=h;
				break;
			case 'M':
				modulos[1]=modulo;
				aulas[1]=aula;
				horario[1]+=h;
				break;
			case 'I':
				modulos[2]=modulo;
				aulas[2]=aula;
				horario[2]+=h;
				break;
			case 'J':
				modulos[3]=modulo;
				aulas[3]=aula;
				horario[3]+=h;
				break;
			case 'V':
				modulos[4]=modulo;
				aulas[4]=aula;
				horario[4]+=h;
				break;
			case 'S':
				modulos[5]=modulo;
				aulas[5]=aula;
				horario[5]+=h;
				break;	
			}
		}
	}
}
