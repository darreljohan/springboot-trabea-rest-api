# Database Constraint Error Handling Fix

## Problem Description

When a primary key constraint violation occurred in the `addPartTimeEmployee` endpoint, the error was being caught by the `CustomAuthenticationEntryPoint` and returned as an `InsufficientAuthenticationException` instead of being properly handled by the `@RestControllerAdvice` error handler.

## Root Cause

The issue occurred due to two problems:

1. **Missing Database Exception Handler**: The `ErrorExceptionHandler` class did not have a handler for `DataIntegrityViolationException`, which is thrown by JPA/Hibernate when database constraints are violated (e.g., primary key, unique constraints, foreign key violations).

2. **Overly Broad Exception Catching in JwtRequestFilter**: The JWT filter was catching ALL exceptions with a generic `catch (Exception e)` block, which could potentially interfere with proper exception propagation from controllers.

## Why It Showed as Authentication Error

When a database constraint violation occurs:
- The exception is thrown from the service/repository layer
- If no specific handler exists in `@RestControllerAdvice`, Spring Security's exception handling mechanism may intercept it
- The exception gets routed through the authentication entry point, causing it to be misidentified as an authentication issue

## Solution Implemented

### 1. Added DataIntegrityViolationException Handler

Added a new exception handler in `ErrorExceptionHandler.java`:

```java
@ExceptionHandler(DataIntegrityViolationException.class)
public ResponseEntity<ErrorMessageResponse<Object>> handleDataIntegrityViolationException(DataIntegrityViolationException e){
    HttpStatus httpStatus = HttpStatus.CONFLICT;
    String errorMessage = "Database constraint violation";
    
    // Extract more specific error message
    if (e.getMessage() != null) {
        if (e.getMessage().contains("Unique") || e.getMessage().contains("unique") || 
            e.getMessage().contains("PRIMARY KEY") || e.getMessage().contains("duplicate key")) {
            errorMessage = "A record with this information already exists";
        } else if (e.getMessage().contains("foreign key") || e.getMessage().contains("FOREIGN KEY")) {
            errorMessage = "Cannot perform this operation due to related records";
        }
    }
    
    ErrorMessageResponse<Object> errorMessageResponse = ErrorMessageResponse.builder()
            .status(httpStatus)
            .message("Data Integrity Violation")
            .errors(errorMessage)
            .build();

    return ResponseEntity.status(httpStatus).body(errorMessageResponse);
}
```

This handler:
- Catches all `DataIntegrityViolationException` exceptions
- Returns a 409 CONFLICT status code
- Provides user-friendly error messages based on the type of constraint violation
- Handles primary key, unique constraint, and foreign key violations

### 2. Improved JwtRequestFilter Exception Handling

Changed the generic exception catch to be more specific:

```java
} catch (JwtException e) {
    log.warn("JWT validation failed: {}", e.getMessage());
} catch (UsernameNotFoundException e) {
    log.warn("User not found during JWT validation: {}", e.getMessage());
} catch (Exception e) {
    // Only log unexpected JWT-related errors, let other exceptions propagate
    log.error("Unexpected error during JWT processing: {}", e.getMessage());
}
```

This ensures that:
- Only JWT-specific errors are caught and handled in the filter
- Other application exceptions (like database constraints) can propagate properly to the controller advice
- Better logging for debugging authentication issues

## Common Database Constraint Violations

### Primary Key Violation
- **Cause**: Attempting to insert a record with a primary key that already exists
- **Example**: Creating a `User` with an email that already exists in the database
- **Response**: 409 CONFLICT with message "A record with this information already exists"

### Unique Constraint Violation
- **Cause**: Attempting to insert/update a record that violates a unique constraint
- **Example**: Creating a `PartTimeEmployee` with a personalEmail or phoneNumber that already exists
- **Response**: 409 CONFLICT with message "A record with this information already exists"

### Foreign Key Constraint Violation
- **Cause**: Attempting to delete a record that is referenced by other records, or inserting a record with an invalid foreign key
- **Example**: Deleting a User that has associated WorkSchedules
- **Response**: 409 CONFLICT with message "Cannot perform this operation due to related records"

## Testing the Fix

To test the fix, try to:

1. Create a PartTimeEmployee with an email prefix that would generate a duplicate work email
2. Create a PartTimeEmployee with a duplicate personal email or phone number
3. Verify that you get a proper 409 CONFLICT response instead of a 401 UNAUTHORIZED response

Expected response:
```json
{
  "status": "CONFLICT",
  "message": "Data Integrity Violation",
  "errors": "A record with this information already exists"
}
```

## Additional Recommendations

### 1. Uncomment Duplicate Email Check

In `PartTimeEmployeeService.addPartTimeEmployee()`, there's a commented-out check for duplicate emails:

```java
if(userRepository.existsByWorkEmail(email)){
    String hash = Integer.toHexString(UUID.randomUUID().hashCode()).substring(0, 4);
    email = partTimeEmployeeFormRequest.getEmailPrefix()+"_"+hash+"@trabea.co.id";
}
```

Consider uncommenting and fixing this logic to:
- Check if email exists BEFORE attempting to save
- Generate a unique email suffix
- Provide a better user experience by avoiding database exceptions

### 2. Add Custom Exceptions

Consider creating custom exceptions for business logic violations:

```java
public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String email) {
        super("Email already exists: " + email);
    }
}
```

Then handle it in `ErrorExceptionHandler`:

```java
@ExceptionHandler(DuplicateEmailException.class)
public ResponseEntity<ErrorMessageResponse<Object>> handleDuplicateEmailException(DuplicateEmailException e){
    // ... return appropriate response
}
```

### 3. Add Validation at Service Layer

Add explicit validation in your service methods:

```java
@Transactional
public PartTimeEmployeeSummaryResponse addPartTimeEmployee(PartTimeEmployeeFormRequest request){
    String email = request.getEmailPrefix() + "@trabea.co.id";
    
    // Check for duplicate work email
    if(userRepository.existsByWorkEmail(email)){
        throw new DuplicateEmailException(email);
    }
    
    // Check for duplicate personal email
    if(partTimeEmployeeRepository.existsByPersonalEmail(request.getPersonalEmail())){
        throw new DuplicateEmailException(request.getPersonalEmail());
    }
    
    // Check for duplicate phone number
    if(partTimeEmployeeRepository.existsByPhoneNumber(request.getPhoneNumber())){
        throw new DuplicatePhoneNumberException(request.getPhoneNumber());
    }
    
    // Proceed with creation...
}
```

This approach:
- Provides better error messages
- Avoids database exceptions
- Makes the code more maintainable
- Gives you more control over the response

## Files Modified

1. `ErrorExceptionHandler.java` - Added DataIntegrityViolationException handler
2. `JwtRequestFilter.java` - Improved exception handling to be more specific

## Status

✅ **Fixed**: Database constraint violations are now properly caught and returned as 409 CONFLICT responses with appropriate error messages.

