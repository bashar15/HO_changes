// %4112883594:de.hattrickorganizer.gui.matches%
package ho.module.matches;

import ho.core.gui.comp.panel.LazyImagePanel;
import ho.core.gui.theme.HOColorName;
import ho.core.gui.theme.HOIconName;
import ho.core.gui.theme.ThemeManager;
import ho.core.model.HOVerwaltung;
import ho.core.model.match.IMatchHighlight;
import ho.core.model.match.MatchHighlight;
import ho.core.model.match.MatchKurzInfo;
import ho.core.model.match.Matchdetails;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Zeigt die Stärken eines Matches an.
 */
public class SpielHighlightPanel extends LazyImagePanel {

	private static final long serialVersionUID = -6491501224900464573L;
	private GridBagConstraints constraints;
	private GridBagLayout layout;
	private JLabel gastTeamNameLabel;
	private JLabel gastTeamToreLabel;
	private JLabel heimTeamNameLabel;
	private JLabel heimTeamToreLabel;
	private JPanel panel;
	private List<Component> highlightLabels;
	private final MatchesModel matchesModel;

	/**
	 * Creates a new SpielHighlightPanel object.
	 */
	public SpielHighlightPanel(MatchesModel matchesModel) {
		this(matchesModel, false);
	}

	/**
	 * Creates a new SpielHighlightPanel object.
	 * 
	 * @param print
	 *            if true: use printer version (no colored background)
	 */
	public SpielHighlightPanel(MatchesModel matchesModel, boolean print) {
		super(print);
		this.matchesModel = matchesModel;
	}

	@Override
	protected void initialize() {
		initComponents();
		addListeners();
		setNeedsRefresh(true);
	}

	@Override
	protected void update() {
		MatchKurzInfo info = this.matchesModel.getMatch();
		if (info == null) {
			clear();
			return;
		}

		if (info.getMatchStatus() != MatchKurzInfo.FINISHED) {
			clear();
		}
		
		heimTeamNameLabel.setText(info.getHeimName());
		gastTeamNameLabel.setText(info.getGastName());

		int teamid = HOVerwaltung.instance().getModel().getBasics().getTeamId();
		if (info.getHeimID() == teamid) {
			heimTeamNameLabel.setForeground(ThemeManager.getColor(HOColorName.TEAM_FG));
		} else {
			heimTeamNameLabel.setForeground(ThemeManager.getColor(HOColorName.LABEL_FG));
		}

		if (info.getGastID() == teamid) {
			gastTeamNameLabel.setForeground(ThemeManager.getColor(HOColorName.TEAM_FG));
		} else {
			gastTeamNameLabel.setForeground(ThemeManager.getColor(HOColorName.LABEL_FG));
		}

		if (info.getMatchStatus() == MatchKurzInfo.FINISHED) {
			removeHighlights(); 
			
			Matchdetails details = this.matchesModel.getDetails();
			heimTeamToreLabel.setText(info.getHeimTore() + " (" + details.getHomeHalfTimeGoals()
					+ ") ");
			gastTeamToreLabel.setText(info.getGastTore() + " (" + details.getGuestHalfTimeGoals()
					+ ") ");

			// Highlights anzeigen
			JLabel playerlabel = null;
			JLabel resultlabel = null;

			List<MatchHighlight> matchHighlights = details.getHighlights();

			for (int i = 0; i < matchHighlights.size(); i++) {
				MatchHighlight highlight = matchHighlights.get(i);

				// Label vorbereiten
				ImageIcon icon = MatchesHelper.getImageIcon4SpielHighlight(highlight);

				// Soll Highlight auch angezeigt werden? (Nur wenn Grafik
				// vorhanden ist)
				if (icon != null) {
					// Spielername
					String spielername = highlight.getSpielerName();
					if (spielername.length() > 30) {
						spielername = spielername.substring(0, 29);
					}

					spielername += (" (" + highlight.getMinute() + ".)");
					playerlabel = new JLabel(spielername, icon, SwingConstants.LEFT);
					playerlabel.setForeground(MatchesHelper.getColor4SpielHighlight(
							highlight.getHighlightTyp(), highlight.getHighlightSubTyp()));
					if (highlight.isWeatherSEHighlight()) {
						playerlabel.setToolTipText(removeHtml(highlight.getEventText()));
					} else {
						playerlabel.setToolTipText(MatchHighlight.getTooltiptext(
								highlight.getHighlightTyp(), highlight.getHighlightSubTyp()));
					}

					// Steht Müll drin!
					if (highlight.getHighlightTyp() == IMatchHighlight.HIGHLIGHT_ERFOLGREICH) {
						resultlabel = new JLabel(highlight.getHeimTore() + " : "
								+ highlight.getGastTore());
					} else {
						resultlabel = new JLabel("");
					}

					// Labels in den Highlightvector hinzufügen
					highlightLabels.add(playerlabel);
					highlightLabels.add(resultlabel);

					// Heimaktion
					if (highlight.getTeamID() == info.getHeimID()) {
						constraints.anchor = GridBagConstraints.WEST;
						constraints.fill = GridBagConstraints.HORIZONTAL;
						constraints.weightx = 1.0;
						constraints.gridx = 2;
						constraints.gridy = i + 4;
						constraints.gridwidth = 2;
						layout.setConstraints(playerlabel, constraints);
						panel.add(playerlabel);
					} else {
						constraints.anchor = GridBagConstraints.WEST;
						constraints.fill = GridBagConstraints.HORIZONTAL;
						constraints.weightx = 1.0;
						constraints.gridx = 5;
						constraints.gridy = i + 4;
						constraints.gridwidth = 2;
						layout.setConstraints(playerlabel, constraints);
						panel.add(playerlabel);
					}

					constraints.anchor = GridBagConstraints.EAST;
					constraints.fill = GridBagConstraints.HORIZONTAL;
					constraints.weightx = 1.0;
					constraints.gridx = 8;
					constraints.gridy = i + 4;
					constraints.gridwidth = 1;
					layout.setConstraints(resultlabel, constraints);
					panel.add(resultlabel);
				}
			}

			// --updaten--
			// Sterne für Sieger!
			if (info.getMatchStatus() != MatchKurzInfo.FINISHED) {
				heimTeamNameLabel.setIcon(null);
				gastTeamNameLabel.setIcon(null);
			} else if (info.getHeimTore() > info.getGastTore()) {
				heimTeamNameLabel.setIcon(ThemeManager.getTransparentIcon(HOIconName.STAR,
						Color.WHITE));
				gastTeamNameLabel.setIcon(null);
			} else if (info.getHeimTore() < info.getGastTore()) {
				heimTeamNameLabel.setIcon(null);
				gastTeamNameLabel.setIcon(ThemeManager.getTransparentIcon(HOIconName.STAR,
						Color.WHITE));
			} else {
				heimTeamNameLabel.setIcon(ThemeManager.getTransparentIcon(HOIconName.STAR_GRAY,
						Color.WHITE));
				gastTeamNameLabel.setIcon(ThemeManager.getTransparentIcon(HOIconName.STAR_GRAY,
						Color.WHITE));
			}
		}
	}

	private void addListeners() {
		this.matchesModel.addMatchModelChangeListener(new MatchModelChangeListener() {

			@Override
			public void matchChanged() {
				setNeedsRefresh(true);
			}
		});
	}

	private void initComponents() {
		highlightLabels = new ArrayList<Component>();

		setBackground(ThemeManager.getColor(HOColorName.PANEL_BG));

		GridBagLayout mainlayout = new GridBagLayout();
		GridBagConstraints mainconstraints = new GridBagConstraints();
		mainconstraints.anchor = GridBagConstraints.NORTH;
		mainconstraints.fill = GridBagConstraints.HORIZONTAL;
		mainconstraints.weighty = 0.1;
		mainconstraints.weightx = 1.0;
		mainconstraints.insets = new Insets(4, 6, 4, 6);

		setLayout(mainlayout);

		layout = new GridBagLayout();
		constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.NORTH;
		constraints.weighty = 0.0;
		constraints.weightx = 1.0;
		constraints.insets = new Insets(5, 3, 2, 2);

		panel = new JPanel(layout);
		panel.setBorder(BorderFactory.createLineBorder(ThemeManager
				.getColor(HOColorName.PANEL_BORDER)));
		panel.setBackground(ThemeManager.getColor(HOColorName.PANEL_BG));

		// Platzhalter
		JLabel label = new JLabel("   ");
		constraints.anchor = GridBagConstraints.WEST;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 0.0;
		constraints.gridx = 4;
		constraints.gridy = 1;
		constraints.gridheight = 30;
		constraints.gridwidth = 1;
		layout.setConstraints(label, constraints);
		panel.add(label);

		label = new JLabel(HOVerwaltung.instance().getLanguageString("Heim"));
		label.setFont(label.getFont().deriveFont(Font.BOLD, label.getFont().getSize() + 1));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 0.0;
		constraints.gridx = 2;
		constraints.gridy = 1;
		constraints.gridwidth = 2;
		constraints.gridheight = 1;
		layout.setConstraints(label, constraints);
		panel.add(label);

		label = new JLabel(HOVerwaltung.instance().getLanguageString("Gast"));
		label.setFont(label.getFont().deriveFont(Font.BOLD, label.getFont().getSize() + 1));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 0.0;
		constraints.gridx = 5;
		constraints.gridy = 1;
		constraints.gridwidth = 2;
		layout.setConstraints(label, constraints);
		panel.add(label);

		constraints.anchor = GridBagConstraints.WEST;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 1.0;
		constraints.gridx = 2;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		heimTeamNameLabel = new JLabel();
		heimTeamNameLabel.setPreferredSize(new Dimension(140, 14));
		heimTeamNameLabel.setFont(heimTeamNameLabel.getFont().deriveFont(Font.BOLD));
		layout.setConstraints(heimTeamNameLabel, constraints);
		panel.add(heimTeamNameLabel);

		constraints.anchor = GridBagConstraints.EAST;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 0.0;
		constraints.gridx = 3;
		constraints.gridy = 2;
		heimTeamToreLabel = new JLabel();
		heimTeamToreLabel.setFont(heimTeamToreLabel.getFont().deriveFont(Font.BOLD));
		layout.setConstraints(heimTeamToreLabel, constraints);
		panel.add(heimTeamToreLabel);

		constraints.anchor = GridBagConstraints.WEST;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 1.0;
		constraints.gridx = 5;
		constraints.gridy = 2;
		gastTeamNameLabel = new JLabel();
		gastTeamNameLabel.setPreferredSize(new Dimension(140, 14));
		gastTeamNameLabel.setFont(gastTeamNameLabel.getFont().deriveFont(Font.BOLD));
		layout.setConstraints(gastTeamNameLabel, constraints);
		panel.add(gastTeamNameLabel);

		constraints.anchor = GridBagConstraints.EAST;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 0.0;
		constraints.gridx = 6;
		constraints.gridy = 2;
		gastTeamToreLabel = new JLabel();
		gastTeamToreLabel.setFont(gastTeamToreLabel.getFont().deriveFont(Font.BOLD));
		layout.setConstraints(gastTeamToreLabel, constraints);
		panel.add(gastTeamToreLabel);

		// Platzhalter
		constraints.anchor = GridBagConstraints.EAST;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 0.0;
		constraints.gridx = 7;
		constraints.gridy = 2;
		label = new JLabel("    ");
		layout.setConstraints(label, constraints);
		panel.add(label);

		constraints.anchor = GridBagConstraints.EAST;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 0.0;
		constraints.gridx = 0;
		constraints.gridy = 3;
		label = new JLabel(" ");
		layout.setConstraints(label, constraints);
		panel.add(label);

		mainconstraints.gridx = 0;
		mainconstraints.gridy = 0;
		mainlayout.setConstraints(panel, mainconstraints);
		add(panel);

		clear();
	}

	/**
	 * Clear all highlights.
	 */
	private void clear() {
		removeHighlights();

		heimTeamNameLabel.setText(" ");
		gastTeamNameLabel.setText(" ");
		heimTeamToreLabel.setText(" ");
		gastTeamToreLabel.setText(" ");
		heimTeamNameLabel.setIcon(null);
		gastTeamNameLabel.setIcon(null);
	}

	private void removeHighlights() {
		for (Component c : this.highlightLabels) {
			panel.remove(c);
		}
		this.highlightLabels.clear();
	}

	/**
	 * Strip HTML from text.
	 */
	private String removeHtml(String in) {
		if (in == null)
			return in;
		else
			return in.replaceAll("<.*?>", "");
	}
}
