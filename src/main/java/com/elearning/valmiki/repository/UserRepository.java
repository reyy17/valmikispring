package com.elearning.valmiki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.elearning.valmiki.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer>
{

    public User findByEmail(String email);

    public User findByUsername(String username);

    public User findByEmailAndPassword(String email, String password);

    public List<User> findProfileByEmail(String email);

    Optional<User> findByResetToken(String resetToken);

}
