// %1158570113:hoplugins.teamAnalyzer.comparator%
package hoplugins.teamAnalyzer.comparator;

import hoplugins.teamAnalyzer.vo.RosterPlayerData;

import java.util.Comparator;


/**
 * Comparator that orders based on number of performanceand rating as secondary parameter
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class RosterPlayerComparator implements Comparator {
    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Compare two objects
     *
     * @param o1
     * @param o2
     *
     * @return
     */
    public int compare(Object o1, Object o2) {
        int s1 = ((RosterPlayerData) o1).getMainPosition();
        int s2 = ((RosterPlayerData) o2).getMainPosition();

        if (s1 > s2) {
            return 1;
        }

        if (s2 > s1) {
            return -1;
        }

        double n1 = ((RosterPlayerData) o1).getMainRole().getMax();
        double n2 = ((RosterPlayerData) o2).getMainRole().getMax();

        if (n1 > n2) {
            return -1;
        }

        if (n2 > n1) {
            return 1;
        }

        return 0;
    }
}
