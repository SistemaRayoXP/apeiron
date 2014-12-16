package util;

import static util.Constants.SIIAU_SERVER;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ca.beq.util.win32.registry.RegistryKey;
import carga.LectorHttp;

public class GeneradorCatalogo
{
	private static class RegistroCatalogo implements Comparable<RegistroCatalogo>
	{
		String departamento;
		String clave;
		String nombre;
		
		public RegistroCatalogo(String line)
		{
			String parts[]=line.split(",");
			
			departamento=parts[0];
			clave=parts[2].replaceAll("\"","");
			nombre=parts[3].replaceAll("\"","").replaceAll("~","Ñ");
		}

		public int compareTo(RegistroCatalogo o)
		{
			return nombre.compareTo(o.nombre);
		}
		
		@Override
		public String toString()
		{
			return clave+"  "+nombre;
		}
	}
	
	private static void generarCatalogoCentroAsync(final File archivo, final char letraCentro)
	{
		new Thread()
		{
			@Override
			public void run()
			{
				try
				{
					generarCatalogoCentro(archivo, letraCentro);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	private static void generarCatalogoCentro(File archivo, char letraCentro) throws Exception
	{
		System.out.println("Generando "+archivo.getName()+"...");
		LectorHttp lector = new LectorHttp(SIIAU_SERVER+"/wco/scpcata.cataxcu?planp=Y&cup="+letraCentro+"&ordenp=1&mostrarp=5&tipop=D");
		String html = lector.getHtml();
		
		BufferedReader br=new BufferedReader(new StringReader(html));
		
		String header=br.readLine();
		
		if (!"DEPARTAMENTO,AREA,CLAVE,MATERIA,CRED,TEORIA,PRACTICA,TIPO,NIVEL,PRERREQUISITOS,CORREQUISITOS,CARRERAS".equals(header))
		{
			throw new IOException("Header not valid");
		}
		
		String line;
		
		List<RegistroCatalogo> listado=new ArrayList<RegistroCatalogo>();
		
		while((line=br.readLine())!=null)
		{
			RegistroCatalogo registro=new RegistroCatalogo(line);
			
			if (registro.departamento.length()==0 || registro.nombre.length()==0 || registro.clave.length()==0)
			{
				continue;
			}
			
			listado.add(registro);
		}
		
		Collections.sort(listado);
		
		PrintStream printStream=new PrintStream(archivo,"UTF-8");
		
		for (RegistroCatalogo registro:listado)
		{
			printStream.println(registro);
		}
		
		printStream.close();
		
		System.out.println("Terminado "+archivo.getName()+"!");
	}
	
	public static void main(String[] args) throws Exception
	{
		generarCatalogoCentroAsync(new File("src/gui/CUAAD.dat"),'A');
		generarCatalogoCentroAsync(new File("src/gui/CUCBA.dat"),'B');
		generarCatalogoCentroAsync(new File("src/gui/CUCEA.dat"),'C');
		generarCatalogoCentroAsync(new File("src/gui/CUCEI.dat"),'D');
		generarCatalogoCentroAsync(new File("src/gui/CUCS.dat"),'E');
		generarCatalogoCentroAsync(new File("src/gui/CUCSH.dat"),'F');
		generarCatalogoCentroAsync(new File("src/gui/CUALTOS.dat"),'G');
		generarCatalogoCentroAsync(new File("src/gui/CUCIENEGA.dat"),'H');
		generarCatalogoCentroAsync(new File("src/gui/CUCOSTA.dat"),'I');
		generarCatalogoCentroAsync(new File("src/gui/CUCSUR.dat"),'J');
		generarCatalogoCentroAsync(new File("src/gui/CUSUR.dat"),'K');
		generarCatalogoCentroAsync(new File("src/gui/CUVALLES.dat"),'M');
		generarCatalogoCentroAsync(new File("src/gui/CUNORTE.dat"),'N');
		generarCatalogoCentroAsync(new File("src/gui/CULAGOS.dat"),'U');
		generarCatalogoCentroAsync(new File("src/gui/UDG VIRTUAL.dat"),'X');
	}
}
