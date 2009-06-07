// %4025553032:de.hattrickorganizer.gui.menu.option%
package de.hattrickorganizer.gui.menu.option;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * OLD! Initaloptionen
 */
public final class InitOptionsDialog extends JDialog implements java.awt.event.ActionListener {

	private static final long serialVersionUID = 1L;
	//~ Instance fields ----------------------------------------------------------------------------

    private JButton m_jbOK;
    private JComboBox m_jcbSprachdatei;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new InitOptionsDialog object.
     */
    public InitOptionsDialog() {
        super(new JFrame(), "Please select your language", true);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        initComponents();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param actionEvent TODO Missing Method Parameter Documentation
     */
    public final void actionPerformed(java.awt.event.ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(m_jbOK)) {
            if (m_jcbSprachdatei.getSelectedItem() != null) {
                gui.UserParameter.instance().sprachDatei = ((String) m_jcbSprachdatei
                                                            .getSelectedItem());

                setVisible(false);
            }
        }
    }

    /**
     * TODO Missing Method Documentation
     */
    private void initComponents() {
        setContentPane(new JPanel(new BorderLayout()));

        final JPanel optionspanel = new JPanel();
        optionspanel.setLayout(new GridLayout(1, 2, 4, 4));

        optionspanel.add(new JLabel("Languagefile"));

        final String[] sprachdateien = de.hattrickorganizer.tools.LanguageFiles.getSprachDateien();

        try {
            java.util.Arrays.sort(sprachdateien);
        } catch (Exception e) {
        }

        m_jcbSprachdatei = new JComboBox(sprachdateien);
        m_jcbSprachdatei.setSelectedItem(gui.UserParameter.instance().sprachDatei);
        optionspanel.add(m_jcbSprachdatei);

        getContentPane().add(optionspanel, BorderLayout.CENTER);

        final JPanel buttonpanel = new JPanel();

        m_jbOK = new JButton("OK");
        m_jbOK.addActionListener(this);
        buttonpanel.add(m_jbOK);

        getContentPane().add(buttonpanel, BorderLayout.SOUTH);

        pack();

        //Spracheinstellung unter dem Splashscreen anzeigen
        setLocation((this.getToolkit().getScreenSize().width - this.getWidth()) / 2,
                    (this.getToolkit().getScreenSize().height - this.getHeight() + 250) / 2);

        setVisible(true);
    }
}