package ho.module.ifa;

import ho.core.gui.theme.ImageUtilities;
import ho.core.model.HOVerwaltung;
import ho.core.model.WorldDetailLeague;
import ho.core.model.WorldDetailsManager;
import ho.module.ifa.model.IfaModel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class FlagPanel extends JPanel {

	private static final long serialVersionUID = 6841405207630506680L;
	private FlagLabel[] flagLabels;
	private JLabel header;
	private JProgressBar percentState;

	public FlagPanel(boolean away, IfaModel ifaModel, FlagDisplayModel flagDisplayModel) {
		initialize(away, ifaModel, flagDisplayModel);
	}

	private void initialize(boolean away, IfaModel ifaModel, FlagDisplayModel flagDisplayModel) {
		createFlagLabels(away, ifaModel, flagDisplayModel);
		int totalCountryCount = WorldDetailsManager.instance().size();
		int playedCountryCount = (away) ? ifaModel.getVistedCountriesCount() : ifaModel
				.getHostedCountriesCount();

		setLayout(new GridBagLayout());
		setBackground(Color.white);

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.insets = new Insets(1, 1, 1, 1);
		constraints.weightx = 1.0;

		this.header = new JLabel("");
		this.header.setForeground(new Color(2522928));
		this.header.setHorizontalTextPosition(0);
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = flagDisplayModel.getFlagWidth();
		add(this.header, constraints);

		this.percentState = new JProgressBar();
		this.percentState.setMaximum(totalCountryCount);
		this.percentState.setValue(playedCountryCount);
		this.percentState.setPreferredSize(new Dimension(100, 14));
		this.percentState.setFont(new Font("Verdana", 1, 8));
		this.percentState.setString(playedCountryCount + "/" + totalCountryCount + " ("
				+ (int) (100.0D * this.percentState.getPercentComplete()) + "%)");
		this.percentState.setStringPainted(true);
		this.percentState.setBorder(BorderFactory.createLineBorder(Color.black));
		constraints.insets = new Insets(1, 1, 5, 1);
		constraints.gridy = 1;
		add(this.percentState, constraints);

		constraints.fill = GridBagConstraints.NONE;
		constraints.insets = new Insets(1, 1, 1, 1);
		constraints.weightx = 0.0;
		constraints.gridwidth = 1;

		if (this.flagLabels != null) {
			for (int i = 0; i < this.flagLabels.length; i++) {
				constraints.gridx = i % flagDisplayModel.getFlagWidth();
				constraints.gridy = 2 + i / flagDisplayModel.getFlagWidth();
				add(this.flagLabels[i], constraints);
			}
		}
	}

	void setHeaderText(String header) {
		this.header.setText(header);
	}

	void setHeaderVisible(boolean enable) {
		this.header.setVisible(enable);
		this.percentState.setVisible(enable);
	}

	private void createFlagLabels(boolean away, IfaModel ifaModel, FlagDisplayModel flagDisplayModel) {
		WorldDetailLeague[] leagues = WorldDetailsManager.instance().getLeagues();
		this.flagLabels = new FlagLabel[leagues.length];
		for (int i = 0; i < leagues.length; i++) {
			FlagLabel flagLabel = new FlagLabel(flagDisplayModel);
			flagLabel.setCountryId(leagues[i].getCountryId());
			flagLabel.setCountryName(leagues[i].getCountryName());
			flagLabel.setIcon(ImageUtilities.getFlagIcon(flagLabel.getCountryId()));
			flagLabel.setToolTipText(flagLabel.getCountryName());

			if (leagues[i].getLeagueId() == HOVerwaltung.instance().getModel().getBasics()
					.getLiga()) {
				flagLabel.setHomeCountry(true);
			} else {
				if ((away && ifaModel.isVisited(leagues[i].getCountryId()))
						|| (!away && ifaModel.isHosted(leagues[i].getCountryId()))) {
					flagLabel.setEnabled(true);
				} else {
					flagLabel.setEnabled(false);
				}
			}
			this.flagLabels[i] = flagLabel;
		}
		Arrays.sort(this.flagLabels, new Comparator<FlagLabel>() {

			@Override
			public int compare(FlagLabel l1, FlagLabel l2) {
				return l1.getCountryName().compareTo(l2.getCountryName());
			}
		});
	}
}
