package edu.scut.resourcemonitor.service;

import edu.scut.resourcemonitor.entity.FileSystemStatus;
import edu.scut.resourcemonitor.util.Updatable;

import java.util.List;

public interface FileSystemStatusService extends Updatable {
    List<FileSystemStatus> getAllFileSystemsStatus();

    FileSystemStatus getFileSystemStatus(Integer hashcode);
}
