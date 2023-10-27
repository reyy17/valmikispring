package com.elearning.valmiki.controller;

import com.elearning.valmiki.dto.InstructorDto;
import com.elearning.valmiki.dto.CourseDto;
import com.elearning.valmiki.dto.ChapterDto;
import com.elearning.valmiki.dto.WishlistDto;
import com.elearning.valmiki.entity.Chapter;
import com.elearning.valmiki.entity.Course;
import com.elearning.valmiki.entity.Instructor;
import com.elearning.valmiki.entity.Wishlist;
import com.elearning.valmiki.service.ChapterService;
import com.elearning.valmiki.service.CourseService;
import com.elearning.valmiki.service.InstructorService;
import com.elearning.valmiki.service.WishlistService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/auth/v1")
@CrossOrigin(origins = "http://localhost:3000")
public class InstructorController {
    @Autowired
    private InstructorService instructorService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private WishlistService wishlistService;

    @GetMapping("/professorlist")
    public ResponseEntity<List<InstructorDto>> getInstructorList() throws Exception {
        List<Instructor> instructors = instructorService.getAllInstructors();
        List<InstructorDto> instructorDtos = convertToInstructorDtos(instructors);
        return new ResponseEntity<>(instructorDtos, HttpStatus.OK);
    }

    @GetMapping("/youtubecourselist")
    public ResponseEntity<List<CourseDto>> getYoutubeCourseList() throws Exception {
        List<Course> youtubeCourseList = courseService.fetchByCourseType("youtube");
        System.out.println("Number of YouTube courses fetched: " + youtubeCourseList.size());
        List<CourseDto> courseDtos = convertToCourseDtos(youtubeCourseList);
        return new ResponseEntity<>(courseDtos, HttpStatus.OK);
    }

    @GetMapping("/websitecourselist")
    public ResponseEntity<List<CourseDto>> getWebsiteCourseList() throws Exception {
        List<Course> websiteCourseList = courseService.fetchByCourseType("website");
        List<CourseDto> courseDtos = convertToCourseDtos(websiteCourseList);
        return new ResponseEntity<>(courseDtos, HttpStatus.OK);
    }


//    @GetMapping("/courselistbyname/{coursename}")
//    public ResponseEntity<CourseDto> getCourseListByName(@PathVariable String courseName) throws Exception {
//        Course course = courseService.fetchCourseByCourseName(courseName);
//        CourseDto courseDto = convertToCourseDto(course);
//        return new ResponseEntity<>(courseDto, HttpStatus.OK);
//    }

//    @GetMapping("/courselistbyname/{coursename}")
//    public ResponseEntity<CourseDto> getCourseListByName(@PathVariable String courseName) throws Exception {
//        String trimmedCourseName = courseName.trim();
//        Course course = courseService.fetchCourseByCourseName(trimmedCourseName);
//        CourseDto courseDto = convertToCourseDto(course);
//        return new ResponseEntity<>(courseDto, HttpStatus.OK);
//    }


    @PostMapping("/courselistbyname")
    public ResponseEntity<CourseDto> getCourseListByName(@RequestBody String courseName) throws Exception {
        String trimmedCourseName = courseName.trim();
        Course course = courseService.fetchCourseByCourseName(trimmedCourseName);

        if (course == null) {
            // Handle the case where the course is not found
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        CourseDto courseDto = convertToCourseDto(course);
        return new ResponseEntity<>(courseDto, HttpStatus.OK);
    }

    @GetMapping("/professorlistbyemail/{email}")
    public ResponseEntity<List<InstructorDto>> getInstructorByEmail(@PathVariable String email) throws Exception {
        List<Instructor> instructors = instructorService.getInstructorsByEmail(email);
        List<InstructorDto> instructorDtos = convertToInstructorDtos(instructors);
        return new ResponseEntity<>(instructorDtos, HttpStatus.OK);
    }

//    @PostMapping("/addProfessor")
//    public InstructorDto addNewInstructor(@RequestBody InstructorDto instructorDto) throws Exception {
//        Instructor instructor = convertToInstructor(instructorDto);
//        String newID = getNewID();
//        instructor.setInstructorId(newID);
//        Instructor instructorObj = instructorService.addNewInstructor(instructor);
//        instructorService.updateStatus(instructor.getEmail());
//        return convertToInstructorDto(instructorObj);
//    }

    @PostMapping("/addCourse")
    public CourseDto addNewCourse(@RequestBody CourseDto courseDto) throws Exception {
        Course course = convertToCourse(courseDto);
//        String newID = getNewID();
//        course.setCourseId(newID);
        Course courseObj = courseService.addNewCourse(course);
        return convertToCourseDto(courseObj);
    }

    @PostMapping("/addnewchapter")
    public ChapterDto addNewChapters(@RequestBody ChapterDto chapterDto) throws Exception {
        Chapter chapter = convertToChapter(chapterDto);
        Chapter chapterObj = chapterService.addNewChapter(chapter);
        return convertToChapterDto(chapterObj);
    }

    @GetMapping("/acceptstatus/{email}")
    public ResponseEntity<List<String>> updateStatus(@PathVariable String email) throws Exception {
        instructorService.updateStatus(email);
        List<String> response = new ArrayList<>();
        response.add("accepted");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/rejectstatus/{email}")
    public ResponseEntity<List<String>> rejectStatus(@PathVariable String email) throws Exception {
        instructorService.rejectStatus(email);
        List<String> response = new ArrayList<>();
        response.add("rejected");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/professorprofileDetails/{email}")
    public ResponseEntity<List<InstructorDto>> getProfileDetails(@PathVariable String email) throws Exception {
        List<Instructor> instructors = instructorService.fetchProfileByEmail(email);
        List<InstructorDto> instructorDtos = convertToInstructorDtos(instructors);
        return new ResponseEntity<>(instructorDtos, HttpStatus.OK);
    }

    @PutMapping("/updateprofessor")
    public ResponseEntity<InstructorDto> updateInstructorProfile(@RequestBody InstructorDto instructorDto)
            throws Exception {
        Instructor instructor = convertToInstructor(instructorDto);
        Instructor instructorObj = instructorService.updateInstructorProfile(instructor);
        return new ResponseEntity<>(convertToInstructorDto(instructorObj), HttpStatus.OK);
    }

    @GetMapping("/gettotalprofessors")
    public ResponseEntity<List<Integer>> getTotalInstructors() throws Exception {
        List<Instructor> instructors = instructorService.getAllInstructors();
        List<Integer> instructorsCount = new ArrayList<>();
        instructorsCount.add(instructors.size());
        return new ResponseEntity<>(instructorsCount, HttpStatus.OK);
    }

    @GetMapping("/gettotalchapters")
    public ResponseEntity<List<Integer>> getTotalChapters() throws Exception {
        List<Chapter> chapters = chapterService.getAllChapters();
        List<Integer> chaptersCount = new ArrayList<>();
        chaptersCount.add(chapters.size());
        return new ResponseEntity<>(chaptersCount, HttpStatus.OK);
    }

    @GetMapping("/gettotalcourses")
    public ResponseEntity<List<Integer>> getTotalCourses() throws Exception {
        List<Course> courses = courseService.getAllCourses();
        List<Integer> coursesCount = new ArrayList<>();
        coursesCount.add(courses.size());
        return new ResponseEntity<>(coursesCount, HttpStatus.OK);
    }

    @GetMapping("/gettotalwishlist")
    public ResponseEntity<List<Integer>> getTotalWishlist() throws Exception {
        List<Wishlist> wishlists = wishlistService.getAllLikedCourses();
        List<Integer> wishlistCount = new ArrayList<>();
        wishlistCount.add(wishlists.size());
        return new ResponseEntity<>(wishlistCount, HttpStatus.OK);
    }

    @GetMapping("/getcoursenames")
    public ResponseEntity<List<String>> getCourseNames() throws Exception {
        List<Course> courses = courseService.getAllCourses();
        List<String> coursenames = new ArrayList<>();
        for (Course obj : courses) {
            coursenames.add(obj.getCourseName());
        }
        return new ResponseEntity<>(coursenames, HttpStatus.OK);
    }

    private String getNewID() {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            int index = (int) (AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
    }

    private List<InstructorDto> convertToInstructorDtos(List<Instructor> instructors) {
        List<InstructorDto> instructorDtos = new ArrayList<>();
        for (Instructor instructor : instructors) {
            instructorDtos.add(convertToInstructorDto(instructor));
        }
        return instructorDtos;
    }

    private InstructorDto convertToInstructorDto(Instructor instructor) {
        InstructorDto instructorDto = new InstructorDto();
        BeanUtils.copyProperties(instructor, instructorDto);
        return instructorDto;
    }

    private Instructor convertToInstructor(InstructorDto instructorDto) {   
        Instructor instructor = new Instructor();
        BeanUtils.copyProperties(instructorDto, instructor);
        return instructor;
    }

    private List<CourseDto> convertToCourseDtos(List<Course> courses) {
        List<CourseDto> courseDtos = new ArrayList<>();
        for (Course course : courses) {
            courseDtos.add(convertToCourseDto(course));
        }
        return courseDtos;
    }

    private CourseDto convertToCourseDto(Course course) {
        CourseDto courseDto = new CourseDto();
        BeanUtils.copyProperties(course, courseDto);
        return courseDto;
    }

    private Course convertToCourse(CourseDto courseDto) {
        Course course = new Course();
        BeanUtils.copyProperties(courseDto, course);
        return course;
    }

    private List<ChapterDto> convertToChapterDtos(List<Chapter> chapters) {
        List<ChapterDto> chapterDtos = new ArrayList<>();
        for (Chapter chapter : chapters) {
            chapterDtos.add(convertToChapterDto(chapter));
        }
        return chapterDtos;
    }

    private ChapterDto convertToChapterDto(Chapter chapter) {
        ChapterDto chapterDto = new ChapterDto();
        BeanUtils.copyProperties(chapter, chapterDto);
        return chapterDto;
    }

    private Chapter convertToChapter(ChapterDto chapterDto) {
        Chapter chapter = new Chapter();
        BeanUtils.copyProperties(chapterDto, chapter);
        return chapter;
    }

    private List<WishlistDto> convertToWishlistDtos(List<Wishlist> wishlists) {
        List<WishlistDto> wishlistDtos = new ArrayList<>();
        for (Wishlist wishlist : wishlists) {
            wishlistDtos.add(convertToWishlistDto(wishlist));
        }
        return wishlistDtos;
    }

    private WishlistDto convertToWishlistDto(Wishlist wishlist) {
        WishlistDto wishlistDto = new WishlistDto();
        BeanUtils.copyProperties(wishlist, wishlistDto);
        return wishlistDto;
    }

    private Wishlist convertToWishlist(WishlistDto wishlistDto) {
        Wishlist wishlist = new Wishlist();
        BeanUtils.copyProperties(wishlistDto, wishlist);
        return wishlist;
    }
}
