package com.bridgelabz.fundoonotes.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class UserModel {

	  @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  @Column(name = "user_id")
      private long userId;
	   
	  
      private String firstName;
	   
	 
      private String lastName;
      
      
      @Column(unique = true)
      private String email;
      
    
      @Column(unique = true)
      private String mobile;
      
     
      private String password;
      
      
      @Column(columnDefinition = "boolean default false")
      private boolean isVerified;
      
      @Column(name = "created_at")
  	  public LocalDateTime createdAt;

  	  @Column(name = "modified_time")
  	  public LocalDateTime modifiedTime;
  	  
  	  @Column(columnDefinition = "boolean default false")
  	  public boolean userStatus;
  	  
//  	  @JsonIgnore
//  	  @OneToMany(cascade = CascadeType.ALL)
//  	  @JoinColumn(name = "user_id")
//  	  private List<NoteModel> notes;
  	  
//  	  @JsonIgnore
//  	  @OneToMany(cascade = CascadeType.ALL)
//  	  private List<NoteModel> notes;
//  	
//  	  @JsonIgnore
//  	  @OneToMany(cascade = CascadeType.ALL)
//  	  private List<LabelModel> label;
//  	
//  	  @JsonIgnore
//  	  @ManyToMany(cascade = CascadeType.ALL)
//  	  private Set<NoteModel> collaboratedNotes;
  	  
  	  public UserModel(String firstName, String lastName, String email, String mobile, String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.mobile = mobile;
		this.email = email;
  	  }

}