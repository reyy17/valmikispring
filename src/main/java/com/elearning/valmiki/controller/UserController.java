package com.elearning.valmiki.controller;

import com.elearning.valmiki.dto.EnrollmentDto;
import com.elearning.valmiki.dto.UserDto;
import com.elearning.valmiki.entity.Enrollment;
import com.elearning.valmiki.entity.User;
import com.elearning.valmiki.dto.WishlistDto;
import com.elearning.valmiki.entity.Chapter;
import com.elearning.valmiki.entity.Instructor;
import com.elearning.valmiki.entity.Wishlist;
import com.elearning.valmiki.service.ChapterService;
import com.elearning.valmiki.service.CourseService;
import com.elearning.valmiki.service.EnrollmentService;
import com.elearning.valmiki.service.InstructorService;
import com.elearning.valmiki.service.UserService;
import com.elearning.valmiki.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


@RestController
@RequestMapping("/api/auth/v1")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private InstructorService instructorService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private WishlistService wishlistService;

    @Autowired
    private ChapterService chapterService;

    @GetMapping("/userlist")
    public ResponseEntity<List<UserDto>> getUsers() throws Exception {
        List<User> users = userService.getAllUsers();
        List<UserDto> userDtos = new ArrayList<>();

        // Convert User entities to UserDto
        for (User user : users) {
            UserDto userDto = convertToUserDto(user);
            userDtos.add(userDto);
        }

        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    @PostMapping("/enrollnewcourse/{email}/{role}")
    public String enrollNewCourse(@RequestBody EnrollmentDto enrollmentDto, @PathVariable String email, @PathVariable String role) throws Exception {
        String enrolledUsername = "", enrolledUserID = "";

        if (role.equalsIgnoreCase("user")) {
            List<User> users = userService.getAllUsers();
            for (User userObj : users) {
                if (userObj.getEmail().equalsIgnoreCase(email)) {
                    enrolledUsername = userObj.getUsername();
                    enrolledUserID = userObj.getUserid();
                    enrollmentDto.setEnrolledUserId(enrolledUserID);
                    enrollmentDto.setEnrolledUsername(enrolledUsername);
                    break;
                }
            }
        } else if (role.equalsIgnoreCase("instructor")) {
            List<Instructor> instructors = instructorService.getAllInstructors();
            for (Instructor instructorObj : instructors) {
                if (instructorObj.getEmail().equalsIgnoreCase(email)) {
                    enrolledUsername = instructorObj.getInstructorName();
                    enrolledUserID = instructorObj.getInstructorId();
                    enrollmentDto.setEnrolledUserId(enrolledUserID);
                    enrollmentDto.setEnrolledUsername(enrolledUsername);
                    break;
                }
            }
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String todayDate = formatter.format(date);
        enrollmentDto.setEnrolledDate(todayDate);

        // Convert EnrollmentDto to Enrollment entity
        Enrollment enrollment = convertToEnrollment(enrollmentDto);

        Enrollment enrollmentObj = enrollmentService.saveEnrollment(enrollment);
        System.out.println(enrollmentObj);

        List<Enrollment> enrollments = enrollmentService.getAllEnrollments();
        Map<String, Integer> enrolledCount = new LinkedHashMap<>();
        for (Enrollment enrollObj : enrollments) {
            String courseName = enrollObj.getCourseName();
            if (enrolledCount.containsKey(courseName))
                enrolledCount.put(courseName, enrolledCount.get(courseName) + 1);
            else
                enrolledCount.put(courseName, 1);
        }
        for (Map.Entry<String, Integer> obj : enrolledCount.entrySet()) {
            if (obj.getKey().equalsIgnoreCase(enrollment.getCourseName())) {
                enrollmentService.updateEnrolledCount(obj.getKey(), obj.getValue());
                courseService.updateEnrolledCount(obj.getKey(), obj.getValue());
            }
        }

        return "done";
    }

    @GetMapping("/getenrollmentstatus/{courseName}/{email}/{role}")
    public ResponseEntity<Set<String>> getEnrollmentStatus(@PathVariable String courseName, @PathVariable String email, @PathVariable String role) throws Exception {
        List<Enrollment> enrollments = enrollmentService.getAllEnrollments();
        Set<String> enrollmentStatus = new LinkedHashSet<>();

        String enrolledUsername = null;
        String enrolledUserId = null;

        if (role != null && role.trim().equalsIgnoreCase("user")) {
            System.out.println("Debug: Inside 'user' role block");
            User userObj = userService.fetchUserByEmail(email);
            System.out.println("Debug: Fetched User - " + userObj);

            if (userObj != null) {
                enrolledUsername = userObj.getUsername();
                enrolledUserId = userObj.getUserid();
                System.out.println("Debug: Fetched User - Username: " + enrolledUsername + ", UserID: " + enrolledUserId);
            }
        }

        if (enrolledUsername != null && enrolledUserId != null) {
            for (Enrollment enrollment : enrollments) {
                // Check if the username and userId match the enrolled user's username and userId
                if (enrollment.getEnrolledUsername().equalsIgnoreCase(enrolledUsername) && enrollment.getEnrolledUserId().equalsIgnoreCase(enrolledUserId)) {
                    System.out.println("Debug: User enrolled - Username: " + enrolledUsername + ", UserID: " + enrolledUserId);
                    enrollmentStatus.add("enrolled");
                    return new ResponseEntity<>(enrollmentStatus, HttpStatus.OK);
                }
            }




        }

        enrollmentStatus.add("notenrolled");
        return new ResponseEntity<>(enrollmentStatus, HttpStatus.OK);
    }




    @PostMapping("/addtowishlist")
    public ResponseEntity<WishlistDto> addNewCourse(@RequestBody WishlistDto wishlistDto) throws Exception {
        // Convert WishlistDto to Wishlist entity
        Wishlist wishlist = convertToWishlist(wishlistDto);

        Wishlist wishlistObj = wishlistService.addToWishlist(wishlist);

        // Convert Wishlist entity to WishlistDto
        WishlistDto wishlistResponseDto = convertToWishlistDto(wishlistObj);

        return new ResponseEntity<>(wishlistResponseDto, HttpStatus.OK);
    }

    @GetMapping("/getwishliststatus/{coursename}/{email}")
    public ResponseEntity<Set<String>> getWishlistStatus(@PathVariable String coursename, @PathVariable String email) throws Exception {
        List<Wishlist> wishlists = wishlistService.getAllLikedCourses();
        Set<String> wishlistsStatus = new LinkedHashSet<>();
        int flag = 0;
        OUTER:
        for (Wishlist wishlistsObj : wishlists) {
            if (wishlistsObj.getCourseName().equalsIgnoreCase(coursename) && wishlistsObj.getLikedUser().equalsIgnoreCase(email)) {
                wishlistsStatus.add("liked");
                flag = 1;
                break OUTER;
            }
        }
        if (flag == 0)
            wishlistsStatus.add("notliked");
        return new ResponseEntity<>(wishlistsStatus, HttpStatus.OK);
    }

    @GetMapping("/getallwishlist")
    public ResponseEntity<List<WishlistDto>> getAllWislist() throws Exception {
        List<Wishlist> Wishlists = wishlistService.getAllLikedCourses();
        List<WishlistDto> wishlistDtos = new ArrayList<>();

        // Convert Wishlist entities to WishlistDto
        for (Wishlist wishlist : Wishlists) {
            WishlistDto wishlistDto = convertToWishlistDto(wishlist);
            wishlistDtos.add(wishlistDto);
        }

        return new ResponseEntity<>(wishlistDtos, HttpStatus.OK);
    }

    @GetMapping("/getwishlistbyemail/{email}")
    public ResponseEntity<List<WishlistDto>> getWishlistByEmail(@PathVariable String email) throws Exception {
        List<Wishlist> Wishlists = wishlistService.fetchByLikedUser(email);
        List<WishlistDto> wishlistDtos = new ArrayList<>();

        // Convert Wishlist entities to WishlistDto
        for (Wishlist wishlist : Wishlists) {
            WishlistDto wishlistDto = convertToWishlistDto(wishlist);
            wishlistDtos.add(wishlistDto);
        }

        return new ResponseEntity<>(wishlistDtos, HttpStatus.OK);
    }

    @GetMapping("/getenrollmentbyemail/{email}/{role}")
    public ResponseEntity<List<EnrollmentDto>> getEnrollmentsByEmail(@PathVariable String email, @PathVariable String role) throws Exception {
        User userObj;
        Instructor instructorObj;
        String enrolledUser = "";
        if (role.equalsIgnoreCase("user")) {
            userObj = userService.fetchUserByEmail(email);
            enrolledUser = userObj.getUsername();
        } else if (role.equalsIgnoreCase("instructor")) {
            instructorObj = instructorService.fetchInstructorByEmail(email);
            enrolledUser = instructorObj.getInstructorName();
        }

        List<Enrollment> enrollments = enrollmentService.fetchByEnrolledUsername(enrolledUser);
        List<EnrollmentDto> enrollmentDtos = new ArrayList<>();

        for (Enrollment enrollment : enrollments) {
            EnrollmentDto enrollmentDto = convertToEnrollmentDto(enrollment);
            enrollmentDtos.add(enrollmentDto);
        }

        return new ResponseEntity<>(enrollmentDtos, HttpStatus.OK);
    }

    @GetMapping("/getchapterlistbycoursename/{coursename}")
    public ResponseEntity<List<Chapter>> getChapterListByCoursename(@PathVariable String coursename) throws Exception {
        List<Chapter> chapterLists = chapterService.fetchByCourseName(coursename);
        if (chapterLists.size() == 0) {
            Chapter obj1 = new Chapter();
            obj1.setChapter1name("");
            obj1.setChapter2name("");
            obj1.setChapter3name("");
            obj1.setChapter4name("");
            obj1.setChapter5name("");
            obj1.setChapter6name("");
            obj1.setChapter7name("");
            obj1.setChapter8name("");
            chapterLists.add(obj1);
        }
        return new ResponseEntity<>(chapterLists, HttpStatus.OK);
    }

    @GetMapping("/userprofileDetails/{email}")
    public ResponseEntity<List<UserDto>> getProfileDetails(@PathVariable String email) throws Exception {
        List<User> users = userService.fetchProfileByEmail(email);
        List<UserDto> userDtos = new ArrayList<>();

        // Convert User entities to UserDto
        for (User user : users) {
            UserDto userDto = convertToUserDto(user);
            userDtos.add(userDto);
        }

        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    @PutMapping("/updateuser")
    public ResponseEntity<UserDto> updateUserProfile(@RequestBody UserDto userDto) throws Exception {
        User existingUser = userService.fetchUserByEmail(userDto.getEmail());

        if (existingUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (userDto.getUsername() != null) {
            existingUser.setUsername(userDto.getUsername());
        }
        User updatedUser = userService.updateUserProfile(existingUser);

        UserDto updatedUserDto = convertToUserDto(updatedUser);

        return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);
    }


    @GetMapping("/gettotalusers")
    public ResponseEntity<List<Integer>> getTotalUsers() throws Exception {
        List<User> users = userService.getAllUsers();
        List<Integer> usersCount = new ArrayList<>();
        usersCount.add(users.size());
        return new ResponseEntity<>(usersCount, HttpStatus.OK);
    }

    @GetMapping("/gettotalenrollmentcount")
    public ResponseEntity<List<Integer>> getTotalEnrollmentCount() throws Exception {
        List<Enrollment> enrollments = enrollmentService.getAllEnrollments();
        int count = 0;
        for (Enrollment enrollmentObj : enrollments) {
            count += Integer.parseInt(enrollmentObj.getEnrolledCount());
        }
        List<Integer> enrollmentsCount = new ArrayList<>();
        enrollmentsCount.add(count);
        return new ResponseEntity<>(enrollmentsCount, HttpStatus.OK);
    }

    @GetMapping("/gettotalenrollments")
    public ResponseEntity<List<Integer>> getTotalEnrollments() throws Exception {
        List<Enrollment> enrollments = enrollmentService.getAllEnrollments();
        List<Integer> enrollmentsCount = new ArrayList<>();
        enrollmentsCount.add(enrollments.size());
        return new ResponseEntity<>(enrollmentsCount, HttpStatus.OK);
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

    private EnrollmentDto convertToEnrollmentDto(Enrollment enrollment) {
        return EnrollmentDto.builder()
                .id(enrollment.getId())
                .courseName(enrollment.getCourseName())
                .courseId(enrollment.getCourseId())
                .enrolledDate(enrollment.getEnrolledDate())
                .enrolledUsername(enrollment.getEnrolledUsername())
                .enrolledUserId(enrollment.getEnrolledUserId())
                .enrolledUserType(enrollment.getEnrolledUserType())
                .instructorName(enrollment.getInstructorName())
                .enrolledCount(enrollment.getEnrolledCount())
                .youtubeURL(enrollment.getYoutubeURL())
                .websiteURL(enrollment.getWebsiteURL())
                .courseType(enrollment.getCourseType())
                .skillLevel(enrollment.getSkillLevel())
                .language(enrollment.getLanguage())
                .description(enrollment.getDescription())
                .build();
    }

    private Enrollment convertToEnrollment(EnrollmentDto enrollmentDto) {
        return Enrollment.builder()
                .id(enrollmentDto.getId())
                .courseName(enrollmentDto.getCourseName())
                .courseId(enrollmentDto.getCourseId())
                .enrolledDate(enrollmentDto.getEnrolledDate())
                .enrolledUsername(enrollmentDto.getEnrolledUsername())
                .enrolledUserId(enrollmentDto.getEnrolledUserId())
                .enrolledUserType(enrollmentDto.getEnrolledUserType())
                .instructorName(enrollmentDto.getInstructorName())
                .enrolledCount(enrollmentDto.getEnrolledCount())
                .youtubeURL(enrollmentDto.getYoutubeURL())
                .websiteURL(enrollmentDto.getWebsiteURL())
                .courseType(enrollmentDto.getCourseType())
                .skillLevel(enrollmentDto.getSkillLevel())
                .language(enrollmentDto.getLanguage())
                .description(enrollmentDto.getDescription())
                .build();
    }

    private WishlistDto convertToWishlistDto(Wishlist wishlist) {
        return WishlistDto.builder()
                .courseName(wishlist.getCourseName())
                .courseId(wishlist.getCourseId())
                .likedUser(wishlist.getLikedUser())
                .likedUserType(wishlist.getLikedUserType())
                .instructorName(wishlist.getInstructorName())
                .enrolledCount(wishlist.getEnrolledCount())
                .courseType(wishlist.getCourseType())
                .websiteURL(wishlist.getWebsiteURL())
                .skillLevel(wishlist.getSkillLevel())
                .language(wishlist.getLanguage())
                .description(wishlist.getDescription())
                .build();
    }

    private Wishlist convertToWishlist(WishlistDto wishlistDto) {
        return Wishlist.builder()
                .courseName(wishlistDto.getCourseName())
                .courseId(wishlistDto.getCourseId())
                .likedUser(wishlistDto.getLikedUser())
                .likedUserType(wishlistDto.getLikedUserType())
                .instructorName(wishlistDto.getInstructorName())
                .enrolledCount(wishlistDto.getEnrolledCount())
                .courseType(wishlistDto.getCourseType())
                .websiteURL(wishlistDto.getWebsiteURL())
                .skillLevel(wishlistDto.getSkillLevel())
                .language(wishlistDto.getLanguage())
                .description(wishlistDto.getDescription())
                .build();
    }
}
