package edu.scut.resourcemonitor.service;

import edu.scut.resourcemonitor.entity.MemStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
public class MemoryStatusServiceImpl implements MemoryStatusService {

    private int SAMPLE_INT = 1;
    MemStatus memStatus;
    GlobalMemory globalMemory;
    private ScheduledExecutorService scheduledExecutorService;

    @Autowired
    MemoryStatusServiceImpl(SystemInfo systemInfo) {
        this.globalMemory = systemInfo.getHardware().getMemory();
        memStatus = new MemStatus();
        initScheduleTask();
    }

    @Override
    public long getAvailableMem() {
        return memStatus.getAvailableMem();
    }

    @Override
    public long getTotalMem() {
        return memStatus.getTotalMem();
    }

    public void update() {
        memStatus.setAvailableMem(globalMemory.getAvailable());
        memStatus.setTotalMem(globalMemory.getTotal());
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
