// %1126721452213:hoplugins.trainingExperience.vo%
package ho.core.training;

import ho.core.model.player.ISkillup;

import java.util.Date;



/**
 * Base Object for the Skillup table
 */
public class PlayerSkillup implements ISkillup
{

    /** Skillup Date */
    private Date date;

    /** Hattrick Season */
    private int htSeason;

    /** Hattrick Week */
    private int htWeek;

    /** Training Type 0 old, 1 min, 2 max */
    private int trainType;

    /** type of skill changed */
    private int type;

    /** Value of skill */
    private int value;

    /**
* Document me!
*
* @param date
*/
    public void setDate(Date date)
    {
        this.date = date;
    }

    /**
* Document me!
*
* @return
*/
    @Override
	public Date getDate()
    {
        return date;
    }

    /**
* Document me!
*
* @param i
*/
    public void setHtSeason(int i)
    {
        htSeason = i;
    }

    /**
* Document me!
*
* @return
*/
    @Override
	public int getHtSeason()
    {
        return htSeason;
    }

    /**
* Document me!
*
* @param i
*/
    public void setHtWeek(int i)
    {
        htWeek = i;
    }

    /**
* Document me!
*
* @return
*/
    @Override
	public int getHtWeek()
    {
        return htWeek;
    }

    /**
* DOCUMENT ME!
*
* @param i
*/
    public void setTrainType(int i)
    {
        trainType = i;
    }

    /**
* DOCUMENT ME!
*
* @return
*/
    @Override
	public int getTrainType()
    {
        return trainType;
    }

    /**
* Set training type
*
* @param type
*/
    public void setType(int type)
    {
        this.type = type;
    }

    /**
* Get Training type
*
* @return type
*/
    @Override
	public int getType()
    {
        return type;
    }

    /**
* Set the new value of the skill
*
* @param newValue
*/
    public void setValue(int newValue)
    {
        value = newValue;
    }

    /**
* Set the new value of the skill
*
* @return value
*/
    @Override
	public int getValue()
    {
        return value;
    }

    /**
* toString methode: creates a String representation of the object
*
* @return the String representation
*/
    @Override
	public String toString()
    {
        StringBuffer buffer = new StringBuffer();

        buffer.append("Skillup["); //$NON-NLS-1$
        buffer.append(", type = " + type); //$NON-NLS-1$
        buffer.append(", value = " + value); //$NON-NLS-1$
        buffer.append(", htSeason = " + htSeason); //$NON-NLS-1$
        buffer.append(", htWeek = " + htWeek); //$NON-NLS-1$
        buffer.append(", trainType = " + trainType); //$NON-NLS-1$
        buffer.append("]"); //$NON-NLS-1$

        return buffer.toString();
    }
}
