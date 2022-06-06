package gui;

import javax.swing.JCheckBox;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import carga.Marcable;

public class ChkListItem extends JCheckBox implements ChangeListener {
    static final long serialVersionUID = 1;
    public Object obj;

    public ChkListItem(Object obj) {
        super();
        this.obj = obj;
        addChangeListener(this);
        init();
    }

    public void init() {
        setText(obj.toString());
        if (obj instanceof Marcable)
            setSelected(((Marcable) obj).getMarca());
    }

    public void stateChanged(ChangeEvent e) {
        if (obj instanceof Marcable)
            ((Marcable) obj).setMarca(isSelected());
    }

}
