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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

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
	   
	  @NotNull
      private String firstName;
	   
	  @NotNull
      private String lastName;
      
	  @NotNull
      @Column(unique = true)
      private String email;
      
	  @NotNull
      @Column(unique = true)
      private String mobile;
      
	  @NotNull
      private String password;
      
	  @NotNull
      @Column(columnDefinition = "boolean default false")
      private boolean isVerified;
      
      @Column(name = "created_at")
  	  public LocalDateTime createdAt;

  	  @Column(name = "modified_time")
  	  public LocalDateTime modifiedTime;
  	  
  	  @Column(columnDefinition = "boolean default false")
  	  public boolean userStatus;
  	  
  	  public UserModel(String firstName, String lastName, String email, String mobile, String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.mobile = mobile;
		this.email = email;
  	  }

}