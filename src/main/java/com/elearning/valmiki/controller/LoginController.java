package com.elearning.valmiki.controller;

import com.elearning.valmiki.dto.InstructorDto;
import com.elearning.valmiki.dto.ResetPasswordDto;
import com.elearning.valmiki.dto.UserDto;
import com.elearning.valmiki.entity.Instructor;
import com.elearning.valmiki.entity.User;
import com.elearning.valmiki.repository.InstructorRepository;
import com.elearning.valmiki.repository.UserRepository;
import com.elearning.valmiki.service.EmailService;
import com.elearning.valmiki.service.InstructorService;
import com.elearning.valmiki.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
@RequestMapping("/api/auth/v1")
@CrossOrigin(origins = "http://localhost:3000")
public class LoginController
{
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private InstructorService instructorService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    private static final Map<String, String> userSessions = new ConcurrentHashMap<>();

    private final Logger logger = LoggerFactory.getLogger(LoginController.class);


    @GetMapping("/")
    public String welcomeMessage()
    {
        return "Welcome to Valmiki !!!";
    }

    @PostMapping("/loginuser")
    public UserDto loginUser(@RequestBody UserDto user) throws Exception
    {
        String currEmail = user.getEmail();
        String currPassword = user.getPassword();

        User userObj = userService.fetchUserByEmail(currEmail);

        if (userObj != null && passwordEncoder.matches(currPassword, userObj.getPassword())) {
            return convertToUserDto(userObj);
        } else {
            throw new Exception("User does not exist or invalid credentials.");
        }


    }

    @PostMapping("/loginprofessor")
    public InstructorDto loginInstructor(@RequestBody InstructorDto instructorDto) throws Exception
    {
        String currEmail = instructorDto.getEmail();
        String currPassword = instructorDto.getPassword();

        Instructor instructorObj = instructorService.fetchInstructorByEmail(currEmail);
        if (currEmail != null && passwordEncoder.matches(currPassword, instructorObj.getPassword()))
        {
            return convertToInstructorDto(instructorObj);
        }
        else
        {
            throw new Exception("Instructor does not exist!!! Please enter valid credentials...");
        }

    }

    @RequestMapping(value = "/forgot", method = RequestMethod.GET)
    public ModelAndView displayForgotPasswordPage()
    {
        return new ModelAndView("forgotPassword");
    }

    @RequestMapping(value = "/forgot", method = RequestMethod.POST)
    public ModelAndView processForgotPasswordForm(ModelAndView modelAndView,
                                                  @RequestBody Map<String, String> requestPayload, HttpServletRequest request)
    {
        String userEmail = requestPayload.get("email");
        User user = userService.fetchUserByEmail(userEmail);

        if (user == null)
        {
            modelAndView.addObject("errorMessage",
                    "We didn't find an account for that e-mail address.");
        }
        else
        {

            user.setResetToken(UUID.randomUUID().toString());

            userService.saveUser(user);

            String appUrl = request.getScheme() + "://" + request.getServerName() + ":"
                    + request.getServerPort() + request.getContextPath() + "/api/auth/v1";

            SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
            passwordResetEmail.setFrom("support@demo.com");
            passwordResetEmail.setTo(user.getEmail());
            passwordResetEmail.setSubject("Password Reset Request");
            passwordResetEmail.setText("To reset your password, click the link below:\n" + appUrl
                    + "/reset?token=" + user.getResetToken());

            emailService.sendEmail(passwordResetEmail);

            modelAndView.addObject("successMessage",
                    "A password reset link has been sent to " + userEmail);
        }

        modelAndView.setViewName("forgotPassword");
        return modelAndView;
    }

    @RequestMapping(value = "/reset", method = RequestMethod.GET)
    public ModelAndView displayResetPasswordPage(ModelAndView modelAndView, @RequestParam("token") String token)
    {
        Optional<User> user = userService.findUserByResetToken(token);

        if (user.isPresent())
        {
            modelAndView = new ModelAndView("resetPassword");
            modelAndView.addObject("resetToken", token);
            return modelAndView;
        }
        else
        {
            modelAndView
                    .addObject("errorMessage", "Oops! This is an invalid password reset link.");
            modelAndView.setViewName("errorPage");
        }

        return modelAndView;
    }

    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(@RequestParam("token") String resetToken, @RequestBody ResetPasswordDto resetPasswordDto) {
        try {
            Optional<User> optionalUser = userService.findUserByResetToken(resetToken);

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();

                user.setPassword(passwordEncoder.encode(resetPasswordDto.getNewPassword()));
                user.setResetToken(null);

                userRepository.save(user);
                logger.info("Password reset successfully for user: {}", user.getEmail());

                return new ResponseEntity<>("Password reset successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Invalid reset token", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            logger.error("Error resetting password: {}", e.getMessage(), e);
            return new ResponseEntity<>("Error resetting password", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/forgotprof", method = RequestMethod.POST)
    public ModelAndView processForgotPasswordProf(ModelAndView modelAndView,
                                                  @RequestBody Map<String, String> requestPayload, HttpServletRequest request)
    {
        String userEmail = requestPayload.get("email");
        Instructor instructor = instructorService.fetchInstructorByEmail(userEmail);

        if (instructor == null)
        {
            modelAndView.addObject("errorMessage",
                    "We didn't find an account for that e-mail address.");
        }
        else
        {

            instructor.setResetToken(UUID.randomUUID().toString());

            instructorService.saveInstructor(instructor);

            String appUrl = request.getScheme() + "://" + request.getServerName() + ":"
                    + request.getServerPort() + request.getContextPath() + "/api/auth/v1";

            SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
            passwordResetEmail.setFrom("support@demo.com");
            passwordResetEmail.setTo(instructor.getEmail());
            passwordResetEmail.setSubject("Password Reset Request");
            passwordResetEmail.setText("To reset your password, click the link below:\n" + appUrl
                    + "/resetprof?token=" + instructor.getResetToken());

            emailService.sendEmail(passwordResetEmail);

            modelAndView.addObject("successMessage",
                    "A password reset link has been sent to " + userEmail);
        }

        modelAndView.setViewName("forgotPassword");
        return modelAndView;
    }

    @RequestMapping(value = "/resetprof", method = RequestMethod.GET)
    public ModelAndView displayResetPasswordPageProf(ModelAndView modelAndView, @RequestParam("token") String token)
    {
        Optional<Instructor> instructor = instructorService.findUserByResetToken(token);

        if (instructor.isPresent())
        {
            modelAndView = new ModelAndView("resetPasswordProf");
            modelAndView.addObject("resetToken", token);
            return modelAndView;
        }
        else
        {
            modelAndView
                    .addObject("errorMessage", "Oops! This is an invalid password reset link.");
            modelAndView.setViewName("errorPage");
        }

        return modelAndView;
    }

    @PostMapping("/resetprof")
    public ResponseEntity<String> resetPasswordProf(@RequestParam("token") String resetToken, @RequestBody ResetPasswordDto resetPasswordDto) {
        try {
            Optional<Instructor> optionalInstructor = instructorService.findUserByResetToken(resetToken);

            if (optionalInstructor.isPresent()) {
                Instructor instructor = optionalInstructor.get();

                instructor.setPassword(passwordEncoder.encode(resetPasswordDto.getNewPassword()));
                instructor.setResetToken(null);

                instructorRepository.save(instructor);
                logger.info("Password reset successfully for user: {}", instructor.getEmail());

                return new ResponseEntity<>("Password reset successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Invalid reset token", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            logger.error("Error resetting password: {}", e.getMessage(), e);
            return new ResponseEntity<>("Error resetting password", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // session management

    @GetMapping("/check-session")
    public ResponseEntity<String> checkSession(HttpServletRequest request) {
        String sessionToken = getSessionToken(request);

        if (isValidSession(sessionToken)) {
            return new ResponseEntity<>("Session is valid", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Session is not valid", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String sessionToken = getSessionToken(request);

        if (sessionToken != null) {
            userSessions.remove(sessionToken);
            return new ResponseEntity<>("Logout successful", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No active session to logout", HttpStatus.BAD_REQUEST);
        }
    }

    private boolean isValidSession(String sessionToken) {
        return userSessions.containsKey(sessionToken);
    }

    private String getSessionToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    // Conversion methods for DTOs

    private UserDto convertToUserDto(User user)
    {
        return UserDto.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .userid(user.getUserid())
                .mobile(user.getMobile())
                .gender(user.getGender())
                .profession(user.getProfession())
                .address(user.getAddress())
                .password(user.getPassword())
                .resetToken(user.getResetToken())
                .build();
    }

    private InstructorDto convertToInstructorDto(Instructor instructor)
    {
        return InstructorDto.builder()
                .email(instructor.getEmail())
                .instructorName(instructor.getInstructorName())
                .instructorId(instructor.getInstructorId())
                .experience(instructor.getExperience())
                .mobile(instructor.getMobile())
                .gender(instructor.getGender())
                .password(instructor.getPassword())
                .status(instructor.getStatus())
                .build();
    }
}
