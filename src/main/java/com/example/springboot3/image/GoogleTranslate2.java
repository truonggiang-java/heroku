package com.example.springboot3.image;


import java.io.IOException;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;


public class GoogleTranslate2 {
	
    public static void main(String[] args) throws IOException {
//    	System.out.println(GoogleTranslate.translate("en", "Tôi muốn đi ăn kem"));
//    	 LanguageIdentifier identifier = new LanguageIdentifier("Hello, this is javatpoint.");  
//         String language = identifier.getLanguage();  
//         System.out.println("Language code is : " + language); 
    	Voice voice;//Creating object of Voice class
        voice = VoiceManager.getInstance().getVoice("kevin");//Getting voice
        if (voice != null) {
            voice.allocate();//Allocating Voice
        }
        try {
            voice.setRate(190);//Setting the rate of the voice
            voice.setPitch(150);//Setting the Pitch of the voice
            voice.setVolume(3);//Setting the volume of the voice
            voice.speak("Hello this is Tutorials Field");//Calling speak() method
  
        }
        catch(Exception e)
        {
       e.printStackTrace();
        }
    }
}
