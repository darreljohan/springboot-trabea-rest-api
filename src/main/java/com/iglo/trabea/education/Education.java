package com.iglo.trabea.education;

import lombok.Getter;

@Getter
public enum Education {
    ELEMENTARY("Elementary School (SD)"),
    JUNIOR("Junior High School (SMP)"),
    HIGH("High School (SMA)"),
    BACHELOR("University - Bachelor (Kuliah - lulusan S1)"),
    MASTER("University - Master (Kuliah - lulusan S2)"),
    DOCTORATE("University - Doctorate (Kuliah - lulusan S3)");

    private final String educationLabel;

    Education(String educationLabel) {
        this.educationLabel = educationLabel;
    }
}
