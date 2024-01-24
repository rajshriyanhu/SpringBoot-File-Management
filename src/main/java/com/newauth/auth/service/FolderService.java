package com.newauth.auth.service;

import com.newauth.auth.config.JwtService;
import com.newauth.auth.models.File;
import com.newauth.auth.models.Folder;
import com.newauth.auth.models.request.CreateFolderRequest;
import com.newauth.auth.models.request.FolderRenameRequest;
import com.newauth.auth.repository.FileRepository;
import com.newauth.auth.repository.FolderRepository;
import com.newauth.auth.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FolderService {
    private final FolderRepository folderRepository;
    private final FileRepository fileRepository;
    private final JwtService jwtService;


    public ResponseEntity<Map<String, Object>> createFolder(CreateFolderRequest request, HttpServletRequest httpServletRequest){
        try{
            String userId = jwtService.extractUserIdFromRequest(httpServletRequest);

            if(userId != null){
                request.setUserId(userId);
            }
            else {
                // Handle the case where user ID cannot be extracted
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Unable to extract user ID from token"));
            }

            if (request.getFolderName() == null || request.getFolderName().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of(
                        "success", false,
                        "message", "Folder name is required"));
            }

            Folder folder = new Folder();
            folder.setFolderName(request.getFolderName());
            folder.setUserId(userId);
            folder.setFolderId(request.getFolderId()==null ? "root" : request.getFolderId());

            folderRepository.save(folder);

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Folder created successfully",
                    "folder", folder));


        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error creating folder"));
        }
    }

    public ResponseEntity<Map<String, Object>> getFolder(String id, HttpServletRequest httpServletRequest){
        try{
            String userId = jwtService.extractUserIdFromRequest(httpServletRequest);
            List<Folder> folders = folderRepository.findByIdAndUserId(id, userId);
            return ResponseEntity.ok(Map.of(
                    "message", "Contents fetched successfully",
                    "folders", folders
            ));
        } catch (Exception e) {
            // Handle exceptions appropriately
            return ResponseEntity.status(500)
                    .body(Map.of("message", "Error fetching folder contents", "error", e.getMessage()));
        }
    }

    public ResponseEntity<Map<String, Object>> renameFolder(String id, @RequestBody FolderRenameRequest request){
        try{
            Optional<Folder> optionalFolder = folderRepository.findById(id);

            if (optionalFolder.isPresent()) {
                // Update the folder name
                Folder existingFolder = optionalFolder.get();
                existingFolder.setFolderName(request.getName());

                // Save the updated folder
                Folder updatedFolder = folderRepository.save(existingFolder);

                return ResponseEntity.ok(Map.of(
                        "message", "Folder renamed successfully",
                        "folder", updatedFolder
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

    public ResponseEntity<Map<String,Object>> deleteFolder(String id){
        try {
            // Delete all files in the folder
            fileRepository.deleteByFolderId(id);

            List<Folder> subFolders = folderRepository.findByFolderId(id);
            for (Folder subfolder : subFolders) {
                deleteFolder(subfolder.getId());
            }

            // After all contents are deleted, delete the folder itself
            folderRepository.deleteById(id);

            return ResponseEntity.ok(Map.of("message", "Folder and all contents deleted successfully"));
        } catch (Exception e) {
            // Handle exceptions appropriately
            return ResponseEntity.status(500)
                    .body(Map.of("message", "Error deleting folder", "error", e.getMessage()));
        }
    }


    public ResponseEntity<Map<String, Object>> getRootFolders(HttpServletRequest httpServletRequest) {
        try{
            String userId = jwtService.extractUserIdFromRequest(httpServletRequest);
            String folderId ="root";
            List<Folder> folders = folderRepository.findByFolderIdAndUserId(folderId, userId);
            List<File> files = fileRepository.findByFolderIdAndUserId(folderId, userId);
            return ResponseEntity.ok(Map.of(
                    "message", "Contents fetched successfully",
                    "folders", folders,
                    "files",files
            ));
        }catch (Exception e) {
            // Handle exceptions appropriately
            return ResponseEntity.status(500)
                    .body(Map.of("message", "Error fetching folder contents", "error", e.getMessage()));
        }
    }
}
