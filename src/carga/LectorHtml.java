package carga;




interface LectorHtmlListener{
	void progreso(String estado,int porcentaje);
	void error(String descripcion);
	void terminado(HtmlTag htmldoc);
}

public class LectorHtml extends LectorHttp implements LectorHttpListener {
	private HtmlParser parser;
	private LectorHtmlListener eventos;
	
	public void error(String descripcion) {
		if (eventos!=null)
			eventos.error(descripcion);
	}
	public void progreso(String estado, int porcentaje) {
		if (eventos!=null)
			eventos.progreso(estado,porcentaje);
	}
	public void terminado(String html) {
		parser.parse(html);
		if (eventos!=null)
			eventos.terminado(parser.parse(html));
	}
	
	
	public LectorHtml(String url) {
		super(url);
		super.setLectorHttplListener(this);
		eventos=null;
		parser=new HtmlParser();
	}
	
	public void setLectorHttpListener(LectorHttpListener e){
		
	}
	
	public void setLectorHtmlListener(LectorHtmlListener e){
		eventos=e;
	}

}

