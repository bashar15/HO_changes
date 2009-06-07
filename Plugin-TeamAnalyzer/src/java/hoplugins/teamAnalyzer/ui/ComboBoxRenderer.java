// %1117338229:hoplugins.teamAnalyzer.ui%
package hoplugins.teamAnalyzer.ui;

import hoplugins.teamAnalyzer.SystemManager;
import hoplugins.teamAnalyzer.vo.Team;

import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class ComboBoxRenderer extends DefaultListCellRenderer {
    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new ComboBoxRenderer object.
     */
    public ComboBoxRenderer() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    /*
     * This method finds the image and text corresponding
     * to the selected value and returns the label, set up
     * to display the text and image.
     */
    public Component getListCellRendererComponent(JList list, Object value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        Team team = (Team) value;

        if (team == null) {
            setForeground(list.getForeground());

            return this;
        }

        if (team.getTeamId() == SystemManager.getLeagueOpponentId()) {
            setForeground(Color.RED);
        } else if (team.getTeamId() == SystemManager.getCupOpponentId()) {
            setForeground(Color.GREEN);
        } else {
            setForeground(list.getForeground());
        }

        return this;
    }
}