package pinheiro.douglas.voicerecorder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.Mixer.Info;
import javax.sound.sampled.TargetDataLine;

import pinheiro.douglas.voicerecorder.ui.VoiceRecorderUI;

public class VoiceRecordManager {
	
	private VoiceRecorderUI voiceRecorderUI;
	private TargetDataLine target;
	private boolean recording = false;
	
	public VoiceRecordManager() {
		super();
		this.voiceRecorderUI = new VoiceRecorderUI(this);
	}
	
	public static void main(String[] args) {
		VoiceRecordManager vrm = new VoiceRecordManager();
		vrm.getVoiceRecorderUI().startUI();
	}

	public List<String> getAudioLines() {
		List<String> audioLines = new ArrayList<>();
		 Mixer.Info[] mixerInfos = AudioSystem.getMixerInfo();
		  // audio input:
		  for (int i = 0; i < mixerInfos.length; i++) {
		    Mixer mixer = AudioSystem.getMixer(mixerInfos[i]);
		    Line.Info[] lines = mixer.getTargetLineInfo();
		    for (int j = 0; j < lines.length; j++) {
		      if (lines[j] instanceof DataLine.Info) {
		    	  audioLines.add(mixer.getMixerInfo().getName());
		    	  break;
		      }
		    }
		  }
		
		return audioLines;
	}

	public VoiceRecorderUI getVoiceRecorderUI() {
		return voiceRecorderUI;
	}

	public void setVoiceRecorderUI(VoiceRecorderUI voiceRecorderUI) {
		this.voiceRecorderUI = voiceRecorderUI;
	}

	public void record(String lineName) {
		Mixer.Info[] mixer = AudioSystem.getMixerInfo();
		Optional<Info> line = Arrays.stream(mixer).filter(m -> m.getName().equals(lineName)).findFirst();
		if(line.isPresent()) {
			try {
				target = AudioSystem.getTargetDataLine(new AudioFormat(44100, 8, 2, true, true), line.get());
				target.open();
				target.start();
				
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] buffer = new byte[4096];
				while(recording && this.target.isOpen()) {
					int bytesRead = this.target.read(buffer, 0, buffer.length);
					baos.write(buffer, 0, bytesRead);
				}
				ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());				
				AudioInputStream ais = new AudioInputStream(bais, new AudioFormat(44100, 8, 2, true, true), bais.available());
				bais.close();
				AudioSystem.write(ais, AudioFileFormat.Type.WAVE, new File("D:/Teste.wav"));
				ais.close();
				baos.close();
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void stop() {
		this.target.stop();
	}

	public boolean isRecording() {
		return recording;
	}

	public void setRecording(boolean recording) {
		this.recording = recording;
	}

}
