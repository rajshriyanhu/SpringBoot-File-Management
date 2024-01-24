package com.newauth.auth.repository;

import com.newauth.auth.models.File;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends MongoRepository<File, String> {
    Optional<File> findById(String id);
    List<File> findByFolderIdAndUserId(String folderId, String userId);

    void deleteByFolderId(String id);

}
