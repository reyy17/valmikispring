package com.elearning.valmiki.repository;

import com.elearning.valmiki.entity.Enrollment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer>
{
    public Enrollment findByCourseName(String courseName);

    public Enrollment findByCourseId(String courseId);

    public List<Enrollment> findByEnrolledUsername(String enrolledUsername);

    public List<Enrollment> findByEnrolledUserId(String enrolledUserId);

    public List<Enrollment> findByEnrolledUserType(String enrolledUserType);

    public List<Enrollment> findByInstructorName(String instructorName);

    public List<Enrollment> findByEnrolledDate(String enrolledDate);

    public List<Enrollment> findByCourseType(String courseType);

    public List<Enrollment> findByYoutubeURL(String youtubeURL);

    public List<Enrollment> findByWebsiteURL(String websiteURL);

    public List<Enrollment> findBySkillLevel(String skillLevel);

    public List<Enrollment> findByLanguage(String language);

    @Transactional
    @Modifying
    @Query(value = "update enrollment set enrolled_count = ?1 where course_name = ?2", nativeQuery = true)
    public void updateEnrolledCount(int enrolledCount, String courseName);

}
