package com.bridgelabz.fundoonotes.serviceImplementation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.dto.ReminderDateTimeDto;
import com.bridgelabz.fundoonotes.exception.NoteException;
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
	 
	@Autowired
	List<NoteDto> listOfNotes;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public ResponseEntity<Response> createNote(NoteDto noteDto , String token) {
		UserModel user = restTemplate.getForObject(Util.USER_MICROSERVICE_URL+token,UserModel.class);
		long userId= tokenGenerator.parseJwtToken(token);
		if( user != null) {
			ModelMapper mapper = new ModelMapper();
			NoteModel notes =mapper.map(noteDto, NoteModel.class);
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
		UserModel user = restTemplate.getForObject(Util.USER_MICROSERVICE_URL+token,UserModel.class);
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
		UserModel user = restTemplate.getForObject(Util.USER_MICROSERVICE_URL+token,UserModel.class);
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
		UserModel user = restTemplate.getForObject(Util.USER_MICROSERVICE_URL+token,UserModel.class);
		long userId = tokenGenerator.parseJwtToken(token);
		if (user != null){		
			noteRepository.deleteNote(userId, noteId);
			return ResponseEntity.status(HttpStatus.OK).body(new Response(Util.OK_RESPONSE_CODE, "Deleted Succussfully"));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response(Util.BAD_REQUEST_RESPONSE_CODE, "Error! note can't be deleted"));
	}
	
	@Override
	public ResponseEntity<Response> isArchivedNote(String token, long noteId) {
		UserModel user = restTemplate.getForObject(Util.USER_MICROSERVICE_URL+token,UserModel.class);
		if (user != null) {
			NoteModel note = noteRepository.findById(noteId);
			if (!note.isArchived()) {
				note.setArchived(true);
				note.setUpdatedDate(LocalDateTime.now());
				noteRepository.save(note);
				return ResponseEntity.status(HttpStatus.OK).body(new Response(Util.OK_RESPONSE_CODE, "Note archieved"));
			}
			note.setArchived(false);
			note.setUpdatedDate(LocalDateTime.now());
			noteRepository.save(note);
			return ResponseEntity.status(HttpStatus.CREATED).body(new Response(Util.OK_RESPONSE_CODE, "Note unarchived"));
		}
		throw new NoteException("Sorry! User not found");
	}
	
	@Override
	public List<NoteModel> getAllNotes(String token) {
		UserModel user = restTemplate.getForObject(Util.USER_MICROSERVICE_URL+token,UserModel.class);
		long userId = tokenGenerator.parseJwtToken(token);
		if (user != null) {
			List<NoteModel> notes = noteRepository.getAll(userId);
			return notes;
		}
		throw new NoteException("No User Found");
	}

	@Override
	public ResponseEntity<Response> setReminder(String token, ReminderDateTimeDto reminderDateTimeDto, long noteId) {
		UserModel user = restTemplate.getForObject(Util.USER_MICROSERVICE_URL+token,UserModel.class);
		if (user == null)
			throw new NoteException("No user Found");
		Optional<NoteModel> note = noteRepository.findBynoteId(noteId);
		LocalDateTime reminderDateTime = LocalDateTime.of(reminderDateTimeDto.getYear(), reminderDateTimeDto.getMonth(),
														  reminderDateTimeDto.getDay(), reminderDateTimeDto.getHour(),
														  reminderDateTimeDto.getMinute());
		LocalDateTime date = reminderDateTime;
		note.get().setReminder(date);
		noteRepository.save(note.get());
		return ResponseEntity.status(HttpStatus.OK).body(new Response(Util.OK_RESPONSE_CODE, "Reminder set successfully"));
	}

	@Override
	public ResponseEntity<Response> unsetReminder(long noteId, String token) {
		UserModel user = restTemplate.getForObject(Util.USER_MICROSERVICE_URL+token,UserModel.class);
		if (user == null)
			throw new NoteException("No user Found");
		NoteModel note = noteRepository.findById(noteId);
		note.setReminder(null);
		noteRepository.save(note);
		return ResponseEntity.status(HttpStatus.OK).body(new Response(Util.OK_RESPONSE_CODE, "Reminder unset successfully"));
	}

	@Override
	public ResponseEntity<Response> isPinnedNote(String token, long noteId) {
		UserModel user = restTemplate.getForObject(Util.USER_MICROSERVICE_URL+token,UserModel.class);
		if (user != null) {
			NoteModel note = noteRepository.findById(noteId);
				if (!note.isPinned()) {
					note.setPinned(true);
					note.setUpdatedDate(LocalDateTime.now());
					noteRepository.save(note);
					return ResponseEntity.status(HttpStatus.OK).body(new Response(Util.OK_RESPONSE_CODE, "Note pinned"));
				}
				note.setPinned(false);
				note.setUpdatedDate(LocalDateTime.now());
				noteRepository.save(note);
				return ResponseEntity.status(HttpStatus.CREATED).body(new Response(Util.OK_RESPONSE_CODE, "Note unpinned"));
		}
		throw new NoteException("Sorry! something went wrong");
	}

	@Override
	public ResponseEntity<Response> trashNote(String token, long noteId) {
		UserModel user = restTemplate.getForObject(Util.USER_MICROSERVICE_URL+token,UserModel.class);
		if (user != null) {
			NoteModel note = noteRepository.findById(noteId);
				if (!note.isTrashed()) {
					note.setTrashed(true);
					note.setArchived(false);
					note.setPinned(false);
					note.setReminder(null);
					note.setUpdatedDate(LocalDateTime.now());
					noteRepository.save(note);
					return ResponseEntity.status(HttpStatus.OK).body(new Response(Util.OK_RESPONSE_CODE, "Note trashed"));
				}
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response(Util.BAD_REQUEST_RESPONSE_CODE,"Error! Note is not trashed"));
			}
			throw new NoteException("Sorry! User not found");
	}

	@Override
	public ResponseEntity<Response> restoreNote(String token, long noteId) {
		UserModel user = restTemplate.getForObject(Util.USER_MICROSERVICE_URL+token,UserModel.class);
		if (user != null) {
			NoteModel note = noteRepository.findById(noteId);
			if (note.isTrashed()) {
				note.setTrashed(false);
				note.setUpdatedDate(LocalDateTime.now());
				noteRepository.save(note);
				return ResponseEntity.status(HttpStatus.OK).body(new Response(Util.OK_RESPONSE_CODE,"Note restored"));
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response(Util.BAD_REQUEST_RESPONSE_CODE,"Error in Restoring note!"));
		}
		throw new NoteException("Sorry! User not found");
	}
	
}