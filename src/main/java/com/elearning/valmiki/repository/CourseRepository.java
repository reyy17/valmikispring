package com.elearning.valmiki.repository;

import com.elearning.valmiki.entity.Course;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Integer>
{
    public Course findByCourseName(String courseName);

    public Course findByCourseId(String courseId);

    public List<Course> findByInstructorName(String instructorName);

    public List<Course> findByEnrolledDate(String enrolledDate);

    public List<Course> findByCourseType(String courseType);

    public List<Course> findByYoutubeURL(String youtubeURL);

    public List<Course> findByWebsiteURL(String websiteURL);

    public List<Course> findBySkillLevel(String skillLevel);

    public List<Course> findByLanguage(String language);

    @Transactional
    @Modifying
    @Query(value = "update course set enrolledCount = ?1 where courseName = ?2",nativeQuery = true)
    public void updateEnrolledCount(int enrolledCount, String courseName);

}
