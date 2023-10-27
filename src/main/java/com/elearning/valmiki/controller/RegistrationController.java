    package com.elearning.valmiki.controller;

    import com.elearning.valmiki.dto.InstructorDto;
    import com.elearning.valmiki.dto.UserDto;
    import com.elearning.valmiki.entity.Instructor;
    import com.elearning.valmiki.entity.User;
    import com.elearning.valmiki.service.InstructorService;
    import com.elearning.valmiki.service.UserService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.web.bind.annotation.CrossOrigin;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestBody;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;

    @RestController
    @RequestMapping("/api/auth/v1")
    @CrossOrigin(origins = "http://localhost:3000")
    public class RegistrationController
    {
        @Autowired
        private UserService userService;
    
        @Autowired
        private InstructorService instructorService;

        @Autowired
        private PasswordEncoder passwordEncoder;

        @PostMapping("/registeruser")
        public UserDto registerUser(@RequestBody UserDto userDto) throws Exception
        {
            String currEmail = userDto.getEmail();
            String newID = getNewID();
            userDto.setUserid(newID);

            if (currEmail != null || !"".equals(currEmail))
            {
                User userObj = userService.fetchUserByEmail(currEmail);
                if (userObj != null)
                {
                    throw new Exception("User with " + currEmail + " already exists !!!");
                }
            }

            // Encode the password using BCrypt
            String encodedPassword = passwordEncoder.encode(userDto.getPassword());
            userDto.setPassword(encodedPassword);

            // Convert UserDto to User entity
            User user = convertToUser(userDto);

            User userObj = userService.saveUser(user);

            // Convert saved User entity to UserDto
            return convertToUserDto(userObj);
        }

        @PostMapping("/registerprofessor")
        public InstructorDto registerDoctor(@RequestBody InstructorDto instructorDto) throws Exception
        {
            String currEmail = instructorDto.getEmail();
            String newID = getNewID();
            instructorDto.setInstructorId(newID);

            if (currEmail != null || !"".equals(currEmail))
            {
                Instructor instructorObj = instructorService.fetchInstructorByEmail(currEmail);
                if (instructorObj != null)
                {
                    throw new Exception("Instructor with " + currEmail + " already exists !!!");
                }
            }

            // Encode the password using BCrypt
            String encodedPassword = passwordEncoder.encode(instructorDto.getPassword());
            instructorDto.setPassword(encodedPassword);

            // Convert InstructorDto to Instructor entity
            Instructor instructor = convertToInstructor(instructorDto);

            Instructor instructorObj = instructorService.saveInstructor(instructor);

            // Convert saved Instructor entity to InstructorDto
            return convertToInstructorDto(instructorObj);
        }

        public String getNewID()
        {
            String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvwxyz";
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 12; i++)
            {
                int index = (int) (AlphaNumericString.length() * Math.random());
                sb.append(AlphaNumericString.charAt(index));
            }
            return sb.toString();
        }

        // Conversion methods from DTO to Entity and vice versa

        private UserDto convertToUserDto(User user) {
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

        private User convertToUser(UserDto userDto) {
            return User.builder()
                    .email(userDto.getEmail())
                    .username(userDto.getUsername())
                    .userid(userDto.getUserid())
                    .mobile(userDto.getMobile())
                    .gender(userDto.getGender())
                    .profession(userDto.getProfession())
                    .address(userDto.getAddress())
                    .password(userDto.getPassword())
                    .resetToken(userDto.getResetToken())
                    .build();
        }

        private InstructorDto convertToInstructorDto(Instructor instructor) {
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

        private Instructor convertToInstructor(InstructorDto instructorDto) {
            return Instructor.builder()
                    .email(instructorDto.getEmail())
                    .instructorName(instructorDto.getInstructorName())
                    .instructorId(instructorDto.getInstructorId())
                    .experience(instructorDto.getExperience())
                    .mobile(instructorDto.getMobile())
                    .gender(instructorDto.getGender())
                    .password(instructorDto.getPassword())
                    .status(instructorDto.getStatus())
                    .build();
        }
    }
