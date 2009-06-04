// %511085336:hoplugins.teamAnalyzer.ui.model%
package hoplugins.teamAnalyzer.ui.model;

import hoplugins.commons.ui.BaseTableModel;

import java.util.Vector;

import javax.swing.ImageIcon;


/**
 * Custom FilterTable model
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class UiFilterTableModel extends BaseTableModel {
    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new instance of UiFilterTableModel
     */
    public UiFilterTableModel() {
        super();
    }

    /**
     * Creates a new UiFilterTableModel object.
     *
     * @param data Vector of table data
     * @param columnNames Vector of column names
     */
    public UiFilterTableModel(Vector data, Vector columnNames) {
        super(data, columnNames);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Returns true if the cell is editable
     *
     * @param row
     * @param column
     *
     * @return
     */
    public boolean isCellEditable(int row, int column) {
        if (column != 0) {
            return false;
        }

        String available = (String) getValueAt(row, 6);

        if (available.equalsIgnoreCase("true")) {
            return true;
        }

        return false;
    }

    /**
     * Returns the column class type
     *
     * @param column
     *
     * @return
     */
    public Class getColumnClass(int column) {
        if (column == 2) {
            return ImageIcon.class;
        }

        return super.getColumnClass(column);
    }
}
