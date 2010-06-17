package hoplugins.tsforecast;

import java.sql.*;
import java.util.*;
import plugins.*;

/*
 * HistoryCurve.java
 *
 * Created on 19.March 2006, 11:04
 *
 *Version 0.2
 *history :
 *19.03.06  Version 0.1
 *28.08.06  Version 0.11 rebuilt
 *18.02.07  Version 0.2  optomized SQL-statements
 *
 *
 */

/**
 *
 * @author  michael.roux
 */


// Referenced classes of package hoplugins.tsforecast:
//            Curve, ErrorLog


public class HistoryCurve extends Curve {

  static final double m_dMaxSpirit = 10.2D;

  
  public HistoryCurve(IHOMiniModel ihominimodel) throws SQLException {
    super(ihominimodel);
    
    readSpiritHistory();
    readPastMatches();
    //BAUSTELLE XLint
    Collections.sort( m_clPoints);
    fillupSpirit();
  }

  //-- private ------------------------------------------------------------------------

  private void readSpiritHistory() throws SQLException  {
    IBasics ibasics = m_clModel.getBasics();
    GregorianCalendar startDate = new GregorianCalendar();
    startDate.setTime( ibasics.getDatum());
    startDate.add( Calendar.WEEK_OF_YEAR, -WEEKS_BACK);
    Timestamp start = new Timestamp( startDate.getTimeInMillis());
    
/*    GregorianCalendar gregoriancalendar = new GregorianCalendar();
    gregoriancalendar.setTime( m_clModel.getBasics().getDatum());
    gregoriancalendar.add( Calendar.WEEK_OF_YEAR, -WEEKS_BACK);
    java.util.Date date = gregoriancalendar.getTime();
*/    
    ResultSet resultset = m_clJDBC.executeQuery( "select DATUM, ISTIMMUNG from HRF, TEAM "
                                               + "where HRF.HRF_ID = TEAM.HRF_ID "
                                               +   "and DATUM <= '" + ibasics.getDatum() + "' and DATUM > '" + start + "'"
                                               + "order by DATUM");
    for( boolean flag = resultset.first(); flag; flag = resultset.next()) {
//      if(   date.before(resultset.getTimestamp( "DATUM"))
//         && !m_clModel.getBasics().getDatum().before( resultset.getTimestamp( "DATUM"))) {
        double dSpirit = resultset.getInt( "ISTIMMUNG") + 0.5D;
        if( dSpirit > m_dMaxSpirit )
          dSpirit = m_dMaxSpirit;
        m_clPoints.add( new Point( resultset.getTimestamp( "DATUM"), dSpirit));
//      }
    }
  }  

  private void readPastMatches() throws SQLException  {
    IBasics ibasics = m_clModel.getBasics();
    plugins.ILiga iliga = m_clModel.getLiga();
    
    Curve.Point pLastLeagueMatch = null;
    GregorianCalendar dateOfLastLeagueMatch = new GregorianCalendar();
    GregorianCalendar startDate = new GregorianCalendar();
    startDate.setTime( ibasics.getDatum());
    startDate.add( Calendar.WEEK_OF_YEAR, -WEEKS_BACK);
    Timestamp start = new Timestamp( startDate.getTimeInMillis());
    Object obj = null;

    //Table PAARUNG is required for SPIELTAG but does only include League matches
    //Table MATCHESKURZINFO includes all matches but not SPIELTAG
    //Table MATCHDETAILS includes EINSTELLUNG
    if( ibasics != null && iliga != null) {
      ResultSet resultset = m_clJDBC.executeQuery( "select MATCHESKURZINFO.MATCHDATE as SORTDATE, -1 AS SPIELTAG, MATCHESKURZINFO.MATCHTYP, "
                                                 +        "MATCHDETAILS.GASTEINSTELLUNG, MATCHDETAILS.HEIMEINSTELLUNG, MATCHDETAILS.HEIMID "
                                                 + "from MATCHESKURZINFO, MATCHDETAILS "
                                                 + "where (MATCHDETAILS.HEIMID=" + ibasics.getTeamId() 
                                                 +      " OR MATCHDETAILS.GASTID=" + ibasics.getTeamId() + ") "
                                                 +   "and MATCHESKURZINFO.MATCHID=MATCHDETAILS.MATCHID "
                                                 +   "and SORTDATE < '" + ibasics.getDatum() + "' and SORTDATE > '" + start + "' "
                                                 +   "and MATCHTYP <> " + LEAGUE_MATCH
                                                 + "union "
                                                 + "select PAARUNG.DATUM as SORTDATE, PAARUNG.SPIELTAG, "+ LEAGUE_MATCH + " as MATCHTYP, "
                                                 +        "MATCHDETAILS.GASTEINSTELLUNG, MATCHDETAILS.HEIMEINSTELLUNG, MATCHDETAILS.HEIMID "
                                                 + "from PAARUNG, MATCHDETAILS "
                                                 + "where (MATCHDETAILS.HEIMID=" + ibasics.getTeamId() 
                                                 +      " OR MATCHDETAILS.GASTID=" + ibasics.getTeamId() + ") "
                                                 +   "and PAARUNG.MATCHID=MATCHDETAILS.MATCHID "
                                                 +   "and SORTDATE < '" + ibasics.getDatum() + "' and SORTDATE > '" + start + "'"
                                                 + "order by SORTDATE");
      /*
        select MATCHESKURZINFO.MATCHDATE as SORTDATE, -1 as SPIELTAG, MATCHESKURZINFO.MATCHTYP, 
               MATCHDETAILS.GASTEINSTELLUNG, MATCHDETAILS.HEIMEINSTELLUNG, MATCHDETAILS.HEIMID 
        from MATCHESKURZINFO, MATCHDETAILS
        where (MATCHDETAILS.HEIMID=132932 OR MATCHDETAILS.GASTID=132932)
          and MATCHESKURZINFO.MATCHID=MATCHDETAILS.MATCHID 
          and SORTDATE < '2006-09-01' and SORTDATE > '2006-01-01'
          and MATCHTYP <> 1
        union
        select PAARUNG.DATUM as SORTDATE, PAARUNG.SPIELTAG, 1 as MATCHTYP, 
                      MATCHDETAILS.GASTEINSTELLUNG, MATCHDETAILS.HEIMEINSTELLUNG, MATCHDETAILS.HEIMID
        from PAARUNG, MATCHDETAILS
        where (MATCHDETAILS.HEIMID=132932 OR MATCHDETAILS.GASTID=132932) 
          and PAARUNG.MATCHID=MATCHDETAILS.MATCHID 
          and SORTDATE < '2006-09-01' and SORTDATE > '2006-01-01'
        order by SORTDATE
      */                                                 
      int i = 0;
      for( boolean flag = resultset != null && resultset.first(); flag; flag = resultset.next()) {
        if(resultset.getInt( "SPIELTAG") > 0)
          i = resultset.getInt( "SPIELTAG");
        Curve.Point pNextLeagueMatch;
        if(ibasics.getTeamId() == resultset.getInt( "HEIMID"))
          pNextLeagueMatch = new Point( resultset.getTimestamp( "SORTDATE"), 
                                        resultset.getInt( "HEIMEINSTELLUNG"), 
                                        i, resultset.getInt( "MATCHTYP"));
        else
          pNextLeagueMatch = new Point( resultset.getTimestamp( "SORTDATE"), 
                                        resultset.getInt( "GASTEINSTELLUNG"), 
                                        i, resultset.getInt( "MATCHTYP"));
        m_clPoints.add( pNextLeagueMatch ); 
        
        // correction of matchdays at end of season for non league matches
        if( pNextLeagueMatch.m_iMatchType != 1) {
          if( pLastLeagueMatch != null) {  //first match 
            if( getDiffDays( pLastLeagueMatch, pNextLeagueMatch) <= 7) //matchday stays the same
              continue;      
            pNextLeagueMatch.m_iMatchDay++;
            if( getDiffDays( pLastLeagueMatch, pNextLeagueMatch) > 14)
              pNextLeagueMatch.m_iMatchDay++;
            continue;
          }
          if( pNextLeagueMatch.m_iMatchDay == 0)
            pNextLeagueMatch.m_iMatchDay = 16;
        } else {
          pLastLeagueMatch = pNextLeagueMatch;
          dateOfLastLeagueMatch.setTime( pNextLeagueMatch.m_dDate);
        }
      }
    }
  }


  private void fillupSpirit() {
    if( !m_clPoints.isEmpty()) {
      double dPreviousSpirit = TEAM_SPIRIT_UNKNOWN;
      for( int i = 0; i < m_clPoints.size(); i++) {
        Curve.Point point = (Curve.Point)m_clPoints.get(i);
        if( point.m_dSpirit == TEAM_SPIRIT_UNKNOWN) {
          if( dPreviousSpirit == TEAM_SPIRIT_UNKNOWN) {
            point.m_dSpirit = nextSpirit(i);
          } else {
            point.m_dSpirit = dPreviousSpirit;
          }
        } else {
          dPreviousSpirit = point.m_dSpirit;
        }
      }
    }
  }

  private double nextSpirit(int i) {
    for(int j = i; j < m_clPoints.size(); j++) {
      Curve.Point point = (Curve.Point)m_clPoints.get(j);
      if(point.m_dSpirit != TEAM_SPIRIT_UNKNOWN)
        return point.m_dSpirit;
    }
    return TEAM_SPIRIT_UNKNOWN;
  }
}