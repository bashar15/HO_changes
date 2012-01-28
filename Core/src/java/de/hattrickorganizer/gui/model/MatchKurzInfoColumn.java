package de.hattrickorganizer.gui.model;

import java.awt.Color;

import plugins.IMatchLineup;
import de.hattrickorganizer.gui.templates.TableEntry;
import de.hattrickorganizer.model.matches.MatchKurzInfo;

/**
 * column shows values from a matchKurzInfo
 * @author Thorsten Dietz
 * @since 1.36
 */
class MatchKurzInfoColumn extends UserColumn {

	/**
	 * constructor
	 * @param id
	 * @param name
	 * @param minWidth
	 */
	protected MatchKurzInfoColumn(int id,String name,int minWidth){
		this(id,name,name,minWidth);
		
	}
	
	/**
	 * constructor
	 * @param id
	 * @param name
	 * @param tooltip
	 * @param minWidth
	 */
	protected MatchKurzInfoColumn(int id,String name, String tooltip,int minWidth){
		super(id,name,tooltip);
		this.minWidth = minWidth;
		preferredWidth = minWidth;
	}
	
	/**
	 * overwritten by created column
	 * @param match
	 * @return
	 */
	public TableEntry getTableEntry(MatchKurzInfo match){
		return null;
	}
	
	/**
	 * overwritten by created column
	 * @param spielerCBItem
	 * @return
	 */
	public TableEntry getTableEntry(SpielerMatchCBItem spielerCBItem){
		return null;
	}
	

}
