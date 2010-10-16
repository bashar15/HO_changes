// %715342267:de.hattrickorganizer.gui.playeroverview%
package de.hattrickorganizer.gui.playeroverview;

import gui.UserParameter;

import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

import de.hattrickorganizer.database.DBZugriff;
import de.hattrickorganizer.gui.RefreshManager;
import de.hattrickorganizer.gui.model.PlayerOverviewModel;
import de.hattrickorganizer.gui.model.SpielerTableRenderer;
import de.hattrickorganizer.gui.model.UserColumn;
import de.hattrickorganizer.gui.model.UserColumnController;
import de.hattrickorganizer.gui.model.UserColumnFactory;
import de.hattrickorganizer.gui.utils.TableSorter;

/**
 * The main player table.
 * 
 * @author Thorsten Dietz
 */
public class PlayerOverviewTable extends JTable implements de.hattrickorganizer.gui.Refreshable {

	private static final long serialVersionUID = -6074136156090331418L;

	// ~ Instance fields
	// ----------------------------------------------------------------------------

	private PlayerOverviewModel m_clTableModel;
	private TableSorter m_clTableSorter;

	// ~ Constructors
	// -------------------------------------------------------------------------------

	public PlayerOverviewTable() {
		super();
		initModel();
		setDefaultRenderer(Object.class, new SpielerTableRenderer());
		setSelectionBackground(SpielerTableRenderer.SELECTION_BG);
		RefreshManager.instance().registerRefreshable(this);
	}

	/**
	 * Breite der BestPosSpalte zurückgeben
	 */
	public final int getBestPosWidth() {
		return getColumnModel().getColumn(
				getColumnModel().getColumnIndex(new Integer(m_clTableModel.getPositionInArray(UserColumnFactory.BEST_POSITION))))
				.getWidth();
	}

	public final TableSorter getSorter() {
		return m_clTableSorter;
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @return int[spaltenanzahl][2] mit 0=ModelIndex und 1=ViewIndex
	 */
	public final int[][] getSpaltenreihenfolge() {
		final int[][] reihenfolge = new int[m_clTableModel.getColumnCount()][2];

		for (int i = 0; i < m_clTableModel.getColumnCount(); i++) {
			// Modelindex
			reihenfolge[i][0] = i;
			// ViewIndex
			reihenfolge[i][1] = convertColumnIndexToView(i);
		}
		return reihenfolge;
	}

	public final void saveColumnOrder() {
		final UserColumn[] columns = m_clTableModel.getDisplayedColumns();
		final TableColumnModel tableColumnModel = getColumnModel();
		for (int i = 0; i < columns.length; i++) {
			columns[i].setIndex(convertColumnIndexToView(i));
			columns[i].setPreferredWidth(tableColumnModel.getColumn(convertColumnIndexToView(i)).getWidth());
		}
		m_clTableModel.setCurrentValueToColumns(columns);
		DBZugriff.instance().saveHOColumnModel(m_clTableModel);
	}

	public final void setSpieler(int spielerid) {
		final int index = m_clTableSorter.getRow4Spieler(spielerid);

		if (index >= 0) {
			this.setRowSelectionInterval(index, index);
		}
	}

	public final void reInit() {
		initModel();
		repaint();
	}

	public final void reInitModel() {
		((PlayerOverviewModel) getSorter().getModel()).reInitData();
	}

	public final void reInitModelHRFVergleich() {
		((PlayerOverviewModel) getSorter().getModel()).reInitDataHRFVergleich();
	}

	public final void refresh() {
		reInitModel();
		repaint();
	}

	public final void refreshHRFVergleich() {
		reInitModelHRFVergleich();
		repaint();
	}

	/**
	 * Gibt die Spalte für die Sortierung zurück
	 */
	private int getSortSpalte() {
		switch (UserParameter.instance().standardsortierung) {
		case UserParameter.SORT_NAME:
			return m_clTableModel.getPositionInArray(UserColumnFactory.NAME);

		case UserParameter.SORT_BESTPOS:
			return m_clTableModel.getPositionInArray(UserColumnFactory.BEST_POSITION);

		case UserParameter.SORT_AUFGESTELLT:
			return m_clTableModel.getPositionInArray(UserColumnFactory.LINUP);

		case UserParameter.SORT_GRUPPE:
			return m_clTableModel.getPositionInArray(UserColumnFactory.GROUP);

		case UserParameter.SORT_BEWERTUNG:
			return m_clTableModel.getPositionInArray(UserColumnFactory.RATING);

		default:
			return m_clTableModel.getPositionInArray(UserColumnFactory.BEST_POSITION);
		}
	}

	/**
	 * Initialisiert das Model
	 */
	private void initModel() {
		setOpaque(false);

		if (m_clTableModel == null) {
			m_clTableModel = UserColumnController.instance().getPlayerOverviewModel();
			m_clTableModel.setValues(de.hattrickorganizer.model.HOVerwaltung.instance().getModel().getAllSpieler());
			m_clTableSorter = new TableSorter(m_clTableModel, m_clTableModel.getDisplayedColumns().length - 1, getSortSpalte());

			final de.hattrickorganizer.gui.utils.ToolTipHeader header = new de.hattrickorganizer.gui.utils.ToolTipHeader(getColumnModel());
			header.setToolTipStrings(m_clTableModel.getTooltips());
			header.setToolTipText("");
			setTableHeader(header);

			setModel(m_clTableSorter);

			final TableColumnModel tableColumnModel = getColumnModel();

			for (int i = 0; i < m_clTableModel.getColumnCount(); i++) {
				tableColumnModel.getColumn(i).setIdentifier(new Integer(i));
			}

			int[][] targetColumn = m_clTableModel.getColumnOrder();// gui.UserParameter.instance().spieleruebersichtsspaltenreihenfolge;

			// Reihenfolge -> nach [][1] sortieren
			targetColumn = de.hattrickorganizer.tools.Helper.sortintArray(targetColumn, 1);

			if (targetColumn != null) {
				for (int i = 0; i < targetColumn.length; i++) {
					this.moveColumn(getColumnModel().getColumnIndex(new Integer(targetColumn[i][0])), targetColumn[i][1]);
				}
			}

			m_clTableSorter.addMouseListenerToHeaderInTable(this);
			m_clTableModel.setColumnsSize(getColumnModel());
		} else {
			// Werte neu setzen
			m_clTableModel.setValues(de.hattrickorganizer.model.HOVerwaltung.instance().getModel().getAllSpieler());
			m_clTableSorter.reallocateIndexes();
		}

		setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		setSelectionMode(0);
		setRowSelectionAllowed(true);
		m_clTableSorter.initsort();
	}
}
