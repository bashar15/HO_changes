package de.hattrickorganizer.gui.dbcleanup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.hattrickorganizer.model.HOVerwaltung;

public class WeekSelectionPanel extends JPanel implements ActionListener, FocusListener {

	private static final long serialVersionUID = -8423470613835503603L;
	private JLabel labelNone = new JLabel (HOVerwaltung.instance().getResource().getProperty("dbcleanup.none"));
	private JLabel labelAll = new JLabel (HOVerwaltung.instance().getResource().getProperty("dbcleanup.all"));
	private JLabel labelRemoveOlderThan = new JLabel (HOVerwaltung.instance().getResource().getProperty("dbcleanup.removeOlderThan"));
	private JLabel labelWeeks = new JLabel (HOVerwaltung.instance().getResource().getProperty("dbcleanup.weeks"));
	private JCheckBox m_jcbNone = new JCheckBox();
	private JCheckBox m_jcbAll = new JCheckBox();
	private JTextField m_jtfWeeks = new JTextField(3);

	public WeekSelectionPanel (int weeks) {
		this (weeks, true);
	}
	
	public WeekSelectionPanel (int weeks, boolean showRemoveAll) {
		m_jcbNone.setSelected(false);
		m_jcbAll.setSelected(false);
		m_jtfWeeks.setText("0");
		if (weeks <= DBCleanupTool.REMOVE_NONE) {
			m_jcbNone.setSelected(true);
		} else if (weeks == DBCleanupTool.REMOVE_ALL) {
			m_jcbAll.setSelected(true);
		} else {
			m_jtfWeeks.setText(""+weeks);
		}
		if (!showRemoveAll)
			m_jcbAll.setVisible(false);
		initComponents();
	}
	
	private void initComponents() {
		m_jcbNone.addActionListener(this);
		m_jcbAll.addActionListener(this);
		m_jtfWeeks.addFocusListener(this);

		add (labelNone);
		add (m_jcbNone);
		add (new JLabel ("     "));
		add (labelAll);
		add (m_jcbAll);
		add (new JLabel ("     "));
		add (labelRemoveOlderThan);
		add (m_jtfWeeks);
		add (labelWeeks);
	}
	
	public void actionPerformed(ActionEvent e) {
		// Deactivate all
		if (e.getSource().equals(m_jcbNone)) {
			if (m_jcbNone.isSelected()) {
				m_jcbAll.setSelected(false);
				m_jtfWeeks.setText("0");
			}
		} else if (e.getSource().equals(m_jcbAll)) {
			if (m_jcbAll.isSelected()) {
				m_jcbNone.setSelected(false);
				m_jtfWeeks.setText("0");
			}
		}

	}

	public int getWeeks () {
		if (m_jcbNone.isSelected()) {
			return DBCleanupTool.REMOVE_NONE;			
		} else if (m_jcbAll.isSelected()) {
			return DBCleanupTool.REMOVE_ALL;
		} else {
			int weeks = DBCleanupTool.REMOVE_NONE;
			try {
				weeks = Integer.parseInt(m_jtfWeeks.getText());
			} catch (Exception e) {
				// be silent
			}
			if (weeks > 0)
				return weeks;
			else
				return DBCleanupTool.REMOVE_NONE;
		}
	}

	public void focusGained(FocusEvent arg0) {
		m_jcbNone.setSelected(false);
		m_jcbAll.setSelected(false);		
	}

	public void focusLost(FocusEvent arg0) {
		// do nothing
	}
}
