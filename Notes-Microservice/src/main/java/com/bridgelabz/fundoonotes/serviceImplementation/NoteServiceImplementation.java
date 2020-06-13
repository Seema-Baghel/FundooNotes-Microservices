package com.bridgelabz.fundoonotes.serviceImplementation;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.dto.ReminderDateTimeDto;
import com.bridgelabz.fundoonotes.exception.NoteException;
import com.bridgelabz.fundoonotes.exception.UserNotFoundException;
import com.bridgelabz.fundoonotes.model.NoteModel;
import com.bridgelabz.fundoonotes.model.UserModel;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.NoteService;
import com.bridgelabz.fundoonotes.utility.Jwt;
import com.bridgelabz.fundoonotes.utility.Util;

@Service
public class NoteServiceImplementation implements NoteService {
	
	@Autowired
	private NoteRepository noteRepository;
	
	@Autowired
	private Jwt tokenGenerator;
	
	private NoteModel notes = new NoteModel();
	 
	@Autowired
	List<NoteDto> listOfNotes;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public ResponseEntity<Response> createNote(NoteDto noteDto , String token) {
		UserModel user = restTemplate.getForObject("http://User-Microservice/user/getUser/"+token,UserModel.class);
		long userId= tokenGenerator.parseJwtToken(token);
		System.out.println(userId);
		if( user != null) {
			BeanUtils.copyProperties(noteDto, notes);
			System.out.println(userId);
			notes.setUserId(userId);
			notes.setCreatedDate(LocalDateTime.now());
			notes.setUpdatedDate(LocalDateTime.now());
			noteRepository.save(notes);
			return ResponseEntity.status(HttpStatus.OK).body(new Response(Util.OK_RESPONSE_CODE, "Note is created successfully"));
		}
		return ResponseEntity.status(HttpStatus.OK).body(new Response(Util.BAD_REQUEST_RESPONSE_CODE, "Note not created "));
	}


	@Override
	public ResponseEntity<Response> updateNote(NoteDto noteDto, long noteId, String token) {
		UserModel user = restTemplate.getForObject("http://User-Microservice/user/getUser/"+token,UserModel.class);
		if(user != null) {
			NoteModel note = noteRepository.findById(noteId);
			if(note != null) {
				note.setDescription(noteDto.getDescription());
				note.setTitle(noteDto.getTitle());
				note.setUpdatedDate(LocalDateTime.now());
				noteRepository.save(note);
				return ResponseEntity.status(HttpStatus.OK).body(new Response(Util.OK_RESPONSE_CODE, "Note is updated successfully"));
			}
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response(Util.BAD_REQUEST_RESPONSE_CODE, "Something went wrong"));
	}
	
	@Override
	public ResponseEntity<Response> addColor( String token, long noteId, String color) {
		UserModel user = restTemplate.getForObject("http://User-Microservice/user/getUser/"+token,UserModel.class);
		long userId = tokenGenerator.parseJwtToken(token);
		if (user != null) {
			NoteModel note = noteRepository.findById(noteId);
			if (note != null) {
				noteRepository.updateColor(userId, noteId, color);
				return ResponseEntity.status(HttpStatus.OK).body(new Response(Util.OK_RESPONSE_CODE, "Color is added"));
			}
			return ResponseEntity.status(HttpStatus.OK).body(new Response(Util.BAD_REQUEST_RESPONSE_CODE, "Error! color is not added"));
		}
		throw new NoteException("No User Found");
	}

	@Override
	public ResponseEntity<Response> deleteNote(String token, long noteId) {
		UserModel user = restTemplate.getForObject("http://User-Microservice/user/getUser/"+token,UserModel.class);
		long userId = tokenGenerator.parseJwtToken(token);
		if (user != null){		
			noteRepository.deleteNote(userId, noteId);
			return ResponseEntity.status(HttpStatus.OK).body(new Response(Util.OK_RESPONSE_CODE, "Deleted Succussfully"));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response(Util.BAD_REQUEST_RESPONSE_CODE, "Error! note can't be deleted"));
	}
	
	
}