package edu.scut.resourcemonitor.entity;

import lombok.Data;

@Data
public class FileSystemStatus {
    String name;
    /**
     * 文件系统类型，如ext4、FAT32、NTFS等
     */
    String type;
    long freeSpace;
    long totalSpace;
    long usedSpace;
    double spaceUsage;
    int hashcode;
}
