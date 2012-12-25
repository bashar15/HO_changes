package ho.core.training;

import java.sql.Timestamp;

/**
 * New Training Class
 */
public class TrainingPerWeek  {
    //~ Instance fields ----------------------------------------------------------------------------

    private int _HTSeason = -1;
    private int _HTWeek = -1;
    private int _HRFID;
    private int _Intensity = -1;
    private int _Stamina = -1;
    private int _TrainingType = -1;
    private int _Week = -1;
    private int _Year = -1;
    private int _PreviousHRFID;
    private Timestamp nextTrainingDate = null;
    private Timestamp trainingDate = null;
    private int assistants = -1;
    
    //~ Constructors -------------------------------------------------------------------------------
    public TrainingPerWeek() {
    	
    }
    /**
     * Creates a new Training object.
     *
     * @param week
     * @param year
     * @param trType
     * @param intensity
     * @param stamina
     */
    public TrainingPerWeek(int week, int year, int trType, int intensity, int stamina) {
        this._Week = week;
        this._Year = year;
        this._TrainingType = trType;
        this._Intensity = intensity;
        this._Stamina = stamina;
    }

    //~ Methods ------------------------------------------------------------------------------------
    public final void setHattrickSeason(int i) {
        _HTSeason = i;
    }

    public final int getHattrickSeason() {
        return _HTSeason;
    }

    public final void setHattrickWeek(int i) {
        _HTWeek = i;
    }

    public final int getHattrickWeek() {
        return _HTWeek;
    }

    public final void setHrfId(int i) {
        _HRFID = i;
    }

    public final int getHrfId() {
        return _HRFID;
    }

    public final void setStaminaPart(int stamina) {
        this._Stamina = stamina;
    }

    public final int getStaminaPart() {
        return this._Stamina;
    }

    public final void setTrainingIntensity(int intensity) {
        this._Intensity = intensity;
    }

    public final int getTrainingIntensity() {
        return this._Intensity;
    }

    public final void setTrainingType(int trType) {
        this._TrainingType = trType;
    }

    public final int getTrainingType() {
        return this._TrainingType;
    }

    public final int getWeek() {
        return this._Week;
    }

    public final int getYear() {
        return this._Year;
    }

    /**
     * toString method: creates a String representation of the object
     *
     * @return the String representation
     */
    @Override
	public final String toString() {
        final StringBuffer buffer = new StringBuffer();
        buffer.append("TrainingPerWeek[");
        buffer.append("intensity = " + _Intensity);
        buffer.append(", staminaTrainingPart = " + _Stamina);
        buffer.append(", typ = " + _TrainingType);
        buffer.append(", week = " + _Week);
        buffer.append(", year = " + _Year);
        buffer.append(", hattrickWeek = " + _HTWeek);
        buffer.append(", hattrickSeason = " + _HTSeason);
        buffer.append(", trainDate = " + trainingDate);
        buffer.append(", hrfId = " + _HRFID);
        buffer.append("]");
        return buffer.toString();
    }
	public int getPreviousHrfId() {
		return _PreviousHRFID;
	}

	public void setPreviousHrfId(int i) {
		_PreviousHRFID = i;
	}

	/**
	 * Returns the timestamp with the training at the start of this trainingweek.
	 *
	 * @return	training date
	 */
	public Timestamp getTrainingDate() {
        return trainingDate;
	}
	
	
	/**
	 *  Sets the date of the training at the start of this training week.
	 *	
	 * @param Timestamp with the training date.
	 */
	public void setTrainingDate(Timestamp date) {
		trainingDate = date;
	}
	
	/**
	 * Returns a timestamp with the  of the training at the end of this training week.
	 
	 * @return The timestamp with the next training date.
	 */
	public Timestamp getNextTrainingDate() {
		return nextTrainingDate;
	}
	
	/**
	 * Sets the time of the next training. 
	 * 
	 * @param A Timestamp containing the time
	 */
	public void setNextTrainingDate(Timestamp t) {
		nextTrainingDate = t;
	}
	
	/**
	 * Returns the number of assistants for the week.
	 * 
	 * @return an integer with the number of assistants
	 */
	public int getAssistants() {
		return assistants;
	}
	
	/**
	 * Sets the number of assisstants
	 * 
	 * @param assistants, an integer with the number of assistants
	 */
	public void setAssistants(int assistants) {
		this.assistants = assistants;
	}
	
	
	
}
