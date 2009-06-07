// %3124307367:hoplugins.teamAnalyzer.vo%
package hoplugins.teamAnalyzer.vo;

import java.util.ArrayList;
import java.util.List;


/**
 * Filter Object class that holds the user settings for selecting analyzed matches
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class Filter {
    //~ Instance fields ----------------------------------------------------------------------------

    /** List of manually selected match ids */
    private List matches;

    /** Automatic or manual selection enabled */
    private boolean automatic;

    /** Consider away games */
    private boolean awayGames;

    /** Consider cup games */
    private boolean cup;

    /** Consider lost games */
    private boolean defeat;

    /** Consider draw games */
    private boolean draw;

    /** Consider friendly games */
    private boolean friendly;

    /** Consider home games */
    private boolean homeGames;

    /** Consider league games */
    private boolean league;

    /** Consider qualifier games */
    private boolean qualifier;

    /** Consider won games */
    private boolean win;

    /** Maximum number of games */
    private int number;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new Filter object.
     */
    public Filter() {
        automatic = true;
        setNumber(10);
        setAwayGames(true);
        setHomeGames(true);
        setWin(true);
        setDefeat(true);
        setDraw(true);
        setLeague(true);
        setCup(false);
        setFriendly(false);
        setQualifier(false);
        matches = new ArrayList();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param b
     */
    public void setAutomatic(boolean b) {
        automatic = b;
    }

    /**
     * DOCUMENT ME!
     *
     * @return
     */
    public boolean isAutomatic() {
        return automatic;
    }

    /**
     * DOCUMENT ME!
     *
     * @param b
     */
    public void setAwayGames(boolean b) {
        awayGames = b;
    }

    /**
     * DOCUMENT ME!
     *
     * @return
     */
    public boolean isAwayGames() {
        return awayGames;
    }

    /**
     * Document Me!
     *
     * @param b
     */
    public void setCup(boolean b) {
        cup = b;
    }

    /**
     * Document Me!
     *
     * @return
     */
    public boolean isCup() {
        return cup;
    }

    /**
     * DOCUMENT ME!
     *
     * @param b
     */
    public void setDefeat(boolean b) {
        defeat = b;
    }

    /**
     * DOCUMENT ME!
     *
     * @return
     */
    public boolean isDefeat() {
        return defeat;
    }

    /**
     * DOCUMENT ME!
     *
     * @param b
     */
    public void setDraw(boolean b) {
        draw = b;
    }

    /**
     * DOCUMENT ME!
     *
     * @return
     */
    public boolean isDraw() {
        return draw;
    }

    /**
     * Document Me!
     *
     * @param b
     */
    public void setFriendly(boolean b) {
        friendly = b;
    }

    /**
     * Document Me!
     *
     * @return
     */
    public boolean isFriendly() {
        return friendly;
    }

    /**
     * DOCUMENT ME!
     *
     * @param b
     */
    public void setHomeGames(boolean b) {
        homeGames = b;
    }

    /**
     * DOCUMENT ME!
     *
     * @return
     */
    public boolean isHomeGames() {
        return homeGames;
    }

    /**
     * Document Me!
     *
     * @param b
     */
    public void setLeague(boolean b) {
        league = b;
    }

    /**
     * Document Me!
     *
     * @return
     */
    public boolean isLeague() {
        return league;
    }

    /**
     * DOCUMENT ME!
     *
     * @param list
     */
    public void setMatches(List list) {
        matches = list;
    }

    /**
     * DOCUMENT ME!
     *
     * @return
     */
    public List getMatches() {
        return matches;
    }

    /**
     * DOCUMENT ME!
     *
     * @param i
     */
    public void setNumber(int i) {
        number = i;
    }

    /**
     * DOCUMENT ME!
     *
     * @return
     */
    public int getNumber() {
        return number;
    }

    /**
     * Document Me!
     *
     * @param b
     */
    public void setQualifier(boolean b) {
        qualifier = b;
    }

    /**
     * Document Me!
     *
     * @return
     */
    public boolean isQualifier() {
        return qualifier;
    }

    /**
     * DOCUMENT ME!
     *
     * @param b
     */
    public void setWin(boolean b) {
        win = b;
    }

    /**
     * DOCUMENT ME!
     *
     * @return
     */
    public boolean isWin() {
        return win;
    }

    /**
     * toString methode: creates a String representation of the object
     *
     * @return the String representation
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("Filter[");
        buffer.append("number = " + number);
        buffer.append(", awayGames = " + awayGames);
        buffer.append(", homeGames = " + homeGames);
        buffer.append(", win = " + win);
        buffer.append(", draw = " + draw);
        buffer.append(", defeat = " + defeat);
        buffer.append(", automatic = " + automatic);
        buffer.append(", matches = " + matches);
        buffer.append("]");

        return buffer.toString();
    }
}