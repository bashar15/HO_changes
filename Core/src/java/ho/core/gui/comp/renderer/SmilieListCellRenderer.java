// %1885149141:de.hattrickorganizer.gui.model%
package ho.core.gui.comp.renderer;

import ho.core.gui.comp.entry.ColorLabelEntry;
import ho.core.gui.theme.HOColorName;
import ho.core.gui.theme.ThemeManager;

import javax.swing.SwingConstants;



/**
 * Renderer für eine Combobox mit SpielerCBItems
 */
public final class SmilieListCellRenderer implements javax.swing.ListCellRenderer {
    //~ Instance fields ----------------------------------------------------------------------------

    private ColorLabelEntry m_clEntry = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
    														ThemeManager.getColor(HOColorName.TABLEENTRY_BG),
                                                            SwingConstants.LEFT);

    private javax.swing.JLabel m_jlLeer = new javax.swing.JLabel(" ");

    //~ Methods ------------------------------------------------------------------------------------

    public final java.awt.Component getListCellRendererComponent(javax.swing.JList jList,
                                                                 Object obj, int index,
                                                                 boolean isSelected,
                                                                 boolean cellHasFocus) {
        if (obj instanceof String && (obj != null) && !obj.equals("")) {
            m_clEntry.setIcon(ThemeManager.getIcon(obj.toString()));
            return m_clEntry.getComponent(isSelected);
        }

        m_jlLeer.setOpaque(true);

        if (isSelected) {
            m_jlLeer.setBackground(HODefaultTableCellRenderer.SELECTION_BG);
        } else {
            m_jlLeer.setBackground(ThemeManager.getColor(HOColorName.TABLEENTRY_BG));
        }

        return m_jlLeer;
    }
}
