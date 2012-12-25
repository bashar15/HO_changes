// %4073212593:de.hattrickorganizer.gui.model%
package ho.core.gui.model;

import ho.core.gui.comp.entry.ColorLabelEntry;
import ho.core.gui.comp.entry.RatingTableEntry;
import ho.core.gui.theme.ImageUtilities;
import ho.core.model.HOVerwaltung;
import ho.core.model.player.SpielerPosition;

import java.util.Vector;

import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;



/**
 * DOCUMENT ME!
 *
 * @author Volker Fischer
 * @version 0.2a    31.10.2001
 */
public class SpielerPositionTableModel extends AbstractTableModel {
    //~ Instance fields ----------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = -4407511039452889638L;

    public String[] m_sToolTipStrings = {
    	HOVerwaltung.instance().getLanguageString("Position"),
        //Maximal
	    HOVerwaltung.instance().getLanguageString("Maximal"),
        //Minimal
	    HOVerwaltung.instance().getLanguageString("Minimal"),
        //Durchschnitt
	    HOVerwaltung.instance().getLanguageString("Durchschnitt"),
    };

    protected Object[][] m_clData;

    protected String[] m_sColumnNames = {
                                            HOVerwaltung.instance().getLanguageString("Position"),
                                            

    //Maximal
    HOVerwaltung.instance().getLanguageString("Maximal"),
                                            

    //Minimal
    HOVerwaltung.instance().getLanguageString("Minimal"),
                                            

    //Durchschnitt
    HOVerwaltung.instance().getLanguageString("Durchschnitt"),
                                        };
    private Vector<float[]> m_vSpielerBewertung;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new SpielerPositionTableModel object.
     */
    public SpielerPositionTableModel(Vector<float[]> spielerbewertung) {
        m_vSpielerBewertung = spielerbewertung;
        initData();
    }

    //~ Methods ------------------------------------------------------------------------------------

    @Override
	public final boolean isCellEditable(int row, int col) {
        return false;
    }

    @Override
	public final Class<?> getColumnClass(int columnIndex) {
        final Object obj = getValueAt(0, columnIndex);

        if (obj != null) {
            return obj.getClass();
        }

        return "".getClass();
    }

    //-----Zugriffsmethoden----------------------------------------        
    public final int getColumnCount() {
        return m_sColumnNames.length;

        /*
           if ( m_clData!=null && m_clData.length > 0 && m_clData[0] != null )
               return m_clData[0].length;
           else
               return 0;
         */
    }

    @Override
	public final String getColumnName(int columnIndex) {
        if ((m_sColumnNames != null) && (m_sColumnNames.length > columnIndex)) {
            return m_sColumnNames[columnIndex];
        }

        return null;
    }

    public final int getRowCount() {
        return (m_clData != null) ? m_clData.length : 0;
    }

    public final Object getValue(int row, String columnName) {
        if ((m_sColumnNames != null) && (m_clData != null)) {
            int i = 0;

            while ((i < m_sColumnNames.length) && !m_sColumnNames[i].equals(columnName)) {
                i++;
            }

            return m_clData[row][i];
        }

        return null;
    }

    @Override
	public final void setValueAt(Object value, int row, int column) {
        m_clData[row][column] = value;
    }

    public final Object getValueAt(int row, int column) {
        if (m_clData != null) {
            return m_clData[row][column];
        }

        return null;
    }

    /**
     * Spieler neu setzen
     */
    public final void setValues(Vector<float[]> spielerbewertung) {
        m_vSpielerBewertung = spielerbewertung;
        initData();
    }

    //-----initialisierung-----------------------------------------

    /**
     * Erzeugt einen Data[][] aus dem Spielervector
     */
    private void initData() {
        m_clData = new Object[m_vSpielerBewertung.size()][m_sColumnNames.length];

        for (int i = 0; i < m_vSpielerBewertung.size(); i++) {
            //Zuerst die Position, dann max, min, durchschnitts Sterne
            final float[] bewertung = ((float[]) m_vSpielerBewertung.get(i));

            //Position
            m_clData[i][0] = new ColorLabelEntry(ImageUtilities.getImage4Position(SpielerPosition
                                                                    .getHTPosidForHOPosition4Image((byte) bewertung[3]),
                                                                    (byte) 0, 0),
                                                 -SpielerPosition.getSortId((byte) bewertung[3],
                                                                            false),
                                                 ColorLabelEntry.FG_STANDARD,
                                                 ColorLabelEntry.BG_STANDARD, SwingConstants.LEFT);
            ((ColorLabelEntry) m_clData[i][0]).setText(SpielerPosition.getNameForPosition((byte) bewertung[3]));

            //Maximal
            m_clData[i][1] = new RatingTableEntry(bewertung[0] * 2, true);

            //Minial
            m_clData[i][2] = new RatingTableEntry(bewertung[1] * 2, true);

            //Durchschnitt
            m_clData[i][3] = new RatingTableEntry(Math.round(bewertung[2] * 2), true);
        }
    }

    /*
       public Spieler getSpieler( int id )
       {
           if ( id > 0 )
           {
               for ( int i = 0; i < m_vSpieler.size(); i++ )
               {
                   if ( ( (Spieler)m_vSpieler.get( i ) ).getSpielerID() == id )
                   {
                       return (Spieler)m_vSpieler.get( i );
                   }
               }
           }
    
           return null;
       }*/
}
