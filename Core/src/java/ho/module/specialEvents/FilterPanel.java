package ho.module.specialEvents;

import ho.core.datatype.CBItem;
import ho.core.gui.theme.HOIconName;
import ho.core.gui.theme.ThemeManager;
import ho.core.model.HOVerwaltung;
import ho.core.model.player.Spieler;
import ho.module.specialEvents.filter.Filter;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

public class FilterPanel extends JPanel {

	private static final long serialVersionUID = 6299290138063653349L;
	// matches
	private JCheckBox onlySEMatchesCheckBox;
	private JComboBox seasonComboBox;
	// match types
	private JCheckBox friendliesCheckBox;
	private JCheckBox leagueCheckBox;
	private JCheckBox relegationCheckBox;
	private JCheckBox tournamentCheckBox;
	private JCheckBox cupCheckBox;
	private JCheckBox mastersCheckBox;
	// SE types
	private JCheckBox specialitySECheckBox;
	private JCheckBox weatherSECheckBox;
	private JCheckBox counterAttackSECheckBox;
	private JCheckBox freeKickSECheckBox;
	private JCheckBox freeKickIndirectSECheckBox;
	private JCheckBox penaltySECheckBox;
	private JCheckBox longshotSECheckBox;
	// player
	private JComboBox playerComboBox;
	private JCheckBox currentPlayersCheckBox;
	private JCheckBox ownPlayersInvolvedCheckBox;
	private final Filter filter;

	public FilterPanel(Filter filter) {
		this.filter = filter;
		initComponents();
		initFromFilter();
		addListeners();
	}

	private void initFromFilter() {
		SeasonFilterValue period = this.filter.getSeasonFilterValue();
		CBItem itemToSelect = null;
		if (period != null) {
			ComboBoxModel comboModel = this.seasonComboBox.getModel();
			for (int i = 0; i < comboModel.getSize(); i++) {
				CBItem item = (CBItem) comboModel.getElementAt(i);
				if (item.getId() == period.getId()) {
					itemToSelect = item;
					break;
				}
			}
		}
		this.seasonComboBox.setSelectedItem(itemToSelect);

		this.onlySEMatchesCheckBox.setSelected(this.filter.isShowMatchesWithSEOnly());
		this.friendliesCheckBox.setSelected(this.filter.isShowFriendlies());
		this.leagueCheckBox.setSelected(this.filter.isShowLeague());
		this.relegationCheckBox.setSelected(this.filter.isShowRelegation());
		this.tournamentCheckBox.setSelected(this.filter.isShowTournament());
		this.cupCheckBox.setSelected(this.filter.isShowCup());
		this.mastersCheckBox.setSelected(this.filter.isShowMasters());
		this.specialitySECheckBox.setSelected(this.filter.isShowSpecialitySE());
		this.weatherSECheckBox.setSelected(this.filter.isShowWeatherSE());
		this.counterAttackSECheckBox.setSelected(this.filter.isShowCounterAttack());
		this.freeKickSECheckBox.setSelected(this.filter.isShowFreeKick());
		this.freeKickIndirectSECheckBox.setSelected(this.filter.isShowFreeKickIndirect());
		this.penaltySECheckBox.setSelected(this.filter.isShowPenalty());
		this.longshotSECheckBox.setSelected(this.filter.isShowLongShot());

		this.currentPlayersCheckBox.setSelected(this.filter.isShowCurrentPlayersOnly());
		updatePlayerComboBoxData(this.filter.isShowCurrentPlayersOnly());
		this.playerComboBox.setSelectedItem(null);
		this.ownPlayersInvolvedCheckBox.setSelected(this.filter.isShowOwnPlayersOnly());
	}

	private void initComponents() {
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.fill = GridBagConstraints.VERTICAL;

		add(createMatchFilterPanel(), gbc);
		gbc.gridx = 1;
		add(createMatchTypeFilterPanel(), gbc);
		gbc.gridx = 2;
		add(createSEFilterPanel(), gbc);
		gbc.gridx = 3;
		gbc.weightx = 1.0;
		add(createPlayerFilterPanel(), gbc);
	}

	private void addListeners() {

		this.seasonComboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CBItem item = (CBItem) seasonComboBox.getSelectedItem();
				if (item == null) {
					filter.setSeasonFilterValue(null);
				} else {
					filter.setSeasonFilterValue(SeasonFilterValue.getById(item.getId()));
				}
			}
		});

		ItemListener checkBoxListener = new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				Object source = e.getSource();
				boolean selected = e.getStateChange() == ItemEvent.SELECTED;

				if (source == onlySEMatchesCheckBox) {
					filter.setShowMatchesWithSEOnly(selected);
				} else if (source == friendliesCheckBox) {
					filter.setShowFriendlies(selected);
				} else if (source == leagueCheckBox) {
					filter.setShowLeague(selected);
				} else if (source == relegationCheckBox) {
					filter.setShowRelegation(selected);
				} else if (source == tournamentCheckBox) {
					filter.setShowTournament(selected);
				} else if (source == cupCheckBox) {
					filter.setShowCup(selected);
				} else if (source == mastersCheckBox) {
					filter.setShowMasters(selected);
				} else if (source == specialitySECheckBox) {
					filter.setShowSpecialitySE(selected);
				} else if (source == weatherSECheckBox) {
					filter.setShowWeatherSE(selected);
				} else if (source == counterAttackSECheckBox) {
					filter.setShowCounterAttack(selected);
				} else if (source == freeKickSECheckBox) {
					filter.setShowFreeKick(selected);
				} else if (source == freeKickIndirectSECheckBox) {
					filter.setShowFreeKickIndirect(selected);
				} else if (source == penaltySECheckBox) {
					filter.setShowPenalty(selected);
				} else if (source == longshotSECheckBox) {
					filter.setShowLongShot(selected);
				} else if (source == ownPlayersInvolvedCheckBox) {
					filter.setShowOwnPlayersOnly(selected);
				} else if (source == currentPlayersCheckBox) {
					filter.setShowCurrentPlayersOnly(selected);
					updatePlayerComboBoxData(selected);
				}
			}
		};

		this.onlySEMatchesCheckBox.addItemListener(checkBoxListener);
		this.friendliesCheckBox.addItemListener(checkBoxListener);
		this.leagueCheckBox.addItemListener(checkBoxListener);
		this.relegationCheckBox.addItemListener(checkBoxListener);
		this.tournamentCheckBox.addItemListener(checkBoxListener);
		this.cupCheckBox.addItemListener(checkBoxListener);
		this.mastersCheckBox.addItemListener(checkBoxListener);
		this.specialitySECheckBox.addItemListener(checkBoxListener);
		this.weatherSECheckBox.addItemListener(checkBoxListener);
		this.counterAttackSECheckBox.addItemListener(checkBoxListener);
		this.freeKickSECheckBox.addItemListener(checkBoxListener);
		this.freeKickIndirectSECheckBox.addItemListener(checkBoxListener);
		this.penaltySECheckBox.addItemListener(checkBoxListener);
		this.longshotSECheckBox.addItemListener(checkBoxListener);
		this.ownPlayersInvolvedCheckBox.addItemListener(checkBoxListener);
		this.currentPlayersCheckBox.addItemListener(checkBoxListener);

		this.playerComboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CBItem item = (CBItem) playerComboBox.getSelectedItem();
				if (item == null || item.getId() == -1 || item.getId() == -2) {
					filter.setPlayerId(null);
				} else if (item.getId() == -2) {
					playerComboBox.setSelectedItem(null);
					filter.setPlayerId(null);
				} else {
					filter.setPlayerId(Integer.valueOf(item.getId()));
				}
			}
		});

	}

	private JPanel createSEFilterPanel() {
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBorder(BorderFactory
				.createTitledBorder(getLangStr("specialEvents.filter.se.title")));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(4, 4, 4, 4);

		this.specialitySECheckBox = new JCheckBox();
		this.specialitySECheckBox.setText(getLangStr("SPECIALTYSE"));
		panel.add(this.specialitySECheckBox, gbc);

		this.weatherSECheckBox = new JCheckBox();
		this.weatherSECheckBox.setText(getLangStr("WEATHERSE"));
		gbc.gridy = 1;
		panel.add(this.weatherSECheckBox, gbc);

		this.counterAttackSECheckBox = new JCheckBox();
		this.counterAttackSECheckBox.setText(getLangStr("ls.match.event.counter-attack"));
		gbc.gridy = 2;
		panel.add(this.counterAttackSECheckBox, gbc);

		this.freeKickSECheckBox = new JCheckBox();
		this.freeKickSECheckBox.setText(getLangStr("highlight_freekick"));
		gbc.gridx = 1;
		gbc.gridy = 0;
		panel.add(this.freeKickSECheckBox, gbc);

		this.freeKickIndirectSECheckBox = new JCheckBox();
		this.freeKickIndirectSECheckBox.setText(getLangStr("highlight_freekick")
				+ getLangStr("indirect"));
		gbc.gridy = 1;
		panel.add(this.freeKickIndirectSECheckBox, gbc);

		this.penaltySECheckBox = new JCheckBox();
		this.penaltySECheckBox.setText(getLangStr("highlight_penalty"));
		gbc.gridy = 2;
		gbc.weighty = 1.0;
		panel.add(this.penaltySECheckBox, gbc);

		this.longshotSECheckBox = new JCheckBox();
		this.longshotSECheckBox.setText(getLangStr("ls.match.event.longshot"));
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		panel.add(this.longshotSECheckBox, gbc);

		return panel;
	}

	private JPanel createMatchFilterPanel() {
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBorder(BorderFactory
				.createTitledBorder(getLangStr("specialEvents.filter.matches.title")));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(4, 4, 4, 4);

		this.onlySEMatchesCheckBox = new JCheckBox();
		this.onlySEMatchesCheckBox.setText(getLangStr("SpieleMitSEs"));
		panel.add(this.onlySEMatchesCheckBox, gbc);

		CBItem[] comboItems = new CBItem[3];
		comboItems[0] = new CBItem(getLangStr("AktSaison"),
				SeasonFilterValue.CURRENT_SEASON.getId());
		comboItems[1] = new CBItem(getLangStr("2Saison"),
				SeasonFilterValue.LAST_TWO_SEASONS.getId());
		comboItems[2] = new CBItem(getLangStr("AllSeasons"), SeasonFilterValue.ALL_SEASONS.getId());
		this.seasonComboBox = new JComboBox(comboItems);
		gbc.gridy = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		panel.add(this.seasonComboBox, gbc);

		return panel;
	}

	private JPanel createMatchTypeFilterPanel() {
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBorder(BorderFactory
				.createTitledBorder(getLangStr("specialEvents.filter.matchTypes.title")));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(4, 4, 4, 4);

		this.friendliesCheckBox = new JCheckBox();
		this.friendliesCheckBox.setText(getLangStr("specialEvents.filter.matchTypes.friendly"));
		panel.add(this.friendliesCheckBox, gbc);

		this.leagueCheckBox = new JCheckBox();
		this.leagueCheckBox.setText(getLangStr("specialEvents.filter.matchTypes.league"));
		gbc.gridy = 1;
		panel.add(this.leagueCheckBox, gbc);

		this.relegationCheckBox = new JCheckBox();
		this.relegationCheckBox.setText(getLangStr("specialEvents.filter.matchTypes.relegation"));
		gbc.gridy = 2;
		panel.add(this.relegationCheckBox, gbc);

		this.cupCheckBox = new JCheckBox();
		this.cupCheckBox.setText(getLangStr("specialEvents.filter.matchTypes.cup"));
		gbc.gridy = 0;
		gbc.gridx = 1;
		panel.add(this.cupCheckBox, gbc);

		this.tournamentCheckBox = new JCheckBox();
		this.tournamentCheckBox.setText(getLangStr("specialEvents.filter.matchTypes.tournament"));
		gbc.gridy = 1;
		panel.add(this.tournamentCheckBox, gbc);

		this.mastersCheckBox = new JCheckBox();
		this.mastersCheckBox.setText(getLangStr("specialEvents.filter.matchTypes.masters"));
		gbc.gridy = 2;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		panel.add(this.mastersCheckBox, gbc);

		return panel;
	}

	private JPanel createPlayerFilterPanel() {
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBorder(BorderFactory
				.createTitledBorder(getLangStr("specialEvents.filter.players.title")));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(4, 4, 4, 4);

		JLabel playerLabel = new JLabel();
		playerLabel.setText(getLangStr("specialEvents.filter.players.player"));
		panel.add(playerLabel, gbc);

		this.playerComboBox = new JComboBox();
		this.playerComboBox.setRenderer(new ComboBoxRenderer(this.playerComboBox.getRenderer()));
		gbc.gridx = 1;
		panel.add(this.playerComboBox, gbc);

		this.currentPlayersCheckBox = new JCheckBox("current players");
		this.currentPlayersCheckBox
				.setText(getLangStr("specialEvents.filter.players.currentPlayers"));
		gbc.gridy = 1;
		panel.add(this.currentPlayersCheckBox, gbc);

		this.ownPlayersInvolvedCheckBox = new JCheckBox();
		this.ownPlayersInvolvedCheckBox
				.setText(getLangStr("specialEvents.filter.players.ownPlayers"));
		gbc.gridy = 2;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		panel.add(this.ownPlayersInvolvedCheckBox, gbc);

		return panel;
	}

	/**
	 * Convenience method.
	 * 
	 * @param key
	 * @return
	 */
	private String getLangStr(String key) {
		return HOVerwaltung.instance().getLanguageString(key);
	}

	private void updatePlayerComboBoxData(boolean currentPlayersOnly) {
		CBItem oldItem = (CBItem) this.playerComboBox.getSelectedItem();

		Comparator<Spieler> comparator = new Comparator<Spieler>() {

			@Override
			public int compare(Spieler o1, Spieler o2) {
				return o1.getName().compareTo(o2.getName());
			}
		};

		List<CBItem> playerItems = new ArrayList<CBItem>();
		List<Spieler> players = new ArrayList<Spieler>(HOVerwaltung.instance().getModel()
				.getAllSpieler());
		Collections.sort(players, comparator);
		for (Spieler player : players) {
			playerItems.add(new PlayerCBItem(player.getName(), player.getSpielerID(), player
					.getSpezialitaet()));
		}

		if (!currentPlayersOnly) {
			players = new ArrayList<Spieler>(HOVerwaltung.instance().getModel().getAllOldSpieler());
			Collections.sort(players, comparator);
			if (!players.isEmpty()) {
				playerItems.add(new PlayerCBItem("--------------------", -2, -1));
			}
			for (Spieler player : players) {
				playerItems.add(new PlayerCBItem(player.getName(), player.getSpielerID(), player
						.getSpezialitaet()));
			}
		}

		playerItems.add(0, new PlayerCBItem("", -1, -1));
		this.playerComboBox.setModel(new DefaultComboBoxModel(playerItems.toArray()));

		if (oldItem != null) {
			restorePlayerComboSelection(oldItem.getId());
		} else {
			this.playerComboBox.setSelectedItem(null);
		}
	}

	private void restorePlayerComboSelection(int playerId) {
		ComboBoxModel model = this.playerComboBox.getModel();
		CBItem item = null;
		for (int i = 0; i < model.getSize(); i++) {
			if (((CBItem) model.getElementAt(i)).getId() == playerId) {
				item = (CBItem) model.getElementAt(i);
				break;
			}
		}
		this.playerComboBox.setSelectedItem(item);
	}

	private class ComboBoxRenderer extends JLabel implements ListCellRenderer {

		private static final long serialVersionUID = 1148438406134827829L;
		private final ListCellRenderer delegate;

		public ComboBoxRenderer(ListCellRenderer delegate) {
			this.delegate = delegate;
		}

		/*
		 * This method finds the image and text corresponding to the selected
		 * value and returns the label, set up to display the text and image.
		 */
		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index,
				boolean isSelected, boolean cellHasFocus) {

			PlayerCBItem item = (PlayerCBItem) value;
			String text = (item != null) ? item.getText() : null;
			setText(text);

			Component component = this.delegate.getListCellRendererComponent(list, text, index,
					isSelected, cellHasFocus);

			if (component instanceof JLabel) {
				if (item != null && item.getId() >= 0) {
					if (item.getSpeciality() > 0) {
						((JLabel) component).setIcon(ThemeManager.getIcon(HOIconName.SPECIAL[item
								.getSpeciality()]));
					} else {
						((JLabel) component).setIcon(ThemeManager.getIcon(HOIconName.EMPTY));
					}
				} else {
					((JLabel) component).setIcon(null);
				}
			}

			return component;
		}
	}

	private class PlayerCBItem extends CBItem {

		private int speciality = -1;

		public PlayerCBItem(String text, int id, int speciality) {
			super(text, id);
			this.speciality = speciality;
		}

		public int getSpeciality() {
			return speciality;
		}
	}
}