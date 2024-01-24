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
@Document(collection = "files")
public class File {
    @Id
    private String id;
    private String folderId;
    private String userId;
    private String fileName;
    private String fileType;
    private String fileUrl;
    private String fileSize;
    private String previewUrl;
}
