// %3796493357:hoplugins.teamplanner.ui.model%
package hoplugins.teamplanner.ui.model;

import hoplugins.Commons;

import hoplugins.commons.utils.HTCalendarFactory;
import hoplugins.commons.utils.PluginProperty;

import hoplugins.teamplanner.vo.FinancesOfWeek;
import hoplugins.teamplanner.vo.HTWeek;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;


/**
 * Missing Class Documentation
 *
 * @author Draghetto
 */
public class FinancesTableModel extends AbstractTableModel {
    //~ Instance fields ----------------------------------------------------------------------------

    private Vector colNames;
    private Vector values;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new FinancesTableModel object.
     *
     * @param values Missing Constructuor Parameter Documentation
     */
    public FinancesTableModel(Vector values) {
        this.values = null;
        colNames = new Vector();
        this.values = values;
        colNames.add(Commons.getModel().getResource().getProperty("Datum"));
        colNames.add(Commons.getModel().getResource().getProperty("Season") + " / "
                     + PluginProperty.getString("Week"));
        colNames.add(Commons.getModel().getResource().getProperty("Kontostand"));
        colNames.add(Commons.getModel().getResource().getProperty("Zuschauer"));
        colNames.add(Commons.getModel().getResource().getProperty("Sponsoren"));
        colNames.add(Commons.getModel().getResource().getProperty("Zinsertraege"));
        colNames.add(Commons.getModel().getResource().getProperty("Sonstiges"));
        colNames.add(Commons.getModel().getResource().getProperty("Gesamteinnahmen"));
        colNames.add(Commons.getModel().getResource().getProperty("Stadion"));
        colNames.add(Commons.getModel().getResource().getProperty("Spielergehaelter"));
        colNames.add(Commons.getModel().getResource().getProperty("Zinsaufwendungen"));
        colNames.add(Commons.getModel().getResource().getProperty("Sonstiges"));
        colNames.add(Commons.getModel().getResource().getProperty("Trainerstab"));
        colNames.add(Commons.getModel().getResource().getProperty("Jugend"));
        colNames.add(Commons.getModel().getResource().getProperty("Gesamtausgaben"));
        colNames.add(Commons.getModel().getResource().getProperty("ErwarteterGewinnVerlust"));
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Missing Method Documentation
     *
     * @param row Missing Method Parameter Documentation
     * @param col Missing Method Parameter Documentation
     *
     * @return Missing Return Method Documentation
     */
    public boolean isCellEditable(int row, int col) {
        return false;
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public int getColumnCount() {
        return colNames.size();
    }

    /**
     * Missing Method Documentation
     *
     * @param col Missing Method Parameter Documentation
     *
     * @return Missing Return Method Documentation
     */
    public String getColumnName(int col) {
        return (String) colNames.get(col);
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public int getRowCount() {
        return values.size();
    }

    /**
     * Missing Method Documentation
     *
     * @param row Missing Method Parameter Documentation
     * @param col Missing Method Parameter Documentation
     *
     * @return Missing Return Method Documentation
     */
    public Object getValueAt(int row, int col) {
        FinancesOfWeek rowData = (FinancesOfWeek) values.get(row);

        switch (col) {
            case 0: // '\0'
                return rowData.getTimestamp();

            case 1: // '\001'

                hoplugins.commons.utils.HTCalendar cal = HTCalendarFactory.createEconomyCalendar(Commons
                                                                                                 .getModel(),
                                                                                                 rowData
                                                                                                 .getDate());
                return new HTWeek(cal);

            case 2: // '\002'
                return new Integer(rowData.getCash());

            case 3: // '\003'
                return new Integer(rowData.getSpectatorsIncome());

            case 4: // '\004'
                return new Integer(rowData.getSponsorsIncome());

            case 5: // '\005'
                return new Integer(rowData.getInterestIncome());

            case 6: // '\006'
                return new Integer(rowData.getTemporaryIncome());

            case 7: // '\007'
                return new Integer(rowData.getTotalIncome());

            case 8: // '\b'
                return new Integer(rowData.getArenaExpenses());

            case 9: // '\t'
                return new Integer(rowData.getSalaries());

            case 10: // '\n'
                return new Integer(rowData.getInterestExpenses());

            case 11: // '\013'
                return new Integer(rowData.getTemporaryExpenses());

            case 12: // '\f'
                return new Integer(rowData.getStaffExpenses());

            case 13: // '\r'
                return new Integer(rowData.getYouthSquadExpenses());

            case 14: // '\016'
                return new Integer(rowData.getTotalExpenses());

            case 15: // '\017'
                return new Integer(rowData.getExpectedProfitOrLoss());
        }

        return "";
    }

    /**
     * Missing Method Documentation
     *
     * @param values Missing Method Parameter Documentation
     */
    public void refresh(Vector values) {
        this.values = values;
        fireTableDataChanged();
    }
}