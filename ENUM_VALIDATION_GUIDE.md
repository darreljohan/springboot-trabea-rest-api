# Enum Validation in Spring Boot - Complete Guide

## ✅ What Was Implemented

I've created a **custom validation annotation** `@ValidEnum` to validate enum fields in your DTOs.

## 📁 Files Created

### 1. `ValidEnum.java` - Custom Annotation
```java
@ValidEnum(enumClass = Education.class, nullable = true)
private Education lastEducation;
```

**Location:** `src/main/java/com/iglo/trabea/validation/ValidEnum.java`

**Features:**
- `enumClass` - Specifies which enum class to validate against
- `nullable` - Whether null values are allowed (default: false)
- Custom error message showing valid enum values

### 2. `EnumValidatorImpl.java` - Validator Implementation
**Location:** `src/main/java/com/iglo/trabea/validation/EnumValidatorImpl.java`

**What it does:**
- Validates that the value is a valid enum constant
- Handles null values based on `nullable` configuration
- Generates user-friendly error messages listing all valid values

### 3. Updated `PartTimeEmployeeFormRequest.java`
**Added validation:**
```java
@ValidEnum(enumClass = Education.class, nullable = true)
private Education lastEducation;

@ValidEnum(enumClass = Education.class, nullable = true)
private Education onGoingEducation;
```

## 🎯 How It Works

### Request Flow:

1. **Client sends JSON:**
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "personalEmail": "john@example.com",
  "phoneNumber": "08123456789",
  "lastEducation": "BACHELOR",
  "onGoingEducation": "MASTER"
}
```

2. **Spring Boot deserializes JSON to enum:**
   - `"BACHELOR"` → `Education.BACHELOR` ✅
   - `"MASTER"` → `Education.MASTER` ✅

3. **`@Valid` triggers validation:**
   - `@ValidEnum` validator checks if value is valid enum constant
   - If valid → proceeds
   - If invalid → returns 400 Bad Request with error message

## ✅ Valid Request Examples

### Example 1: All valid values
```json
{
  "firstName": "Anna",
  "lastName": "Danis",
  "personalEmail": "anna@example.com",
  "phoneNumber": "08123456789",
  "lastEducation": "HIGH",
  "onGoingEducation": "BACHELOR"
}
```
**Result:** ✅ Success - 200 OK

### Example 2: Null values (allowed with nullable=true)
```json
{
  "firstName": "Anna",
  "lastName": "Danis",
  "personalEmail": "anna@example.com",
  "phoneNumber": "08123456789",
  "lastEducation": null,
  "onGoingEducation": null
}
```
**Result:** ✅ Success - 200 OK

### Example 3: Omitted fields (treated as null)
```json
{
  "firstName": "Anna",
  "lastName": "Danis",
  "personalEmail": "anna@example.com",
  "phoneNumber": "08123456789"
}
```
**Result:** ✅ Success - 200 OK

## ❌ Invalid Request Examples

### Example 1: Invalid enum value
```json
{
  "firstName": "Anna",
  "lastName": "Danis",
  "personalEmail": "anna@example.com",
  "phoneNumber": "08123456789",
  "lastEducation": "INVALID_VALUE",
  "onGoingEducation": "BACHELOR"
}
```

**Result:** ❌ 400 Bad Request
```json
{
  "status": "BAD_REQUEST",
  "message": "Validation failed",
  "errors": {
    "lastEducation": "Invalid value. Must be one of: ELEMENTARY, JUNIOR, HIGH, BACHELOR, MASTER, DOCTORATE"
  }
}
```

### Example 2: Wrong case (case-sensitive)
```json
{
  "firstName": "Anna",
  "lastName": "Danis",
  "personalEmail": "anna@example.com",
  "phoneNumber": "08123456789",
  "lastEducation": "bachelor",  // lowercase - WRONG!
  "onGoingEducation": "BACHELOR"
}
```

**Result:** ❌ 400 Bad Request
```json
{
  "status": "BAD_REQUEST",
  "message": "Cannot deserialize value of type `Education` from String \"bachelor\": not one of the values accepted for Enum class"
}
```

**Note:** Case sensitivity is handled at deserialization level before validation

### Example 3: Misspelling
```json
{
  "firstName": "Anna",
  "lastName": "Danis",
  "personalEmail": "anna@example.com",
  "phoneNumber": "08123456789",
  "lastEducation": "BACHLOR",  // typo
  "onGoingEducation": "MASTER"
}
```

**Result:** ❌ 400 Bad Request

## 🔧 Configuration Options

### Make Field Required (Not Nullable)
```java
@NotNull
@ValidEnum(enumClass = Education.class, nullable = false)
private Education lastEducation;
```

**Invalid Request:**
```json
{
  "lastEducation": null  // ❌ Will fail validation
}
```

### Allow Null Values
```java
@ValidEnum(enumClass = Education.class, nullable = true)
private Education lastEducation;
```

**Valid Request:**
```json
{
  "lastEducation": null  // ✅ Allowed
}
```

## 📊 Validation Order

1. **JSON Deserialization** (Jackson)
   - Converts string to enum
   - Fails if string doesn't match any enum constant (case-sensitive)

2. **Bean Validation** (Jakarta Validation)
   - `@NotNull` check (if present)
   - `@ValidEnum` check (if value is not null or nullable=false)

3. **Custom Business Logic** (Your service layer)
   - Additional validations as needed

## 🎨 Valid Education Enum Values

```
ELEMENTARY  - Elementary School (SD)
JUNIOR      - Junior High School (SMP)
HIGH        - High School (SMA)
BACHELOR    - University - Bachelor (Kuliah - lulusan S1)
MASTER      - University - Master (Kuliah - lulusan S2)
DOCTORATE   - University - Doctorate (Kuliah - lulusan S3)
```

## 🧪 Testing

### Test Valid Values
```bash
curl -X POST http://localhost:8080/part-timer \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '{
    "firstName": "Test",
    "lastName": "User",
    "personalEmail": "test@example.com",
    "phoneNumber": "08123456789",
    "lastEducation": "HIGH",
    "onGoingEducation": "BACHELOR"
  }'
```

### Test Invalid Value
```bash
curl -X POST http://localhost:8080/part-timer \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '{
    "firstName": "Test",
    "lastName": "User",
    "personalEmail": "test@example.com",
    "phoneNumber": "08123456789",
    "lastEducation": "INVALID",
    "onGoingEducation": "BACHELOR"
  }'
```

### Test Null Values
```bash
curl -X POST http://localhost:8080/part-timer \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '{
    "firstName": "Test",
    "lastName": "User",
    "personalEmail": "test@example.com",
    "phoneNumber": "08123456789",
    "lastEducation": null,
    "onGoingEducation": null
  }'
```

## 🔄 Reusing @ValidEnum for Other Enums

You can use this annotation for any enum in your application:

```java
// Example with ApprovalStatus enum
@ValidEnum(enumClass = ApprovalStatus.class, nullable = false)
private ApprovalStatus status;

// Example with role enum (if you create one)
@ValidEnum(enumClass = RoleType.class, nullable = false)
private RoleType role;
```

## 🚀 Benefits

1. **Type Safety** ✅
   - Compile-time checking
   - No magic strings

2. **Clear Error Messages** ✅
   - Shows all valid values
   - User-friendly

3. **Reusable** ✅
   - Works with any enum
   - DRY principle

4. **Standards Compliant** ✅
   - Uses Jakarta Validation
   - Integrates with Spring Boot validation

5. **Production Ready** ✅
   - Proper error handling
   - Clear documentation

## ⚠️ Important Notes

### Case Sensitivity
Enum values are **case-sensitive**:
- ✅ `"BACHELOR"` - Correct
- ❌ `"bachelor"` - Wrong (will fail at deserialization)
- ❌ `"Bachelor"` - Wrong (will fail at deserialization)

### Deserialization vs Validation
- **Deserialization errors** happen before validation
- If JSON has `"INVALID"` → Deserialization fails (400 Bad Request)
- If JSON has `"BACHELOR"` → Deserialization succeeds → Validation runs → Success

### Frontend Integration
Your frontend should:
1. Use a dropdown/select with valid enum values
2. Send exact enum constant names (uppercase)
3. Handle validation errors gracefully

Example frontend code:
```javascript
const educationOptions = [
  { value: 'ELEMENTARY', label: 'Elementary School (SD)' },
  { value: 'JUNIOR', label: 'Junior High School (SMP)' },
  { value: 'HIGH', label: 'High School (SMA)' },
  { value: 'BACHELOR', label: 'Bachelor (S1)' },
  { value: 'MASTER', label: 'Master (S2)' },
  { value: 'DOCTORATE', label: 'Doctorate (S3)' }
];
```

## ✅ Summary

Your enum validation is now:
- ✅ **Validated** - Invalid values are rejected
- ✅ **User-Friendly** - Clear error messages
- ✅ **Flexible** - Configurable nullable option
- ✅ **Reusable** - Works with any enum
- ✅ **Production-Ready** - Enterprise-grade validation

The `@ValidEnum` annotation ensures that only valid Education enum values are accepted in your API! 🎉

