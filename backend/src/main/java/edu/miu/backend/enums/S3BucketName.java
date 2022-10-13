package edu.miu.backend.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum S3BucketName {
    STUDENT_BUCKET("student-files-bucket"), JOB_ADVERTISEMENT_BUCKET("job-advertisement-files-bucket");

    private final String s3BucketName;
}
