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
	private VoiceRecordManager vrm;
	
	public VoiceRecorderUI(VoiceRecordManager vrm) {
		super();
		this.vrm = vrm;
	}

	public VoiceRecorderUI(JButton btnRecord) {
		super();
		this.btnRecord = btnRecord;
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
		String[] audioLines = parseAudioLines(this.getVrm().getAudioLines());
		JComboBox<String> cbLines = new JComboBox<>(audioLines);
		mainFrame.add(cbLines);
		this.getBtnRecord().setSize(new Dimension(60, 32));
		mainFrame.add(this.getBtnRecord());
		this.getBtnRecord().addActionListener((e) -> {
			if(getBtnRecord().getText().equals("Record")) {
				setRecording(true);					
				new Thread(() -> this.vrm.record(String.valueOf(cbLines.getSelectedItem()))).start();
			} else {
				setRecording(false);					
				new Thread(() -> this.vrm.stop()).start();
			}
		});
		mainFrame.pack();
		mainFrame.setVisible(true);
	}
	
	private void setRecording(boolean recording) {
		if(recording) {
			this.getVrm().setRecording(true);
			this.getBtnRecord().setText("Stop");			
		} else {
			this.getVrm().setRecording(false);
			this.getBtnRecord().setText("Record");			
		}
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

	public VoiceRecordManager getVrm() {
		return vrm;
	}

	public void setVrm(VoiceRecordManager vrm) {
		this.vrm = vrm;
	}
}
