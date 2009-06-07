// %3449686638:hoplugins.teamAnalyzer.manager%
package hoplugins.teamAnalyzer.manager;

import hoplugins.Commons;

import hoplugins.commons.utils.ListUtil;

import hoplugins.teamAnalyzer.SystemManager;
import hoplugins.teamAnalyzer.comparator.MatchComparator;
import hoplugins.teamAnalyzer.vo.Match;

import plugins.IMatchKurzInfo;
import plugins.ISpielePanel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class MatchManager {
    //~ Static fields/initializers -----------------------------------------------------------------

    private static MatchList matches = null;

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static List getAllMatches() {
        return matches.getMatches();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static List getMatchDetails() {
        List filteredMatches = getSelectedMatches();
        MatchPopulator matchPopulator = new MatchPopulator();

        return matchPopulator.populate(filteredMatches);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static List getSelectedMatches() {
        if (matches == null) {
            loadActiveTeamMatchList();
        }

        return matches.filterMatches(SystemManager.getFilter());
    }

    /**
     * TODO Missing Method Documentation
     */
    public static void clean() {
        loadActiveTeamMatchList();
    }

    /**
     * TODO Missing Method Documentation
     */
    public static void loadActiveTeamMatchList() {
        matches = new MatchList();

        SortedSet sortedMatches = loadMatchList();

        for (Iterator iter = sortedMatches.iterator(); iter.hasNext();) {
            Match element = (Match) iter.next();

            matches.addMatch(element);
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private static List getTeamMatch() {
        List teamMatches = new ArrayList();
        String oldName = SystemManager.getActiveTeamName();

        IMatchKurzInfo[] matchKurtzInfo = Commons.getModel().getMatchesKurzInfo(SystemManager
                                                                                .getActiveTeamId(),
                                                                                ISpielePanel.NUR_EIGENE_SPIELE,
                                                                                false);

        for (int i = 0; i < matchKurtzInfo.length; i++) {
            IMatchKurzInfo matchInfo = matchKurtzInfo[i];

            if (matchInfo.getMatchStatus() != IMatchKurzInfo.FINISHED) {
                continue;
            }

            Match match = new Match(matchInfo);

            String temp;

            if (match.getHomeId() == SystemManager.getActiveTeamId()) {
                temp = match.getHomeTeam();
            } else {
                temp = match.getAwayTeam();
            }

            if (SystemManager.getConfig().isCheckTeamName()) {
                // Fix for missing last dot!
                String oldShort = oldName.substring(0, oldName.length() - 1);

                if (oldShort.equalsIgnoreCase(temp)) {
                    temp = oldName;
                }

                if (!temp.equalsIgnoreCase(oldName)) {
                    if (match.getWeek() > 14) {
                        oldName = temp;
                    } else {
                        return teamMatches;
                    }
                }
            }

            teamMatches.add(match);
        }

        return teamMatches;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private static SortedSet loadMatchList() {
        Map matchIds = new HashMap();

        for (Iterator iter = getTeamMatch().iterator(); iter.hasNext();) {
            Match match = (Match) iter.next();

            if (!matchIds.containsKey(match.getMatchId() + "")) {
                matchIds.put(match.getMatchId() + "", match);
            }
        }

        Collection matchList = matchIds.values();
        SortedSet sorted = ListUtil.getSortedSet(matchList, new MatchComparator());

        return sorted;
    }
}