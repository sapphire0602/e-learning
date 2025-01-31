package com.ahmad.e_learning.service.file;

import com.ahmad.e_learning.model.Course;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.util.List;

public interface IFileService {
    void saveFile(MultipartFile file, Long courseId);

    Resource loadFile(String fileName) throws MalformedURLException;

    List<String> getAllFiles();
}
