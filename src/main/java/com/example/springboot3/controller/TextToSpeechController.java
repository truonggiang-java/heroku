package com.example.springboot3.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

@RestController
@RequestMapping("/api/v1/text")
public class TextToSpeechController {
	
	@GetMapping
	public void textToSpeech(@RequestParam("text") String text) {
		System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
	    Voice voice = VoiceManager.getInstance().getVoice("kevin16");
	    if (voice != null) {
	        voice.allocate();// Allocating Voice
	        try {
	        	// sets the rate (words per minute i.e. 190) of the speech
				voice.setRate(150);
				// sets the baseline pitch (150) in hertz
				voice.setPitch(150);
				// sets the volume (10) of the voice
				voice.setVolume(10);
	            voice.speak(text);//Calling speak() method

	        } catch (Exception e1) {
	            e1.printStackTrace();
	        }

	    } else {
	        throw new IllegalStateException("Cannot find voice: kevin16");
	    }
	}
}
