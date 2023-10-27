package com.elearning.valmiki.repository;

import com.elearning.valmiki.entity.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChapterRepository extends JpaRepository<Chapter, Integer>
{
    public List<Chapter> findByCourseName(String CourseName);
}
