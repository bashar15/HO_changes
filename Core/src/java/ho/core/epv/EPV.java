// %1598857424:de.hattrickorganizer.logik%
package ho.core.epv;

import ho.core.model.HOVerwaltung;
import ho.core.model.player.Spieler;
import ho.core.util.HOLogger;

/**
 * Main EPV manager class
 *
 * @author draghetto
 */
public class EPV {
    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new EPV object.
     */
    public EPV() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    public final EPVData getEPVData(Spieler spieler) {
        final EPVData data = new EPVData(spieler);
        return data;
    }


    ////////////////////////////////////////////////////////////////////////////////    
    //Accessor
    ////////////////////////////////////////////////////////////////////////////////    
    public final double getPrice(EPVData data) {
        return getPrice(data, HOVerwaltung.instance().getModel().getBasics().getSpieltag());
    }

    public final double getPrice(EPVData data, int week) {
    	try {
    		return EPVCalculator.getInstance().getPrice( data, week, HOVerwaltung.instance().getModel().getXtraDaten().getCurrencyRate() );
    	} catch (Exception e) {
    		HOLogger.instance().log(EPV.class, e);
    		return 0d;
    	}
    }
}
