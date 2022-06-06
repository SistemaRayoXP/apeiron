package gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static util.Constants.*;

import javax.swing.JComboBox;

import carga.LectorHttp;

public class CalenCombo extends JComboBox {
    private static final long serialVersionUID = -4485583504827955808L;

    private static Cal calen[];
    private static int selected = -1;

    public CalenCombo() {
        super(obtenerCalendarios());
        if (selected > -1) {
            setSelectedIndex(selected);
        }
    }

    private static Cal[] obtenerCalendarios() {
        if (calen == null) {
            try {
                calen = buscarCalendarios();
                return calen;
            } catch (Exception e) {
                e.printStackTrace();

                Cal tmp[] = { new Cal("200520", "2005-B"),
                        new Cal("200610", "2006-A"),
                        new Cal("200620", "2006-B"),
                        new Cal("200710", "2007-A"),
                        new Cal("200720", "2007-B"),
                        new Cal("200810", "2008-A"),
                        new Cal("200820", "2008-B") };

                calen = tmp;
                selected = 6;

                return calen;
            }
        }

        return calen;
    }

    private static Cal[] buscarCalendarios() throws IOException {
        ArrayList<Cal> calendarios = new ArrayList<Cal>();
        LectorHttp lector = new LectorHttp(SIIAU_SERVER + "/wco/sspseca.forma_consulta");
        String html = lector.getHtml();
        Pattern selectPattern = Pattern.compile("(?s)(?i)<select(.*?)>(.*?)</select>");
        Pattern optionPattern = Pattern.compile("(?i)<option(.*?)value='(.*?)'>(.*)");

        Matcher matcher = selectPattern.matcher(html);
        if (matcher.find()) {
            String comboContent = matcher.group();
            Matcher optionMatcher = optionPattern.matcher(comboContent);
            int index = 0;
            while (optionMatcher.find()) {
                if (optionMatcher.group().indexOf("SELECTED") > -1) {
                    selected = index;
                }

                calendarios.add(new Cal(optionMatcher.group(2), optionMatcher.group(3)));
                index++;
            }
        }
        return calendarios.toArray(new Cal[calendarios.size()]);
    }

    public static void main(String[] args) throws Exception {
        Cal res[] = buscarCalendarios();

        System.out.println(Arrays.toString(res));
    }
}
