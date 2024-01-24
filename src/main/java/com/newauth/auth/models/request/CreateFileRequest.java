package com.newauth.auth.models.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateFileRequest {
    private String folderId;
    private String fileName;
    private String fileType;
    private String fileUrl;
    private String fileSize;
    private String previewUrl;

}
