package com.elearning.valmiki.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CourseDto {
    private int id;
    private String courseName;
    private String courseId;
    private String enrolledDate;
    private String instructorName;
    private String enrolledCount;
    private String youtubeURL;
    private String websiteURL;
    private String courseType;
    private String skillLevel;
    private String language;
    private String description;
}
