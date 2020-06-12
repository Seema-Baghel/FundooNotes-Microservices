package com.bridgelabz.fundoonotes.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.dto.ReminderDateTimeDto;
import com.bridgelabz.fundoonotes.exception.NoteException;
import com.bridgelabz.fundoonotes.exception.UserNotFoundException;
import com.bridgelabz.fundoonotes.model.NoteModel;
import com.bridgelabz.fundoonotes.model.UserModel;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.NoteService;
import com.bridgelabz.fundoonotes.utility.Util;

@RestController
@RequestMapping("/notes")
public class NoteController {

	@Autowired
	private NoteService noteService;
	
	@Autowired
	private RestTemplate restTemplate;

		
	@PostMapping("/create")
	private ResponseEntity<Response> createNote(@RequestBody NoteDto notedto, @RequestHeader("token") String token)throws NoteException{
		
		UserModel user = restTemplate.getForObject("http://User-Microservice/user/getUser/"+token,UserModel.class);

		if(user!=null) {
			noteService.createNote(notedto, token, user.getUserId());
		return ResponseEntity.status(HttpStatus.OK).body(new Response(Util.OK_RESPONSE_CODE, "Note is created successfully"));
		}
		return ResponseEntity.status(HttpStatus.OK).body(new Response(Util.BAD_REQUEST_RESPONSE_CODE, "Note not created "));
	}
	
	@PostMapping("/updatenote")
	public ResponseEntity<Response> updateNote(@RequestBody NoteDto notedto, @RequestParam("id") long id, @RequestHeader("token") String token) {
		
		UserModel user = restTemplate.getForObject("http://User-Microservice/user/getUser/"+token,UserModel.class);
		if(user!=null) {
				noteService.updateNote(notedto, id);
		 return ResponseEntity.status(HttpStatus.OK).body(new Response(Util.OK_RESPONSE_CODE, "Note is created successfully"));
		}
		return ResponseEntity.status(HttpStatus.OK).body(new Response(Util.BAD_REQUEST_RESPONSE_CODE, "Note not created "));
	}
	
//	@PutMapping("/addcolor")
//	public ResponseEntity<Response> addColor(@RequestHeader("token") String token, @RequestParam("id") long id, @RequestParam("color") String color){
//		
//		return noteService.addColor(token, id, color);
//	}
//	
//	@DeleteMapping("/delete")
//	public ResponseEntity<Response> deleteNote(@RequestHeader("token") String token, @RequestParam("id") long id){
//
//		return noteService.deleteNote(token, id);
//	}
//	
//	
//	@GetMapping("/allnotes")
//	public ResponseEntity<Response> getAllNotes(@RequestHeader("token") String token)  {
//		
//		List<NoteModel> notesList = noteService.getAllNotes(token);
//		return ResponseEntity.status(HttpStatus.OK).body(new Response("All notes of user", Util.OK_RESPONSE_CODE, notesList));
//	}
//	
	
}
