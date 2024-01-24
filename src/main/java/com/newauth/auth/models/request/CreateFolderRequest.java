package com.newauth.auth.models.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateFolderRequest {
    private String folderName;
    private String FolderId;

    public void setUserId(String userId) {

    }

    public void setFolderId(String folderId){

    }
}
