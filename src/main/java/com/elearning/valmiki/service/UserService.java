package com.elearning.valmiki.service;

import com.elearning.valmiki.repository.UserRepository;
import com.elearning.valmiki.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService
{
    @Autowired
    private UserRepository userRepo;

    public User saveUser(User user)
    {
        return userRepo.save(user);
    }

    public User updateUserProfile(User user)
    {
        return userRepo.save(user);
    }

    public List<User> getAllUsers()
    {
        return (List<User>)userRepo.findAll();
    }

    public User fetchUserByEmail(String email) {
        User user = userRepo.findByEmail(email);

        if (user != null) {
            System.out.println("Fetched User - Email: " + email + ", Username: " + user.getUsername() + ", UserID: " + user.getUserid());
        } else {
            System.out.println("User not found for Email: " + email);
        }

        return user;
    }




    public User fetchUserByUsername(String username)
    {
        return userRepo.findByUsername(username);
    }

    public User fetchUserByEmailAndPassword(String email, String password)
    {
        System.out.println("Debug: Fetching user by email: " + email);
        User user = userRepo.findByEmail(email);
        System.out.println("Debug: Fetched user: " + user);
        return user;
    }

    public Optional findUserByResetToken(String resetToken) {
        return userRepo.findByResetToken(resetToken);
    }

    public List<User> fetchProfileByEmail(String email)
    {
        return (List<User>)userRepo.findProfileByEmail(email);
    }
}
