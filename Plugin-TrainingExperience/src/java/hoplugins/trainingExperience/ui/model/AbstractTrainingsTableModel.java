// %1126721451323:hoplugins.trainingExperience.ui.model%
package hoplugins.trainingExperience.ui.model;

import hoplugins.commons.utils.PluginProperty;

import plugins.IHOMiniModel;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;


/**
 * Bast training table model
 */
public abstract class AbstractTrainingsTableModel extends AbstractTableModel {
    //~ Instance fields ----------------------------------------------------------------------------

    /** HO Model */
    protected IHOMiniModel p_IHMM_miniModel;

    /** Vector of ITrainingPerPlayer object */
    protected Vector p_V_data;

    /** Vector of ITrainingPerWeek object */
    protected Vector p_V_trainingsVector;
    private String[] columnNames;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new AbstractTrainingsTableModel object.
     *
     * @param miniModel
     */
    public AbstractTrainingsTableModel(IHOMiniModel miniModel) {
        p_V_data = new Vector();
        p_V_trainingsVector = new Vector();
        p_IHMM_miniModel = miniModel;
        columnNames = new String[5];
        columnNames[0] = PluginProperty.getString("Week"); //$NON-NLS-1$
        columnNames[1] = miniModel.getResource().getProperty("Season"); //$NON-NLS-1$
        columnNames[2] = PluginProperty.getString("Type"); //$NON-NLS-1$
        columnNames[3] = miniModel.getResource().getProperty("Intensitaet"); //$NON-NLS-1$
        columnNames[4] = miniModel.getResource().getProperty("Kondition"); //$NON-NLS-1$
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Method that returns if a cell is editable or not
     *
     * @param row
     * @param column
     *
     * @return
     */
    public boolean isCellEditable(int row, int column) {
        return (column == 2 || column == 3 || column == 4);
    }

    /**
     * DOCUMENT ME!
     *
     * @param column
     *
     * @return
     */
    public Class getColumnClass(int column) {
        return getValueAt(0, column).getClass();
    }

    /**
     * Return number of columns
     *
     * @return
     */
    public int getColumnCount() {
        return columnNames.length;
    }

    /**
     * Return header for the specified column
     *
     * @param column
     *
     * @return column header
     */
    public String getColumnName(int column) {
        return columnNames[column];
    }

    /**
     * Returns row number
     *
     * @return
     */
    public int getRowCount() {
        return p_V_data.size();
    }

    /**
     * Method that returns the Training vector
     *
     * @return actual training vector
     */
    public Vector getTrainingsData() {
        return p_V_trainingsVector;
    }

    /**
     * Returns the cell value
     *
     * @param row
     * @param column
     *
     * @return Object representing the cell value
     */
    public Object getValueAt(int row, int column) {
        Object[] aobj = (Object[]) p_V_data.get(row);

        return aobj[column];
    }

    /**
     * Method to be called to populate the table with the data from HO API
     */
    public abstract void populate();
}