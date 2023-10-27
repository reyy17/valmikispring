package com.elearning.valmiki.service;

import com.elearning.valmiki.entity.Course;
import com.elearning.valmiki.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService
{
    @Autowired
    private CourseRepository courseRepo;

    public Course saveCourse(Course course)
    {
        return courseRepo.save(course);
    }

    public Course addNewCourse(Course course)
    {
        return courseRepo.save(course);
    }

    public List<Course> getAllCourses()
    {
        return (List<Course>)courseRepo.findAll();
    }

    public void updateEnrolledCount(String courseName, int enrolledCount)
    {
        courseRepo.updateEnrolledCount(enrolledCount, courseName);
    }

    public Course fetchCourseByCourseName(String courseName)
    {
        return courseRepo.findByCourseName(courseName);
    }

    public Course fetchCourseByCourseId(String courseId)
    {
        return courseRepo.findByCourseId(courseId);
    }

    public List<Course> fetchByInstructorName(String instructorName)
    {
        return (List<Course>)courseRepo.findByInstructorName(instructorName);
    }

    public List<Course> fetchByEnrolledDate(String enrolledDate)
    {
        return (List<Course>)courseRepo.findByEnrolledDate(enrolledDate);
    }

    public List<Course> fetchByCourseType(String courseType)
    {
        return (List<Course>)courseRepo.findByCourseType(courseType);
    }

    public List<Course> fetchByYoutubeURL(String youtubeURL)
    {
        return (List<Course>)courseRepo.findByYoutubeURL(youtubeURL);
    }

    public List<Course> fetchByWebsiteURL(String websiteURL)
    {
        return (List<Course>)courseRepo.findByWebsiteURL(websiteURL);
    }

    public List<Course> fetchBySkillLevel(String skillLevel)
    {
        return (List<Course>)courseRepo.findBySkillLevel(skillLevel);
    }

    public List<Course> fetchByLanguage(String language)
    {
        return (List<Course>)courseRepo.findByLanguage(language);
    }

}
