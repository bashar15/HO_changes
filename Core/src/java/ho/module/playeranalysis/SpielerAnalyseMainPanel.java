// %222653727:de.hattrickorganizer.gui.playeranalysis%
package ho.module.playeranalysis;

import ho.core.gui.ApplicationClosingListener;
import ho.core.gui.HOMainFrame;
import ho.core.gui.comp.panel.ImagePanel;
import ho.core.gui.comp.panel.LazyImagePanel;
import ho.core.gui.theme.HOIconName;
import ho.core.gui.theme.ThemeManager;
import ho.core.model.UserParameter;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class SpielerAnalyseMainPanel extends LazyImagePanel {

	private static final long serialVersionUID = 5384638406362299060L;
	private JButton arrangeButton;
	private JSplitPane splitPane;
	private SpielerAnalysePanel playersPanel1;
	private SpielerAnalysePanel playersPanel2;

	public final void setSpieler4Bottom(int spielerid) {
		if (this.playersPanel2 != null) {
			playersPanel2.setAktuelleSpieler(spielerid);
		}
	}

	public final void setSpieler4Top(int spielerid) {
		if (this.playersPanel1 != null) {
			playersPanel1.setAktuelleSpieler(spielerid);
		}
	}

	@Override
	protected void initialize() {
		initComponents();
		addListeners();
	}

	@Override
	protected void update() {
		// nothing to do here
	}

	private void addListeners() {
		HOMainFrame.instance().addApplicationClosingListener(new ApplicationClosingListener() {

			@Override
			public void applicationClosing() {
				saveSettings();
			}
		});

		arrangeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (splitPane.getOrientation() == JSplitPane.VERTICAL_SPLIT) {
					splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
				} else {
					splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
				}

				UserParameter.instance().spieleranalyseVertikal = !UserParameter.instance().spieleranalyseVertikal;
			}
		});
	}

	private void saveSettings() {
		UserParameter parameter = UserParameter.instance();
		parameter.spielerAnalysePanel_horizontalSplitPane = splitPane.getDividerLocation();
		playersPanel1.saveColumnOrder();
		playersPanel2.saveColumnOrder();
	}

	private void initComponents() {
		setLayout(new BorderLayout());

		JPanel panel = new ImagePanel(new BorderLayout());

		arrangeButton = new JButton(ThemeManager.getIcon(HOIconName.TURN));
		arrangeButton.setToolTipText(ho.core.model.HOVerwaltung.instance().getLanguageString(
				"tt_SpielerAnalyse_drehen"));
		arrangeButton.setPreferredSize(new Dimension(24, 24));
		panel.add(arrangeButton, BorderLayout.WEST);
		panel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

		add(panel, BorderLayout.NORTH);

		playersPanel1 = new SpielerAnalysePanel(1);
		playersPanel2 = new SpielerAnalysePanel(2);
		splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, playersPanel1, playersPanel2);
		splitPane
				.setDividerLocation(UserParameter.instance().spielerAnalysePanel_horizontalSplitPane);
		add(splitPane, BorderLayout.CENTER);

		if (!UserParameter.instance().spieleranalyseVertikal) {
			splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		} else {
			splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		}
	}
}
