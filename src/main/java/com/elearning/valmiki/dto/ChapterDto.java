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
public class ChapterDto {
    private int id;
    private String courseName;
    private String chapter1name;
    private String chapter1id;
    private String chapter2name;
    private String chapter2id;
    private String chapter3name;
    private String chapter3id;
    private String chapter4name;
    private String chapter4id;
    private String chapter5name;
    private String chapter5id;
    private String chapter6name;
    private String chapter6id;
    private String chapter7name;
    private String chapter7id;
    private String chapter8name;
    private String chapter8id;
}
