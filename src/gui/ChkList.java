package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import carga.Marcable;

public class ChkList extends Container implements ListDataListener {
	static final long serialVersionUID=1;
	private DefaultListModel lm;
	public void paint(Graphics g) {
		g.setColor(getBackground());
		g.fillRect(0,0,getWidth(),getHeight());
		super.paint(g);
	}
	public ChkList(DefaultListModel l) {
		super();
		setBackground(Color.WHITE);
		lm=l;
		lm.addListDataListener(this);
		setLayout(new LayoutManager(){
			public void addLayoutComponent(String name, Component comp) {}
			public void layoutContainer(Container parent) {
				int y=0;
				for(int x=0;x<parent.getComponentCount();x++){
					Component c=parent.getComponent(x);
					Dimension d=c.getPreferredSize();
					c.setBounds(0,y,d.width,d.height);
					y+=d.height;
				}
			}
			public Dimension minimumLayoutSize(Container parent) {
				return getPreferredSize();
			}
			public Dimension preferredLayoutSize(Container parent) {
				int altura=0;
				int anchura=0;
				for(int x=0;x<parent.getComponentCount();x++){
					Dimension d=parent.getComponent(x).getPreferredSize();
					altura+=d.height;
					if (d.width>anchura)
						anchura=d.width;
				}
				return new Dimension(anchura,altura);
			}
			public void removeLayoutComponent(Component comp) {}
		});
	}
	
	public void contentsChanged(ListDataEvent e) {
	
	}

	public void intervalAdded(ListDataEvent e) {
		add(lm.get(lm.getSize()-1));
		Container c;
		if ((c=getParent())!=null)
			c.validate();
	}
	
	public void intervalRemoved(ListDataEvent e) {
		removeAll();
		Container c;
		if ((c=getParent())!=null)
			c.validate();
	}
	
	private void add(Object obj) {
		JCheckBox chk;
		if (obj instanceof Marcable)
			chk=new ChkListItem(obj);
		else
			chk=new JCheckBox(obj.toString());

		chk.setBackground(getBackground());
		super.add(chk);
	}
	
	public Vector getChecked(){
		Vector v=new Vector(10,10);
		for (int x=0;x<getComponentCount();x++){
			if (((JCheckBox)getComponent(x)).isSelected())
				v.add(getComponent(x));
		}
		return v;
	}
}
