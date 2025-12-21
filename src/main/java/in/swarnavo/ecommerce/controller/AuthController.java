package in.swarnavo.ecommerce.controller;

import in.swarnavo.ecommerce.model.AppRole;
import in.swarnavo.ecommerce.model.Role;
import in.swarnavo.ecommerce.model.User;
import in.swarnavo.ecommerce.repositories.RoleRepository;
import in.swarnavo.ecommerce.repositories.UserRepository;
import in.swarnavo.ecommerce.security.jwt.JwtUtils;
import in.swarnavo.ecommerce.security.request.LoginRequest;
import in.swarnavo.ecommerce.security.request.SignupRequest;
import in.swarnavo.ecommerce.security.response.MessageResponse;
import in.swarnavo.ecommerce.security.response.UserInfoResponse;
import in.swarnavo.ecommerce.security.services.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    RoleRepository roleRepository;

    @Tag(name = "Auth APIs", description = "APIs for managing authentication")
    @Operation(summary = "Login User", description = "API to login users")
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
        } catch (AuthenticationException exception) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Bad credentials");
            map.put("status", false);
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        // UserInfoResponse response = new UserInfoResponse(userDetails.getId(), userDetails.getUsername(), jwtToken, roles);
        UserInfoResponse response = new UserInfoResponse(userDetails.getId(), jwtCookie.toString(), userDetails.getUsername(), roles);

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).body(response);
    }

    @Tag(name = "Auth APIs", description = "APIs for managing authentication")
    @Operation(summary = "Register User", description = "API to register users")
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        if(userRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if(userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already taken!"));
        }

        User user = new User(
                signupRequest.getUsername(),
                signupRequest.getEmail(),
                encoder.encode(signupRequest.getPassword())
        );

        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if(strRoles == null) {
            Role userRole = roleRepository.findByRoleName(AppRole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not Found"));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByRoleName(AppRole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not Found"));;
                        roles.add(adminRole);
                        break;
                    case "seller":
                        Role sellerRole = roleRepository.findByRoleName(AppRole.ROLE_SELLER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not Found"));;
                        roles.add(sellerRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByRoleName(AppRole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not Found"));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User Registered successfully!"));
    }

    @Tag(name = "Auth APIs", description = "APIs for managing authentication")
    @Operation(summary = "Fetch Current Username", description = "API to fetch current username")
    @GetMapping("/username")
    public String currentUserName(Authentication authentication) {
        if(authentication != null) {
            return authentication.getName();
        } else {
            return "";
        }
    }

    @Tag(name = "Auth APIs", description = "APIs for managing authentication")
    @Operation(summary = "Fetch User Details", description = "API to fetch user details")
    @GetMapping("/user")
    public ResponseEntity<?> getUserDetails(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        UserInfoResponse response = new UserInfoResponse(userDetails.getId(), userDetails.getUsername(), roles);

        return ResponseEntity.ok().body(response);
    }

    @Tag(name = "Auth APIs", description = "APIs for managing authentication")
    @Operation(summary = "Logout", description = "API to logout users")
    @PostMapping("/signout")
    public ResponseEntity<?> signoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanCookie();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("You have been signed out"));
    }
}
