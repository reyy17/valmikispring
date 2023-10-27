package com.elearning.valmiki.service;

import com.elearning.valmiki.entity.Wishlist;
import com.elearning.valmiki.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService
{
    @Autowired
    private WishlistRepository wishlistRepo;

    public Wishlist saveToWishlist(Wishlist course)
    {
        return wishlistRepo.save(course);
    }

    public Wishlist addToWishlist(Wishlist course)
    {
        return wishlistRepo.save(course);
    }

    public List<Wishlist> getAllLikedCourses()
    {
        return (List<Wishlist>)wishlistRepo.findAll();
    }

    public Wishlist fetchCourseByCourseName(String courseName)
    {
        return wishlistRepo.findByCourseName(courseName);
    }

    public Wishlist fetchCourseByCourseId(String courseId)
    {
        return wishlistRepo.findByCourseId(courseId);
    }

    public List<Wishlist> fetchByInstructorName(String instructorName)
    {
        return (List<Wishlist>)wishlistRepo.findByInstructorName(instructorName);
    }

    public List<Wishlist> fetchByLikedUser(String likedUser)
    {
        return (List<Wishlist>)wishlistRepo.findByLikedUser(likedUser);
    }

    public List<Wishlist> fetchByLikedUserType(String likedUserType)
    {
        return (List<Wishlist>)wishlistRepo.findByLikedUserType(likedUserType);
    }

    public List<Wishlist> fetchByCourseType(String courseType)
    {
        return (List<Wishlist>)wishlistRepo.findByCourseType(courseType);
    }

    public List<Wishlist> fetchBySkillLevel(String skillLevel)
    {
        return (List<Wishlist>)wishlistRepo.findBySkillLevel(skillLevel);
    }

    public List<Wishlist> fetchByLanguage(String language)
    {
        return (List<Wishlist>)wishlistRepo.findByLanguage(language);
    }

}
