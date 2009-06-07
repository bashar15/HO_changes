// %3190202247:hoplugins.trainingExperience%
package hoplugins.trainingExperience;

import hoplugins.Commons;

import hoplugins.commons.utils.HTCalendar;
import hoplugins.commons.utils.HTCalendarFactory;

import hoplugins.trainingExperience.constants.Skills;
import hoplugins.trainingExperience.vo.PastSkillup;

import plugins.ISkillup;
import plugins.ISpieler;

import java.text.ParseException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;


/**
 * Class that keeps track of the past skillup for the active user
 */
public class OldTrainingManager {
    //~ Instance fields ----------------------------------------------------------------------------

    /** List of all skill up */
    private List allSkillups = new ArrayList();

    /** List of trained skill up */
    private List trainSkillups = new ArrayList();

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Calculates data for the player
     *
     * @param player
     */
    public OldTrainingManager(ISpieler player) {
        if (player == null) {
            return;
        }

        //TreeMap mapTrained = new TreeMap();
        //TreeMap mapAll = new TreeMap();
        allSkillups = new ArrayList();
        trainSkillups = new ArrayList();

        for (int skill = 0; skill < 10; skill++) {
        	// Skip Form ups
        	if (skill == ISpieler.SKILL_FORM)
        		continue;

            Vector v = player.getAllLevelUp(skill);
            int count = 0;

            for (Iterator iter = v.iterator(); iter.hasNext();) {
                Object[] element = (Object[]) iter.next();
                PastSkillup su = null;

                try {
                    Date htDate = Commons.getModel().getHelper().getHattrickDate("" //$NON-NLS-1$
                                                                                 + element[0]);
                    Date trainingDate = Commons.getModel().getHelper()
                                               .getLastTrainingDate(htDate,
                                                                    Commons.getModel().getXtraDaten()
                                                                           .getTrainingDate())
                                               .getTime();

                    su = getSkillup(trainingDate);
                    su.setValue(Skills.getSkillValue(player, skill) - count);
                    su.setType(skill);
                    su.setTrainType(ISkillup.SKILLUP_REAL);
                    allSkillups.add(su);

                    if (skill == ISpieler.SKILL_TORWART ||
                    		skill == ISpieler.SKILL_VERTEIDIGUNG ||
                    		skill == ISpieler.SKILL_FLUEGEL ||
                    		skill == ISpieler.SKILL_SPIELAUFBAU ||
                    		skill == ISpieler.SKILL_TORSCHUSS ||
                    		skill == ISpieler.SKILL_PASSSPIEL ||
                    		skill == ISpieler.SKILL_STANDARDS) {
                        trainSkillups.add(su);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                count++;
            }
        }

        SkillupComperator comp = new SkillupComperator();

        Collections.sort(allSkillups, comp);
        Collections.sort(trainSkillups, comp);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Returns the list of all calculated Skillups for the active player.
     *
     * @return list of all skillups
     */
    public List getAllSkillups() {
        return allSkillups;
    }

    /**
     * Returns the list of calculated Skillups for the active player as a result of training.
     *
     * @return list of trained skillups
     */
    public List getTrainedSkillups() {
        return trainSkillups;
    }

    /**
     * Calculates the HT Week and Season from the SkillupDate and initialize the Skillup Object
     *
     * @param skillupDate Skillup Date
     *
     * @return a skillup object with season and week value
     */
    private PastSkillup getSkillup(Date skillupDate) {
        HTCalendar calendar = HTCalendarFactory.createEconomyCalendar(Commons.getModel(),
                                                                      skillupDate);

        PastSkillup skillup = new PastSkillup();

        skillup.setHtSeason(calendar.getHTSeason());
        skillup.setHtWeek(calendar.getHTWeek());
        skillup.setDate(skillupDate);

        return skillup;
    }

    //~ Inner Classes ------------------------------------------------------------------------------

    /**
     * TODO Missing Class Documentation
     *
     * @author TODO Author Name
     */
    private class SkillupComperator implements Comparator {
        //~ Methods --------------------------------------------------------------------------------

        /**
         * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
         */
        public int compare(Object o1, Object o2) {
            ISkillup skillup1 = (ISkillup) o1;
            ISkillup skillup2 = (ISkillup) o2;

            if (skillup1.getDate().before(skillup2.getDate())) {
                return -1;
            } else if (skillup1.getDate().after(skillup2.getDate())) {
                return 1;
            } else {
                if (skillup1.getType() == skillup2.getType()) {
                    if (skillup1.getValue() > skillup2.getValue()) {
                        return 1;
                    } else {
                        return -1;
                    }
                }

                return 0;
            }
        }
    }
}