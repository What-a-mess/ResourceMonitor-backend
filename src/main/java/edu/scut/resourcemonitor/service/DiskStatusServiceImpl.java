package edu.scut.resourcemonitor.service;

import edu.scut.resourcemonitor.entity.DiskStatus;
import edu.scut.resourcemonitor.exception.ResourceNotFoundException;
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
    private int SAMPLE_INT = 1;
    private final SystemInfo systemInfo;
    private List<DiskStatus> statusList;
    private final Map<Integer, Integer> hashcodeToDiskMapping;
    private ScheduledExecutorService scheduledExecutorService;

    @Autowired
    DiskStatusServiceImpl(SystemInfo systemInfo) {
        this.systemInfo = systemInfo;
        this.statusList = new ArrayList<>();
        this.hashcodeToDiskMapping = new HashMap<>();
        initScheduleTask();
    }

    @Override
    public List<DiskStatus> getAllDiskStatus() {
        return statusList;
    }

    @Override
    public DiskStatus getDiskStatus(Integer diskHashCode) {
        if (hashcodeToDiskMapping.containsKey(diskHashCode)) {
            return statusList.get(hashcodeToDiskMapping.get(diskHashCode));
        } else {
            throw new ResourceNotFoundException("找不到编号为 ["+diskHashCode+"] 的硬盘");
        }
    }

    public void update() {
        List<HWDiskStore> diskList = systemInfo.getHardware().getDiskStores();
        List<DiskStatus> newStatusList = new ArrayList<>();
        for (int i = 0; i < diskList.size(); i++) {
            HWDiskStore tempDisk = diskList.get(i);
            tempDisk.updateAttributes();
            DiskStatus tempStatus = new DiskStatus();
            // 为了实现每个硬盘单独获取信息的接口，name会放到url中，因此必须符合url格式
            int tempHashCode = tempDisk.getName().hashCode();
            tempStatus.setName(tempDisk.getName());
            tempStatus.setHashcode(tempHashCode);
            tempStatus.setTimestamp(tempDisk.getTimeStamp());
            tempStatus.setReadBytes(tempDisk.getReadBytes());
            tempStatus.setWriteBytes(tempDisk.getWriteBytes());
            tempStatus.setTransferTime(tempDisk.getTransferTime());
            // 如果 1. Map中不存在该硬盘（即硬盘是新插入的）；2. 硬盘的信息没有更新
            // 就认为硬盘读写速度为0，其他按照读到的数据直接写入当前状态列表。
            if (!hashcodeToDiskMapping.containsKey(tempHashCode) ||
                statusList.get(hashcodeToDiskMapping.get(tempHashCode)).getTimestamp() == tempStatus.getTimestamp()) {
                newStatusList.add(tempStatus);
            } else {
                // 否则，根据两次数据的间隔以及差来计算速率、占用率等信息
                DiskStatus oldStatus = statusList.get(hashcodeToDiskMapping.get(tempHashCode));
                // 两个status采样时时间的差；由于磁盘信息并不保证实时获取（即使调用更新的函数也只能best effort）因此需要
                long timeDiff = tempStatus.getTimestamp()-oldStatus.getTimestamp();
                tempStatus.setWriteSpeed((tempStatus.getWriteBytes()-oldStatus.getWriteBytes())*1.0 / timeDiff * 1000.0);
                tempStatus.setReadSpeed((tempStatus.getReadBytes()-oldStatus.getReadBytes())*1.0 / timeDiff * 1000.0);
                tempStatus.setDiskBusyRate((tempStatus.getTransferTime()-oldStatus.getTransferTime())*1.0 / timeDiff);
                // 硬盘占用率不能超过100%，但结果有时候会超，可能和一些硬件设计有关
                if (tempStatus.getDiskBusyRate() > 1.0) {
                    tempStatus.setDiskBusyRate(1.0);
                }
                newStatusList.add(tempStatus);
            }
            hashcodeToDiskMapping.put(tempHashCode, i);
        }

        this.statusList = newStatusList;
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
