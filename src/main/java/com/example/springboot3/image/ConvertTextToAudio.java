package com.example.springboot3.image;

import com.sun.speech.freetts.*;

public class ConvertTextToAudio {
	private ConvertTextToAudio() {
		System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_time_awb.AlanVoiceDirectory");

		Voice voice;
		
		VoiceManager vm = VoiceManager.getInstance();
		voice = vm.getVoice("alan");
		voice.setPitch((float) (57));
		voice.setPitchShift((float) (2.5));
		voice.setPitchRange((float) (10));
		FreeTTS tts = new FreeTTS(voice);
		tts.setAudioFile("C:\\Users\\Cao Hoang Giang\\Downloads\\test.wav");
		tts.startup();
		voice.allocate();
		try {
			voice.speak("I want to go home");

		} catch (Exception e) {
		}
		voice.deallocate();
		tts.shutdown();
	}

	public static void main(String args[]) {
		new ConvertTextToAudio();
	}
}
