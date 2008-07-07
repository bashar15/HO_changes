// %1942107811:de.hattrickorganizer.menu.option%
package de.hattrickorganizer.gui.menu.option;

import gui.UserParameter;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.event.ChangeListener;

import de.hattrickorganizer.gui.templates.ImagePanel;
import de.hattrickorganizer.logik.RatingOptimizer;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.tools.Helper;

/**
 * Alle weiteren Optionen, die Keine Formeln sind
 */
public final class RatingOffsetPanel
	extends ImagePanel
	implements ChangeListener, ActionListener {
	//~ Static fields/initializers -----------------------------------------------------------------

	private SliderPanel[] slider = new SliderPanel[7];
	private SliderPanel numberMatches;
	private JLabel[] err = new JLabel[7];
	private JButton m_jbCalculate;
	private JButton m_jbReset;

	//~ Constructors -------------------------------------------------------------------------------

	/**
	 * Creates a new SonstigeOptionenPanel object.
	 */
	public RatingOffsetPanel() {
		initComponents();
	}

	//~ Methods ------------------------------------------------------------------------------------

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param changeEvent TODO Missing Method Parameter Documentation
	 */
	public final void stateChanged(javax.swing.event.ChangeEvent changeEvent) {
		UserParameter.instance().leftDefenceOffset =
			slider[2].getValue() / 100.0f;
		UserParameter.instance().middleDefenceOffset =
			slider[1].getValue() / 100.0f;
		UserParameter.instance().rightDefenceOffset =
			slider[0].getValue() / 100.0f;
		UserParameter.instance().midfieldOffset =
			slider[3].getValue() / 100.0f;
		UserParameter.instance().leftAttackOffset =
			slider[6].getValue() / 100.0f;
		UserParameter.instance().middleAttackOffset =
			slider[5].getValue() / 100.0f;
		UserParameter.instance().rightAttackOffset =
			slider[4].getValue() / 100.0f;

	}

	/**
	 * TODO Missing Method Documentation
+	 */
	private void initComponents() {        
		slider[0] = createSlider(UserParameter.instance().rightDefenceOffset,"rechteAbwehrseite");
        slider[1] = createSlider(UserParameter.instance().middleDefenceOffset,"Abwehrzentrum");
		slider[2] = createSlider(UserParameter.instance().leftDefenceOffset,"linkeAbwehrseite");
        slider[3] = createSlider(UserParameter.instance().midfieldOffset,"MatchMittelfeld");
		slider[4] = createSlider(UserParameter.instance().rightAttackOffset,"rechteAngriffsseite");        
        slider[5] = createSlider(UserParameter.instance().middleAttackOffset,"Angriffszentrum");
		slider[6] = createSlider(UserParameter.instance().leftAttackOffset,"linkeAngriffsseite");
               

        err[0] = new JLabel();
        err[1] = new JLabel();
        err[2] = new JLabel();
        err[3] = new JLabel();
        err[4] = new JLabel();
        err[5] = new JLabel();
        err[6] = new JLabel();       

        numberMatches = new SliderPanel("Number Matches",30,1,1,1f,120,0);
        numberMatches.setValue(10);

        m_jbCalculate =
            new JButton(
                HOVerwaltung.instance().getResource().getProperty("Calculate"));
        m_jbCalculate.addActionListener(this);

        m_jbReset =
            new JButton(
                HOVerwaltung.instance().getResource().getProperty("Reset"));
        m_jbReset.addActionListener(this);

        //Layout components
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.gridy = 0;
        c.gridx = 1;
        c.insets = new Insets(0,200,0,0);
        c.anchor = GridBagConstraints.CENTER;
        add(new JLabel("Offset"), c);
        c.insets = new Insets(0,25,0,0);
        c.gridx = 3;
        add(new JLabel("Error"), c);

        c.insets = new Insets(10,0,0,0);
        c.gridy = 1;
        c.gridx = 0;
        c.gridwidth = 3;
        c.anchor = GridBagConstraints.LINE_START;
        add(slider[0], c);
        c.gridx = 3;
        c.anchor = GridBagConstraints.LINE_END;
        add(err[0], c);
        
        c.gridy = 2;
        c.gridx = 0;
        c.gridwidth = 3;
        c.anchor = GridBagConstraints.LINE_START;
        add(slider[1], c);
        c.gridx = 3;
        c.anchor = GridBagConstraints.LINE_END;
        add(err[1], c);
        
        c.gridy = 3;
        c.gridx = 0;
        c.gridwidth = 3;
        c.anchor = GridBagConstraints.LINE_START;
        add(slider[2], c);
        c.gridx = 3;
        c.anchor = GridBagConstraints.LINE_END;
        add(err[2], c);
        
        c.gridy = 4;
        c.gridx = 0;
        c.gridwidth = 3;
        c.anchor = GridBagConstraints.LINE_START;
        add(slider[3], c);
        c.gridx = 3;
        c.anchor = GridBagConstraints.LINE_END;
        add(err[3], c);
        
        c.gridy = 5;
        c.gridx = 0;
        c.gridwidth = 3;
        c.anchor = GridBagConstraints.LINE_START;
        add(slider[4], c);
        c.gridx = 3;
        c.anchor = GridBagConstraints.LINE_END;
        add(err[4], c);
        
        c.gridy = 6;
        c.gridx = 0;
        c.gridwidth = 3;
        c.anchor = GridBagConstraints.LINE_START;
        add(slider[5], c);
        c.gridx = 3;
        c.anchor = GridBagConstraints.LINE_END;
        add(err[5], c);
        
        c.gridy = 7;
        c.gridx = 0;
        c.gridwidth = 3;
        c.anchor = GridBagConstraints.LINE_START;
        add(slider[6], c);
        c.gridx = 3;
        c.anchor = GridBagConstraints.LINE_END;
        add(err[6], c);
        
        c.gridy = 8;
        c.gridx = 0;
        c.gridwidth = 3;
        c.anchor = GridBagConstraints.LINE_START;
        add(numberMatches, c);

        c.gridy = 9;
        c.gridx = 1;
        c.anchor = GridBagConstraints.CENTER;
        add(m_jbReset, c);
        c.gridx = 3;
        c.anchor = GridBagConstraints.CENTER;
        add(m_jbCalculate, c);

	}

    private SliderPanel createSlider(double initialValue, String name) {
        SliderPanel slider = new SliderPanel(
                HOVerwaltung.instance().getResource().getProperty(name),100,-100,1,0.01f,120,2);
        slider.setValue((int) (100 * initialValue));
        slider.addChangeListener(this);
        return slider;
    }
    
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {

		if (e.getSource().equals(m_jbCalculate)) {
			calculate();
		} else if (e.getSource().equals(m_jbReset)) {
			slider[0].setValue(0);
			slider[1].setValue(0);
			slider[2].setValue(0);
			slider[3].setValue(0);
			slider[4].setValue(0);
			slider[5].setValue(0);
			slider[6].setValue(0);
			for(int i = 0; i < 7; i++) {
				err[i].setText("");
			}			
			numberMatches.setValue(10);
			stateChanged(null);
		}


	}

	public void calculate() {
		double offset[][] = RatingOptimizer.optimize((int) numberMatches.getValue());
		if (offset.length > 7) { // length=7 -> no matches, nothing to do 
			numberMatches.setValue((int) offset[7][0]);
			for(int i = 0; i < 7; i++) {
				slider[i].setValue((int) (100 * offset[i][0]));
				double value = offset[i][1];
				if (value>0) {				
					if (gui.UserParameter.instance().anzahlNachkommastellen == 1) {
						err[i].setText(Helper.DEFAULTDEZIMALFORMAT.format(value));
					} else {
						err[i].setText(Helper.DEZIMALFORMAT_2STELLEN.format(value));
					}								
				} else {
					err[i].setText("");
				}			
			}
		}
		stateChanged(null);
	}
}
