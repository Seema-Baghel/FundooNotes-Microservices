package com.bridgelabz.fundoonotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.LabelDto;
import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.exception.UserNotFoundException;
import com.bridgelabz.fundoonotes.model.LabelModel;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.LabelService;
import com.bridgelabz.fundoonotes.utility.Util;

@RestController
@RequestMapping("/label")
public class LabelController {

	@Autowired
	private LabelService labelService;
		
	/**
	 * This function takes {@link LabelDto} as request body and token from
	 * {@link RequestHeader} and verify originality of client
	 * {@link LabelServiceImpl} and accordingly returns the response.
	 * 
	 * @param LabelDto as {@link LabelDto}
	 * @param token as String input parameter
	 * @return ResponseEntity<Response>
	 * @URL http://localhost:8080/label/addlabel
	 */
	
	@PostMapping("/addlabel")
	public ResponseEntity<Response> createLabel(@RequestBody LabelDto labeldto, @RequestHeader("token") String token) throws UserNotFoundException {
		
		return labelService.createLabel(labeldto, token);
	}
	
	/**
	 * This function takes {@link LabelDto} as request body,labelId as {@link RequestParam} and token from
	 * {@link RequestHeader} and verify originality of client
	 * {@link LabelServiceImpl} and after update accordingly it returns the response.
	 * 
	 * @param LabelDto as {@link LabelDto}
	 * @param LabelId as {@link RequestParam}
	 * @param token   as String input parameter
	 * @return ResponseEntity<Response>
	 * @URL http://localhost:8080/notes/updatenote?id=85
	 */

	@PutMapping("/updatelabel")
	public ResponseEntity<Response> updateLabel(@RequestBody LabelDto labeldto,@RequestHeader("token") String token, @RequestParam("labelId") long labelId){
		
		return labelService.updateLabel(labeldto, token, labelId);
	}

	/**
	 * This function takes LabelId as {@link RequestParam} and token as
	 * {@link RequestHeader} and verify originality of client
	 * {@link LabelServiceImpl} and after deletion operation of note accordingly it
	 * returns the response.
	 * 
	 * @param labelId as {@link RequestParam}
	 * @param token  as {@link RequestHeader}
	 * @return ResponseEntity<Response>
	 * @URL http://localhost:8080/label/deletelabel?labelId=1
	 */
	
	@DeleteMapping("/deletelabel")
	public ResponseEntity<Response> deleteLabel(@RequestHeader("token") String token, @RequestParam("labelId") long labelId){

		return labelService.deleteLabel(token, labelId);
	}
	
	/**
	 * This function takes authentication token as {@link RequestHeader} and verify
	 * originality of client {@link LabelServiceImpl} after verification allows user
	 * to get all labels which are not trashed.
	 * 
	 * @param token as {@link RequestHeader}
	 * @return ResponseEntity<Response>
	 * @URL http://localhost:8080/label/alllabel
	 */
		
	@GetMapping("/alllabel")
	public ResponseEntity<Response> getAllLabel(@RequestHeader("token") String token) throws UserNotFoundException{
		
		List<LabelModel> labelList = labelService.getAllLabel(token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("All labels of user",Util.OK_RESPONSE_CODE, labelList));
	}
	
	/**
	 * This function takes {@link LabelDto} as request body and token from
	 * {@link RequestHeader} and verify originality of client and noteId as
	 * {@link RequestParam} to map with the label after that
	 * {@link LabelServiceImpl} will accordingly returns the response.
	 * 
	 * @param LabelDto as {@link LabelDto}
	 * @param noteId as {@link noteId}
	 * @param token as String input parameter
	 * @return ResponseEntity<Response>
	 * @URL http://localhost:8080/label/maptonote
	 */
	
//	@PutMapping("/maptonote")
//	public ResponseEntity<Response> mapToNote(@RequestBody LabelDto labeldto,@RequestHeader("token") String token,
//									@RequestParam("noteid") long noteid) throws UserNotFoundException{
//		
//		return labelService.mapToNote(labeldto, noteid, token);
//	}
	
	/**
	 * This function takes labelId as{@link RequestParam} and token from
	 * {@link RequestHeader} and verify originality of client and noteId as
	 * {@link RequestParam} to map with the label after that
	 * {@link LabelServiceImpl} will accordingly returns the response.
	 * 
	 * @param labelid as {@link RequestParam}
	 * @param noteId as {@link noteId}
	 * @param token as String input parameter
	 * @return ResponseEntity<Response>
	 * @URL http://localhost:8080/label/addLabelsToNote
	 */
	
	@PutMapping("/addLabelsToNote")
	public ResponseEntity<Response> addLabels(@RequestHeader("token") String token, @RequestParam("labelId") long labelId,
											  @RequestParam("noteid") long noteId){
		
		return labelService.addLabelsToNote(token, labelId, noteId);
	}


}
