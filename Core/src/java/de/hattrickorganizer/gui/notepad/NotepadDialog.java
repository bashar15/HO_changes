package de.hattrickorganizer.gui.notepad;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import de.hattrickorganizer.gui.templates.ImagePanel;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.tools.HOLogger;
import de.hattrickorganizer.tools.Helper;

public class NotepadDialog extends JDialog implements ActionListener{

    private final int dialogWidth = 480;
	private final int dialogHeight = 320;
	private final JTextArea textArea = new JTextArea();
	private File file = null;
	private final JButton cmdSave	 = new JButton(new ImageIcon(Helper.loadImage("gui/bilder/disk.png")));
	
	
	public NotepadDialog(JFrame owner, String title){
		super(owner,title);
		initialize();
	}
	
	private void initialize(){
		file = new File(HOVerwaltung.instance().getModel().getBasics().getTeamId()+"_note.txt");
		readFile();
        int with = (int) GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds()
                                            .getWidth();
        int height = (int) GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds()
	                                              .getHeight();
	    setLocation((with - dialogWidth) / 2, (height - dialogHeight) / 2);
	    setSize(dialogWidth, dialogHeight);

		cmdSave.setPreferredSize(new Dimension(25,25));
		cmdSave.setBackground(Color.WHITE);
		cmdSave.addActionListener( this );
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(getButtonPanel(), BorderLayout.NORTH);
		getContentPane().add(new JScrollPane(textArea),BorderLayout.CENTER);
	}
	
	private JPanel getButtonPanel(){
		JPanel panel = new ImagePanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel.add(cmdSave);
		return panel;
	}

	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == cmdSave){
			write();
		}
		
	}
	
	protected void readFile(){
        try {
        	if(!file.exists())
        		return;
            BufferedReader in = new BufferedReader(new FileReader(file));
            StringBuffer txt = new StringBuffer();
            String line;
            while((line = in.readLine()) != null){
                txt.append(line+"\n");
            }
            textArea.setText(txt.toString());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            HOLogger.instance().log(getClass(),e);
        }
    }

	private void write(){
        try {
            FileWriter fileWriter = new FileWriter(file, false);
            fileWriter.write(textArea.getText());
            fileWriter.flush();
            fileWriter.close();

        } catch (Exception innerEx) {
            HOLogger.instance().log(getClass(),innerEx);
        }
	}
}