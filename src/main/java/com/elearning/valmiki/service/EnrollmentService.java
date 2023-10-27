package com.elearning.valmiki.service;

import com.elearning.valmiki.entity.Enrollment;
import com.elearning.valmiki.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EnrollmentService
{
    @Autowired
    private EnrollmentRepository enrollmentRepo;

    @Transactional
    public Enrollment saveEnrollment(Enrollment enrollment)
    {
        return enrollmentRepo.save(enrollment);
    }

    public Enrollment addNewEnrollment(Enrollment enrollment)
    {
        return enrollmentRepo.save(enrollment);
    }

    public List<Enrollment> getAllEnrollments()
    {
        return (List<Enrollment>)enrollmentRepo.findAll();
    }

    @Transactional
    public void updateEnrolledCount(String course_name, int enrolledCount) {
        try {
            enrollmentRepo.updateEnrolledCount(enrolledCount, course_name);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update enrolled count for course: " + course_name, e);
        }
    }


    public Enrollment fetchByCourseName(String courseName)
    {
        return enrollmentRepo.findByCourseName(courseName);
    }

    public Enrollment fetchByCourseId(String courseId)
    {
        return enrollmentRepo.findByCourseId(courseId);
    }

    public List<Enrollment> fetchByEnrolledUsername(String enrolledUsername)
    {
        return (List<Enrollment>)enrollmentRepo.findByEnrolledUsername(enrolledUsername);
    }

    public List<Enrollment> fetchByEnrolledUserId(String enrolledUserId)
    {
        return (List<Enrollment>)enrollmentRepo.findByEnrolledUserId(enrolledUserId);
    }

    public List<Enrollment> fetchByEnrolledUserType(String enrolledUserType)
    {
        return (List<Enrollment>)enrollmentRepo.findByEnrolledUserType(enrolledUserType);
    }

    public List<Enrollment> fetchByInstructorName(String instructorName)
    {
        return (List<Enrollment>)enrollmentRepo.findByInstructorName(instructorName);
    }

    public List<Enrollment> fetchByEnrolledDate(String enrolledDate)
    {
        return (List<Enrollment>)enrollmentRepo.findByEnrolledDate(enrolledDate);
    }

    public List<Enrollment> fetchByCourseType(String courseType)
    {
        return (List<Enrollment>)enrollmentRepo.findByCourseType(courseType);
    }

    public List<Enrollment> fetchByYoutubeURL(String youtubeURL)
    {
        return (List<Enrollment>)enrollmentRepo.findByYoutubeURL(youtubeURL);
    }

    public List<Enrollment> fetchByWebsiteURL(String websiteURL)
    {
        return (List<Enrollment>)enrollmentRepo.findByWebsiteURL(websiteURL);
    }

    public List<Enrollment> fetchBySkillLevel(String skillLevel)
    {
        return (List<Enrollment>)enrollmentRepo.findBySkillLevel(skillLevel);
    }

    public List<Enrollment> fetchByLanguage(String language)
    {
        return (List<Enrollment>)enrollmentRepo.findByLanguage(language);
    }
}
