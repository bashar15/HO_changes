// %3158012967:de.hattrickorganizer.gui.menu.option%
package de.hattrickorganizer.gui.menu.option;

import de.hattrickorganizer.gui.templates.ImagePanel;

import java.awt.GridLayout;

import javax.swing.JCheckBox;


/**
 * Alle weiteren Optionen, die Keine Formeln sind
 */
final class TabOptionenPanel extends ImagePanel implements java.awt.event.ItemListener {
    //~ Instance fields ----------------------------------------------------------------------------

    private JCheckBox m_jchArenasizer;
    private JCheckBox m_jchAufstellung;
    private JCheckBox m_jchInformation;
    private JCheckBox m_jchLigatabelle;
    private JCheckBox m_jchSpiele;
    private JCheckBox m_jchSpielerAnalyse;

    //private ComboBoxPanel       m_jcbHTIP           = null;
    private JCheckBox m_jchSpieleruebersicht;
    private JCheckBox m_jchStatistik;
    private JCheckBox m_jchTransferscout;
    private boolean m_bSomethingChanged;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new TabOptionenPanel object.
     */
    protected TabOptionenPanel() {
        initComponents();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param itemEvent TODO Missing Method Parameter Documentation
     */
    public final void itemStateChanged(java.awt.event.ItemEvent itemEvent) {
        m_bSomethingChanged = true;

        //Kein Selected Event!
        gui.UserParameter.instance().tempTabSpieleruebersicht = !m_jchSpieleruebersicht.isSelected();
        gui.UserParameter.instance().tempTabAufstellung = !m_jchAufstellung.isSelected();
        gui.UserParameter.instance().tempTabLigatabelle = !m_jchLigatabelle.isSelected();
        gui.UserParameter.instance().tempTabSpiele = !m_jchSpiele.isSelected();
        gui.UserParameter.instance().tempTabSpieleranalyse = !m_jchSpielerAnalyse.isSelected();
        gui.UserParameter.instance().tempTabStatistik = !m_jchStatistik.isSelected();
        gui.UserParameter.instance().tempTabTransferscout = !m_jchTransferscout.isSelected();
        gui.UserParameter.instance().tempTabArenasizer = !m_jchArenasizer.isSelected();
        gui.UserParameter.instance().tempTabInformation = !m_jchInformation.isSelected();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final boolean somethingChanged() {
        return m_bSomethingChanged;
    }

    /**
     * TODO Missing Method Documentation
     */
    private void initComponents() {
        setLayout(new GridLayout(9, 1, 4, 4));

        //        m_jcbHTIP= new ComboBoxPanel( model.HOVerwaltung.instance ().getResource ().getProperty( "Hattrick" ), HT_IP_ADRESSEN, 120 );
        //        m_jcbHTIP.setSelectedItem ( gui.UserParameter.instance ().htip );
        //        m_jcbHTIP.addItemListener ( this );
        //        add( m_jcbHTIP );
        m_jchSpieleruebersicht = new JCheckBox(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                      .getResource()
                                                                                      .getProperty("Spieleruebersicht"));
        m_jchSpieleruebersicht.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                     .getResource()
                                                                                     .getProperty("tt_Optionen_TabManagement"));
        m_jchSpieleruebersicht.setOpaque(false);
        m_jchSpieleruebersicht.setSelected(!gui.UserParameter.instance().tempTabSpieleruebersicht);
        m_jchSpieleruebersicht.addItemListener(this);
        add(m_jchSpieleruebersicht);

        m_jchAufstellung = new JCheckBox(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                .getResource()
                                                                                .getProperty("Aufstellung"));
        m_jchAufstellung.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                               .getResource()
                                                                               .getProperty("tt_Optionen_TabManagement"));
        m_jchAufstellung.setOpaque(false);
        m_jchAufstellung.setSelected(!gui.UserParameter.instance().tempTabAufstellung);
        m_jchAufstellung.addItemListener(this);
        add(m_jchAufstellung);

        m_jchLigatabelle = new JCheckBox(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                .getResource()
                                                                                .getProperty("Ligatabelle"));
        m_jchLigatabelle.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                               .getResource()
                                                                               .getProperty("tt_Optionen_TabManagement"));
        m_jchLigatabelle.setOpaque(false);
        m_jchLigatabelle.setSelected(!gui.UserParameter.instance().tempTabLigatabelle);
        m_jchLigatabelle.addItemListener(this);
        add(m_jchLigatabelle);

        m_jchSpiele = new JCheckBox(de.hattrickorganizer.model.HOVerwaltung.instance().getResource()
                                                                           .getProperty("Spiele"));
        m_jchSpiele.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance().getResource()
                                                                          .getProperty("tt_Optionen_TabManagement"));
        m_jchSpiele.setOpaque(false);
        m_jchSpiele.setSelected(!gui.UserParameter.instance().tempTabSpiele);
        m_jchSpiele.addItemListener(this);
        add(m_jchSpiele);

        m_jchSpielerAnalyse = new JCheckBox(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                   .getResource()
                                                                                   .getProperty("SpielerAnalyse"));
        m_jchSpielerAnalyse.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                  .getResource()
                                                                                  .getProperty("tt_Optionen_TabManagement"));
        m_jchSpielerAnalyse.setOpaque(false);
        m_jchSpielerAnalyse.setSelected(!gui.UserParameter.instance().tempTabSpieleranalyse);
        m_jchSpielerAnalyse.addItemListener(this);
        add(m_jchSpielerAnalyse);

        m_jchStatistik = new JCheckBox(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                              .getResource()
                                                                              .getProperty("Statistik"));
        m_jchStatistik.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                             .getResource()
                                                                             .getProperty("tt_Optionen_TabManagement"));
        m_jchStatistik.setOpaque(false);
        m_jchStatistik.setSelected(!gui.UserParameter.instance().tempTabStatistik);
        m_jchStatistik.addItemListener(this);
        add(m_jchStatistik);

        m_jchTransferscout = new JCheckBox(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                  .getResource()
                                                                                  .getProperty("TransferScout"));
        m_jchTransferscout.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                 .getResource()
                                                                                 .getProperty("tt_Optionen_TabManagement"));
        m_jchTransferscout.setOpaque(false);
        m_jchTransferscout.setSelected(!gui.UserParameter.instance().tempTabTransferscout);
        m_jchTransferscout.addItemListener(this);
        add(m_jchTransferscout);

        m_jchArenasizer = new JCheckBox(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                               .getResource()
                                                                               .getProperty("ArenaSizer"));
        m_jchArenasizer.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                              .getResource()
                                                                              .getProperty("tt_Optionen_TabManagement"));
        m_jchArenasizer.setOpaque(false);
        m_jchArenasizer.setSelected(!gui.UserParameter.instance().tempTabArenasizer);
        m_jchArenasizer.addItemListener(this);
        add(m_jchArenasizer);

        m_jchInformation = new JCheckBox(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                .getResource()
                                                                                .getProperty("Verschiedenes"));
        m_jchInformation.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                               .getResource()
                                                                               .getProperty("tt_Optionen_TabManagement"));
        m_jchInformation.setOpaque(false);
        m_jchInformation.setSelected(!gui.UserParameter.instance().tempTabInformation);
        m_jchInformation.addItemListener(this);
        add(m_jchInformation);
    }
}
