package ho.module.ifa;

import ho.core.gui.CursorToolkit;
import ho.core.model.HOVerwaltung;
import ho.core.module.config.ModuleConfig;
import ho.module.ifa.config.Config;
import ho.module.ifa.model.Country;
import ho.module.ifa.model.IfaModel;
import ho.module.ifa.table.IfaTableCellRenderer;
import ho.module.ifa.table.IfaTableModel;
import ho.module.ifa.table.SummaryTableSorter;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.RowSorter.SortKey;
import javax.swing.SortOrder;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class PluginIfaPanel extends JPanel {

	private static final long serialVersionUID = 3806181337290704445L;
	private JSplitPane splitPane;
	private IfaModel model;
	private boolean initialized = false;

	public PluginIfaPanel() {

		addHierarchyListener(new HierarchyListener() {

			@Override
			public void hierarchyChanged(HierarchyEvent e) {
				if ((HierarchyEvent.SHOWING_CHANGED == (e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) && isShowing())) {
					if (!initialized) {
						CursorToolkit.startWaitCursor(PluginIfaPanel.this);
						try {
							initialize();
						} finally {
							CursorToolkit.stopWaitCursor(PluginIfaPanel.this);
						}
					}
				}
			}
		});
	}

	private void initialize() {
		this.model = new IfaModel();
		initComponents();
		addListeners();
		this.initialized = true;
	}

	private void addListeners() {
		this.splitPane.addPropertyChangeListener("dividerLocation", new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (splitPane.getSize().height > 0 && splitPane.getDividerLocation() > 0) {
					double proportionalDividerLocation = 1.0 / ((double) splitPane.getSize().height / (double) splitPane
							.getDividerLocation());
					ModuleConfig.instance().setBigDecimal(
							Config.STATS_TABLES_DIVIDER_LOCATION.toString(),
							BigDecimal.valueOf(proportionalDividerLocation));
				}
			}
		});
	}

	private void initComponents() {
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		StatsPanel statsPanel = new StatsPanel(this.model);
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(10, 10, 3, 10);
		add(statsPanel, gbc);

		this.splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		this.splitPane.add(createTablePanel(true), 0);
		this.splitPane.add(createTablePanel(false), 1);
		gbc = new GridBagConstraints();
		gbc.gridy = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.BOTH;
		add(this.splitPane, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridheight = 2;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.BOTH;
		add(new RightPanel(this.model), gbc);

		validate();

		double dividerLocation = ModuleConfig
				.instance()
				.getBigDecimal(Config.STATS_TABLES_DIVIDER_LOCATION.toString(),
						BigDecimal.valueOf(0.5)).doubleValue();
		this.splitPane.setDividerLocation(dividerLocation);
	}

	private JPanel createTablePanel(boolean away) {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		JLabel headerLabel = new JLabel();
		if (away) {
			headerLabel.setText(HOVerwaltung.instance().getLanguageString(
					"ifa.statisticsTable.header.away"));
		} else {
			headerLabel.setText(HOVerwaltung.instance().getLanguageString(
					"ifa.statisticsTable.header.home"));
		}
		Font boldFont = headerLabel.getFont().deriveFont(
				headerLabel.getFont().getStyle() ^ Font.BOLD);
		headerLabel.setFont(boldFont);
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(10, 10, 3, 10);
		panel.add(headerLabel, gbc);

		final IfaTableModel tblModel = new IfaTableModel();
		tblModel.setData(this.model, away);
		JTable table = new JTable(tblModel);
		IfaTableCellRenderer renderer = new IfaTableCellRenderer();
		table.getColumnModel().getColumn(0).setCellRenderer(renderer);
		table.getColumnModel().getColumn(1).setCellRenderer(renderer);
		table.getColumnModel().getColumn(2).setCellRenderer(renderer);
		table.getColumnModel().getColumn(3).setCellRenderer(renderer);
		table.getColumnModel().getColumn(4).setCellRenderer(renderer);
		table.getColumnModel().getColumn(5).setCellRenderer(renderer);
		table.getColumnModel().getColumn(6).setCellRenderer(renderer);

		TableRowSorter<TableModel> sorter = new SummaryTableSorter<TableModel>(table.getModel());
		table.setRowSorter(sorter);
		sorter.setComparator(0, new Comparator<Country>() {

			@Override
			public int compare(Country o1, Country o2) {
				return o1.getName().compareTo(o2.getName());
			}

		});
		List<SortKey> list = new ArrayList<SortKey>();
		list.add(new SortKey(5, SortOrder.DESCENDING));
		sorter.setSortKeys(list);

		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridy = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.insets = new Insets(3, 10, 10, 10);
		panel.add(new JScrollPane(table), gbc);

		return panel;
	}
}
