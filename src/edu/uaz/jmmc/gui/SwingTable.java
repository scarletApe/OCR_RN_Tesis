
//<editor-fold defaultstate="collapsed" desc="licence">
/*
 * Copyright (C) 2016 juanmartinez
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
//</editor-fold>


package edu.uaz.jmmc.gui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author juanmartinez
 */
public class SwingTable extends JPanel {

	private static final long serialVersionUID = 1;
	public JTable tabla;

    public SwingTable(String[][] datos, String[] cloumnas) {
        DefaultTableModel dtm = new DefaultTableModel(datos, cloumnas);
        tabla = new JTable(dtm);
        JScrollPane scrollPane = new JScrollPane(tabla);

        this.add(scrollPane);
    }

    public JTable getTabla() {
        return tabla;
    }

    public void setTabla(JTable tabla) {
        this.tabla = tabla;
    }

}
