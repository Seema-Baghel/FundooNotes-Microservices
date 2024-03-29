package com.bridgelabz.fundoonotes.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.dto.ReminderDateTimeDto;
import com.bridgelabz.fundoonotes.model.NoteModel;
import com.bridgelabz.fundoonotes.model.UserModel;
import com.bridgelabz.fundoonotes.response.Response;

@Component
public interface NoteService {

	public ResponseEntity<Response> createNote(NoteDto noteDto, String token);
	
	public ResponseEntity<Response> updateNote(NoteDto noteDto, long noteId, String token);

	public ResponseEntity<Response> addColor(String token, long noteId, String noteColor);
	
	public ResponseEntity<Response> deleteNote(String token, long noteId);

	public ResponseEntity<Response> isArchivedNote(String token, long noteId);

	public List<NoteModel> getAllNotes(String token);

	public ResponseEntity<Response> setReminder(String token, ReminderDateTimeDto reminderDateTimeDto, long id);

	public ResponseEntity<Response> unsetReminder(long id, String token);

	public ResponseEntity<Response> isPinnedNote(String token, long noteId);

	public ResponseEntity<Response> trashNote(String token, long noteId);

	public ResponseEntity<Response> restoreNote(String token, long noteId);	
	
}
