package com.bridgelabz.fundoonotes.utility;

public class Util {
	
	public static final String SECRET_KEY = "SB45BU5";
	public static String verify_Mail_Url = "http://localhost:8080/user/verify/";
	public static String resetpassword_url= "http://localhost:4200/resetpassword/";
	public static final String SENDER_EMAIL_ID = "seemabaghel1997@gmail.com";
	public static final String SENDER_PASSWORD = "seemabaghel@";
	public static String USER_MICROSERVICE_URL = "http://User-Microservice/user/getUser/";
	
	public static final int OK_RESPONSE_CODE = 200;
	public static final int BAD_REQUEST_RESPONSE_CODE = 400;
	public static final int USER_AUTHENTICATION_EXCEPTION_STATUS = 401;
	public static final int ALREADY_VERIFIED_EXCEPTION_STATUS = 208;
}
