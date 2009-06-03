// %597232359:de.hattrickorganizer.logik.xml%
/*
 * XMLMatchLineupParser.java
 *
 * Created on 20. Oktober 2003, 08:08
 */
package de.hattrickorganizer.logik.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import de.hattrickorganizer.model.matches.MatchLineup;
import de.hattrickorganizer.model.matches.MatchLineupPlayer;
import de.hattrickorganizer.model.matches.MatchLineupTeam;
import de.hattrickorganizer.tools.HOLogger;
import de.hattrickorganizer.tools.xml.XMLManager;


/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public class XMLMatchLineupParser {
    //~ Constructors -------------------------------------------------------------------------------

    /////////////////////////////////////////////////////////////////////////////////    
    //KONSTRUKTOR
    ////////////////////////////////////////////////////////////////////////////////    

    /**
     * Creates a new instance of XMLMatchLineupParser
     */
    public XMLMatchLineupParser() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    /////////////////////////////////////////////////////////////////////////////////    
    //parse public
    ////////////////////////////////////////////////////////////////////////////////    
    public final MatchLineup parseMatchLineup(String dateiname) {
        MatchLineup ml = null;
        Document doc = null;

        doc = XMLManager.instance().parseFile(dateiname);
        ml = createLineup(doc);

        return ml;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param datei TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final MatchLineup parseMatchLineup(java.io.File datei) {
        Document doc = null;

        doc = XMLManager.instance().parseFile(datei);

        return createLineup(doc);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param inputStream TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final MatchLineup parseMatchLineupFromString(String inputStream) {
        Document doc = null;

        doc = XMLManager.instance().parseString(inputStream);

        return createLineup(doc);
    }

    /////////////////////////////////////////////////////////////////////////////////    
    //Parser Helper private
    ////////////////////////////////////////////////////////////////////////////////    

    /**
     * erstellt das MAtchlineup Objekt
     *
     * @param doc TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    protected final MatchLineup createLineup(Document doc) {
        Element ele = null;
        Element root = null;
        MatchLineup ml = new MatchLineup();
        MatchLineupTeam team = null;

        if (doc == null) {
            return ml;
        }

        //Tabelle erstellen
        root = doc.getDocumentElement();

        try {
            //Daten füllen
            ele = (Element) root.getElementsByTagName("FetchedDate").item(0);
            ml.setFetchDatum(ele.getFirstChild().getNodeValue());
            ele = (Element) root.getElementsByTagName("MatchID").item(0);
            ml.setMatchID(Integer.parseInt(ele.getFirstChild().getNodeValue()));
            ele = (Element) root.getElementsByTagName("HomeTeam").item(0);
            ml.setHeimId(Integer.parseInt(ele.getElementsByTagName("HomeTeamID").item(0)
                                             .getFirstChild().getNodeValue()));
            ml.setHeimName(ele.getElementsByTagName("HomeTeamName").item(0).getFirstChild()
                              .getNodeValue());
            ele = (Element) root.getElementsByTagName("AwayTeam").item(0);
            ml.setGastId(Integer.parseInt(ele.getElementsByTagName("AwayTeamID").item(0)
                                             .getFirstChild().getNodeValue()));
            ml.setGastName(ele.getElementsByTagName("AwayTeamName").item(0).getFirstChild()
                              .getNodeValue());
            ele = (Element) root.getElementsByTagName("MatchType").item(0);
            ml.setMatchTyp(Integer.parseInt(ele.getFirstChild().getNodeValue()));
            ele = (Element) root.getElementsByTagName("Arena").item(0);
            ml.setArenaID(Integer.parseInt(ele.getElementsByTagName("ArenaID").item(0)
                                              .getFirstChild().getNodeValue()));
            ml.setArenaName(ele.getElementsByTagName("ArenaName").item(0).getFirstChild()
                               .getNodeValue());
            ele = (Element) root.getElementsByTagName("MatchDate").item(0);
            ml.setSpielDatum(ele.getFirstChild().getNodeValue());

            //team adden
            team = createTeam((Element) root.getElementsByTagName("Team").item(0));

            if (team.getTeamID() == ml.getHeimId()) {
                ml.setHeim(team);
            } else {
                ml.setGast(team);
            }
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),"XMLMatchLineupParser.createLineup Exception gefangen: " + e);
            HOLogger.instance().log(getClass(),e);
            ml = null;
        }

        return ml;
    }

    /**
     * erzeugt einen Spieler aus dem xml
     *
     * @param ele TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     *
     * @throws Exception TODO Missing Constructuor Exception Documentation
     */
    protected final MatchLineupPlayer createPlayer(Element ele) throws Exception {
        Element tmp = null;
        MatchLineupPlayer player = null;
        int roleID = -1;
        int behaivior = 0;
        int spielerID = -1;
        double rating = -1.0d;
        String name = "";
        int positionsCode = -1;

        tmp = (Element) ele.getElementsByTagName("PlayerID").item(0);
        spielerID = Integer.parseInt(tmp.getFirstChild().getNodeValue());
        tmp = (Element) ele.getElementsByTagName("RoleID").item(0);
        if (tmp != null) {
        	roleID = Integer.parseInt(tmp.getFirstChild().getNodeValue());
        }

        //nur wenn Spieler existiert
        if (spielerID > 0) {
            tmp = (Element) ele.getElementsByTagName("PlayerName").item(0);

            //Fix für xml BUG von HT
            if (tmp.getFirstChild() != null) {
                name = tmp.getFirstChild().getNodeValue();
            }

            //taktik nur für aufgestellte
            if (roleID == 1) {
                //Diese Werte sind von HT vorgegeben aber nicht garantiert  mitgeliefert in xml, daher selbst setzen!
                behaivior = 0;
                positionsCode = 1;
            } else if ((roleID > 1) && (roleID < 12)) {
                tmp = (Element) ele.getElementsByTagName("Behaviour").item(0);
                behaivior = Integer.parseInt(tmp.getFirstChild().getNodeValue());
                tmp = (Element) ele.getElementsByTagName("PositionCode").item(0);
                positionsCode = Integer.parseInt(tmp.getFirstChild().getNodeValue());
            }

            //rating nur für leute die gespielt haben
            if ((roleID < 12) || (roleID > 18)) {
                tmp = (Element) ele.getElementsByTagName("RatingStars").item(0);
                rating = Double.parseDouble(tmp.getFirstChild().getNodeValue().replaceAll(",","."));
            }
        }

        player = new MatchLineupPlayer(roleID, behaivior, spielerID, rating, name, positionsCode,
                                       0, positionsCode);

        return player;
    }

    /**
     * erzeugt das Team aus dem xml
     *
     * @param ele TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     *
     * @throws Exception TODO Missing Constructuor Exception Documentation
     */
    protected final MatchLineupTeam createTeam(Element ele) throws Exception {
        Element tmp = null;
        NodeList list = null;

        //new MatchLineupTeam();
        MatchLineupTeam team = null;
        int teamId = -1;
        int erfahrung = -1;
        String teamName = "";

        tmp = (Element) ele.getElementsByTagName("TeamID").item(0);
        teamId = Integer.parseInt(tmp.getFirstChild().getNodeValue());
        tmp = (Element) ele.getElementsByTagName("ExperienceLevel").item(0);
        erfahrung = Integer.parseInt(tmp.getFirstChild().getNodeValue());
        tmp = (Element) ele.getElementsByTagName("TeamName").item(0);
        teamName = tmp.getFirstChild().getNodeValue();
        team = new MatchLineupTeam(teamName, teamId, erfahrung);
        tmp = (Element) ele.getElementsByTagName("Lineup").item(0);

        //Einträge adden
        list = tmp.getElementsByTagName("Player");

        for (int i = 0; (list != null) && (i < list.getLength()); i++) {
            team.add2Aufstellung(createPlayer((Element) list.item(i)));
        }

        return team;
    }
}