package ho.module.specialEvents.filter;

import java.sql.SQLException;

import ho.core.module.config.ModuleConfig;
import ho.module.specialEvents.SeasonFilterValue;

public class FilterHelper {

	public static void loadSettings(Filter filter) {
		filter.setShowCounterAttack(ModuleConfig.instance().getBoolean(
				"SpecialEvents_ShowCounterAttack", Boolean.TRUE));
		filter.setShowCup(ModuleConfig.instance().getBoolean("SpecialEvents_ShowCup", Boolean.TRUE));
		filter.setShowCurrentPlayersOnly(ModuleConfig.instance().getBoolean(
				"SpecialEvents_ShowCurrentPlayersOnly", Boolean.FALSE));
		filter.setShowFreeKick(ModuleConfig.instance().getBoolean("SpecialEvents_ShowFreeKick",
				Boolean.TRUE));
		filter.setShowFreeKickIndirect(ModuleConfig.instance().getBoolean(
				"SpecialEvents_ShowFreeKickIndirect", Boolean.TRUE));
		filter.setShowFriendlies(ModuleConfig.instance().getBoolean("SpecialEvents_ShowFriendlies",
				Boolean.TRUE));
		filter.setShowLeague(ModuleConfig.instance().getBoolean("SpecialEvents_ShowLeague",
				Boolean.TRUE));
		filter.setShowLongShot(ModuleConfig.instance().getBoolean("SpecialEvents_ShowLongShot",
				Boolean.TRUE));
		filter.setShowMasters(ModuleConfig.instance().getBoolean("SpecialEvents_ShowMasters",
				Boolean.TRUE));
		filter.setShowMatchesWithSEOnly(ModuleConfig.instance().getBoolean(
				"SpecialEvents_ShowMatchesWithSEOnly", Boolean.TRUE));
		filter.setShowOwnPlayersOnly(ModuleConfig.instance().getBoolean(
				"SpecialEvents_ShowOwnPlayersOnly", Boolean.FALSE));
		filter.setShowPenalty(ModuleConfig.instance().getBoolean("SpecialEvents_ShowPenalty",
				Boolean.TRUE));
		filter.setShowRelegation(ModuleConfig.instance().getBoolean("SpecialEvents_ShowRelegation",
				Boolean.TRUE));
		filter.setShowSpecialitySE(ModuleConfig.instance().getBoolean(
				"SpecialEvents_ShowSpecialitySE", Boolean.TRUE));
		filter.setShowTournament(ModuleConfig.instance().getBoolean("SpecialEvents_ShowTournament",
				Boolean.TRUE));
		filter.setShowWeatherSE(ModuleConfig.instance().getBoolean("SpecialEvents_ShowWeatherSE",
				Boolean.TRUE));
		Integer sfId = ModuleConfig.instance().getInteger("SpecialEvents_SeasonFilterValue",
				SeasonFilterValue.LAST_TWO_SEASONS.getId());
		filter.setSeasonFilterValue(SeasonFilterValue.getById(sfId));
		filter.setPlayerId(ModuleConfig.instance().getInteger("SpecialEvents_PlayerId"));
		filter.setTactic(ModuleConfig.instance().getInteger("SpecialEvents_tactic"));
	}

	public static void saveSettings(Filter filter) {
		ModuleConfig.instance().setBoolean("SpecialEvents_ShowCounterAttack",
				filter.isShowCounterAttack());
		ModuleConfig.instance().setBoolean("SpecialEvents_ShowCup", filter.isShowCup());
		ModuleConfig.instance().setBoolean("SpecialEvents_ShowCurrentPlayersOnly",
				filter.isShowCurrentPlayersOnly());
		ModuleConfig.instance().setBoolean("SpecialEvents_ShowFreeKick", filter.isShowFreeKick());
		ModuleConfig.instance().setBoolean("SpecialEvents_ShowFreeKickIndirect",
				filter.isShowFreeKickIndirect());
		ModuleConfig.instance().setBoolean("SpecialEvents_ShowFriendlies",
				filter.isShowFriendlies());
		ModuleConfig.instance().setBoolean("SpecialEvents_ShowLeague", filter.isShowLeague());
		ModuleConfig.instance().setBoolean("SpecialEvents_ShowLongShot", filter.isShowLongShot());
		ModuleConfig.instance().setBoolean("SpecialEvents_ShowMasters", filter.isShowMasters());
		ModuleConfig.instance().setBoolean("SpecialEvents_ShowMatchesWithSEOnly",
				filter.isShowMatchesWithSEOnly());
		ModuleConfig.instance().setBoolean("SpecialEvents_ShowOwnPlayersOnly",
				filter.isShowOwnPlayersOnly());
		ModuleConfig.instance().setBoolean("SpecialEvents_ShowPenalty", filter.isShowPenalty());
		ModuleConfig.instance().setBoolean("SpecialEvents_ShowRelegation",
				filter.isShowRelegation());
		ModuleConfig.instance().setBoolean("SpecialEvents_ShowSpecialitySE",
				filter.isShowSpecialitySE());
		ModuleConfig.instance().setBoolean("SpecialEvents_ShowTournament",
				filter.isShowTournament());
		ModuleConfig.instance().setBoolean("SpecialEvents_ShowWeatherSE", filter.isShowWeatherSE());
		try {
			if (filter.getSeasonFilterValue() != null) {
				ModuleConfig.instance().setInteger("SpecialEvents_SeasonFilterValue",
						filter.getSeasonFilterValue().getId());
			} else {
				ModuleConfig.instance().removeKey("SpecialEvents_SeasonFilterValue");
			}
			if (filter.getPlayerId() != null) {
				ModuleConfig.instance().setInteger("SpecialEvents_PlayerId", filter.getPlayerId());
			} else {

				ModuleConfig.instance().removeKey("SpecialEvents_PlayerId");
			}
			if (filter.getPlayerId() != null) {
				ModuleConfig.instance().setInteger("SpecialEvents_PlayerId", filter.getPlayerId());
			} else {
				ModuleConfig.instance().removeKey("SpecialEvents_PlayerId");
			}
			if (filter.getTactic() != null) {
				ModuleConfig.instance().setInteger("SpecialEvents_tactic", filter.getTactic());
			} else {
				ModuleConfig.instance().removeKey("SpecialEvents_tactic");
			}
		} catch (SQLException e) {
			// the removeKey can throw an SQLException
			throw new RuntimeException(e);
		}
	}

}
