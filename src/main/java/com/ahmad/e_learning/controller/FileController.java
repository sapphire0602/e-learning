package com.ahmad.e_learning.controller;

import com.ahmad.e_learning.model.Course;
import com.ahmad.e_learning.model.FileData;
import com.ahmad.e_learning.response.ApiResponse;
import com.ahmad.e_learning.service.file.IFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/v1")
public class FileController {
    private final IFileService fileService;
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    public FileController(IFileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/file/course/{courseId}/upload")
    public ResponseEntity<ApiResponse> addFile(@RequestParam("file") MultipartFile file, @PathVariable Long courseId) {
        try {
            fileService.saveFile(file, courseId);
            return ResponseEntity.ok(new ApiResponse("File saved successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("UNABLE TO UPLOAD FILE ! ", null));
        }
    }

    //    @Secured({"ADMIN" , "INSTRUCTOR" , "USER"})
    @GetMapping("/file/download")
    public ResponseEntity<Resource> loadFile(@RequestParam String fileName) {
        logger.info("Processing file download request for: " + fileName);
        try {
            Resource resource = fileService.loadFile(fileName);
            logger.info("✅ File found: {}", resource.getFilename());
            return ResponseEntity.ok()
                    //                .headers(HttpHeaders.CONTENT_DISPOSITION , "attachment;fileName=\"" + resource.getFilename() + "\"");
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/file/all")
    public ResponseEntity<ApiResponse> getAllFiles() {
        try {
            List<String> file = fileService.getAllFiles();
            return ResponseEntity.ok(new ApiResponse("File retrieved successfully", file));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("UNABLE TO  FILE RETRIEVE FILES ! ", null));
        }
    }

}
