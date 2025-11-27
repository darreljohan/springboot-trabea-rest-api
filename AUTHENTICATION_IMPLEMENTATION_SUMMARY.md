# Complete Authentication Implementation with Role-Based JWT

## Overview
Your authentication system now validates **workEmail + password + role** and generates JWT tokens with role information embedded in the SecurityContext.

## 🔐 Complete Authentication Flow

### 1. **Login Request** (Client → Backend)
```json
POST /auth/login
{
  "username": "john@company.com",
  "password": "secretPassword",
  "role": "MANAGER"
}
```

### 2. **AuthService.createToken()** - Validates Credentials
```java
// Step 1: Validate user exists with specified role
User user = userRepository.findByWorkEmailAndUserRoles_Role_Name(email, role)
    .orElseThrow(() -> new RuntimeException("Invalid credentials or role"));

// Step 2: Verify password using BCrypt
if (!passwordEncoder.matches(password, user.getPassword())) {
    throw new RuntimeException("Invalid credentials");
}

// Step 3: Load UserDetails with role validation
UserDetails userDetails = authUserDetailService.loadUserByUsernameAndRole(email, role);

// Step 4: Set SecurityContext with Authentication
Authentication auth = new UsernamePasswordAuthenticationToken(
    userDetails, null, userDetails.getAuthorities());
SecurityContextHolder.getContext().setAuthentication(auth);

// Step 5: Generate JWT with role in claims
String token = jwtService.generateToken(user, role);
```

### 3. **JWT Token Generated** - Contains Role
```java
// JwtService.generateToken(user, role)
{
  "subject": "john@company.com",
  "issuer": "Trabea",
  "issuedAt": "2025-11-26T10:00:00Z",
  "expiration": "2025-11-26T18:00:00Z",
  "claims": {
    "role": "MANAGER",
    "workEmail": "john@company.com"
  }
}
```

### 4. **Subsequent Requests** - JWT Validation
```
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

### 5. **JwtRequestFilter** - Validates & Sets SecurityContext
```java
// Extract from token
String username = jwtService.extractUsername(token);
String role = jwtService.extractRole(token);

// Load user with role validation
UserDetails userDetails = userDetailsService.loadUserByUsernameAndRole(username, role);

// Validate token
if (jwtService.isTokenValid(token, userDetails)) {
    // Set SecurityContext with authorities
    Authentication auth = new UsernamePasswordAuthenticationToken(
        userDetails, null, userDetails.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(auth);
}
```

## 📦 Components Summary

### ✅ AuthJwtRequest (DTO)
```java
- String username  // workEmail
- String password  // plain text password
- String role      // role name to authenticate with
```

### ✅ AuthUserDetails (UserDetails Implementation)
```java
- Wraps User entity
- Extracts authorities from User.userRoles collection
- Converts to Spring Security GrantedAuthority as "ROLE_MANAGER", "ROLE_ADMIN", etc.
```

### ✅ AuthUserDetailService
```java
// Standard Spring Security method
loadUserByUsername(String email) → UserDetails

// Custom method with role validation
loadUserByUsernameAndRole(String email, String role) → UserDetails
```

### ✅ UserRepository
```java
// Find user by email only
Optional<User> findByWorkEmail(String workEmail);

// Find user with role validation
Optional<User> findByWorkEmailAndUserRoles_Role_Name(String workEmail, String roleName);
```

### ✅ JwtService
```java
// Generate token with role
String generateToken(User user, String role);

// Extract claims from token
String extractUsername(String token);
String extractRole(String token);

// Validate token
boolean isTokenValid(String token, UserDetails userDetails);
```

### ✅ JwtRequestFilter (OncePerRequestFilter)
```java
- Intercepts all HTTP requests
- Extracts JWT from Authorization header
- Extracts username + role from token
- Loads user with role validation
- Sets SecurityContext with authenticated user + authorities
```

## 🔒 Security Features Implemented

1. **✅ Email + Password + Role Validation**
   - User must exist in database
   - User must have the specified role
   - Password must match (BCrypt encrypted)

2. **✅ SecurityContext Population**
   - Authentication object created with UserDetails
   - Authorities populated from UserRole collection
   - SecurityContext set for the request

3. **✅ JWT Token with Role**
   - Role embedded in token claims
   - Token validated on each request
   - Role re-validated from database

4. **✅ Spring Security Chain Integration**
   - JwtRequestFilter runs before authentication
   - Sets SecurityContext for @PreAuthorize, @Secured annotations
   - Enables role-based access control

## 🎯 Usage in Controllers

### Access Current User
```java
@GetMapping("/profile")
public ResponseEntity<?> getProfile() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    AuthUserDetails userDetails = (AuthUserDetails) auth.getPrincipal();
    User user = userDetails.getUser();
    return ResponseEntity.ok(user);
}
```

### Role-Based Authorization
```java
@PreAuthorize("hasRole('MANAGER')")
@GetMapping("/manager-only")
public ResponseEntity<?> managerEndpoint() {
    return ResponseEntity.ok("Manager access granted");
}

@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
@GetMapping("/admin-or-manager")
public ResponseEntity<?> adminOrManagerEndpoint() {
    return ResponseEntity.ok("Access granted");
}
```

## ⚠️ Important Notes

1. **User can have multiple roles** - UserRole is a many-to-many relationship
2. **Login with specific role** - Client must specify which role to use
3. **Token contains one role** - Each token is bound to one role context
4. **Re-login for different role** - User must get new token to switch roles

## 🔧 Configuration Required

Ensure your `AuthConfiguration` has:
```java
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}

@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) {
    return http
        .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/auth/**").permitAll()
            .anyRequest().authenticated()
        )
        .build();
}
```

## ✅ Complete!
Your authentication system is now fully configured with:
- ✅ Email + Password + Role validation
- ✅ JWT token generation with role claims
- ✅ SecurityContext population with authorities
- ✅ Spring Security chain integration
- ✅ Role-based access control ready

## 📋 Files Modified/Created

1. **AuthJwtRequest.java** - Added `role` field
2. **AuthUserDetails.java** - Fixed to use custom User entity with UserRole collection
3. **AuthUserDetailService.java** - Implemented `loadUserByUsernameAndRole()`
4. **AuthService.java** - Complete authentication flow with role validation
5. **JwtService.java** - Added `generateToken(user, role)` and `extractRole(token)`
6. **JwtRequestFilter.java** - Extract role from token and validate
7. **UserRepository.java** - Added `findByWorkEmailAndUserRoles_Role_Name()`
8. **User.java** - Fixed relationship: `Set<Role>` → `Set<UserRole>`

