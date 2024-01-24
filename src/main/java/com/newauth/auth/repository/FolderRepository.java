package com.newauth.auth.repository;

import com.newauth.auth.models.Folder;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FolderRepository extends MongoRepository<Folder, String> {
    // Query method to find folders by id and userId
    List<Folder> findByIdAndUserId(String id, String userId);
    List<Folder> findByFolderIdAndUserId(String folderId, String userId);
    Optional<Folder> findById(String id);
    List<Folder> findByFolderId(String id);
}
