package com.jointeams.backend.controller;

import com.jointeams.backend.event.SendSavePasswordEmailEvent;
import com.jointeams.backend.event.SendVerifyEmailEvent;
import com.jointeams.backend.model.PasswordRequest;
import com.jointeams.backend.model.RegisterUserRequest;
import com.jointeams.backend.pojo.User;
import com.jointeams.backend.service.RegisterService;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/register")
@Slf4j
public class RegistrationController {

    @Autowired
    private RegisterService registerService;

    @Autowired
    private ApplicationEventPublisher publisher;

    /**
     *
     * @param registerUserRequest use RegisterUserRequest Model
     * @param request HttpServletRequest
     * @return if success, body: (User) JSONObject, status code: 200
     *         if email validation fail, body: bad email, status code: 406
     *         if university validation fail, body: bad university, status code: 406
     *         if unexpected result: body null, status code: 500
     */
    @PostMapping({"/", ""})
    public ResponseEntity<JSONObject> registerUser(@RequestBody RegisterUserRequest registerUserRequest,
                                                   final HttpServletRequest request) {

        String result = registerService.isUserModelValid(registerUserRequest);
        if (result.equalsIgnoreCase("bad email")) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("msg", result);
            return new ResponseEntity<>(jsonObject, HttpStatus.NOT_ACCEPTABLE);
        } else if (result.equalsIgnoreCase("bad university")) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("msg", result);
            return new ResponseEntity<>(jsonObject, HttpStatus.NOT_ACCEPTABLE);
        } else if (result.equalsIgnoreCase("valid")) {
            JSONObject jsonObject = registerService.registerUser(registerUserRequest);
            publisher.publishEvent(new SendVerifyEmailEvent((String) jsonObject.get("email"),
                    getApplicationUrl(request)));
            return new ResponseEntity<>(jsonObject, HttpStatus.OK);
        } else {
            log.error("UserModel validation not catch");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     *
     * @param token verification_token
     * @param request HttpRequest
     * @return if success, body: User verified successfully, status code: 200
     *         if token expired, body: Token expired, resend token , status code: 400
     *         if token not found, body: Token not found, status code: 404
     *         if unexpected result, body: null, status code: 500
     */
    @GetMapping("/verify")
    public ResponseEntity<JSONObject> verifyRegistration(@RequestParam("token") String token, final HttpServletRequest request) {
        String result = registerService.validateVerificationToken(token);
        JSONObject jsonObject = new JSONObject();
        if (result.equalsIgnoreCase("valid")) {
            jsonObject.put("msg", "User verified successfully");
            return new ResponseEntity<>(jsonObject, HttpStatus.OK);
        } else if (result.equalsIgnoreCase("timeout")) {
            User user = registerService.deleteOldVerifyToken(token);
            publisher.publishEvent(new SendVerifyEmailEvent(user.getEmail(), getApplicationUrl(request)));
            jsonObject.put("msg", "Token expired. Resend Token.");
            return new ResponseEntity<>(jsonObject, HttpStatus.BAD_REQUEST);
        } else if (result.equalsIgnoreCase("notfound")){
            jsonObject.put("msg", "Token not found");
            return new ResponseEntity<>(jsonObject, HttpStatus.NOT_FOUND);
        } else {
            log.error("Failed to catch validateVerificationToken");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     *
     * @param passwordRequest
     * @param request
     * @return
     */
    @PreAuthorize("hasRole(USER)")
    @PostMapping("/resetPassword")
    public ResponseEntity<JSONObject> resetPassword(@RequestBody PasswordRequest passwordRequest, HttpServletRequest request) {
        User user = registerService.findUserByEmail(passwordRequest.getEmail());
        if (user == null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("msg", "User Not Found");
            return new ResponseEntity<>(jsonObject, HttpStatus.NOT_FOUND);
        }
        publisher.publishEvent(new SendSavePasswordEmailEvent(user.getEmail(), getApplicationUrl(request)));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", "Reset Link Sent");
        return new ResponseEntity<>(jsonObject, HttpStatus.OK);
    }

    /**
     *
     * @param token
     * @param passwordRequest
     * @param request
     * @return
     */
    @PostMapping("/savePassword")
    public ResponseEntity<JSONObject> savePassword(@RequestParam("token") String token,
                               @RequestBody PasswordRequest passwordRequest,
                               HttpServletRequest request) {
        String result = registerService.validatePasswordToken(token);
        if (result.equalsIgnoreCase("notfound")) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("msg", "Token Not Found");
            return new ResponseEntity<>(jsonObject, HttpStatus.NOT_FOUND);
        }

        User user = registerService.deleteOldPasswordToken(token);

        if (result.equalsIgnoreCase("timeout")) {
            publisher.publishEvent(new SendSavePasswordEmailEvent(user.getEmail(), getApplicationUrl(request)));
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("msg", "Token expired. Resend token.");
            return new ResponseEntity<>(jsonObject, HttpStatus.BAD_REQUEST);
        }

        registerService.savePassword(user, passwordRequest);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", "Reset Password Successfully");
        return new ResponseEntity<>(jsonObject, HttpStatus.OK);
    }

    private String getApplicationUrl(HttpServletRequest request) {
        return "http://" +
                request.getServerName() +
                ":" +
                request.getServerPort() +
                request.getContextPath();
    }
}
