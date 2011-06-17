// %2709500570:de.hattrickorganizer.tools.updater%
package de.hattrickorganizer.tools.updater;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import de.hattrickorganizer.gui.theme.ThemeManager;


/**
 * TableCellRenderer for the update plugins panel and user columns settings panel. 
 *
 * @author tdietz
 */
public final class UpdaterCellRenderer implements TableCellRenderer {

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

		double site = 0;
		double ho = 0;
		
		
		if (value instanceof JCheckBox) {
			return (JCheckBox) value;
		}
		
		try {
			site = Double.parseDouble(((JLabel) table.getModel().getValueAt(row, 3)).getText());
		} catch (Exception e) {
		}
		try {
			ho = Double.parseDouble(((JLabel) table.getModel().getValueAt(row, 2)).getText());
		} catch (Exception e1) {
		}

		boolean color = false;
		if ((ho > 0) && (ho < site)) {
			color = true;
		}

		if (value instanceof JButton) {
			JButton b = (JButton) value;
			b.setBorderPainted(false);
			if (color) {
				b.setForeground(ThemeManager.getColor("ho.label.error.foreground"));
			}
			int labelHeight = 20; // fix table height problems with non-classic l&f
			int tableRowHeight = table.getRowHeight(row);
			if (labelHeight != tableRowHeight) {
				table.setRowHeight(row, labelHeight);
			}
			return b;
		}

		JLabel label = null;
		if (value instanceof JLabel) {
			label = (JLabel) value;
		} else {
			label = new JLabel(value.toString());
		}

		if (color) {
			label.setForeground(ThemeManager.getColor("ho.label.error.foreground"));
		}
		return label;
	}

}
