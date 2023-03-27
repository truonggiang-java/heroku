package com.example.springboot3.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.springboot3.dto.UserDto;
import com.example.springboot3.repository.UserRepository;
import com.example.springboot3.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	private static final String sourceUrl = System.getProperty("user.dir") + "\\src\\main\\resources\\static" + "\\file";
	
	@Autowired
    private JobLauncher jobLauncher;
	
    @Autowired
    private Job job;
    
	@PostMapping("/createUser")
	public ResponseEntity<?> insertUser(@RequestBody UserDto userDto){
		return userService.insertUser(userDto);
	}
	
	@GetMapping("/get")
	public ResponseEntity<?> getUser(){
		return ResponseEntity.ok(userRepository.findAll());
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<?> getUser(@PathVariable("id") Integer id) throws Exception{
		return ResponseEntity.ok(userRepository.findById(id).orElseThrow(()->new Exception("There is no such user?")));
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<?> updateFieldUser(@PathVariable("id") Integer id,@RequestBody Map<String,Object> maps){
		return  ResponseEntity.ok(userService.updateFieldUser(id, maps));
	}
	
	@PostMapping("/file")
	public ResponseEntity<?> insertDataByFileCsv(@RequestParam("file") MultipartFile file) throws IOException{
		return ResponseEntity.ok(userService.insertUserFile(file));
	}
	
	@PostMapping("/importUser")
    public void importCsvToDBJob(@RequestParam("file") MultipartFile file) throws JobRestartException, IllegalStateException, IOException {
		File convFile = new File(sourceUrl);
		if (!convFile.exists()) {
			convFile.mkdir();
		}
		String[] filename=file.getOriginalFilename().split("\\.") ;
		String pathFile=sourceUrl+"\\"+filename[0] +"-"+System.currentTimeMillis()+"."+filename[1];
		Path path = Paths.get(pathFile);
		file.transferTo(path);
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("fileName", pathFile).toJobParameters();
        try {
            jobLauncher.run(job, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            e.printStackTrace();
        }
    }
	
	@PostMapping("/sort")
	public ResponseEntity<?> sortUser(@RequestParam("page") Integer page,@RequestParam("limit") Integer limit,@RequestBody Map<String,Object> fields){
		return ResponseEntity.ok(userService.sortUserAndPage(page, limit,fields));
	}
	
	@GetMapping("/all")
	public ResponseEntity<?> findAllUser(){
		try {
			
			return ResponseEntity.ok(userService.findAllUser());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
			// TODO: handle exception
		}
	}
}
