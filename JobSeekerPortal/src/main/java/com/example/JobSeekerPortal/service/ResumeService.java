package com.example.JobSeekerPortal.service;

import com.example.JobSeekerPortal.entity.User;
import com.example.JobSeekerPortal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;

@Service
public class ResumeService {

    private static final String UPLOAD_DIR = "uploads/resumes/";

    private final UserRepository userRepository;

    public ResumeService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void uploadResume(Long userId, MultipartFile file) throws IOException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs(); // Create folder if it doesn't exist
        }

        // Save file
        String fileName = userId + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir.getAbsolutePath(), fileName);

        File[] files = uploadDir.listFiles();
        if(files != null) {
            for(File f : files) {
                String index = f.getName().split("_")[0];
                if(Objects.equals(index, userId.toString())) {
                    f.delete();
                }
            }
        }

        Files.write(filePath, file.getBytes());

        user.setResumePath(String.valueOf(filePath));
        userRepository.save(user);
    }

    public FileSystemResource getResume(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent() && user.get().getResumePath() != null) {
            return new FileSystemResource(new File(user.get().getResumePath()));
        } else {
         return null;
        }
    }
}
