package com.nidito.nest.publication.domain;

import com.amazonaws.services.s3.AmazonS3;
import com.nidito.nest.publication.domain.entity.Picture;
import com.nidito.nest.publication.domain.entity.dto.PictureDto;
import com.nidito.nest.publication.infrastructure.PictureRepository;
import com.nidito.nest.user.domain.UserService;
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
    private PictureRepository pictureRepository;

    @Autowired
    private UserService userService;

    public String createPicture(PictureDto pictureDto){
        MultipartFile file = pictureDto.getImage();
        String fileName = file.getOriginalFilename() + "_" + ZonedDateTime.now();
        s3Client.putObject(bucketName, fileName, convertMultipartFileToFile(file));
        String fileUrl = s3Client.getUrl(bucketName, fileName).toString();
        Picture picture = new Picture(pictureDto);
        picture.setUrl(fileUrl);
        picture.setOwner(userService.getUserById(pictureDto.getOwnerId()));
        pictureRepository.save(picture);
        return "File uploaded " + fileName;
    }

    private File convertMultipartFileToFile(MultipartFile file){
        File convertedFile = new File(file.getOriginalFilename());
        try(FileOutputStream fos = new FileOutputStream(convertedFile)){
            fos.write(file.getBytes());
        }catch (IOException e){
            System.out.println("Error converting multipartFile to file" + e);
        }
        return convertedFile;
    }


}
