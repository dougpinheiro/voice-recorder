package pinheiro.douglas.voicerecorder;

import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Line;
import javax.sound.sampled.Mixer;

public class VoiceRecordManager {
	
	public VoiceRecordManager() {
		super();
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
	
	

}
