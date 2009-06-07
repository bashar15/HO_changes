// %3911272477:de.hattrickorganizer.gui.keepertool%
package de.hattrickorganizer.gui.keepertool;

import de.hattrickorganizer.gui.HOMainFrame;
import de.hattrickorganizer.gui.model.TransferTableModel;
import de.hattrickorganizer.gui.pluginWrapper.GUIPluginWrapper;
import de.hattrickorganizer.model.ScoutEintrag;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import java.util.Iterator;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * Panel for selection of scouted keepers
 *
 * @author draghetto
 */
public class ScoutPanel extends JPanel {
    //~ Instance fields ----------------------------------------------------------------------------

    private JComboBox players = new JComboBox();
    private ResultPanel target;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new ScoutPanel object.
     *
     * @param panel the panel where to show results
     */
    public ScoutPanel(ResultPanel panel) {
        target = panel;
        init();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Reload the data and update the panel
     */
    public final void reload() {
        players.removeAllItems();
        players.addItem(new PlayerItem());

        final TransferTableModel model = (TransferTableModel) HOMainFrame.instance()
                                                                         .getTransferScoutPanel()
                                                                         .getTransferTable()
                                                                         .getTransferTableModel();

        for (Iterator iter = model.getScoutListe().iterator(); iter.hasNext();) {
            final ScoutEintrag element = (ScoutEintrag) iter.next();

            if (element.getTorwart() > 4) {
                players.addItem(new PlayerItem(element));
            }
        }

        if (players.getItemCount() == 1) {
            players.setEnabled(false);
        }

        players.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    final PlayerItem selected = (PlayerItem) players.getSelectedItem();

                    if (selected != null) {
                        target.setPlayer(selected.getForm(), selected.getTsi(), 0,
                                         selected.toString());
                    }
                }
            });

        reset();
    }

    /**
     * Reset the panel to default data
     */
    public final void reset() {
        players.setSelectedIndex(0);
    }

    /**
     * Initialize the GUI components
     */
    private void init() {
        setLayout(new BorderLayout());
        setOpaque(false);

        final JPanel buttonPanel = GUIPluginWrapper.instance().createImagePanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new GridLayout(6, 2));
        buttonPanel.add(label(""));
        buttonPanel.add(label(""));
        buttonPanel.add(label(""));
        buttonPanel.add(players);
        buttonPanel.add(label(""));
        buttonPanel.add(label(""));
        buttonPanel.add(label(""));
        buttonPanel.add(label(""));
        buttonPanel.add(label(""));
        buttonPanel.add(label(""));
        buttonPanel.add(label(""));
        add(buttonPanel, BorderLayout.CENTER);
    }

    /**
     * Create a configured label
     *
     * @param string the label text
     *
     * @return the built component
     */
    private Component label(String string) {
        final JLabel label = new JLabel(string, JLabel.CENTER);
        label.setOpaque(false);
        return label;
    }
}