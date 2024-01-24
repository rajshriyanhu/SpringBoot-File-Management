package com.newauth.auth.service;

import com.newauth.auth.config.JwtService;
import com.newauth.auth.models.File;
import com.newauth.auth.models.Folder;
import com.newauth.auth.models.request.CreateFileRequest;
import com.newauth.auth.models.request.RenameMoveFileRequest;
import com.newauth.auth.repository.FileRepository;
import com.newauth.auth.repository.FolderRepository;
import com.newauth.auth.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;
    private final JwtService jwtService;


    public ResponseEntity<Map<String, Object>> getFile(String id) {
        try{
            Optional<File> file = fileRepository.findById(id);
            return ResponseEntity.ok(Map.of(
                    "message", "Contents fetched successfully",
                    "file", file
            ));
        } catch (Exception e) {
            // Handle exceptions appropriately
            return ResponseEntity.status(500)
                    .body(Map.of("message", "Error fetching folder contents", "error", e.getMessage()));
        }
    }

    public ResponseEntity<Map<String, Object>> uploadFile(CreateFileRequest request,  HttpServletRequest httpServletRequest) {
        try{
        if (request.getFileName() == null || request.getFileName().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "File name is required"));
        }
            String userId = jwtService.extractUserIdFromRequest(httpServletRequest);


        File file = new File();
        file.setFolderId((request.getFolderId()==null || request.getFolderId().isEmpty()) ? "root" : request.getFolderId());
        file.setFileName(request.getFileName());
        file.setUserId(userId);
        file.setFileType(request.getFileType());
        file.setFileUrl(request.getFileUrl());
        file.setFileSize(request.getFileSize());
        file.setPreviewUrl(request.getPreviewUrl());
        fileRepository.save(file);
        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "File created successfully",
                "file", file));


    } catch(Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Error creating folder"));
    }
    }

    public ResponseEntity<Map<String, Object>> renameAndMoveFile(String id, RenameMoveFileRequest request) {
        if(!Objects.equals(request.getNewName(), "")){
            try{
                Optional<File> optionalFile = fileRepository.findById(id);

                if (optionalFile.isPresent()) {
                    // Update the folder name
                    File existingFile = optionalFile.get();
                    existingFile.setFileName(request.getNewName());

                    // Save the updated folder
                    File updatedFile = fileRepository.save(existingFile);

                    return ResponseEntity.ok(Map.of(
                            "message", "Folder renamed successfully",
                            "file", updatedFile
                    ));
                } else {
                    // Return not found response if the folder doesn't exist
                    return ResponseEntity.notFound().build();
                }
            }catch (Exception e) {
                // Handle exceptions appropriately
                return ResponseEntity.status(500)
                        .body(Map.of("message", "Error renaming folder", "error", e.getMessage()));
            }
        }
        else if(!Objects.equals(request.getNewFolderId(), "")){
            try{
                Optional<File> optionalFile = fileRepository.findById(id);

                if (optionalFile.isPresent()) {
                    // Update the folder name
                    File existingFile = optionalFile.get();
                    existingFile.setFolderId(request.getNewFolderId());

                    // Save the updated folder
                    File updatedFile = fileRepository.save(existingFile);

                    return ResponseEntity.ok(Map.of(
                            "message", "File renamed successfully",
                            "file", updatedFile
                    ));
                } else {
                    // Return not found response if the folder doesn't exist
                    return ResponseEntity.notFound().build();
                }
            }catch (Exception e) {
                // Handle exceptions appropriately
                return ResponseEntity.status(500)
                        .body(Map.of("message", "Error moving file", "error", e.getMessage()));
            }
        }
        return ResponseEntity.status(500)
                .body(Map.of("message", "Something went wrong"));
    }

    public ResponseEntity<Map<String, Object>> deleteFile(String id) {
        try {
            Optional<File> optionalFile = fileRepository.findById(id);

            if (optionalFile.isPresent()) {
                fileRepository.deleteById(id);

                return ResponseEntity.ok(Map.of(
                        "message", "File deleted successfully"
                ));
            } else {
                // Return not found response if the folder doesn't exist
                return ResponseEntity.notFound().build();
            }

        } catch (Exception e) {
            // Handle exceptions appropriately
            return ResponseEntity.status(500)
                    .body(Map.of("message", "Error deleting file", "error", e.getMessage()));
        }

    }
}
