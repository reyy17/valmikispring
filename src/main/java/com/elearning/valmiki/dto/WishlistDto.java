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
public class WishlistDto {
    private String courseName;
    private String courseId;
    private String likedUser;
    private String likedUserType;
    private String instructorName;
    private String enrolledCount;
    private String courseType;
    private String websiteURL;
    private String skillLevel;
    private String language;
    private String description;
}

