package ho.module.ifa.table;

import ho.module.ifa.model.Country;

import java.awt.Component;
import java.awt.Font;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class IfaTableCellRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = -114748630131222088L;
	private final DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,
			DateFormat.SHORT);
	private final NumberFormat doubleFormat;

	public IfaTableCellRenderer() {
		this.doubleFormat = NumberFormat.getInstance();
		this.doubleFormat.setMaximumFractionDigits(2);
		this.doubleFormat.setMinimumFractionDigits(2);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
			boolean hasFocus, int row, int column) {

		String displayValue = "";
		ImageIcon icon = null;
		int modelColumnIndex = table.convertColumnIndexToModel(column);
		int alignment = SwingConstants.RIGHT;

		switch (modelColumnIndex) {
		case IfaTableModel.COL_COUNTRY:
			if (!isSummaryRow(row, table)) {
				Country country = (Country) value;
				displayValue = country.getName();
				icon = country.getCountryFlag();
				alignment = SwingConstants.LEFT;
			} else {
				displayValue = String.valueOf(value);
			}
			break;
		case IfaTableModel.COL_LASTMATCH:
			displayValue = this.dateFormat.format((Date) value);
			alignment = SwingConstants.LEFT;
			break;
		case IfaTableModel.COL_COOLNESS:
			displayValue = this.doubleFormat.format((Double) value);
			break;
		default:			
			displayValue = String.valueOf(value);
		}

		JLabel label = (JLabel) super.getTableCellRendererComponent(table, displayValue,
				isSelected, hasFocus, row, column);
		label.setIcon(icon);
		label.setHorizontalAlignment(alignment);
		
		Font f = label.getFont();
		if (!isSummaryRow(row, table)) {
			label.setFont(label.getFont().deriveFont(Font.PLAIN));
		} else {
			label.setFont(label.getFont().deriveFont(Font.BOLD));
		}

		return label;
	}
	
	private boolean isSummaryRow(int rowIndex, JTable table) {
		return (rowIndex == table.getRowCount() - 1);
	}
}
