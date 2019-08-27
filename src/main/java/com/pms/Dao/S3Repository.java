package com.pms.Dao;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Repository
public class S3Repository {
    private static final String accesskey = "<access-key>";
    private static final String secretkey = "<secret-key>";
    private static final String bucket = "<bucket-name>";
    private static AmazonS3 s3Client = null;

    public S3Repository() {

        AWSCredentials credentials = new BasicAWSCredentials(
                accesskey,
                secretkey
        );

        s3Client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.AP_SOUTH_1)
                .build();
    }

    public byte[] getFile(String key) {
        S3Object s3object = s3Client.getObject(bucket, key);
        S3ObjectInputStream inputStream = s3object.getObjectContent();
        try {
            return IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteFile(String keySynop) {
        Thread thread = new Thread(() -> s3Client.deleteObject(bucket, keySynop));

        thread.setDaemon(true);
        thread.start();
    }


    public void saveSynopsis(CommonsMultipartFile synopsis, Integer projectId) {
        ObjectMetadata metadata = new ObjectMetadata();
        byte[] bytes = synopsis.getBytes();
        metadata.setContentLength(bytes.length);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);


        s3Client.putObject(
                bucket,
                "synopsis/" + projectId + "." + FilenameUtils.getExtension(synopsis.getOriginalFilename()),
                byteArrayInputStream,
                metadata);

    }
}
