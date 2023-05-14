package edu.scut.resourcemonitor.controller;

import edu.scut.resourcemonitor.entity.FileSystemStatus;
import edu.scut.resourcemonitor.service.FileSystemStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("filesystem")
public class FileSystemController {
    FileSystemStatusService fileSystemStatusService;
    @Autowired
    FileSystemController(FileSystemStatusService fileSystemStatusService) {
        this.fileSystemStatusService = fileSystemStatusService;
    }

    @GetMapping("allfilesystemstatus")
    public List<FileSystemStatus> getAllFileSystemStatus() {
        return fileSystemStatusService.getAllFileSystemsStatus();
    }

    @GetMapping("status/{hashcode}")
    public FileSystemStatus getFileSystemStatus(@PathVariable Integer hashcode) {
        return fileSystemStatusService.getFileSystemStatus(hashcode);
    }
}
