package com.example.springboot3.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.darkprograms.speech.translator.GoogleTranslate;
import com.example.springboot3.dto.LanguageDto;

@RestController
@RequestMapping("/api/v1/language")
public class LanguageController {
	
	@PostMapping
	public ResponseEntity<?> translate(@RequestBody LanguageDto languageDto) throws IOException{
		return ResponseEntity.ok(GoogleTranslate.translate(languageDto.getLanguage(), languageDto.getText()));
	}
}
