// %3889649867:de.hattrickorganizer.gui.menu.option%
package de.hattrickorganizer.gui.menu.option;

import de.hattrickorganizer.gui.templates.ImagePanel;
import de.hattrickorganizer.model.OptionManager;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * Panel zum editieren der Farben
 */
final class FarbPanel extends ImagePanel implements ActionListener {
    //~ Instance fields ----------------------------------------------------------------------------

	private static final long serialVersionUID = 1L;
	private JButton m_jbAngeschlagen;
    private JButton m_jbGesperrt;
    private JButton m_jbTransfermarkt;
    private JButton m_jbVerletzt;
    private JButton m_jbZweiKarten;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new FarbPanel object.
     */
    protected FarbPanel() {
        initComponents();
    }

    //~ Methods ------------------------------------------------------------------------------------

    //---------------Listener-------------------------------------------    
    public final void actionPerformed(java.awt.event.ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(m_jbAngeschlagen)) {
            Color color = JColorChooser.showDialog(this,
                                                   de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Angeschlagen"),
                                                   gui.UserParameter.temp().FG_ANGESCHLAGEN);

            if (color != null) {
                gui.UserParameter.temp().FG_ANGESCHLAGEN = color;
                OptionManager.instance().setReInitNeeded();
                refresh();
            }
        } else if (actionEvent.getSource().equals(m_jbVerletzt)) {
            Color color = JColorChooser.showDialog(this,
                                                   de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Verletzt"),
                                                   gui.UserParameter.temp().FG_VERLETZT);

            if (color != null) {
                gui.UserParameter.temp().FG_VERLETZT = color;
                OptionManager.instance().setReInitNeeded();
                refresh();
            }
        } else if (actionEvent.getSource().equals(m_jbZweiKarten)) {
            Color color = JColorChooser.showDialog(this,
                                                   de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Verwarnt"),
                                                   gui.UserParameter.temp().FG_ZWEIKARTEN);

            if (color != null) {
                gui.UserParameter.temp().FG_ZWEIKARTEN = color;
                OptionManager.instance().setReInitNeeded();
                refresh();
            }
        } else if (actionEvent.getSource().equals(m_jbGesperrt)) {
            Color color = JColorChooser.showDialog(this,
                                                   de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Gesperrt"),
                                                   gui.UserParameter.temp().FG_GESPERRT);

            if (color != null) {
                gui.UserParameter.temp().FG_GESPERRT = color;
                OptionManager.instance().setReInitNeeded();
                refresh();
            }
        } else if (actionEvent.getSource().equals(m_jbTransfermarkt)) {
            Color color = JColorChooser.showDialog(this,
                                                   de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Transfermarkt"),
                                                   gui.UserParameter.temp().FG_TRANSFERMARKT);

            if (color != null) {
                gui.UserParameter.temp().FG_TRANSFERMARKT = color;
                OptionManager.instance().setReInitNeeded();
                refresh();
            }
        }
    }

    //---------------Hilfsmethoden--------------------------------------
    public final void refresh() {
        m_jbAngeschlagen.setBackground(gui.UserParameter.temp().FG_ANGESCHLAGEN);
        m_jbVerletzt.setBackground(gui.UserParameter.temp().FG_VERLETZT);
        m_jbZweiKarten.setBackground(gui.UserParameter.temp().FG_ZWEIKARTEN);
        m_jbGesperrt.setBackground(gui.UserParameter.temp().FG_GESPERRT);
        m_jbTransfermarkt.setBackground(gui.UserParameter.temp().FG_TRANSFERMARKT);
    }

    /**
     * TODO Missing Method Documentation
     */
    private void initComponents() {
        final GridBagLayout layout = new GridBagLayout();
        final GridBagConstraints constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.insets = new Insets(150, 4, 150, 4);

        setLayout(layout);

        //----Slider -----------
        final JPanel panel = new ImagePanel();
        panel.setLayout(new GridLayout(5, 2, 4, 10));
        panel.setBorder(BorderFactory.createLineBorder(Color.darkGray));

        JLabel label = new JLabel("  "
                                  + de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Angeschlagen"));
        panel.add(label);

        m_jbAngeschlagen = new JButton();
        m_jbAngeschlagen.setBackground(gui.UserParameter.temp().FG_ANGESCHLAGEN);
        m_jbAngeschlagen.addActionListener(this);
        panel.add(m_jbAngeschlagen);

        label = new JLabel("  "
                           + de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Verletzt"));
        panel.add(label);

        m_jbVerletzt = new JButton();
        m_jbVerletzt.setBackground(gui.UserParameter.temp().FG_VERLETZT);
        m_jbVerletzt.addActionListener(this);
        panel.add(m_jbVerletzt);

        label = new JLabel("  "
                           + de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Verwarnt"));
        panel.add(label);

        m_jbZweiKarten = new JButton();
        m_jbZweiKarten.setBackground(gui.UserParameter.temp().FG_ZWEIKARTEN);
        m_jbZweiKarten.addActionListener(this);
        panel.add(m_jbZweiKarten);

        label = new JLabel("  "
                           + de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Gesperrt"));
        panel.add(label);

        m_jbGesperrt = new JButton();
        m_jbGesperrt.setBackground(gui.UserParameter.temp().FG_GESPERRT);
        m_jbGesperrt.addActionListener(this);
        panel.add(m_jbGesperrt);

        label = new JLabel("  "
                           + de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Transfermarkt"));
        panel.add(label);

        m_jbTransfermarkt = new JButton();
        m_jbTransfermarkt.setBackground(gui.UserParameter.temp().FG_TRANSFERMARKT);
        m_jbTransfermarkt.addActionListener(this);
        panel.add(m_jbTransfermarkt);

        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        layout.setConstraints(panel, constraints);
        add(panel);

        refresh();
    }
}