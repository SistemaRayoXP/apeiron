package carga;

import java.util.Vector;


class HtmlTag{
	String etiqueta;
	String parametros;
	String contenido;
	private Vector hijos;
	
	public HtmlTag buscar(String palabra, int padres){
		String path=buscar2(palabra,this,"");
		if (!path.equals("")){
			int ini=0;
			for(int x=0;x<padres;x++)
				ini=path.indexOf(';',ini)+1;
			
			path=path.substring(ini-1,path.length()-1);
			return seguirRuta(path,this);
		}
		
		return null;
		
	}
	public String buscarRuta(String palabra,int padres){
		String path=buscar2(palabra,this,"");
		if (!path.equals("")){
			int ini=0;
			for(int x=0;x<padres;x++)
				ini=path.indexOf(';',ini)+1;
			
			path=';'+path.substring(ini,path.length()-1);
			return path;
		}
		
		return null;
	}
	
	public HtmlTag seguirRuta(String path, HtmlTag t){
		int ini;
		HtmlTag tmp=t;
		
		while((ini=path.lastIndexOf(';'))>-1){
			tmp=tmp.getSubTag(Integer.parseInt(path.substring(ini+1)));
			path=path.substring(0,ini);
		}	
		return tmp;
	}
	 
	private String buscar2(String palabra, HtmlTag n, String path){
		if (n.contenido.equalsIgnoreCase(palabra))
			return path;
		else{
			for (int x=0;x<n.getSubCont();x++){
				String res=buscar2(palabra,(HtmlTag)n.getSubTag(x),Integer.toString(x)+';'+path);
				if (!res.equals(""))
					return res;
			}
		}
		return "";
	}
	
	public HtmlTag getSubTag(int indice){
		return (HtmlTag)hijos.get(indice);
	}
	public void insertar(HtmlTag h){
		hijos.add(h);
	}
	public int getSubCont(){
		return hijos.size();
	}
	public void remover(int indice){
		hijos.remove(indice);
	}
	public HtmlTag(){
		etiqueta="";
		contenido="";
		parametros="";
		hijos=new Vector(10,10);
	}
	public static void mostrar(HtmlTag n, StringBuffer sb,int nivel){
		sb.append("Nivel: "+Integer.toString(nivel)+"\n");
		sb.append("Etiqueta: "+n.etiqueta+"\n");
		sb.append("Contenido: "+n.contenido+"\n");
		for (int x=0;x<n.hijos.size();x++){
			mostrar(((HtmlTag)(n.hijos.get(x))),sb,nivel+1);
		}
	}
}