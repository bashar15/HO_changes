// %2775781217:de.hattrickorganizer.gui.keepertool%
package de.hattrickorganizer.gui.keepertool;

import de.hattrickorganizer.model.ScoutEintrag;

import plugins.ISpieler;


/**
 * A Player Item object to be used in JComboBox
 *
 * @author draghetto
 */
public class PlayerItem {
    //~ Instance fields ----------------------------------------------------------------------------

    private String name = "";
    private int form;
    private int id;
    private int tsi;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new PlayerItem object.
     *
     * @param spieler the roster player to include in the Combo
     */
    public PlayerItem(ISpieler spieler) {
        tsi = spieler.getMarkwert();
        form = spieler.getForm();
        id = spieler.getSpielerID();
        name = spieler.getName();
    }

    /**
     * Creates a new PlayerItem object.
     *
     * @param spieler the scouted player to include in the combo
     */
    public PlayerItem(ScoutEintrag spieler) {
        tsi = spieler.getMarktwert();
        form = spieler.getForm();
        id = 0;
        name = spieler.getName();
    }

    /**
     * Creates a new empty PlayerItem object.
     */
    public PlayerItem() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Return the keeper form
     *
     * @return form
     */
    public final int getForm() {
        return form;
    }

    /**
     * Return the keeper id
     *
     * @return id
     */
    public final int getId() {
        return id;
    }

    /**
     * Return the keeper tsi
     *
     * @return tsi
     */
    public final int getTsi() {
        return tsi;
    }

    /**
     * Returns the name
     *
     * @return The String to show in the combo
     */
    public final String toString() {
        return name;
    }
}
