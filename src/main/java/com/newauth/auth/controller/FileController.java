package com.newauth.auth.controller;


import com.newauth.auth.models.request.CreateFileRequest;
import com.newauth.auth.models.request.RenameMoveFileRequest;
import com.newauth.auth.service.FileService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @GetMapping("/{fileId}")
    public ResponseEntity<Map<String,Object>> getRootFolders(@PathVariable("fileId") String id){
        return fileService.getFile(id);
    }

    @PostMapping("/uploadfile")
    public ResponseEntity<Map<String,Object>> uploadFile(@RequestBody CreateFileRequest request, HttpServletRequest httpServletRequest){
        return fileService.uploadFile(request, httpServletRequest);
    }

    @PostMapping("/rename-movefile/{fileId}")
    public ResponseEntity<Map<String,Object>> renameAndMoveFile(@PathVariable("fileId") String id, @RequestBody RenameMoveFileRequest request){
        return fileService.renameAndMoveFile(id, request);
    }

    @DeleteMapping("/deletefile/{fileId}")
    public ResponseEntity<Map<String,Object>> deleteFile(@PathVariable("fileId") String id){
        return fileService.deleteFile(id);
    }


}
