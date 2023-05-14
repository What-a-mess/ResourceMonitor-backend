package edu.scut.resourcemonitor.service;

import edu.scut.resourcemonitor.entity.FileSystemStatus;
import edu.scut.resourcemonitor.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
public class FileSystemStatusServiceImpl implements FileSystemStatusService {
    private long SAMPLE_INT = 1;
    FileSystem fileSystem;
    List<FileSystemStatus> fileSystemStatusList;
    Map<Integer, Integer> hashcodeToIndexMapping;

    private ScheduledExecutorService scheduledExecutorService;

    @Autowired
    FileSystemStatusServiceImpl(SystemInfo systemInfo) {
        this.fileSystem = systemInfo.getOperatingSystem().getFileSystem();
        this.fileSystemStatusList = new ArrayList<>();
        this.hashcodeToIndexMapping = new HashMap<>();
        initScheduleTask();
    }

    @Override
    public List<FileSystemStatus> getAllFileSystemsStatus() {
        return this.fileSystemStatusList;
    }

    @Override
    public FileSystemStatus getFileSystemStatus(Integer hashcode) {
        if (hashcodeToIndexMapping.containsKey(hashcode) &&
            hashcodeToIndexMapping.get(hashcode)<fileSystemStatusList.size()) {
            FileSystemStatus status = fileSystemStatusList.get(hashcodeToIndexMapping.get(hashcode));
            if (status.getHashcode() == hashcode) {
                return status;
            }
        }

        throw new ResourceNotFoundException("找不到编号为 ["+hashcode+"] 的卷");
    }

    public void update() {
        List<OSFileStore> fileStoreList = fileSystem.getFileStores();
        List<FileSystemStatus> newStatusList = new ArrayList<>();
        for (int i = 0; i < fileStoreList.size(); i++) {
            OSFileStore tempFileSystem = fileStoreList.get(i);
            FileSystemStatus tempStatus = new FileSystemStatus();
            tempStatus.setName(tempFileSystem.getName());
            tempStatus.setHashcode(tempFileSystem.getName().hashCode());
            tempStatus.setFreeSpace(tempFileSystem.getFreeSpace());
            tempStatus.setTotalSpace(tempFileSystem.getTotalSpace());
            tempStatus.setUsedSpace(tempStatus.getTotalSpace()-tempStatus.getFreeSpace());
            tempStatus.setSpaceUsage(tempStatus.getUsedSpace()*1.0 / tempStatus.getTotalSpace());

            hashcodeToIndexMapping.put(tempStatus.getHashcode(), i);
            newStatusList.add(tempStatus);
        }
        fileSystemStatusList = newStatusList;
    }

    private void initScheduleTask() {
        this.scheduledExecutorService = new ScheduledThreadPoolExecutor(2);
        this.scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                update();
            }
        }, 0, SAMPLE_INT, TimeUnit.SECONDS);
    }

    @Override
    public void setUpdateRate(int second) {
        SAMPLE_INT = second;
        this.scheduledExecutorService.shutdown();
        initScheduleTask();
    }
}
