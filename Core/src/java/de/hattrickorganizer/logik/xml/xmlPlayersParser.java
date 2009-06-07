// %762900153:de.hattrickorganizer.logik.xml%
/*
 * xmlPlayersParser.java
 *
 * Created on 13. Januar 2004, 08:46
 */
package de.hattrickorganizer.logik.xml;

import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import de.hattrickorganizer.tools.xml.XMLManager;


/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public class xmlPlayersParser {
    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new instance of xmlPlayersParser
     */
    public xmlPlayersParser() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    /////////////////////////////////////////////////////////////////////////////////    
    //parse public
    ////////////////////////////////////////////////////////////////////////////////   
    public final Vector parsePlayersFromString(String inputStream) {
        Document doc = null;

        doc = XMLManager.instance().parseString(inputStream);

        return createListe(doc);
    }

    /////////////////////////////////////////////////////////////////////////////////    
    //Parser Helper private
    ////////////////////////////////////////////////////////////////////////////////     

    /**
     * erzeugt das Team aus dem xml
     *
     * @param doc TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */

    //throws Exception
    protected final Vector createListe(Document doc) {
        final Vector liste = new Vector();
        de.hattrickorganizer.model.MyHashtable hash = null;
        Element ele = null;
        Element root = null;
        NodeList list = null;

        try {
            root = doc.getDocumentElement();
            root = (Element) root.getElementsByTagName("Team").item(0);
            root = (Element) root.getElementsByTagName("PlayerList").item(0);

            //Einträge adden
            list = root.getElementsByTagName("Player");

            for (int i = 0; (list != null) && (i < list.getLength()); i++) {
                hash = new de.hattrickorganizer.model.MyHashtable();

                //Root setzen
                root = (Element) list.item(i);

                //ht füllen
                ele = (Element) root.getElementsByTagName("PlayerID").item(0);
                hash.put("PlayerID", (XMLManager.instance().getFirstChildNodeValue(ele)));
                ele = (Element) root.getElementsByTagName("PlayerName").item(0);
                hash.put("PlayerName", (XMLManager.instance().getFirstChildNodeValue(ele)));
                ele = (Element) root.getElementsByTagName("PlayerNumber").item(0);
                hash.put("PlayerNumber", (XMLManager.instance().getFirstChildNodeValue(ele)));
                ele = (Element) root.getElementsByTagName("Age").item(0);
                hash.put("Age", (XMLManager.instance().getFirstChildNodeValue(ele)));
                ele = (Element) root.getElementsByTagName("AgeDays").item(0);
                hash.put("AgeDays", (XMLManager.instance().getFirstChildNodeValue(ele)));

                //TSI löste Marktwert ab!
                ele = (Element) root.getElementsByTagName("TSI").item(0);
                hash.put("MarketValue", (XMLManager.instance().getFirstChildNodeValue(ele)));
                ele = (Element) root.getElementsByTagName("PlayerForm").item(0);
                hash.put("PlayerForm", (XMLManager.instance().getFirstChildNodeValue(ele)));
                ele = (Element) root.getElementsByTagName("Statement").item(0);
                hash.put("Statement", (XMLManager.instance().getFirstChildNodeValue(ele)));
                ele = (Element) root.getElementsByTagName("Experience").item(0);
                hash.put("Experience", (XMLManager.instance().getFirstChildNodeValue(ele)));
                ele = (Element) root.getElementsByTagName("Leadership").item(0);
                hash.put("Leadership", (XMLManager.instance().getFirstChildNodeValue(ele)));
                ele = (Element) root.getElementsByTagName("Salary").item(0);
                hash.put("Salary", (XMLManager.instance().getFirstChildNodeValue(ele)));
                ele = (Element) root.getElementsByTagName("Agreeability").item(0);
                hash.put("Agreeability", (XMLManager.instance().getFirstChildNodeValue(ele)));
                ele = (Element) root.getElementsByTagName("Aggressiveness").item(0);
                hash.put("Aggressiveness", (XMLManager.instance().getFirstChildNodeValue(ele)));
                ele = (Element) root.getElementsByTagName("Honesty").item(0);
                hash.put("Honesty", (XMLManager.instance().getFirstChildNodeValue(ele)));
                ele = (Element) root.getElementsByTagName("LeagueGoals").item(0);
                hash.put("LeagueGoals", (XMLManager.instance().getFirstChildNodeValue(ele)));
                ele = (Element) root.getElementsByTagName("CupGoals").item(0);
                hash.put("CupGoals", (XMLManager.instance().getFirstChildNodeValue(ele)));
                ele = (Element) root.getElementsByTagName("FriendliesGoals").item(0);
                hash.put("FriendliesGoals", (XMLManager.instance().getFirstChildNodeValue(ele)));
                ele = (Element) root.getElementsByTagName("CareerGoals").item(0);
                hash.put("CareerGoals", (XMLManager.instance().getFirstChildNodeValue(ele)));
                ele = (Element) root.getElementsByTagName("CareerHattricks").item(0);
                hash.put("CareerHattricks", (XMLManager.instance().getFirstChildNodeValue(ele)));
                ele = (Element) root.getElementsByTagName("Specialty").item(0);
                hash.put("Specialty", (XMLManager.instance().getFirstChildNodeValue(ele)));
                ele = (Element) root.getElementsByTagName("TransferListed").item(0);
                hash.put("TransferListed", (XMLManager.instance().getFirstChildNodeValue(ele)));
                ele = (Element) root.getElementsByTagName("NationalTeamID").item(0);
                hash.put("NationalTeamID", (XMLManager.instance().getFirstChildNodeValue(ele)));
                ele = (Element) root.getElementsByTagName("CountryID").item(0);
                hash.put("CountryID", (XMLManager.instance().getFirstChildNodeValue(ele)));
                ele = (Element) root.getElementsByTagName("Caps").item(0);
                hash.put("Caps", (XMLManager.instance().getFirstChildNodeValue(ele)));
                ele = (Element) root.getElementsByTagName("CapsU20").item(0);
                hash.put("CapsU20", (XMLManager.instance().getFirstChildNodeValue(ele)));
                ele = (Element) root.getElementsByTagName("Cards").item(0);
                hash.put("Cards", (XMLManager.instance().getFirstChildNodeValue(ele)));
                ele = (Element) root.getElementsByTagName("InjuryLevel").item(0);
                hash.put("InjuryLevel", (XMLManager.instance().getFirstChildNodeValue(ele)));
                ele = (Element) root.getElementsByTagName("StaminaSkill").item(0);
                hash.put("StaminaSkill", (XMLManager.instance().getFirstChildNodeValue(ele)));
                ele = (Element) root.getElementsByTagName("KeeperSkill").item(0);
                hash.put("KeeperSkill", (XMLManager.instance().getFirstChildNodeValue(ele)));
                ele = (Element) root.getElementsByTagName("PlaymakerSkill").item(0);
                hash.put("PlaymakerSkill", (XMLManager.instance().getFirstChildNodeValue(ele)));
                ele = (Element) root.getElementsByTagName("ScorerSkill").item(0);
                hash.put("ScorerSkill", (XMLManager.instance().getFirstChildNodeValue(ele)));
                ele = (Element) root.getElementsByTagName("PassingSkill").item(0);
                hash.put("PassingSkill", (XMLManager.instance().getFirstChildNodeValue(ele)));
                ele = (Element) root.getElementsByTagName("WingerSkill").item(0);
                hash.put("WingerSkill", (XMLManager.instance().getFirstChildNodeValue(ele)));
                ele = (Element) root.getElementsByTagName("DefenderSkill").item(0);
                hash.put("DefenderSkill", (XMLManager.instance().getFirstChildNodeValue(ele)));
                ele = (Element) root.getElementsByTagName("SetPiecesSkill").item(0);
                hash.put("SetPiecesSkill", (XMLManager.instance().getFirstChildNodeValue(ele)));

                //Trainer
                try {
                    root = (Element) root.getElementsByTagName("TrainerData").item(0);
                    ele = (Element) root.getElementsByTagName("TrainerType").item(0);
                    hash.put("TrainerType", (XMLManager.instance().getFirstChildNodeValue(ele)));
                    ele = (Element) root.getElementsByTagName("TrainerSkill").item(0);
                    hash.put("TrainerSkill", (XMLManager.instance().getFirstChildNodeValue(ele)));
                } catch (Exception ep) {
                }

                liste.add(hash);
            }
        } catch (Exception e) {
        }

        return liste;
    }
}