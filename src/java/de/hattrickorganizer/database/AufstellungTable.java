package de.hattrickorganizer.database;

import java.sql.ResultSet;
import java.sql.Types;
import java.util.Vector;

import de.hattrickorganizer.model.Aufstellung;
import de.hattrickorganizer.tools.HOLogger;


public final class AufstellungTable extends AbstractTable {
	/** tablename **/
	public final static String TABLENAME = "AUFSTELLUNG";
	
	protected AufstellungTable(JDBCAdapter  adapter){
		super(TABLENAME,adapter);
	}
	
	protected void initColumns() {
		columns = new ColumnDescriptor[6];
		columns[0]= new ColumnDescriptor("HRF_ID",Types.INTEGER,false);
		columns[1]= new ColumnDescriptor("Kicker",Types.INTEGER,false);
		columns[2]= new ColumnDescriptor("Kapitaen",Types.INTEGER,false);
		columns[3]= new ColumnDescriptor("Attitude",Types.INTEGER,false);
		columns[4]= new ColumnDescriptor("Tactic",Types.INTEGER,false);
		columns[5]= new ColumnDescriptor("Aufstellungsname",Types.VARCHAR,false,256);

	}

	/**
	 * lädt System Positionen
	 *
	 * @param hrfID TODO Missing Constructuor Parameter Documentation
	 * @param name TODO Missing Constructuor Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public Aufstellung getAufstellung(int hrfID, String name) {
		ResultSet rs = null;
		de.hattrickorganizer.model.Aufstellung auf = null;
		String sql = null;

		sql = "SELECT * FROM "+getTableName()+" WHERE HRF_ID = " + hrfID + " and Aufstellungsname ='" + name + "'";
		rs = adapter.executeQuery(sql);

		try {
			if (rs != null) {
				rs.first();

				auf = new de.hattrickorganizer.model.Aufstellung();
				auf.setKapitaen(rs.getInt("Kapitaen"));
				auf.setKicker(rs.getInt("Kicker"));
				auf.setTacticType(rs.getInt("Tactic"));
				auf.setAttitude(rs.getInt("Attitude"));
			}
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),"DatenbankZugriff.getAufstellung: " + e);
		}

		if (auf != null) {
			auf.setPositionen(DBZugriff.instance().getSystemPositionen(hrfID, name));
		}

		return auf;
	}
	
	/**
	 * gibt liste für Aufstellungen
	 *
	 * @param hrfID -1 für default = hrf unabhängig
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public Vector getAufstellungsListe(int hrfID) {
		final Vector ret = new Vector();
		ResultSet rs = null;
		String sql = null;

		sql = "SELECT Aufstellungsname FROM "+getTableName()+"";
		rs = adapter.executeQuery(sql);

		try {
			if (rs != null) {
				rs.beforeFirst();

				while (rs.next()) {
					ret.add(rs.getString("Aufstellungsname"));
				}
			}
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),"DatenbankZugriff.getAufstellungsListe: " + e);
		}

		return ret;
	}
	
	/**
	 * Gibt eine Liste aller Usergespeicherten Aufstellungsnamen zurück
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public Vector getUserAufstellungsListe() {
		ResultSet rs = null;
		final String statement = "SELECT Aufstellungsname FROM "+getTableName()+" WHERE HRF_ID=" + Aufstellung.NO_HRF_VERBINDUNG;
		final Vector ret = new Vector();

		try {
			rs = adapter.executeQuery(statement);

			if (rs != null) {
				rs.beforeFirst();

				while (rs.next()) {
					ret.add(rs.getString("Aufstellungsname"));
				}
			}
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),"DatenbankZugriff.getUserAufstellungsListe: " + e);
		}

		return ret;
	}
	
	/**
	 * speichert die Aufstellung und die aktuelle Aufstellung als STANDARD
	 *
	 * @param hrfId TODO Missing Constructuor Parameter Documentation
	 * @param aufstellung TODO Missing Constructuor Parameter Documentation
	 * @param name TODO Missing Constructuor Parameter Documentation
	 */
	public void saveAufstellung(int hrfId, Aufstellung aufstellung, String name) {
		String statement = null;

		if (aufstellung != null) {
			
			DBZugriff.instance().deleteAufstellung(hrfId, name);

			//insert vorbereiten
			statement = "INSERT INTO "+getTableName()+" ( HRF_ID, Kicker, Kapitaen, Attitude, Tactic, Aufstellungsname ) VALUES(";
			statement += ("" + hrfId + "," + aufstellung.getKicker() + "," + aufstellung.getKapitaen() + "," + aufstellung.getAttitude() + "," + aufstellung.getTacticType() + ",'" + name + "' )");

			adapter.executeUpdate(statement);

			//Standard sys saven
			DBZugriff.instance().saveSystemPositionen(hrfId, aufstellung.getPositionen(), name);
		}
	}
	
	
	
}