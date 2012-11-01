// %155607735:de.hattrickorganizer.gui.league%
package ho.module.series;

import ho.core.db.DBManager;
import ho.core.gui.CursorToolkit;
import ho.core.gui.RefreshManager;
import ho.core.gui.Refreshable;
import ho.core.gui.comp.panel.ImagePanel;
import ho.core.gui.theme.HOColorName;
import ho.core.gui.theme.HOIconName;
import ho.core.gui.theme.ThemeManager;
import ho.core.model.HOVerwaltung;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.text.DateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Panel, das die Ligatabelle sowie das letzte und das nächste Spiel enthält
 */
public class SeriesPanel extends ImagePanel implements Refreshable {

	private static final long serialVersionUID = -5179683183917344230L;
	private JButton printButton;
	private JButton deleteButton;
	private JComboBox seasonComboBox;
	private SeriesTablePanel seriesTable;
	private MatchDayPanel[] matchDayPanels;
	private SeriesHistoryPanel seriesHistoryPanel;
	private Model model = new Model();
	private boolean initialized = false;
	private boolean needsRefresh = false;

	/**
	 * Creates a new LigaTabellePanel object.
	 */
	public SeriesPanel() {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				if (isShowing()) {
					if (!initialized) {
						CursorToolkit.startWaitCursor(SeriesPanel.this);
						try {
							initialize();
						} finally {
							CursorToolkit.stopWaitCursor(SeriesPanel.this);
						}
					}
					if (needsRefresh) {
						update();
					}
				}

			}
		});
	}

	private void initialize() {
		RefreshManager.instance().registerRefreshable(this);
		initComponents();
		fillSaisonCB();
		addListeners();
		this.initialized = true;
	}

	private void print() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());

		String titel = HOVerwaltung.instance().getLanguageString("Ligatabelle") + " - "
				+ HOVerwaltung.instance().getModel().getBasics().getTeamName() + " - "
				+ DateFormat.getDateTimeInstance().format(calendar.getTime());

		SeriesPrintPanelDialog printDialog = new SeriesPrintPanelDialog(this.model);
		printDialog.doPrint(titel);
		printDialog.setVisible(false);
		printDialog.dispose();
	}

	private void delete() {
		if (seasonComboBox.getSelectedItem() != null) {
			Spielplan spielplan = (Spielplan) seasonComboBox.getSelectedItem();
			int value = JOptionPane.showConfirmDialog(this,
					HOVerwaltung.instance().getLanguageString("ls.button.delete") + " "
							+ HOVerwaltung.instance().getLanguageString("Ligatabelle") + ":\n"
							+ spielplan.toString(), "", JOptionPane.YES_NO_OPTION);

			if (value == JOptionPane.YES_OPTION) {
				final String[] dbkey = { "Saison", "LigaID" };
				final String[] dbvalue = { spielplan.getSaison() + "", spielplan.getLigaId() + "" };

				DBManager.instance().deleteSpielplanTabelle(dbkey, dbvalue);
				DBManager.instance().deletePaarungTabelle(dbkey, dbvalue);
				this.model.setCurrentSeries(null);
				RefreshManager.instance().doReInit();
			}
		}
	}

	private void addListeners() {
		this.deleteButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				delete();
			}
		});

		this.printButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				print();
			}
		});

		this.seasonComboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Aktuellen Spielplan bestimmen
				if (seasonComboBox.getSelectedItem() instanceof Spielplan) {
					model.setCurrentSeries((Spielplan) seasonComboBox.getSelectedItem());
				} else {
					model.setCurrentSeries(null);
				}

				// Alle Panels informieren
				informSaisonChange();
			}
		});

		this.seriesTable.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					teamSelectionChanged();
				}
			}
		});
	}

	private void teamSelectionChanged() {
		if (this.model.getCurrentTeam() == null && seriesTable.getSelectedTeam() == null) {
			return;
		}

		if (this.model.getCurrentTeam() == null
				|| !this.model.getCurrentTeam().equals(seriesTable.getSelectedTeam())) {
			this.model.setCurrentTeam(seriesTable.getSelectedTeam());
			markierungInfo();
		}
	}

	@Override
	public final void reInit() {
		if (isShowing()) {
			update();
		} else {
			this.needsRefresh = true;
		}
	}

	@Override
	public void refresh() {
	}

	private void update() {
		fillSaisonCB();
		this.needsRefresh = false;
	}

	private void fillSaisonCB() {
		// Die Spielpläne als Objekte mit den Paarungen holen
		final Spielplan[] spielplaene = DBManager.instance().getAllSpielplaene(true);
		final Spielplan markierterPlan = (Spielplan) seasonComboBox.getSelectedItem();

		// Alle alten Saisons entfernen
		seasonComboBox.removeAllItems();

		// Neue füllen
		for (int i = 0; (spielplaene != null) && (i < spielplaene.length); i++) {
			seasonComboBox.addItem(spielplaene[i]);
		}

		// Alte markierung wieder herstellen
		seasonComboBox.setSelectedItem(markierterPlan);

		if ((seasonComboBox.getSelectedIndex() < 0) && (seasonComboBox.getItemCount() > 0)) {
			seasonComboBox.setSelectedIndex(0);
		}

		// Aktuellen Spielplan bestimmen
		if (seasonComboBox.getSelectedItem() instanceof Spielplan) {
			this.model.setCurrentSeries((Spielplan) seasonComboBox.getSelectedItem());
		} else {
			this.model.setCurrentSeries(null);
		}

		// Alle Panels informieren
		informSaisonChange();
	}

	private void informSaisonChange() {
		seriesTable.changeSaison();
		seriesHistoryPanel.changeSaison();
		markierungInfo();
	}

	private void initComponents() {
		setLayout(new BorderLayout());

		// ComboBox für Saisonauswahl
		final JPanel panel = new ImagePanel(new BorderLayout());

		final JPanel toolbarPanel = new ImagePanel(null);
		seasonComboBox = new JComboBox();
		seasonComboBox.setToolTipText(HOVerwaltung.instance().getLanguageString(
				"tt_Ligatabelle_Saisonauswahl"));
		seasonComboBox.setSize(200, 25);
		seasonComboBox.setLocation(10, 5);
		toolbarPanel.add(seasonComboBox);

		deleteButton = new JButton(ThemeManager.getIcon(HOIconName.REMOVE));
		deleteButton.setToolTipText(HOVerwaltung.instance().getLanguageString(
				"tt_Ligatabelle_SaisonLoeschen"));
		deleteButton.setSize(25, 25);
		deleteButton.setLocation(220, 5);
		deleteButton.setBackground(ThemeManager.getColor(HOColorName.BUTTON_BG));
		toolbarPanel.add(deleteButton);

		printButton = new JButton(ThemeManager.getIcon(HOIconName.PRINTER));
		printButton.setToolTipText(HOVerwaltung.instance().getLanguageString(
				"tt_Ligatabelle_SaisonDrucken"));
		printButton.setSize(25, 25);
		printButton.setLocation(255, 5);
		toolbarPanel.add(printButton);

		toolbarPanel.setPreferredSize(new Dimension(240, 35));
		panel.add(toolbarPanel, BorderLayout.NORTH);

		final JPanel tablePanel = new ImagePanel(new BorderLayout());
		tablePanel.add(initLigaTabelle(), BorderLayout.NORTH);

		final JPanel historyPanel = new ImagePanel(new BorderLayout());
		historyPanel.add(initTabellenverlaufStatistik(), BorderLayout.NORTH);
		historyPanel.add(initSpielPlan(), BorderLayout.CENTER);

		tablePanel.add(historyPanel, BorderLayout.CENTER);

		panel.add(tablePanel, BorderLayout.CENTER);

		add(panel, BorderLayout.CENTER);
	}

	private Component initLigaTabelle() {
		seriesTable = new SeriesTablePanel(this.model);

		JScrollPane scrollpane = new JScrollPane(seriesTable);
		scrollpane.getViewport().setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);
		scrollpane.setPreferredSize(new Dimension((int) seriesTable.getPreferredSize().getWidth(),
				(int) seriesTable.getPreferredSize().getHeight() + 22));

		return scrollpane;
	}

	private Component initSpielPlan() {
		JLabel label = null;
		matchDayPanels = new MatchDayPanel[14];
		for (int i = 0; i < matchDayPanels.length; i++) {
			matchDayPanels[i] = new MatchDayPanel(this.model, i + 1);
		}

		final GridBagLayout layout = new GridBagLayout();
		final GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.NONE;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		constraints.gridy = 0;
		constraints.insets = new Insets(4, 4, 4, 4);

		final JPanel panel = new ImagePanel(layout);

		label = new JLabel();
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridheight = 7;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		layout.setConstraints(label, constraints);
		panel.add(label);

		for (int i = 0; i < 7; i++) {
			constraints.gridx = 1;
			constraints.gridy = i;
			constraints.gridheight = 1;
			layout.setConstraints(matchDayPanels[i], constraints);
			panel.add(matchDayPanels[i]);
		}

		label = new JLabel();
		constraints.gridx = 2;
		constraints.gridy = 0;
		constraints.gridheight = 7;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		layout.setConstraints(label, constraints);
		panel.add(label);

		for (int i = 7; i < matchDayPanels.length; i++) {
			constraints.gridx = 3;
			constraints.gridy = i - 7;
			constraints.gridheight = 1;
			layout.setConstraints(matchDayPanels[i], constraints);
			panel.add(matchDayPanels[i]);
		}

		label = new JLabel();
		constraints.gridx = 4;
		constraints.gridy = 0;
		constraints.gridheight = 7;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		layout.setConstraints(label, constraints);
		panel.add(label);

		final JScrollPane scrollpane = new JScrollPane(panel);
		scrollpane.getVerticalScrollBar().setBlockIncrement(100);
		scrollpane.getVerticalScrollBar().setUnitIncrement(20);
		scrollpane.getViewport().setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);
		return scrollpane;
	}

	private Component initTabellenverlaufStatistik() {
		seriesHistoryPanel = new SeriesHistoryPanel(this.model);

		final JPanel panel = new ImagePanel();
		panel.add(seriesHistoryPanel);

		final JScrollPane scrollpane = new JScrollPane(panel);
		scrollpane.getViewport().setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);
		scrollpane.setPreferredSize(new Dimension((int) seriesTable.getPreferredSize().getWidth(),
				(int) seriesTable.getPreferredSize().getHeight()));

		return scrollpane;
	}

	private void markierungInfo() {
		for (int i = 0; i < matchDayPanels.length; i++) {
			matchDayPanels[i].changeSaison();
		}
	}
}
