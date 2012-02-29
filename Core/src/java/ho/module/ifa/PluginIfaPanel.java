package ho.module.ifa;

import ho.core.db.DBManager;
import ho.core.file.xml.XMLManager;
import ho.core.model.HOVerwaltung;
import ho.core.model.WorldDetailLeague;
import ho.core.model.WorldDetailsManager;
import ho.core.net.MyConnector;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class PluginIfaPanel extends JPanel {
	private static final long serialVersionUID = 6250843484613905192L;
	private ImageDesignPanel imageDesignPanel;
	private StatisticScrollPanel statisticScrollPanelAway;
	private StatisticScrollPanel statisticScrollPanelHome;
	private JPanel toolbarPanel;
	private JButton refreshButton = new JButton(HOVerwaltung.instance().getLanguageString("Refresh"));
	private JTabbedPane tabbedPane;
	
	public PluginIfaPanel() {
		initialize();
	}

	private void initialize() {
		imageDesignPanel = new ImageDesignPanel(this);
		this.statisticScrollPanelAway = new StatisticScrollPanel(false);
		this.statisticScrollPanelHome = new StatisticScrollPanel(true);
		setLayout(new BorderLayout());
		add(getToolbar(),BorderLayout.NORTH);
		add(getTabbedPane(),BorderLayout.CENTER);
		
	}

	
	private JTabbedPane getTabbedPane(){
		if(tabbedPane == null){
			tabbedPane = new JTabbedPane();
			tabbedPane.addTab(HOVerwaltung.instance().getLanguageString("AutoFilterPanel.Home_Games"), statisticScrollPanelHome);
			tabbedPane.addTab(HOVerwaltung.instance().getLanguageString("AutoFilterPanel.Away_Games"), statisticScrollPanelAway);
			//tabbedPane.addTab(HOVerwaltung.instance().getLanguageString("ImageBuilder"), imageDesignPanel);
		}
		return tabbedPane;
	}
	

	public StatisticScrollPanel getStatisticScrollPanelAway() {
		return this.statisticScrollPanelAway;
	}

	public StatisticScrollPanel getStatisticScrollPanelHome() {
		return this.statisticScrollPanelHome;
	}
	
	public JPanel getToolbar(){
		if(toolbarPanel == null){
			toolbarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			refreshButton.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					String worldDetails;
					try {
						worldDetails = MyConnector.instance().getWorldDetails(0);
						WorldDetailLeague[] leagues =XMLManager.instance().parseWorldDetails(worldDetails);
						DBManager.instance().saveWorldDetailLeagues(leagues);
						WorldDetailsManager.instance().refresh();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
					PluginIfaUtils.updateMatchesTable();
					imageDesignPanel.refreshFlagPanel();
					getStatisticScrollPanelHome().refresh();
					getStatisticScrollPanelAway().refresh();
					
				}
			});
			toolbarPanel.add(refreshButton);
		}
		return toolbarPanel;
		}
}
