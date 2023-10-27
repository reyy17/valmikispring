package com.elearning.valmiki.service;

import com.elearning.valmiki.entity.Chapter;
import com.elearning.valmiki.repository.ChapterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChapterService
{
    @Autowired
    private ChapterRepository chapterRepo;

    public Chapter saveChapter(Chapter chapter)
    {
        return chapterRepo.save(chapter);
    }

    public Chapter addNewChapter(Chapter chapter)
    {
        return chapterRepo.save(chapter);
    }

    public List<Chapter> getAllChapters()
    {
        return (List<Chapter>)chapterRepo.findAll();
    }

    public List<Chapter> fetchByCourseName(String courseName)
    {
        return (List<Chapter>)chapterRepo.findByCourseName(courseName);
    }
}
