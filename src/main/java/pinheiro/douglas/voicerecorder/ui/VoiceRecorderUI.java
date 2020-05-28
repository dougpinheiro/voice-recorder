package pinheiro.douglas.voicerecorder.ui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import pinheiro.douglas.voicerecorder.VoiceRecordManager;

public class VoiceRecorderUI {
	
	private JButton btnRecord = new JButton("Record");
	private VoiceRecordManager manager = new VoiceRecordManager();
	
	public static void main(String[] args) {
		VoiceRecorderUI vr = new VoiceRecorderUI();
		vr.startUI();
	}
	
	public void startUI() {
		JFrame mainFrame = new JFrame("Voice Recorder");
		mainFrame.setSize(new Dimension(640, 480));
		mainFrame.setIconImage(Toolkit.getDefaultToolkit().getImage("logo.PNG"));
		mainFrame.setResizable(false);
		mainFrame.setLocationByPlatform(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLayout(new GridLayout(2,1));
		mainFrame.setMinimumSize(new Dimension(200,250));
		String[] audioLines = parseAudioLines(manager.getAudioLines());
		JComboBox<String> cbLines = new JComboBox<>(audioLines);
		mainFrame.add(cbLines);
		this.getBtnRecord().setSize(new Dimension(60, 32));
		mainFrame.add(this.getBtnRecord());
		this.getBtnRecord().addActionListener((e) -> {
			System.out.println("Clicked!");
		});
		mainFrame.pack();
		mainFrame.setVisible(true);
	}
	
	public String[] parseAudioLines(List<String> lines){
		String[] strList = new String[lines.size()];
		return lines.toArray(strList);
	}

	public JButton getBtnRecord() {
		return btnRecord;
	}

	public void setBtnRecord(JButton btnRecord) {
		this.btnRecord = btnRecord;
	}
}
