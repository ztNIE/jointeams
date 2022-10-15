package com.jointeams.backend.controller;

import com.jointeams.backend.model.request.LoginRequest;
import com.jointeams.backend.model.request.ReCaptchaRequest;
import com.jointeams.backend.model.response.CheckEmailResponse;
import com.jointeams.backend.model.response.StandardResponse;
import com.jointeams.backend.model.response.UserInfoResponse;
import com.jointeams.backend.repositery.UserRepository;
import com.jointeams.backend.security.jwt.JwtUtils;
import com.jointeams.backend.security.service.CustomUserDetails;
import com.jointeams.backend.service.RegisterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    /**
     * AuthController API: /auth/login & /auth/logout
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RegisterService registerService;

    @GetMapping("/validEmailExist")
    public ResponseEntity<?> isEmailExist(@RequestParam(name="email") String email) {
        Boolean result = userRepository.existsByEmail(email);
        return ResponseEntity.ok().body(new StandardResponse<>("Success", new CheckEmailResponse(result)));
    }

    @GetMapping("/validEmailActivate")
    public ResponseEntity<?> isEmailActivate(@RequestParam(name="email") String email) {
        Boolean result = userRepository.existsByEmailAndIsActivateTrue(email);
        return ResponseEntity.ok().body(new StandardResponse<>("Success", new CheckEmailResponse(result)));
    }

    /**
     *
     * @param loginRequest  email & password
     * @return if success, body: userInfoResponse, status code: 200
     *         if failed, body: message: Bad credentials, status code: 401
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                    .body(new StandardResponse<>("success",
                            new UserInfoResponse(userDetails.getId(), userDetails.getUsername(), roles)));
    }

    @PostMapping("/validReCaptchaToken")
    public ResponseEntity<?> validReCaptchaToken(@RequestBody ReCaptchaRequest request) {
        Boolean response = registerService.postReCaptchaToken(request.getToken(), request.getAction());
        return ResponseEntity.ok().body(new StandardResponse<>(response ? "success" : "failed", null));
    }
}
