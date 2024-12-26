package com.tasklytic.shared.constants;

public class Constants {

    // *********** General Error Messages ***********
    public static final String DATABASE_ERROR = "Error accessing the database.";
    public static final String UNEXPECTED_ERROR = "An unexpected error occurred. Please try again.";
    public static final String JWT_TOKEN_INVALID = "Token is invalid or expired.";

    // *********** User Service Messages ***********
    public static final String USER_NOT_FOUND = "User with ID %d not found.";
    public static final String INVALID_USER_DATA = "Invalid user data provided.";
    public static final String EMAIL_ALREADY_EXISTS = "Email already exists.";
    public static final String MOBILE_ALREADY_EXISTS = "Mobile number already exists.";
    public static final String INVALID_CREDENTIALS = "Invalid login credentials.";
    public static final String EMAIL_SENDING_ERROR = "Failed to send OTP email. Please try again later.";
    public static final String OTP_NOT_FOUND_OR_EXPIRED = "OTP for email %s not found or has expired.";
    public static final String OTP_INVALID = "The provided OTP is invalid.";
    public static final String EMAIL_NOT_VERIFIED = "Email address not verified. Please verify your email before proceeding.";
    public static final String PASSWORD_MISMATCH = "Confirm password must match the new password.";

    public static final String USER_REGISTERED = "User registered successfully.";
    public static final String USER_LOGGED_IN = "User logged in successfully.";
    public static final String USER_LOGGED_OUT = "User logged out successfully.";
    public static final String USER_UPDATED = "User updated successfully.";
    public static final String OTP_SENT = "OTP sent successfully.";
    public static final String USER_DELETED = "User deleted successfully.";
    public static final String USER_LIST_FETCHED = "User list fetched successfully.";
    public static final String OTP_VERIFIED = "OTP verified successfully.";

    // *********** Task Service Messages ***********
    public static final String TASK_NOT_FOUND = "Task with ID %d not found.";
    public static final String INVALID_TASK_DATA = "Invalid task data provided.";
    public static final String TASK_CREATED = "Task created successfully.";
    public static final String TASK_UPDATED = "Task updated successfully.";
    public static final String TASK_DELETED = "Task deleted successfully.";

    // *********** Collaboration Service Messages ***********
    public static final String SESSION_NOT_FOUND = "Collaboration session with ID %d not found.";
    public static final String INVALID_SESSION_DATA = "Invalid session data provided.";
    public static final String MESSAGE_PROCESSING_ERROR = "Error processing the chat message.";
    public static final String CONNECTION_FAILED = "Failed to establish WebSocket connection.";
    public static final String SESSION_CREATED = "Collaboration session created successfully.";
    public static final String SESSION_UPDATED = "Collaboration session updated successfully.";
    public static final String SESSION_DELETED = "Collaboration session deleted successfully.";
    public static final String MESSAGE_SENT = "Chat message sent successfully.";
    public static final String MESSAGE_HISTORY_FETCHED = "Chat message history fetched successfully.";

    // *********** Nested Custom Exceptions ***********
    public static class Exceptions {

        // General Exceptions
        @SuppressWarnings("serial")
        public static class JwtValidationException extends RuntimeException {
            public JwtValidationException(String message, Throwable cause) {
                super(message, cause);
            }
        }

        // User Service Exceptions
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
        public static class InvalidCredentialsException extends IllegalArgumentException {
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
        public static class OtpNotFoundException extends RuntimeException {
            public OtpNotFoundException(String message) {
                super(message);
            }
        }

        @SuppressWarnings("serial")
        public static class InvalidOtpException extends IllegalArgumentException {
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
        public static class MobileAlreadyExistsException extends RuntimeException {
            public MobileAlreadyExistsException(String message) {
                super(message);
            }
        }

        @SuppressWarnings("serial")
        public static class PasswordMismatchException extends IllegalArgumentException {
            public PasswordMismatchException(String message) {
                super(message);
            }
        }

        @SuppressWarnings("serial")
        public static class EmailSendingExceptions extends RuntimeException {
            public EmailSendingExceptions(String message) {
                super(message);
            }
        }

        // Task Service Exceptions
        @SuppressWarnings("serial")
        public static class TaskNotFoundException extends RuntimeException {
            public TaskNotFoundException(String message) {
                super(message);
            }
        }

        // Collaboration Service Exceptions
        @SuppressWarnings("serial")
        public static class CollaborationSessionNotFoundException extends RuntimeException {
            public CollaborationSessionNotFoundException(String message) {
                super(message);
            }
        }

        @SuppressWarnings("serial")
        public static class MessageProcessingException extends RuntimeException {
            public MessageProcessingException(String message) {
                super(message);
            }
        }

        @SuppressWarnings("serial")
        public static class WebSocketConnectionException extends RuntimeException {
            public WebSocketConnectionException(String message) {
                super(message);
            }
        }
    }
}
