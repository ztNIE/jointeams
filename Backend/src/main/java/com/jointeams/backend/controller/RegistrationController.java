package com.jointeams.backend.controller;

import com.jointeams.backend.springmail.EmailType;
import com.jointeams.backend.springmail.SendEmailEvent;
import com.jointeams.backend.model.request.PasswordRequest;
import com.jointeams.backend.model.request.RegisterUserRequest;
import com.jointeams.backend.model.response.RegisterResponse;
import com.jointeams.backend.model.response.StandardResponse;
import com.jointeams.backend.pojo.User;
import com.jointeams.backend.repositery.PasswordTokenRepository;
import com.jointeams.backend.service.RegisterService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/register")
@Slf4j
@AllArgsConstructor
public class RegistrationController {

    private RegisterService registerService;

    private ApplicationEventPublisher publisher;

    private PasswordTokenRepository passwordTokenRepository;

    /**
     *
     * @param registerUserRequest use RegisterUserRequest Model
     * @param request HttpServletRequest
     * @return if success, body: registerResponse, status code: 200
     *         if failed, body: error message, status code: 400
     */

    @PostMapping({"/", ""})
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterUserRequest registerUserRequest,
                                                   final HttpServletRequest request) {

        String result = registerService.isUserModelValid(registerUserRequest);
        if (result.equalsIgnoreCase("valid")) {
            User user = registerService.registerUser(registerUserRequest);
            RegisterResponse registerResponse = new RegisterResponse(user.getEmail(), user.getFirstName(), user.getLastName());
//            publisher.publishEvent(
//                    SendEmailEvent.builder()
//                            .applicationUrl(getApplicationUrl(request))
//                            .emailType(EmailType.VERIFY)
//                            .email(user.getEmail())
//                            .build()
//            );
            publisher.publishEvent(
                    new SendEmailEvent(
                            user.getEmail(),
                            getApplicationUrl(request),
                            EmailType.VERIFY)
            );
            return ResponseEntity.ok().body(new StandardResponse<>("success", registerResponse));
        } else {
            return ResponseEntity.badRequest().body(new StandardResponse<>(result, null));
        }
    }

    /**
     *
     * @param token verification_token
     * @param request HttpRequest
     * @return if success, msg: success, data: null, status code: 200
     *         if failed, msg: error message, data: null, status code: 400
     */
    @GetMapping("/verify")
    public ResponseEntity<?> verifyRegistration(@RequestParam("token") String token, final HttpServletRequest request) {
        String result = registerService.validateVerificationToken(token);

        if (result.equalsIgnoreCase("valid")) {
            return ResponseEntity.ok().body(new StandardResponse<>("success", null));
        } else if (result.equalsIgnoreCase("timeout")) {
            User user = registerService.deleteOldVerifyToken(token);
//            publisher.publishEvent(new SendVerifyEmailEvent(user.getEmail(), getApplicationUrl(request)));
            publisher.publishEvent(
//                    SendEmailEvent.builder()
//                            .applicationUrl(getApplicationUrl(request))
//                            .emailType(EmailType.VERIFY)
//                            .email(user.getEmail())
//                            .build()
                    new SendEmailEvent(
                            user.getEmail(),
                            getApplicationUrl(request),
                            EmailType.VERIFY)
            );
            return ResponseEntity.badRequest().body(
                    new StandardResponse<>("Token expired, resend token.", null));
        } else if (result.equalsIgnoreCase("notfound")){
            return ResponseEntity.badRequest().body(new StandardResponse<>("Token not found", null));
        } else {
            log.error("Failed to catch validateVerificationToken");
            return ResponseEntity.internalServerError().body(new StandardResponse<>("Unknown exception", null));
        }
    }

    /**
     *
     * @param passwordRequest Give the email of the user
     * @param request HttpRequest
     * @return if success, msg: success, status code: 200, send reset password token email
     *         if failed, msg: User Not Found, status code: 400
     */

    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody PasswordRequest passwordRequest, HttpServletRequest request) {
        User user = registerService.findUserByEmail(passwordRequest.getEmail());
        if (user == null) {
            return ResponseEntity.badRequest().body(new StandardResponse<>("User Not Found", null));
        }

        // If already sent a reset password email, delete the old token
        if (passwordTokenRepository.existsByUserId(user.getId())){
            passwordTokenRepository.deleteByUserId(user.getId());
        }

//        publisher.publishEvent(new SendSavePasswordEmailEvent(user.getEmail(), getApplicationUrl(request)));
        publisher.publishEvent(
                new SendEmailEvent(
                        user.getEmail(),
                        getApplicationUrl(request),
                        EmailType.RESET_PASSWORD)
        );
        return ResponseEntity.ok().body(new StandardResponse<>("success", null));
    }

    /**
     *
     * @param token String in header generated by resetPassword
     * @param passwordRequest  email & newPassword
     * @param request HttpRequest
     * @return if success, msg: success
     *         if failed, msg: error message
     */
    @PostMapping("/savePassword")
    public ResponseEntity<?> savePassword(@RequestParam("token") String token,
                               @RequestBody PasswordRequest passwordRequest,
                               HttpServletRequest request) {
        String result = registerService.validatePasswordToken(token);
        if (result.equalsIgnoreCase("notfound")) {
            return ResponseEntity.ok().body(new StandardResponse<>("Token Not Found", null));
        }

        User user = registerService.deleteOldPasswordToken(token);

        if (result.equalsIgnoreCase("timeout")) {
//            publisher.publishEvent(new SendSavePasswordEmailEvent(user.getEmail(), getApplicationUrl(request)));
            publisher.publishEvent(
                    new SendEmailEvent(
                            user.getEmail(),
                            getApplicationUrl(request),
                            EmailType.RESET_PASSWORD)
            );
            return ResponseEntity.badRequest().body(new StandardResponse<>("Token expired, resend", null));
        }

        registerService.savePassword(user, passwordRequest);
        return ResponseEntity.ok().body(new StandardResponse<>("success", null));
    }

    private String getApplicationUrl(HttpServletRequest request) {
        return "http://" +
                request.getServerName() +
                ":" +
                request.getServerPort() +
                request.getContextPath();
    }
}
