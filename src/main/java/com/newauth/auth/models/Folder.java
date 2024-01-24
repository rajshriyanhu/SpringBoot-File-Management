package com.newauth.auth.models;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "folders")
public class Folder {
    @Id
    private String id;
    private String FolderName;
    private String userId;
    private String folderId; //parent folder id

    public Folder(String FolderName, String folderId, String userId) {
        this.FolderName = FolderName;
        this.folderId = folderId;
        this.userId = userId;
    }
}
