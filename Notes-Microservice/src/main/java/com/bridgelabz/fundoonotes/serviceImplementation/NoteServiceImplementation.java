package com.bridgelabz.fundoonotes.serviceImplementation;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.dto.ReminderDateTimeDto;
import com.bridgelabz.fundoonotes.exception.NoteException;
import com.bridgelabz.fundoonotes.exception.UserNotFoundException;
import com.bridgelabz.fundoonotes.model.NoteModel;
import com.bridgelabz.fundoonotes.model.UserModel;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.NoteService;
import com.bridgelabz.fundoonotes.utility.Util;

@Service
public class NoteServiceImplementation implements NoteService {
	
	@Autowired
	private NoteRepository noteRepository;
	
	private NoteModel notes = new NoteModel();
	 
	@Autowired
	List<NoteDto> listOfNotes;
	 
	@Override
	public ResponseEntity<Response> createNote(NoteDto noteDto , String token, long userId) {
			BeanUtils.copyProperties(noteDto, notes);
			notes.setUserId(userId);
			notes.setCreatedDate(LocalDateTime.now());
			notes.setUpdatedDate(LocalDateTime.now());
			noteRepository.save(notes);
			return ResponseEntity.status(HttpStatus.OK).body(new Response(Util.OK_RESPONSE_CODE, "Note is created successfully"));
	}

	@Override
	public ResponseEntity<Response> updateNote(NoteDto noteDto, long noteId) {
		NoteModel note = noteRepository.findById(noteId);
		if(note != null) {
			note.setDescription(noteDto.getDescription());
			note.setTitle(noteDto.getTitle());
			note.setUpdatedDate(LocalDateTime.now());
			noteRepository.save(note);
			return ResponseEntity.status(HttpStatus.OK).body(new Response(Util.OK_RESPONSE_CODE, "Note is update successfully"));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response(Util.BAD_REQUEST_RESPONSE_CODE, "Something went wrong"));
	}
	
//	@Override
//	public ResponseEntity<Response> addColor(String token, long noteId, String color) {
//		long userId = tokenGenerator.parseJwtToken(token);
//		UserModel isUserAvailable = userRepository.findById(userId);
//		if (isUserAvailable != null) {
//			NoteModel note = noteRepository.findById(noteId);
//			if (note != null) {
//				noteRepository.updateColor(userId, noteId, color);
//				return ResponseEntity.status(HttpStatus.OK).body(new Response(Util.OK_RESPONSE_CODE, "Color is added"));
//			}
//			return ResponseEntity.status(HttpStatus.OK).body(new Response(Util.BAD_REQUEST_RESPONSE_CODE, "Error! color is not added"));
//		}
//		throw new NoteException("No Data Found");
//	}
//
//	@Override
//	public ResponseEntity<Response> deleteNote(String token, long noteId) {
//		long userId = tokenGenerator.parseJwtToken(token);
//		UserModel note = userRepository.findById(userId);
//		if (note != null){		
//			noteRepository.deleteNote(userId, noteId);
//			//elasticSearch.deleteNote(note);
//			return ResponseEntity.status(HttpStatus.OK).body(new Response(Util.OK_RESPONSE_CODE, "Deleted Succussfully"));
//		}
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response(Util.BAD_REQUEST_RESPONSE_CODE, "Error! note can't be deleted"));
//	}
//	
//	@Override
//	public List<NoteModel> getAllNotes(String token) {
//		long userId = tokenGenerator.parseJwtToken(token);
//		Object isUserAvailable = userRepository.findById(userId);
//		if (isUserAvailable != null) {
//			List<NoteModel> notes = noteRepository.getAll(userId);
//			return notes;
//		}
//		throw new NoteException("No Notes Found");
//	}
//	
}