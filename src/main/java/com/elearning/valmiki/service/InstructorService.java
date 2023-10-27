package com.elearning.valmiki.service;

import com.elearning.valmiki.entity.Instructor;
import com.elearning.valmiki.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InstructorService {
    @Autowired
    private InstructorRepository instructorRepository;

    public Instructor saveInstructor(Instructor instructor)
    {
        return instructorRepository.save(instructor);
    }

    public Instructor addNewInstructor(Instructor instructor)
    {
        return instructorRepository.save(instructor);
    }

    public Instructor updateInstructorProfile(Instructor instructor)
    {
        return instructorRepository.save(instructor);
    }

    public List<Instructor> getAllInstructors()
    {
        return (List<Instructor>)instructorRepository.findAll();
    }

    public List<Instructor> getInstructorListByEmail(String email)
    {
        return (List<Instructor>)instructorRepository.findInstructorListByEmail(email);
    }

    public Instructor fetchInstructorByEmail(String email)
    {
        return instructorRepository.findByEmail(email);
    }

    public Instructor fetchInstructorByInstructorName(String instructorName)
    {
        return instructorRepository.findByInstructorName(instructorName);
    }

    public Instructor fetchInstructorByEmailAndPassword(String email, String password)
    {
        return instructorRepository.findByEmailAndPassword(email, password);
    }

    public List<Instructor> fetchProfileByEmail(String email)
    {
        return (List<Instructor>)instructorRepository.findProfileByEmail(email);
    }

    public Optional findUserByResetToken(String resetToken) {
        return instructorRepository.findByResetToken(resetToken);
    }

    public void updateStatus(String email)
    {
        instructorRepository.updateStatus(email);
    }

    public void rejectStatus(String email)
    {
        instructorRepository.rejectStatus(email);
    }

    public List<Instructor> getInstructorsByEmail(String email)
    {
        return instructorRepository.findInstructorListByEmail(email);
    }
}

