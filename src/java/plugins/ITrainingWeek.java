// %1127327012509:plugins%
/*
 * ITrainingWeek.java
 *
 * Created on 15. Oktober 2004, 13:12
 */
package plugins;

/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public interface ITrainingWeek {
    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public int getHattrickSeason();

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public int getHattrickWeek();

    /**
     * TODO Missing Method Documentation
     *
     * @param hrfId TODO Missing Method Parameter Documentation
     */
    public void setHrfId(int hrfId);

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public int getHrfId();

    /**
     * TODO Missing Method Documentation
     *
     * @param intensitaet TODO Missing Method Parameter Documentation
     */
    public void setIntensitaet(int intensitaet);

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public int getIntensitaet();

    /**
     * TODO Missing Method Documentation
     *
     * @param typ TODO Missing Method Parameter Documentation
     */
    public void setTyp(int typ);

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public int getTyp();

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public int getWeek();

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public int getYear();

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public String toString();
}
