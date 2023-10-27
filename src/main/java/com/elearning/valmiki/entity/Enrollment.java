package com.elearning.valmiki.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String courseName;
    private String courseId;
    private String enrolledDate;
    private String enrolledUsername;
    private String enrolledUserId;
    private String enrolledUserType;
    private String instructorName;
    private String enrolledCount;
    @Column(name = "youtube_url")
    private String youtubeURL;
    @Column(name = "website_url")
    private String websiteURL;
    private String courseType;
    private String skillLevel;
    private String language;
    private String description;
}
