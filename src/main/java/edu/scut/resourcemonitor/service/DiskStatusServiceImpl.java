package edu.scut.resourcemonitor.service;

import edu.scut.resourcemonitor.entity.DiskStatus;
import edu.scut.resourcemonitor.util.StateUpdater;
import edu.scut.resourcemonitor.util.Updatable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.hardware.HWDiskStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DiskStatusServiceImpl implements DiskStatusService, Updatable {
    private final SystemInfo systemInfo;
    private List<DiskStatus> statusList;
    private final Map<String, Integer> nameToDiskMapping;

    @Autowired
    DiskStatusServiceImpl(SystemInfo systemInfo, StateUpdater updater) {
        this.systemInfo = systemInfo;
        this.statusList = new ArrayList<>();
        this.nameToDiskMapping = new HashMap<>();
        updater.addUpdateObj(this);
        update();
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
                tempStatus.setWriteSpeed((tempStatus.getWriteBytes()-oldStatus.getWriteBytes())*1.0 / (tempStatus.getTimestamp()-oldStatus.getTimestamp()));
                tempStatus.setReadSpeed((tempStatus.getReadBytes()-oldStatus.getReadBytes())*1.0 / (tempStatus.getTimestamp()-oldStatus.getTimestamp()));
                newStatusList.add(tempStatus);
            }
            nameToDiskMapping.put(tempDisk.getName(), i);
        }

        this.statusList = newStatusList;
    }
}
