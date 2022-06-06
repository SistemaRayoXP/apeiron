package carga;

import java.io.ByteArrayOutputStream;
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

public class LectorHttp extends Thread {
    private String url;
    private String html;
    private LectorHttpListener eventos;
    private boolean abortar;
    private static boolean disableProxyResolution = false;
    private static Object lock = new Object();

    public LectorHttp(String url) {
        super();
        eventos = null;
        this.url = url;
        abortar = false;
    }

    public synchronized String getHtml() throws IOException {
        if (html == null) {
            fillHtml();
        }
        return html;
    }

    public void setLectorHttplListener(LectorHttpListener e) {
        eventos = e;
    }

    public void cancelar() {
        abortar = true;
    }

    protected void inicializarProxy() {
        synchronized (lock) {
            if (!System.getProperty("os.name").startsWith("Windows")) {
				disableProxyResolution = true;
			}
            if (!disableProxyResolution) {
                try {
                    if (System.getProperty("http.proxyHost") == null) {
                        String proxyHost = null;
                        String proxyPort = null;
                        boolean proxyEnabled = false;

                        RegistryKey r = new RegistryKey(RootKey.HKEY_CURRENT_USER,
                                "Software\\Microsoft\\Windows\\CurrentVersion\\Internet Settings");

                        if (r.hasValue("ProxyEnable")) {
                            RegistryValue v = r.getValue("ProxyEnable");
                            proxyEnabled = !"0".equals(v.getStringValue());
                        }

                        if (proxyEnabled && r.hasValue("ProxyServer")) {
                            RegistryValue v = r.getValue("ProxyServer");
                            String proxyServer = v.getStringValue();
                            int pos = proxyServer.indexOf(":");
                            if (pos > -1) {
                                proxyHost = proxyServer.substring(0, pos);
                                proxyPort = proxyServer.substring(pos + 1, proxyServer.length());
                            }

                            System.setProperty("http.proxyHost", proxyHost);
                            System.setProperty("http.proxyPort", proxyPort);
                        }
                    }
                } catch (Throwable e) {
                    System.out.println("WARNING: Could not load JRegistryKey, proxy resolution disabled");
                    disableProxyResolution = true;
                }
            }
        }
    }

    protected synchronized void fillHtml() throws IOException {
        inicializarProxy();

        byte buffer[] = new byte[1024 * 9];
        ByteArrayOutputStream outputBytes = new ByteArrayOutputStream();

        URL u1 = new URL(url);
        URLConnection uc = u1.openConnection();
        InputStream is = uc.getInputStream();
        int contentLength = uc.getContentLength();
        int res;
        int pos = 0;

        do {
            res = is.read(buffer);

            if (res > -1) {
                outputBytes.write(buffer, 0, res);
                pos += res;
            }

            if (contentLength > -1) {
                int completado = (pos + 1) * 100 / contentLength;

                if (eventos != null)
                    eventos.progreso("Descargando desde " + url + " :", completado);
            }
        } while (res > -1 && !abortar);

        outputBytes.flush();
        outputBytes.close();

        is.close();

        html = new String(outputBytes.toByteArray());

        if (eventos != null && !abortar)
            eventos.terminado(html);
    }

    public void run() {
        try {
            fillHtml();
        } catch (SocketTimeoutException e) {
            if (eventos != null)
                eventos.error("Error: Sin respuesta del servidor");
        } catch (NoRouteToHostException e) {
            if (eventos != null)
                eventos.error("Error: No se encuentra el servidor");
        } catch (UnknownHostException e) {
            if (eventos != null)
                eventos.error("Error: No se encuentra el servidor");
        } catch (Exception e) {
            e.printStackTrace();
            if (eventos != null)
                eventos.error(e.toString());
        }

    }

}
