package com.newauth.auth.controller;

import com.newauth.auth.models.request.CreateFolderRequest;
import com.newauth.auth.models.request.FolderRenameRequest;
import com.newauth.auth.service.FolderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/folder")
@RequiredArgsConstructor
public class FolderController {

    private final FolderService folderService;

    @GetMapping("/rootfolders")
    public ResponseEntity<Map<String,Object>> getRootFolders(HttpServletRequest httpServletRequest){
        return folderService.getRootFolders(httpServletRequest);
    }

    @GetMapping("/{folderId}")
    public ResponseEntity<Map<String,Object>> getFolder(@PathVariable("folderId") String id, HttpServletRequest httpServletRequest){
        return folderService.getFolder(id, httpServletRequest);
    }

    @PostMapping("/createfolder")
    public ResponseEntity<Map<String, Object>> createNewFolder(@RequestBody CreateFolderRequest request, HttpServletRequest httpServletRequest){
        return folderService.createFolder(request, httpServletRequest);
    }

    @PostMapping("/remanefolder/{folderId}")
    public ResponseEntity<Map<String,Object>> getFolder(@PathVariable("folderId") String id, @RequestBody FolderRenameRequest request){
        return folderService.renameFolder(id, request);
    }

    @DeleteMapping("/deletefolder/{folderId}")
    public ResponseEntity<Map<String,Object>> deleteFolder(@PathVariable("folderId") String id){
        return folderService.deleteFolder(id);
    }
}
