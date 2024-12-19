package com.tasklytic.shared.constants;

public class Constants {

	// Error Messages
	public static final String TASK_NOT_FOUND = "Task with ID %d not found.";
	public static final String INVALID_TASK_DATA = "Invalid task data provided.";
	public static final String DATABASE_ERROR = "Error accessing the database.";
	public static final String UNEXPECTED_ERROR = "An unexpected error occurred. Please try again.";
	public static final String USER_NOT_FOUND = "User with ID %d not found.";
	public static final String INVALID_USER_DATA = "Invalid user data provided.";
	public static final String EMAIL_ALREADY_EXISTS = "Email already exists.";
	public static final String MOBILE_ALREADY_EXISTS = "Mobile number already exists.";
	public static final String INVALID_CREDENTIALS = "Invalid login credentials.";
	public static final String EMAIL_SENDING_ERROR = "Failed to send OTP email. Please try again later.";
	public static final String OTP_NOT_FOUND_OR_EXPIRED = "OTP for email %s not found or has expired.";
    public static final String OTP_INVALID = "The provided OTP is invalid.";
    public static final String EMAIL_SENDING_FAILED = "Failed to send OTP email to %s.";
    public static final String EMAIL_NOT_VERIFIED = "Email address not verified. Please verify your email before proceeding.";

	// Success Messages
    public static final String USER_REGISTERED = "User registered successfully.";
    public static final String USER_LOGGED_IN = "User logged in successfully.";
    public static final String USER_LOGGED_OUT = "User logged out successfully.";
    public static final String USER_UPDATED = "User updated successfully.";
    public static final String OTP_SENT = "OTP sent successfully.";
    public static final String USER_DELETED = "User deleted successfully.";
    public static final String USER_LIST_FETCHED = "User list fetched successfully.";
    public static final String OTP_VERIFIED = "OTP Verified Successfully";
	public static final String PASSWORD_MISMATCH = "Confirm password must be same.";
	public static final String TASK_CREATED = "Task created successfully.";
	public static final String TASK_UPDATED = "Task updated successfully.";
	public static final String TASK_DELETED = "Task deleted successfully.";


	// exceptions
	@SuppressWarnings("serial")
	public static class UserNotFoundException extends RuntimeException {
		public UserNotFoundException(String message) {
			super(message);
		}
	}

	
	@SuppressWarnings("serial")
	public static class UserAlreadyExistsException extends RuntimeException {
		public UserAlreadyExistsException(String message) {
			super(message);
		}
	}

	@SuppressWarnings("serial")
	public static class InvalidCredentialsException extends RuntimeException {
		public InvalidCredentialsException(String message) {
			super(message);
		}
	}

	@SuppressWarnings("serial")
	public static class EmailAlreadyExistsException extends RuntimeException {
		public EmailAlreadyExistsException(String message) {
			super(message);
		}
	}

	@SuppressWarnings("serial")
	public static class EmailSendingException extends RuntimeException {
	    public EmailSendingException(String message) {
	        super(message);
	    }
	}
	
	@SuppressWarnings("serial")
	public static class MobileAlreadyExistsException extends RuntimeException {
		public MobileAlreadyExistsException(String message) {
			super(message);
		}
	}
	
	@SuppressWarnings("serial")
	public static class OtpNotFoundException extends RuntimeException {
        public OtpNotFoundException(String message) {
            super(message);
        }
    }

	@SuppressWarnings("serial")
    public static class InvalidOtpException extends RuntimeException {
        public InvalidOtpException(String message) {
            super(message);
        }
    }
	
	 @SuppressWarnings("serial")
	    public static class EmailNotVerifiedException extends RuntimeException {
	        public EmailNotVerifiedException(String message) {
	            super(message);
	        }
	    }

	 
	 @SuppressWarnings("serial")
	    public static class PasswordMismatchException extends RuntimeException {
	        public PasswordMismatchException(String message) {
	            super(message);
	        }
	    }
	 
	 @SuppressWarnings("serial")
		public static class TaskNotFoundException extends RuntimeException {
			public TaskNotFoundException(String message) {
				super(message);
			}
		}
		
}
