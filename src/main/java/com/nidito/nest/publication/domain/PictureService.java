package com.nidito.nest.publication.domain;

import com.amazonaws.services.s3.AmazonS3;
import com.nidito.nest.publication.domain.entity.Picture;
import com.nidito.nest.publication.domain.entity.dto.PictureDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.ZonedDateTime;

@Service
public class PictureService {

    @Value("${cloud.aws.s3.bucket-name}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3Client;

    @Autowired
    private PublicationService publicationService;

    public PictureDto createPicture(PictureDto pictureDto, MultipartFile file) {

        String fileName = file.getOriginalFilename() + "_" + ZonedDateTime.now();
        s3Client.putObject(bucketName, fileName, convertMultipartFileToFile(file));

        File tempFile = new File(file.getOriginalFilename());
        if(tempFile.exists()) tempFile.delete();
        
        String fileUrl = s3Client.getUrl(bucketName, fileName).toString();
        Picture picture = new Picture(pictureDto);
        picture.setUrl(fileUrl);
        var returnedPictureDto = (PictureDto) publicationService.createPublication(picture, pictureDto.getOwnerId(), pictureDto.getWatchers()).toDto();
        returnedPictureDto.setUrl(fileUrl);

        return returnedPictureDto;
    }

    private File convertMultipartFileToFile(MultipartFile file) {

        File convertedFile = new File(file.getOriginalFilename());
        try(FileOutputStream fos = new FileOutputStream(convertedFile)){
            fos.write(file.getBytes());
        }catch (IOException e){
            System.out.println("Error converting multipartFile to file" + e);
        }
        return convertedFile;
    }


}
