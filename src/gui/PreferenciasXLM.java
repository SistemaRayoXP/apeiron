package gui;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import carga.AdDatos;
import carga.Horario;
import carga.Maestro;
import carga.Materia;

public class PreferenciasXLM {
	private Node raiz=null;
	private AdDatos datos;
	
	public PreferenciasXLM(AdDatos datos){
		this.datos=datos;
	}
	
	public void leer(String archivo)throws Exception{
		Document doc=parseFile(archivo);
		raiz = doc.getDocumentElement();
		if (raiz.getNodeName().equals("apeiron")){		
			for (Node hijo=raiz.getFirstChild();hijo!=null;hijo=hijo.getNextSibling()){
				if (hijo.getNodeName().equals("combinaciones")){
					combinaciones(hijo);
				}else if (hijo.getNodeName().equals("materias")){
					materias(hijo);
				}else if (hijo.getNodeName().equals("horario")){
					datos.horarioUsuario=new Horario(getHorario(hijo));
				}
			}
		}else
			throw new Exception("Formato Incorrecto");
	}
	
		
	public void escribir(String archivo){
		try {
			OutputStreamWriter osw=new OutputStreamWriter(new FileOutputStream(archivo),"UTF-8");
			osw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
			osw.write("<apeiron>\n");
				osw.write("<horario>\n");
					osw.write("<lunes>"+datos.horarioUsuario.getHorario(0)+"</lunes>\n");
					osw.write("<martes>"+datos.horarioUsuario.getHorario(1)+"</martes>\n");
					osw.write("<miercoles>"+datos.horarioUsuario.getHorario(2)+"</miercoles>\n");
					osw.write("<jueves>"+datos.horarioUsuario.getHorario(3)+"</jueves>\n");
					osw.write("<viernes>"+datos.horarioUsuario.getHorario(4)+"</viernes>\n");
					osw.write("<sabado>"+datos.horarioUsuario.getHorario(5)+"</sabado>\n");
			    osw.write("</horario>\n");
			
			    osw.write("<combinaciones>\n");
			    	osw.write("<horariosMaximos>"+datos.maxHorarios+"</horariosMaximos>\n");
			    	osw.write("<huecosPermisibles>"+datos.maxHuecos+"</huecosPermisibles>\n");
			    	osw.write("<huecosIntermedios>"+datos.maxHuecosInt+"</huecosIntermedios>\n");
			    	osw.write("<soloConCupo>"+(datos.conCupo?"si":"no")+"</soloConCupo>\n");
			    	osw.write("<horaDia>"+datos.prHora+"</horaDia>\n");
			    	osw.write("<demanda>"+datos.prDemanda+"</demanda>\n");
			    osw.write("</combinaciones>\n");
			    
			    osw.write("<materias>\n");
			    for (int x=0;x<datos.materias.size();x++){
			    	
			    	Materia mat=(Materia)datos.materias.get(x);
			    	osw.write("<materia nombre=\""+mat.getNombre()+"\" clave=\""+mat.getMateriaSiiau().getClave()+"\">\n");
			    	for (int x2=0;x2<mat.maestros.size();x2++){
			    		Maestro maest=(Maestro)mat.maestros.get(x2);
			    		osw.write("<maestro incluido=\""+(maest.getMarca()?"si":"no")+"\">"+maest.getNombre()+"</maestro>\n");
			    	}
			    	
			    	osw.write("</materia>\n");
			    }
			    osw.write("</materias>\n");
			    
			osw.write("</apeiron>");
			osw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private final static String getElementValue( Node elem ) {
		Node kid;
		if( elem != null){
			if (elem.hasChildNodes()){
				for( kid = elem.getFirstChild(); kid != null; kid = kid.getNextSibling() ){
					if( kid.getNodeType() == Node.TEXT_NODE  ){
						return kid.getNodeValue();
		            }
		        }
			}
		}
		return "";
	}
	
	private Materia buscarMateria(String clave){
		for (int x=0;x<datos.materias.size();x++){
			Materia mat=(Materia)datos.materias.get(x);
			if (mat.getMateriaSiiau().getClave().equals(clave))
				return mat;
		}
		return null;
	}
	
	private Maestro buscarMaestro(Materia mat,String nombre){
		for (int x=0;x<mat.maestros.size();x++){
			Maestro maest=(Maestro)mat.maestros.get(x);
			if (maest.getNombre().equals(nombre))
				return maest;
		}
		return null;
	}
	
	private void materias(Node materias){
		for (Node mat=materias.getFirstChild();mat!=null;mat=mat.getNextSibling()){
			if (mat.getNodeName().equals("materia")){
				String clave=mat.getAttributes().getNamedItem("clave").getNodeValue();
				Materia materia=buscarMateria(clave);
				if (materia!=null){
					for (Node maest=mat.getFirstChild();maest!=null;maest=maest.getNextSibling()){
						if (maest.getNodeName().equals("maestro")){
							boolean incluido=maest.getAttributes().getNamedItem("incluido").getNodeValue().equals("si");
							String nombre=getElementValue(maest);
							Maestro mtro=buscarMaestro(materia,nombre);
							mtro.setMarca(incluido);
						}
					}
				}
			}
		}
	}
	
	private void combinaciones(Node hijo){
		for (Node hijo2=hijo.getFirstChild();hijo2!=null;hijo2=hijo2.getNextSibling()){
			if (hijo2.getNodeName().equals("horariosMaximos")){
				datos.maxHorarios=Integer.parseInt(getElementValue(hijo2));
			}else if (hijo2.getNodeName().equals("huecosPermisibles")){
				datos.maxHuecos=Integer.parseInt(getElementValue(hijo2));
			}else if (hijo2.getNodeName().equals("huecosIntermedios")){
				datos.maxHuecosInt=Integer.parseInt(getElementValue(hijo2));
			}else if (hijo2.getNodeName().equals("soloConCupo")){
				datos.conCupo=getElementValue(hijo2).equals("si");
			}else if (hijo2.getNodeName().equals("horaDia")){
				datos.prHora=Integer.parseInt(getElementValue(hijo2));
			}else if (hijo2.getNodeName().equals("demanda")){
				datos.prDemanda=Integer.parseInt(getElementValue(hijo2));
			}
		}
	}
	
	private int[] getHorario(Node hijo){
		int tmp[]=new int[6];
		for (Node dia=hijo.getFirstChild();dia!=null;dia=dia.getNextSibling()){
			if (dia.getNodeName().equals("lunes")){
				tmp[0]=Integer.parseInt(getElementValue(dia));
			}else if (dia.getNodeName().equals("martes")){
				tmp[1]=Integer.parseInt(getElementValue(dia));
			}else if (dia.getNodeName().equals("miercoles")){
				tmp[2]=Integer.parseInt(getElementValue(dia));
			}else if (dia.getNodeName().equals("jueves")){
				tmp[3]=Integer.parseInt(getElementValue(dia));
			}else if (dia.getNodeName().equals("viernes")){
				tmp[4]=Integer.parseInt(getElementValue(dia));
			}else if (dia.getNodeName().equals("sabado")){
				tmp[5]=Integer.parseInt(getElementValue(dia));
			}
		}
		
		return tmp;
	}
	
			
	private Document parseFile(String fileName) {
		//System.out.println("Parsing XML file... " + fileName);
		DocumentBuilder docBuilder;
		Document doc = null;
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		docBuilderFactory.setIgnoringElementContentWhitespace(true);
		try {
			docBuilder = docBuilderFactory.newDocumentBuilder();
		}catch (ParserConfigurationException e) {
		    System.out.println("Wrong parser configuration: " + e.getMessage());
		    return null;
		}
		
		File sourceFile = new File(fileName);
		try {
			doc = docBuilder.parse(sourceFile);
		}catch (SAXException e) {
			System.out.println("Wrong XML file structure: " + e.getMessage());
		    return null;
		}catch (IOException e) {
			System.out.println("Could not read source file: " + e.getMessage());
		}
		
		//System.out.println("XML file parsed");
		return doc;
	}
	
	
}
