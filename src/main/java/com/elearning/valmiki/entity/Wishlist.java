package com.elearning.valmiki.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Wishlist {
    @Id
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
