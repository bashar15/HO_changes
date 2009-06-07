// %2208660911:de.hattrickorganizer.gui.matches%
package de.hattrickorganizer.gui.matches;

import java.awt.BorderLayout;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.text.html.StyleSheet;

import de.hattrickorganizer.gui.templates.ImagePanel;
import de.hattrickorganizer.tools.HOLogger;


/**
 * Zeigt den Spielstand an
 */
public class MatchberichtEditorPanel extends ImagePanel {
    //~ Instance fields ----------------------------------------------------------------------------

    private JEditorPane m_jepTextModusEditorPane;
    private JScrollPane m_jscTextModusScrollPane;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new MatchberichtEditorPanel object.
     */
    public MatchberichtEditorPanel() {
        initComponents();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param text TODO Missing Method Parameter Documentation
     */
    public final void setText(String text) {
        try {
            if ((text == null) || (text.trim().length() == 0)) {
                text = " --- ";
            }

            m_jepTextModusEditorPane.setText(text);
            m_jepTextModusEditorPane.setCaretPosition(0);
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),"MatchberichtPanel.append : " + e);
        }
    }

    /**
     * TODO Missing Method Documentation
     */
    public final void clear() {
        m_jepTextModusEditorPane.setText(" --- ");
    }

    /**
     * TODO Missing Method Documentation
     */
    private void initComponents() {
        setLayout(new BorderLayout());

        final StyleSheet style = new StyleSheet();
        style.addRule("a { color:#006400; font-weight:bold; }");
        style.addRule("BODY, P {font: " + gui.UserParameter.instance().schriftGroesse
                      + "pt sans-serif; color:#000000}");

        final javax.swing.text.html.HTMLEditorKit kit = new javax.swing.text.html.HTMLEditorKit();
        kit.setStyleSheet(style);

        m_jepTextModusEditorPane = new JEditorPane("text/html", " ");
        m_jepTextModusEditorPane.setEditorKit(kit);

        //m_jepTextModusEditorPane.setBackground ( new Color( 240, 240, 230 ) );
        //m_jepTextModusEditorPane.addHyperlinkListener(this);
        m_jscTextModusScrollPane = new JScrollPane(m_jepTextModusEditorPane);
        m_jscTextModusScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        m_jscTextModusScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(m_jscTextModusScrollPane, BorderLayout.CENTER);
    }
}