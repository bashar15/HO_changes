// %1218743623:de.hattrickorganizer.model%
/*
 * ServerTeam.java
 *
 * Created on 7. Mai 2003, 07:27
 */
package de.hattrickorganizer.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;

import plugins.ISpielerPosition;
import de.hattrickorganizer.tools.HOLogger;


/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */

/*
   Schnittstellen Obj f�r Heim und Gast Verein erstellen
   - Name
   - Managername
   - Aufstellung ( Kopie von m_vPositionen )
   - ElfmeterListe
   - getTWStk
   - getABWStk
   - getMFStk
   - getStStk
   - getZufaelligenSpieler...
   - getGesamtStk
   - getPositionBySpielerID
   - getElfmeterschuetze( ElferNr )
 */
public class ServerTeam implements java.io.Serializable {
    //~ Instance fields ----------------------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    private String m_sManagerName = "";

    /** TODO Missing Parameter Documentation */
    private String m_sTeamName = "";

    /** TODO Missing Parameter Documentation */
    private Vector m_vPositionen = new Vector();

    /** TODO Missing Parameter Documentation */
    private Vector m_vSpieler = new Vector();

    /** TODO Missing Parameter Documentation */
    private Vector m_vStartAufstellung = new Vector();

    /** TODO Missing Parameter Documentation */
    private int[] m_aElferKicker;

    /** TODO Missing Parameter Documentation */
    private int m_iKapitaen = -1;

    /** TODO Missing Parameter Documentation */
    private int m_iKicker = -1;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new instance of ServerTeam
     *
     * @param auf TODO Missing Constructuor Parameter Documentation
     * @param teamName TODO Missing Constructuor Parameter Documentation
     * @param managerName TODO Missing Constructuor Parameter Documentation
     * @param serverSpieler TODO Missing Constructuor Parameter Documentation
     */
    public ServerTeam(Aufstellung auf, String teamName, String managerName, Vector serverSpieler) {
        Vector temp = null;
        SpielerPosition pos = null;

        m_aElferKicker = auf.getBestElferKicker();
        m_sTeamName = teamName;
        m_sManagerName = managerName;
        m_iKicker = auf.getKicker();
        m_iKapitaen = auf.getKapitaen();
        m_vSpieler = serverSpieler;

        temp = auf.getPositionen();

        //Spielerpositionen kopieren
        for (int i = 0; (temp != null) && (i < temp.size()); i++) {
            pos = (SpielerPosition) temp.elementAt(i);

            //SpielerPosition merken
            m_vPositionen.addElement(new SpielerPosition(pos));

            //StartAufstellung merken
            if ((pos.getId() < ISpielerPosition.beginnReservere) && (pos.getSpielerId() > 0)) {
                m_vStartAufstellung.add(new Integer(pos.getSpielerId()));
            }
        }
    }

    ///////////////Load/Save////////////////////////////////

    /**
     * Creates a new instance of ServerTeam
     *
     * @param dis TODO Missing Constructuor Parameter Documentation
     */
    public ServerTeam(DataInputStream dis) {
        //        DataInputStream dis         =   null;
        int anzahl = -1;

        try {
            //Einzulesenden Strom konvertieren
            //bais = new ByteArrayInputStream(data);
            // dis  = new DataInputStream (bais);            
            //Daten auslesen
            m_sTeamName = dis.readUTF();
            m_sManagerName = dis.readUTF();
            m_iKapitaen = dis.readInt();
            m_iKicker = dis.readInt();

            //Elfer
            anzahl = dis.readInt();
            m_aElferKicker = new int[anzahl];

            for (int i = 0; i < anzahl; i++) {
                m_aElferKicker[i] = dis.readInt();
            }

            //Aufstellung
            anzahl = dis.readInt();
            m_vStartAufstellung = new Vector();

            for (int i = 0; i < anzahl; i++) {
                m_vStartAufstellung.addElement(new Integer(dis.readInt()));
            }

            //ServerSpieler
            anzahl = dis.readInt();
            m_vSpieler = new Vector();

            for (int i = 0; i < anzahl; i++) {
                m_vSpieler.addElement(new ServerSpieler(dis));
            }

            //SpielerPosition
            anzahl = dis.readInt();
            m_vPositionen = new Vector();

            for (int i = 0; i < anzahl; i++) {
                m_vPositionen.addElement(new SpielerPosition(dis));
            }

            //Und wieder schliessen
            //dis.close ();
        } catch (IOException ioe) {
            HOLogger.instance().log(getClass(),ioe);
        }
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final float getAWTeamStk() {
        float stk = 0.0f;
        stk += calcTeamStk(m_vSpieler, ISpielerPosition.INNENVERTEIDIGER);
        stk += calcTeamStk(m_vSpieler, ISpielerPosition.AUSSENVERTEIDIGER_OFF);
        stk += calcTeamStk(m_vSpieler, ISpielerPosition.INNENVERTEIDIGER_OFF);
        stk += calcTeamStk(m_vSpieler, ISpielerPosition.AUSSENVERTEIDIGER);
        stk += calcTeamStk(m_vSpieler, ISpielerPosition.INNENVERTEIDIGER_AUS);
        stk += calcTeamStk(m_vSpieler, ISpielerPosition.AUSSENVERTEIDIGER_IN);
        stk += calcTeamStk(m_vSpieler, ISpielerPosition.AUSSENVERTEIDIGER_DEF);

        return de.hattrickorganizer.tools.Helper.round(stk, 1);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int getAnzAbwehr() {
        int anzahl = 0;

        anzahl += getAnzPosImSystem(ISpielerPosition.AUSSENVERTEIDIGER);
        anzahl += getAnzPosImSystem(ISpielerPosition.AUSSENVERTEIDIGER_IN);
        anzahl += getAnzPosImSystem(ISpielerPosition.AUSSENVERTEIDIGER_OFF);
        anzahl += getAnzPosImSystem(ISpielerPosition.AUSSENVERTEIDIGER_DEF);
        anzahl += getAnzPosImSystem(ISpielerPosition.INNENVERTEIDIGER);
        anzahl += getAnzPosImSystem(ISpielerPosition.INNENVERTEIDIGER_AUS);
        anzahl += getAnzPosImSystem(ISpielerPosition.INNENVERTEIDIGER_OFF);

        return anzahl;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int getAnzAufgestellteSpieler() {
        int anzahl = 0;
        SpielerPosition pos = null;

        for (int i = 0; (m_vPositionen != null) && (i < m_vPositionen.size()); i++) {
            pos = (SpielerPosition) m_vPositionen.elementAt(i);

            if ((pos.getSpielerId() > 0) && (pos.getId() < ISpielerPosition.beginnReservere)) {
                ++anzahl;
            }
        }

        return anzahl;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int getAnzSturm() {
        int anzahl = 0;

        anzahl += getAnzPosImSystem(ISpielerPosition.STURM);
        anzahl += getAnzPosImSystem(ISpielerPosition.STURM_AUS);
        anzahl += getAnzPosImSystem(ISpielerPosition.STURM_DEF);

        return anzahl;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int getAnzTW() {
        int anzahl = 0;

        anzahl += getAnzPosImSystem(ISpielerPosition.TORWART);

        return anzahl;
    }

    /**
     * Setter for property m_aElferKicker.
     *
     * @param m_aElferKicker New value of property m_aElferKicker.
     */
    public final void setElferKicker(int[] m_aElferKicker) {
        this.m_aElferKicker = m_aElferKicker;
    }

    /**
     * Getter for property m_aElferKicker.
     *
     * @return Value of property m_aElferKicker.
     */
    public final int[] getElferKicker() {
        return this.m_aElferKicker;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final float getGesamtStk() {
        return getTWTeamStk() + getAWTeamStk() + getMFTeamStk() + getSTTeamStk();
    }

    /**
     * Setter for property m_iKapitaen.
     *
     * @param m_iKapitaen New value of property m_iKapitaen.
     */
    public final void setKapitaen(int m_iKapitaen) {
        this.m_iKapitaen = m_iKapitaen;
    }

    /**
     * Getter for property m_iKapitaen.
     *
     * @return Value of property m_iKapitaen.
     */
    public final int getKapitaen() {
        return m_iKapitaen;
    }

    /**
     * Setter for property m_iKicker.
     *
     * @param m_iKicker New value of property m_iKicker.
     */
    public final void setKicker(int m_iKicker) {
        this.m_iKicker = m_iKicker;
    }

    /**
     * Getter for property m_iKicker.
     *
     * @return Value of property m_iKicker.
     */
    public final int getKicker() {
        return m_iKicker;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final float getMFTeamStk() {
        float stk = 0.0f;
        stk += calcTeamStk(m_vSpieler, ISpielerPosition.MITTELFELD);
        stk += calcTeamStk(m_vSpieler, ISpielerPosition.FLUEGELSPIEL);
        stk += calcTeamStk(m_vSpieler, ISpielerPosition.MITTELFELD_OFF);
        stk += calcTeamStk(m_vSpieler, ISpielerPosition.FLUEGELSPIEL_OFF);
        stk += calcTeamStk(m_vSpieler, ISpielerPosition.MITTELFELD_DEF);
        stk += calcTeamStk(m_vSpieler, ISpielerPosition.FLUEGELSPIEL_DEF);
        stk += calcTeamStk(m_vSpieler, ISpielerPosition.MITTELFELD_AUS);
        stk += calcTeamStk(m_vSpieler, ISpielerPosition.FLUEGELSPIEL_IN);

        return de.hattrickorganizer.tools.Helper.round(stk, 1);
    }

    /**
     * Setter for property m_sManagerName.
     *
     * @param m_sManagerName New value of property m_sManagerName.
     */
    public final void setManagerName(java.lang.String m_sManagerName) {
        this.m_sManagerName = m_sManagerName;
    }

    /**
     * Getter for property m_sManagerName.
     *
     * @return Value of property m_sManagerName.
     */
    public final java.lang.String getManagerName() {
        return m_sManagerName;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param player TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final byte getMannschaftsteil4Spieler(ServerSpieler player) {
        byte pos = ISpielerPosition.UNBESTIMMT;
        byte ret = 0;

        pos = getSpielerPositionBySpielerID(player.getID());
        ret = getMannschaftsteil4Position(pos);

        return ret;
    }

    /**
     * Gibt alle SpielerPositionen als Array zur�ck
     *
     * @return TODO Missing Return Method Documentation
     */
    public final SpielerPosition[] getPositionen() {
        final SpielerPosition[] positionen = new SpielerPosition[m_vPositionen.size()];

        for (int i = 0; i < m_vPositionen.size(); i++) {
            positionen[i] = (SpielerPosition) m_vPositionen.get(i);
        }

        java.util.Arrays.sort(positionen);
        return positionen;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param mt TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int getReserveSpielerId4Mannschaftsteil(byte mt) {
        int id = -1;

        switch (mt) {
            case de.hattrickorganizer.logik.SpielLogik.MT_TORWART:
                id = getSpielerID4Position(ISpielerPosition.substKeeper);
                break;

            case de.hattrickorganizer.logik.SpielLogik.MT_ABWEHR:
                id = getSpielerID4Position(ISpielerPosition.substBack);
                break;

            case de.hattrickorganizer.logik.SpielLogik.MT_MITTELFELD:
                id = getSpielerID4Position(ISpielerPosition.substInsideMid);

                if (id == -1) {
                    id = getSpielerID4Position(ISpielerPosition.substWinger);
                }

                break;

            case de.hattrickorganizer.logik.SpielLogik.MT_STURM:
                id = getSpielerID4Position(ISpielerPosition.substForward);
                break;
        }

        if (id <= 0) {
            id = getSpielerID4Position(ISpielerPosition.substInsideMid);
        }

        return id;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final float getSTTeamStk() {
        float stk = 0.0f;
        stk += calcTeamStk(m_vSpieler, ISpielerPosition.STURM);
        stk += calcTeamStk(m_vSpieler, ISpielerPosition.STURM_AUS);
        stk += calcTeamStk(m_vSpieler, ISpielerPosition.STURM_DEF);

        return de.hattrickorganizer.tools.Helper.round(stk, 1);
    }

    /**
     * liefert einen Spieler der auf dem Platz steht
     *
     * @param nr von 0 - anzAufgestellteSpieler - 1 Achtung TW wird ignoriert! )
     *
     * @return TODO Missing Return Method Documentation
     */
    public final ServerSpieler getSpielerAufFeld(byte nr) {
        SpielerPosition pos = null;
        final Vector liste = new Vector();
        ServerSpieler player = null;

        //Liste nur mit vergebenen Positionen innnerhalb der ersten elf f�llen
        for (int i = 0; (m_vPositionen != null) && (i < m_vPositionen.size()); i++) {
            pos = (SpielerPosition) m_vPositionen.elementAt(i);

            if ((pos.getId() < ISpielerPosition.beginnReservere)
                && (pos.getSpielerId() > 0)
                && (pos.getId() != ISpielerPosition.keeper)) {
                liste.addElement(new Integer(pos.getSpielerId()));
            }
        }

        //Spieler ausw�hlen
        if (!liste.isEmpty()) {
            if (nr > (liste.size() - 1)) {
                nr = (byte) de.hattrickorganizer.logik.SpielLogik.getZufallsZahl(liste.size());
            }

            player = getSpielerById(((Integer) liste.elementAt(nr)).intValue());
        }

        /*
           for ( int i = 0; m_vPositionen != null && i < m_vPositionen.size ();i++ )
           {
               pos =   (SpielerPosition) m_vPositionen.elementAt ( i );
        
               if ( pos.getId () < SpielerPosition.beginnReservere )
               {
                   if ( anzahl == nr )
                   {
                       //TW ignorieren
                       if ( ( pos.getId () == SpielerPosition.keeper )
                           || ( pos.getSpielerId() <= 0 ) )
                       {
                           continue;
                       }
        
                       return getSpielerById( pos.getSpielerId () );
                   }
                   ++anzahl;
               }
           }
         */
        if (player == null) {
            System.err.println("getSpielerAufFeld: Kein Spieler gefunden!");

            for (int i = 0; i < liste.size(); i++) {
                player = getSpielerById(((Integer) liste.elementAt(nr)).intValue());

                if (player != null) {
                    return player;
                }
            }
        }

        return player;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param id TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final boolean isSpielerAufFeld(int id) {
        SpielerPosition pos = null;

        for (int i = 0; (id > 0) && (m_vPositionen != null) && (i < m_vPositionen.size()); i++) {
            pos = (SpielerPosition) m_vPositionen.elementAt(i);

            if ((pos.getId() < ISpielerPosition.beginnReservere)
                && (pos.getSpielerId() == id)
                && (pos.getSpielerId() > 0)) {
                return true;
            }
        }

        return false;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param id TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final ServerSpieler getSpielerById(int id) {
        ServerSpieler player = null;

        for (int i = 0; (m_vSpieler != null) && (i < m_vSpieler.size()); i++) {
            player = (ServerSpieler) m_vSpieler.elementAt(i);

            if (player.getID() == id) {
                return player;
            }
        }

        return null;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param id TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final SpielerPosition getSpielerPositionObjBySpielerID(int id) {
        SpielerPosition pos = null;

        for (int i = 0; (m_vPositionen != null) && (i < m_vPositionen.size()); i++) {
            pos = (SpielerPosition) m_vPositionen.elementAt(i);

            if (pos.getSpielerId() == id) {
                return pos;
            }
        }

        return null;
    }

    /**
     * Setter for property m_vStartAufstellung.
     *
     * @param m_vStartAufstellung New value of property m_vStartAufstellung.
     */
    public final void setStartAufstellung(java.util.Vector m_vStartAufstellung) {
        this.m_vStartAufstellung = m_vStartAufstellung;
    }

    /**
     * Getter for property m_vStartAufstellung.
     *
     * @return Value of property m_vStartAufstellung.
     */
    public final java.util.Vector getStartAufstellung() {
        return m_vStartAufstellung;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final float getTWTeamStk() {
        float stk = 0.0f;
        stk += calcTeamStk(m_vSpieler, ISpielerPosition.TORWART);

        return de.hattrickorganizer.tools.Helper.round(stk, 1);
    }

    /**
     * Setter for property m_sTeamName.
     *
     * @param m_sTeamName New value of property m_sTeamName.
     */
    public final void setTeamName(java.lang.String m_sTeamName) {
        this.m_sTeamName = m_sTeamName;
    }

    /**
     * Getter for property m_sTeamName.
     *
     * @return Value of property m_sTeamName.
     */
    public final java.lang.String getTeamName() {
        return m_sTeamName;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param mt TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final ServerSpieler getZufaelligenSpieler(byte mt) {
        int wert = 0;
        ServerSpieler player = null;
        final Vector liste = new Vector();
        SpielerPosition pos = null;

        if (!isSpieler4MannschaftsteilAufgestellt(mt)) {
            System.err.println("getZufaelligenSpieler: umleiten zu SpielerAufFeld");
            return getSpielerAufFeld((byte) getAnzAufgestellteSpieler());
        }

        //Liste f�llen
        for (int i = 0; (m_vPositionen != null) && (i < m_vPositionen.size()); i++) {
            pos = (SpielerPosition) m_vPositionen.elementAt(i);

            if ((getMannschaftsteil4Position(pos.getPosition()) == mt)
                && (pos.getId() < ISpielerPosition.beginnReservere)
                && (pos.getSpielerId() > 0)) {
                liste.addElement(new Integer(pos.getSpielerId()));
            }
        }

        //Spieler ausw�hlen
        if (!liste.isEmpty()) {
            wert = de.hattrickorganizer.logik.SpielLogik.getZufallsZahl(liste.size());
            player = this.getSpielerById(((Integer) liste.elementAt(wert)).intValue());
        }

        /*
           do
           {
               wert    = ( byte )( logik.SpielLogik.getZufallsZahl() % getAnzAufgestellteSpieler() );
               player        = getSpielerAufFeld (wert);
           }
         */
        if (player == null) {
            System.err.println("getZufaelligenSpieler: nix gefunden :(");
        }

        return player;
    }

    /*
       saved das ServerTeam
       @param baos Der Outputstream in den gesaved werden soll
       @return Byte Array der Daten die in den Output geschireben wurden
     */
    public final void save(DataOutputStream das) {
        //ByteArrayOutputStream   baos    =  null;
        //DataOutputStream        das     =   null;
        //Byte Array
        //	byte[]                  data    =   null;
        try {
            //Instanzen erzeugen
            //baos = new ByteArrayOutputStream();
            //  das = new DataOutputStream(baos);
            //Daten schreiben in Strom
            das.writeUTF(m_sTeamName);
            das.writeUTF(m_sManagerName);
            das.writeInt(m_iKapitaen);
            das.writeInt(m_iKicker);

            //Elferkicker saven
            if (m_aElferKicker != null) {
                das.writeInt(m_aElferKicker.length);

                for (int i = 0; i < m_aElferKicker.length; i++) {
                    das.writeInt(m_aElferKicker[i]);
                }
            } else {
                das.writeInt(0);
            }

            //StartAufstellung saven
            if (m_vStartAufstellung != null) {
                das.writeInt(m_vStartAufstellung.size());

                for (int i = 0; i < m_vStartAufstellung.size(); i++) {
                    das.writeInt(((Integer) m_vStartAufstellung.elementAt(i)).intValue());
                }
            } else {
                das.writeInt(0);
            }

            //Spieler saven
            if (m_vSpieler != null) {
                das.writeInt(m_vSpieler.size());

                for (int i = 0; i < m_vSpieler.size(); i++) {
                    ((ServerSpieler) m_vSpieler.elementAt(i)).save(das);
                }
            } else {
                das.writeInt(0);
            }

            //Positionen
            if (m_vPositionen != null) {
                das.writeInt(m_vPositionen.size());

                for (int i = 0; i < m_vPositionen.size(); i++) {
                    ((SpielerPosition) m_vPositionen.elementAt(i)).save(das);
                }
            } else {
                das.writeInt(0);
            }

            /*       //Strom konvertieren in Byte
               data = baos.toByteArray();
               //Hilfsstrom schlie�en
               das.close ();
            
               return data;
             */
        } catch (IOException ioe) {
            HOLogger.instance().log(getClass(),ioe);
        }

        //return data;
    }

    /**
     * berechnet Anzahl Abwehr im System
     *
     * @return TODO Missing Return Method Documentation
     */
    private final int getAnzMittelfeld() {
        int anzahl = 0;

        anzahl += getAnzPosImSystem(ISpielerPosition.MITTELFELD);
        anzahl += getAnzPosImSystem(ISpielerPosition.MITTELFELD_OFF);
        anzahl += getAnzPosImSystem(ISpielerPosition.MITTELFELD_DEF);
        anzahl += getAnzPosImSystem(ISpielerPosition.MITTELFELD_AUS);
        anzahl += getAnzPosImSystem(ISpielerPosition.FLUEGELSPIEL);
        anzahl += getAnzPosImSystem(ISpielerPosition.FLUEGELSPIEL_IN);
        anzahl += getAnzPosImSystem(ISpielerPosition.FLUEGELSPIEL_OFF);
        anzahl += getAnzPosImSystem(ISpielerPosition.FLUEGELSPIEL_DEF);

        return anzahl;
    }

    /**
     * ermittelt ANzahl der gesuchten Pos im aktuellen System
     *
     * @param position TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private final int getAnzPosImSystem(byte position) {
        SpielerPosition pos = null;
        int anzahl = 0;

        for (int i = 0; (m_vPositionen != null) && (i < m_vPositionen.size()); i++) {
            pos = (SpielerPosition) m_vPositionen.elementAt(i);

            if ((position == pos.getPosition())
                && (pos.getId() < ISpielerPosition.beginnReservere)
                && (pos.getSpielerId() > 0)) {
                ++anzahl;
            }
        }

        return anzahl;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param pos TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private final byte getMannschaftsteil4Position(byte pos) {
        byte ret = 0;

        switch (pos) {
            case ISpielerPosition.TORWART:
                ret = de.hattrickorganizer.logik.SpielLogik.MT_TORWART;
                break;

            case ISpielerPosition.INNENVERTEIDIGER:
            case ISpielerPosition.INNENVERTEIDIGER_OFF:
            case ISpielerPosition.INNENVERTEIDIGER_AUS:
            case ISpielerPosition.AUSSENVERTEIDIGER:
            case ISpielerPosition.AUSSENVERTEIDIGER_OFF:
            case ISpielerPosition.AUSSENVERTEIDIGER_DEF:
            case ISpielerPosition.AUSSENVERTEIDIGER_IN:
                ret = de.hattrickorganizer.logik.SpielLogik.MT_ABWEHR;
                break;

            case ISpielerPosition.MITTELFELD:
            case ISpielerPosition.MITTELFELD_OFF:
            case ISpielerPosition.MITTELFELD_DEF:
            case ISpielerPosition.MITTELFELD_AUS:
            case ISpielerPosition.FLUEGELSPIEL:
            case ISpielerPosition.FLUEGELSPIEL_OFF:
            case ISpielerPosition.FLUEGELSPIEL_DEF:
            case ISpielerPosition.FLUEGELSPIEL_IN:
                ret = de.hattrickorganizer.logik.SpielLogik.MT_MITTELFELD;
                break;

            case ISpielerPosition.STURM:
            case ISpielerPosition.STURM_DEF:
            case ISpielerPosition.STURM_AUS:
                ret = de.hattrickorganizer.logik.SpielLogik.MT_STURM;
                break;
        }

        return ret;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param mt TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private final boolean isSpieler4MannschaftsteilAufgestellt(byte mt) {
        boolean ret = false;

        switch (mt) {
            case de.hattrickorganizer.logik.SpielLogik.MT_TORWART:
                ret = getAnzTW() > 0;
                break;

            case de.hattrickorganizer.logik.SpielLogik.MT_ABWEHR:
                ret = getAnzAbwehr() > 0;
                break;

            case de.hattrickorganizer.logik.SpielLogik.MT_MITTELFELD:
                ret = getAnzMittelfeld() > 0;
                break;

            case de.hattrickorganizer.logik.SpielLogik.MT_STURM:
                ret = getAnzSturm() > 0;
                break;
        }

        return ret;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param pos TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private final int getSpielerID4Position(int pos) {
        SpielerPosition sPos = null;

        for (int i = 0; (m_vPositionen != null) && (i < m_vPositionen.size()); i++) {
            sPos = (SpielerPosition) m_vPositionen.elementAt(i);

            if ((sPos.getId() >= ISpielerPosition.beginnReservere) && (sPos.getId() == pos)) {
                return sPos.getSpielerId();
            }
        }

        return -1;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param id TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private final byte getSpielerPositionBySpielerID(int id) {
        SpielerPosition pos = null;

        for (int i = 0; (m_vPositionen != null) && (i < m_vPositionen.size()); i++) {
            pos = (SpielerPosition) m_vPositionen.elementAt(i);

            if (pos.getSpielerId() == id) {
                return pos.getPosition();
            }
        }

        return ISpielerPosition.UNBESTIMMT;
    }

    /**
     * berechnet die stk des Spielers f�r die angegebene Position
     *
     * @param spieler TODO Missing Constructuor Parameter Documentation
     * @param spielerId TODO Missing Constructuor Parameter Documentation
     * @param position TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private final float calcPlayerStk(Vector spieler, int spielerId, byte position) {
        ServerSpieler player = null;

        for (int i = 0; (spieler != null) && (i < spieler.size()); i++) {
            player = (ServerSpieler) spieler.elementAt(i);

            if (player.getID() == spielerId) {
                return player.getStk(position);
            }
        }

        return 0.0f;
    }

    /**
     * berechnet die STK-Summe aller aufgestllten Spieler f�r diese Position
     *
     * @param spieler TODO Missing Constructuor Parameter Documentation
     * @param position TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private final float calcTeamStk(Vector spieler, byte position) {
        float stk = 0.0f;
        de.hattrickorganizer.model.SpielerPosition pos = null;

        for (int i = 0; (m_vPositionen != null) && (spieler != null) && (i < m_vPositionen.size());
             i++) {
            pos = (SpielerPosition) m_vPositionen.elementAt(i);

            if ((pos.getPosition() == position)
                && (pos.getId() < ISpielerPosition.substBack)) {
                stk += calcPlayerStk(spieler, pos.getSpielerId(), position);
            }
        }

        return de.hattrickorganizer.tools.Helper.round(stk, 1);
    }
}
