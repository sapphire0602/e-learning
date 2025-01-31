package com.ahmad.e_learning.service.file;

import com.ahmad.e_learning.exception.ResourceNotFoundException;
import com.ahmad.e_learning.model.Course;
import com.ahmad.e_learning.model.FileData;
import com.ahmad.e_learning.repository.CourseRepository;
import com.ahmad.e_learning.repository.FileRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileService implements IFileService {
    private final FileRepository fileRepository;
    private final Path fileStorageLocation;
    private final CourseRepository courseRepository;

    public FileService(FileRepository fileRepository, CourseRepository courseRepository) {
        this.fileStorageLocation = Paths.get("uploads").toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (IOException e) {
            throw new RuntimeException("Unable to create the directory where the uploaded files will be stored", e);
        }
        this.fileRepository = fileRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public void saveFile(MultipartFile file, Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course Not Found"));
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            FileData fileData = new FileData();
            fileData.setFileName(fileName);
            fileData.setFilePath(targetLocation.toString());
            fileData.setFileType(file.getContentType());
            fileData.setFileSize(file.getSize());
            fileData.setCourse(course);
            fileRepository.save(fileData);

        } catch (IOException e) {
            throw new RuntimeException("Unable to save file : " + fileName + " , Please , Try Again!", e);
        }
    }


    @Override
    public Resource loadFile(String fileName) throws MalformedURLException {
        System.out.println("Load file in operation");
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists())
                return resource;
            else {
                throw new ResourceNotFoundException("File Not Found !" + fileName);
            }
        } catch (MalformedURLException | ResourceNotFoundException e) {
            throw new RuntimeException("File Not Found " + fileName, e);
        }
    }

    @Override
    public List<String> getAllFiles() {
        try {
            return
                    Files.walk(this.fileStorageLocation, 1)
                            .filter(path -> !path.equals(this.fileStorageLocation))
                            .map(this.fileStorageLocation::relativize)
                            .map(Path::toString)
                            .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(" Unable to retrieve files !", e);
        }
    }
}
