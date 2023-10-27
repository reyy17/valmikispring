package com.elearning.valmiki.repository;

import com.elearning.valmiki.entity.Instructor;
import com.elearning.valmiki.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface InstructorRepository extends JpaRepository<Instructor, Integer>
{
    public Instructor findByEmail(String email);

    public List<Instructor> findInstructorListByEmail(String email);

    public Instructor findByInstructorName(String instructorName);

    public Instructor findByEmailAndPassword(String email, String password);

    public List<Instructor> findProfileByEmail(String email);

    Optional<Instructor> findByResetToken(String resetToken);

    @Transactional
    @Modifying
    @Query(value = "update instructor set status = 'accept' where email = ?1", nativeQuery = true)
    public void updateStatus(String email);

    @Transactional
    @Modifying
    @Query(value = "update instructor set status = 'reject' where email = ?1", nativeQuery = true)
    public void rejectStatus(String email);

}
