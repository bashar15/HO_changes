package ho.core.model.player;

import java.util.Date;

public interface ISkillup {
	
	/** Real Skillup happened in the past */
	public static final int SKILLUP_REAL = 0;

	/** PRedicted Skillup at maximum training */
	public static final int SKILLUP_FUTURE = 1;
	public abstract Date getDate();
	public abstract int getHtSeason();
	public abstract int getHtWeek();

	public abstract int getTrainType();

	/**
	* Get Training type
	*
	* @return type
	*/
	public abstract int getType();

	/**
	* Set the new value of the skill
	*
	* @return value
	*/
	public abstract int getValue();
}