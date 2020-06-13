package com.bridgelabz.fundoonotes.controller;

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
	
	
	@PostMapping("/create")
	private ResponseEntity<Response> createNote(@RequestBody NoteDto notedto, @RequestHeader("token") String token)throws NoteException{
		
		return noteService.createNote(notedto, token);	
	}
	
	@PostMapping("/updatenote")
	public ResponseEntity<Response> updateNote(@RequestBody NoteDto notedto, @RequestParam("id") long id, @RequestHeader("token") String token) {
		
		return noteService.updateNote(notedto, id, token);
	}
	
	@PutMapping("/addcolor")
	public ResponseEntity<Response> addColor(@RequestHeader("token") String token, @RequestParam("id") long id, @RequestParam("color") String color){
		
		return noteService.addColor(token, id, color);
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<Response> deleteNote(@RequestHeader("token") String token, @RequestParam("id") long id){		

		return noteService.deleteNote(token, id);	
	}
	
	@DeleteMapping("/archive")
	public ResponseEntity<Response> archieveNote(@RequestHeader("token") String token, @RequestParam("id") long noteId) {
		
		return noteService.isArchivedNote(token, noteId);
	}
	
	@GetMapping("/allnotes")
	public ResponseEntity<Response> getAllNotes(@RequestHeader("token") String token)  {
		
		List<NoteModel> notesList = noteService.getAllNotes(token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("All notes of user", Util.OK_RESPONSE_CODE, notesList));
	}
	
	@PostMapping("/setReminder/{id}")
	public ResponseEntity<Response> setReminder(@RequestHeader("token") String token, @RequestBody ReminderDateTimeDto reminderDateTimeDto, @PathVariable("id") long id) {
		
		return noteService.setReminder(token, reminderDateTimeDto, id);
	}
	
	@PutMapping("/unsetReminder/{id}")
	public ResponseEntity<Response> unsetReminder(@PathVariable("id") long id, @RequestHeader("token") String token) {

		return noteService.unsetReminder(id, token);
	}
	
	@PatchMapping("/pin/{id}")
	public ResponseEntity<Response> pinNote(@RequestHeader("token") String token, @PathVariable("id") long noteId) {

		return noteService.isPinnedNote(token, noteId);
	}
	
	@DeleteMapping("/trash")
	public ResponseEntity<Response> trashNote(@RequestHeader("token") String token,@RequestParam("id") long noteId) {

		return noteService.trashNote(token, noteId); 
	}
	
	@PutMapping("/restore")
	public ResponseEntity<Response> restoreFromTrashed(@RequestHeader("token") String token, @RequestParam("id") long noteId) {
		 
		return noteService.restoreNote(token, noteId);
	}
	
	
	
}
