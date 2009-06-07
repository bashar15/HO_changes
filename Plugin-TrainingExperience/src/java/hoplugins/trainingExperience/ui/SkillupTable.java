// %827897234:hoplugins.trainingExperience.ui%
package hoplugins.trainingExperience.ui;

import hoplugins.commons.ui.BaseTableModel;

import plugins.ISkillup;

import java.awt.event.MouseEvent;

import javax.swing.JTable;
import javax.swing.table.TableModel;


/**
 * Table for players past and future skillups
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class SkillupTable extends JTable {
    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new SkillupTable object.
     *
     * @param tableModel The table model to be used
     */
    public SkillupTable(BaseTableModel tableModel) {
        super(tableModel);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Return string toolTip for the skillup
     *
     * @param e MouseEvent of being over the cell
     *
     * @return String toolTip for active skillup
     */
    public String getToolTipText(MouseEvent e) {
        TableModel tableModel = (TableModel) getModel();
        java.awt.Point p = e.getPoint();
        int rowIndex = rowAtPoint(p);
        int type = ISkillup.SKILLUP_FUTURE;

        try {
            type = Integer.parseInt((String) tableModel.getValueAt(rowIndex, 3));
        } catch (NumberFormatException ex) {
        }

        if (type == ISkillup.SKILLUP_REAL) {
            Object obj = tableModel.getValueAt(rowIndex, 4);

            return obj.toString();
        }

        return ""; //$NON-NLS-1$
    }
}