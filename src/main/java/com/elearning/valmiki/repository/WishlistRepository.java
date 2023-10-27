package com.elearning.valmiki.repository;

import com.elearning.valmiki.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist, Integer>
{
    public Wishlist findByCourseName(String courseName);

    public Wishlist findByCourseId(String courseId);

    public List<Wishlist> findByLikedUserType(String likedUserType);

    public List<Wishlist> findByLikedUser(String likedUser);

    public List<Wishlist> findByInstructorName(String instructorName);

    public List<Wishlist> findByCourseType(String courseType);

    public List<Wishlist> findBySkillLevel(String skillLevel);

    public List<Wishlist> findByLanguage(String language);
}
