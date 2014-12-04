package carga;

import java.io.IOException;
import java.io.InputStream;
import java.net.NoRouteToHostException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

import ca.beq.util.win32.registry.RegistryKey;
import ca.beq.util.win32.registry.RegistryValue;
import ca.beq.util.win32.registry.RootKey;

public class LectorHttp extends Thread
{
	private String url;
	private String html;
	private LectorHttpListener eventos;
	private boolean abortar;

	public LectorHttp(String url)
	{
		super();
		eventos = null;
		this.url = url;
		abortar = false;
	}

	public synchronized String getHtml() throws IOException
	{
		if (html == null)
		{
			fillHtml();
		}
		return html;
	}

	public void setLectorHttplListener(LectorHttpListener e)
	{
		eventos = e;
	}

	public void cancelar()
	{
		abortar = true;
	}

	protected void inicializarProxy()
	{
		if (System.getProperty("http.proxyHost")==null)
		{
			String proxyHost=null;
			String proxyPort=null;
			boolean proxyEnabled=false;		
			
			RegistryKey r = new RegistryKey(RootKey.HKEY_CURRENT_USER, "Software\\Microsoft\\Windows\\CurrentVersion\\Internet Settings");
			
			if(r.hasValue("ProxyEnable")) 
			{
				RegistryValue v = r.getValue("ProxyEnable");
				proxyEnabled=!"0".equals(v.getStringValue());
			}
			
			if(proxyEnabled && r.hasValue("ProxyServer")) 
			{
				RegistryValue v = r.getValue("ProxyServer");
				String proxyServer=v.getStringValue();
				int pos=proxyServer.indexOf(":");
				if (pos>-1)
				{
					proxyHost=proxyServer.substring(0,pos);
					proxyPort=proxyServer.substring(pos+1,proxyServer.length());
				}

				System.setProperty("http.proxyHost",proxyHost);
				System.setProperty("http.proxyPort",proxyPort);
			}
		}
	}
	
	protected synchronized void fillHtml() throws IOException
	{
		try
		{
			inicializarProxy();
		}
		catch (Throwable e)
		{
			e.printStackTrace();
		}
		
		URL u1 = new URL(url);
		URLConnection uc = u1.openConnection();

		InputStream is = uc.getInputStream();
		byte buffer[];

		if (uc.getContentLength() > -1)
			buffer = new byte[uc.getContentLength()];
		else
			buffer = new byte[1024 * 500];

		int res;
		int pos = 0;
		int completado;

		do
		{
			res = is.read(buffer, pos, buffer.length - pos);
			if (res > -1)
				pos += res;
			completado = (pos + 1) * 100 / buffer.length;

			if (eventos != null)
				eventos.progreso("Descargando desde " + url + " :", completado);
		}
		while (res > -1 && !abortar);

		html = new String(buffer, 0, pos);

		if (eventos != null && !abortar)
			eventos.terminado(html);
	}

	public void run()
	{
		try
		{
			fillHtml();
		}
		catch (SocketTimeoutException e)
		{
			if (eventos != null)
				eventos.error("Error: Sin respuesta del servidor");
		}
		catch (NoRouteToHostException e)
		{
			if (eventos != null)
				eventos.error("Error: No se encuentra el servidor");
		}
		catch (UnknownHostException e)
		{
			if (eventos != null)
				eventos.error("Error: No se encuentra el servidor");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if (eventos != null)
				eventos.error(e.toString());
		}

	}

}
