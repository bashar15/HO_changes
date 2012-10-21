package ho.module.specialEvents;

import ho.core.model.match.MatchHighlight;
import ho.core.model.match.Weather;

import java.util.Date;

public class Match {

	private Date matchDate;
	private int matchId;
	private int hostingTeamTactic;
	private int visitingTeamTactic;
	private int hostingTeamId;
	private int visitingTeamId;
	private Weather weather;
	private String hostingTeam;
	private String visitingTeam;
	private String matchResult;
	private MatchHighlight matchHighlight;

	public Date getMatchDate() {
		return matchDate;
	}

	public void setMatchDate(Date matchDate) {
		this.matchDate = matchDate;
	}

	public int getMatchId() {
		return matchId;
	}

	public void setMatchId(int matchId) {
		this.matchId = matchId;
	}

	public int getHostingTeamTactic() {
		return hostingTeamTactic;
	}

	public void setHostingTeamTactic(int hostingTeamTactic) {
		this.hostingTeamTactic = hostingTeamTactic;
	}

	public int getVisitingTeamTactic() {
		return visitingTeamTactic;
	}

	public void setVisitingTeamTactic(int visitingTeamTactic) {
		this.visitingTeamTactic = visitingTeamTactic;
	}

	public String getHostingTeam() {
		return hostingTeam;
	}

	public void setHostingTeam(String hostingTeam) {
		this.hostingTeam = hostingTeam;
	}

	public String getVisitingTeam() {
		return visitingTeam;
	}

	public void setVisitingTeam(String visitingTeam) {
		this.visitingTeam = visitingTeam;
	}

	public String getMatchResult() {
		return matchResult;
	}

	public void setMatchResult(String matchResult) {
		this.matchResult = matchResult;
	}

	public MatchHighlight getMatchHighlight() {
		return matchHighlight;
	}

	public void setMatchHighlight(MatchHighlight matchHighlight) {
		this.matchHighlight = matchHighlight;
	}

	public Weather getWeather() {
		return weather;
	}

	public void setWeather(Weather weather) {
		this.weather = weather;
	}

	public int getHostingTeamId() {
		return hostingTeamId;
	}

	public void setHostingTeamId(int hostingTeamId) {
		this.hostingTeamId = hostingTeamId;
	}

	public int getVisitingTeamId() {
		return visitingTeamId;
	}

	public void setVisitingTeamId(int visitingTeamId) {
		this.visitingTeamId = visitingTeamId;
	}
}
