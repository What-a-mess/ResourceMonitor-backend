package edu.scut.resourcemonitor.service;

import edu.scut.resourcemonitor.entity.DiskStatus;
import edu.scut.resourcemonitor.util.StateUpdater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.hardware.HWDiskStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
public class DiskStatusServiceImpl implements DiskStatusService {
    private int SAMPLE_MILLS = 1;
    private final SystemInfo systemInfo;
    private List<DiskStatus> statusList;
    private final Map<String, Integer> nameToDiskMapping;
    private ScheduledExecutorService scheduledExecutorService;

    @Autowired
    DiskStatusServiceImpl(SystemInfo systemInfo) {
        this.systemInfo = systemInfo;
        this.statusList = new ArrayList<>();
        this.nameToDiskMapping = new HashMap<>();
//        updater.addUpdateObj(this);
        initScheduleTask();
    }

    @Override
    public List<DiskStatus> getAllDiskStatus() {
        return statusList;
    }

    @Override
    public DiskStatus getDiskStatus(String diskName) {
        return statusList.get(nameToDiskMapping.get(diskName));
    }

    @Override
    public void update() {
        List<HWDiskStore> diskList = systemInfo.getHardware().getDiskStores();
        List<DiskStatus> newStatusList = new ArrayList<>();
        for (int i = 0; i < diskList.size(); i++) {
            HWDiskStore tempDisk = diskList.get(i);
            tempDisk.updateAttributes();
            DiskStatus tempStatus = new DiskStatus();
            tempStatus.setName(tempDisk.getName());
            tempStatus.setTimestamp(tempDisk.getTimeStamp());
            tempStatus.setReadBytes(tempDisk.getReadBytes());
            tempStatus.setWriteBytes(tempStatus.getWriteBytes());
            if (!nameToDiskMapping.containsKey(tempDisk.getName()) ||
                    statusList.get(nameToDiskMapping.get(tempDisk.getName())).getTimestamp()
                    == tempStatus.getTimestamp()) {
                newStatusList.add(tempStatus);
            } else {
                DiskStatus oldStatus = statusList.get(nameToDiskMapping.get(tempDisk.getName()));
                tempStatus.setWriteSpeed((tempStatus.getWriteBytes()-oldStatus.getWriteBytes())*1.0 / (tempStatus.getTimestamp()-oldStatus.getTimestamp()) * 1000.0);
                tempStatus.setReadSpeed((tempStatus.getReadBytes()-oldStatus.getReadBytes())*1.0 / (tempStatus.getTimestamp()-oldStatus.getTimestamp()) * 1000.0);
                newStatusList.add(tempStatus);
            }
            nameToDiskMapping.put(tempDisk.getName(), i);
        }

        this.statusList = newStatusList;
    }

    private void initScheduleTask() {
        this.scheduledExecutorService = new ScheduledThreadPoolExecutor(2);
        this.scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                update();
            }
        }, 0, SAMPLE_MILLS, TimeUnit.SECONDS);
    }

    @Override
    public void setUpdateRate(int second) {
        SAMPLE_MILLS = second;
        this.scheduledExecutorService.shutdown();
        initScheduleTask();
    }
}
