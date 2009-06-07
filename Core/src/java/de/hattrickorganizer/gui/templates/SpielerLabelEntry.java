// %2768837177:de.hattrickorganizer.gui.templates%
package de.hattrickorganizer.gui.templates;

import de.hattrickorganizer.gui.HOMainFrame;
import de.hattrickorganizer.model.SpielerPosition;
import de.hattrickorganizer.tools.Helper;
import de.hattrickorganizer.tools.PlayerHelper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import plugins.ISpieler;


/**
 *
 */
public final class SpielerLabelEntry extends TableEntry {
 
    //~ Instance fields ----------------------------------------------------------------------------

    /** Icon for playing creatively */

    //   private ImageIcon            m_clLeer                   =   new ImageIcon( new java.awt.image.BufferedImage( 14, 14, java.awt.image.BufferedImage.TYPE_INT_ARGB ) );
    private plugins.ISpieler m_clPlayer;
    private JComponent m_clComponent;
    private final JLabel m_jlGroup 			= new JLabel();
    private final JLabel m_jlName			= new JLabel();
    private final JLabel m_jlSkill			= new JLabel();
    private final JLabel m_jlSpezialitaet	= new JLabel();
    private final JLabel m_jlWeatherEffect	= new JLabel();
    private SpielerPosition m_clCurrentPlayerPosition;
    private boolean m_bShowTrikot;
    private boolean m_bShowWeatherEffect = true;
    private float m_fPositionsbewertung;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Label für den Spielernamen (je nach Status)
     *
     * @param spieler TODO Missing Constructuor Parameter Documentation
     * @param positionAktuell TODO Missing Constructuor Parameter Documentation
     * @param positionsbewertung TODO Missing Constructuor Parameter Documentation
     * @param showTrikot TODO Missing Constructuor Parameter Documentation
     * @param showWetterwarnung TODO Missing Constructuor Parameter Documentation
     */
    public SpielerLabelEntry(plugins.ISpieler spieler, SpielerPosition positionAktuell,
                             float positionsbewertung, boolean showTrikot, boolean showWetterwarnung) {
        m_clPlayer = spieler;
        m_clCurrentPlayerPosition = positionAktuell;
        m_fPositionsbewertung = positionsbewertung;
        m_bShowTrikot = showTrikot;
        m_bShowWeatherEffect = showWetterwarnung;
        createComponent();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Gibt eine passende Komponente zurück
     *
     * @param isSelected TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final JComponent getComponent(boolean isSelected) {
        //Alte Komponente wiederverwenden
        if (isSelected) {
            m_clComponent.setOpaque(true);
            m_clComponent.setBackground(de.hattrickorganizer.gui.model.SpielerTableRenderer.SELECTION_BG);
        } else {
            m_clComponent.setOpaque(true);
            m_clComponent.setBackground(ColorLabelEntry.BG_STANDARD);
        }

        return m_clComponent;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final plugins.ISpieler getSpieler() {
        return m_clPlayer;
    }

    //----Zugriff----------------------------
    public final void clear() {
        m_clPlayer = null;
        m_clCurrentPlayerPosition = null;
        m_fPositionsbewertung = 0f;
        updateComponent();
    }

    /**
     * Vergleich zum Sortieren
     *
     * @param obj TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int compareTo(Object obj) {
        if (obj instanceof SpielerLabelEntry) {
            final SpielerLabelEntry entry = (SpielerLabelEntry) obj;

            return m_clPlayer.getName().compareTo(entry.getSpieler().getName());
        }

        return 0;
    }

    //-------------------------------------------------------------    

    /**
     * Erstellt eine passende Komponente
     */
    public final void createComponent() {
        m_clComponent = new JPanel();

        final GridBagLayout layout = new GridBagLayout();
        final GridBagConstraints constraints = new GridBagConstraints();

        m_clComponent.setLayout(layout);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.weightx = 1.0;
        constraints.gridx = 1;
        constraints.insets = new Insets(0, 0, 0, 0);
        m_jlName.setIconTextGap(1);
        layout.setConstraints(m_jlName, constraints);
        m_clComponent.add(m_jlName);

        final JPanel spezPanel = new JPanel();
        spezPanel.setDoubleBuffered(false);
        spezPanel.setLayout(new BoxLayout(spezPanel, BoxLayout.X_AXIS));
        spezPanel.setBackground(ColorLabelEntry.BG_STANDARD);
        spezPanel.setOpaque(false);

        //Wetterwarnung
        m_jlWeatherEffect.setBackground(ColorLabelEntry.BG_STANDARD);
        m_jlWeatherEffect.setOpaque(false);
        m_jlWeatherEffect.setBorder(BorderFactory.createEmptyBorder(0, 1, 0, 0));

        spezPanel.add(m_jlWeatherEffect);

        //Spezialität
        m_jlSpezialitaet.setBackground(ColorLabelEntry.BG_STANDARD);
        m_jlSpezialitaet.setOpaque(false);

        spezPanel.add(m_jlSpezialitaet);

        //Bewertung
        m_jlSkill.setBackground(ColorLabelEntry.BG_STANDARD);
        m_jlSkill.setOpaque(false);
        m_jlSkill.setBorder(BorderFactory.createEmptyBorder(0, 1, 0, 0));

        spezPanel.add(m_jlSkill);

        //MiniGruppe
        m_jlGroup.setBackground(ColorLabelEntry.BG_STANDARD);
        m_jlGroup.setVerticalAlignment(SwingConstants.BOTTOM);
        m_jlGroup.setOpaque(false);
        m_jlGroup.setBorder(BorderFactory.createEmptyBorder(0, 1, 0, 1));

        spezPanel.add(m_jlGroup);

        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.EAST;
        constraints.weightx = 0.0;
        constraints.gridx = 3;
        layout.setConstraints(spezPanel, constraints);
        m_clComponent.add(spezPanel);

        if (m_clPlayer != null) {
            //Name
            m_jlName.setText(m_clPlayer.getName());
            m_jlName.setOpaque(false);
            m_jlName.setForeground(ColorLabelEntry.getForegroundForSpieler(m_clPlayer));

            //Trikot
            //&& m_clSpielerPositionAktuell != null )
            if (m_bShowTrikot) {
                m_jlName.setIcon(Helper.getImage4Position(m_clCurrentPlayerPosition,
                                                                                     m_clPlayer
                                                                                     .getTrikotnummer()));
                m_jlGroup.setIcon(Helper.getImageIcon4MiniGruppe(m_clPlayer
                                                                                            .getTeamInfoSmilie()));
            }

            //            else if ( m_bShowTrikot )
            //            {
            //                m_jlName.setIcon ( m_clLeer );
            //                m_jlGruppe.setIcon ( tools.Helper.getImageIcon4MiniGruppe ( m_clSpieler.getTeamInfoSmilie () ) );
            //            }
            
            updateDisplay(m_clPlayer);
        }

        m_clComponent.setPreferredSize(new Dimension(Helper.calcCellWidth(150),Helper.calcCellWidth(18)));
    }

    /**
     * TODO Missing Method Documentation
     */
    public final void updateComponent() {
        if (m_clPlayer != null) {
            m_jlName.setForeground(ColorLabelEntry.getForegroundForSpieler(m_clPlayer));

            //Trikot
            //&& m_clSpielerPositionAktuell != null )
            if (m_bShowTrikot) {
                m_jlName.setIcon(Helper.getImage4Position(m_clCurrentPlayerPosition,
                                                                                     m_clPlayer
                                                                                     .getTrikotnummer()));
                m_jlGroup.setIcon(Helper.getImageIcon4MiniGruppe(m_clPlayer
                                                                                            .getTeamInfoSmilie()));
            }

            //            else if ( m_bShowTrikot )
            //            {
            //                m_jlName.setIcon ( m_clLeer );
            //                m_jlGruppe.setIcon ( tools.Helper.getImageIcon4MiniGruppe ( m_clSpieler.getTeamInfoSmilie () ) );
            //            }

            updateDisplay(m_clPlayer);

        } else {
        	setEmptyLabel();
        }
    }

    /**
     * Aktualisierung des Entrys
     *
     * @param spieler TODO Missing Constructuor Parameter Documentation
     * @param positionAktuell TODO Missing Constructuor Parameter Documentation
     * @param positionsbewertung TODO Missing Constructuor Parameter Documentation
     */
    public final void updateComponent(ISpieler spieler, SpielerPosition positionAktuell,
                                      float positionsbewertung) {
        m_clPlayer = spieler;
        m_clCurrentPlayerPosition = positionAktuell;
        m_fPositionsbewertung = positionsbewertung;

        if (m_clPlayer != null) {
            if (m_clPlayer.isOld()) {
                m_jlName.setForeground(Color.GRAY);
            } else {
                m_jlName.setForeground(ColorLabelEntry.getForegroundForSpieler(m_clPlayer));
            }

            m_jlName.setText(m_clPlayer.getName());

            //Trikot
            //&& m_clSpielerPositionAktuell != null )
            if (m_bShowTrikot) {
                m_jlName.setIcon(Helper.getImage4Position(m_clCurrentPlayerPosition,
                                                                                     m_clPlayer
                                                                                     .getTrikotnummer()));
                m_jlGroup.setIcon(Helper.getImageIcon4MiniGruppe(m_clPlayer
                                                                                            .getTeamInfoSmilie()));
            }

            //            else if ( m_bShowTrikot )
            //            {
            //                m_jlName.setIcon ( m_clLeer );
            //                m_jlGruppe.setIcon ( tools.Helper.getImageIcon4MiniGruppe ( m_clSpieler.getTeamInfoSmilie () ) );
            //            }
            
            updateDisplay(m_clPlayer);

        } else {
        	setEmptyLabel();
        	m_jlGroup.setIcon(null);
        }

        m_clComponent.setPreferredSize(new Dimension(Helper.calcCellWidth(150),Helper.calcCellWidth(18)));
    }
    
    private void setEmptyLabel(){
        m_jlName.setText("");
        m_jlName.setIcon(null);
        m_jlWeatherEffect.setIcon(null);
        m_jlSpezialitaet.setIcon(null);
        m_jlSkill.setText("");
    }
    
    private void updateDisplay(ISpieler player){
    	// weatherEffect
    	if (m_bShowWeatherEffect) {
            final ImageIcon wettericon = Helper.getImageIcon4WetterEffekt(PlayerHelper
                                                                    .getWeatherEffect(HOMainFrame
                                                                                      .getWetter(),
                                                                                      player
                                                                                      .getSpezialitaet()));
            m_jlWeatherEffect.setIcon(wettericon);
        } else {
            m_jlWeatherEffect.setIcon(null);
        }
    	
    	// special
        if (player.getSpezialitaet() != 0) {
            final ImageIcon icon = Helper.getImageIcon4Spezialitaet(player.getSpezialitaet());
            m_jlSpezialitaet.setIcon(icon);
        } else {
            m_jlSpezialitaet.setIcon(null);
        }
        
        // positionValue
        if (m_bShowTrikot && (m_fPositionsbewertung != 0f)) {
            m_jlSkill.setText("(" + m_fPositionsbewertung + ")");
        } else {
            m_jlSkill.setText("");
        }
    	
    }
}