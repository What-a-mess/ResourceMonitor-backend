package edu.scut.resourcemonitor.service;

import edu.scut.resourcemonitor.entity.MemStatus;
import edu.scut.resourcemonitor.util.StateUpdater;
import edu.scut.resourcemonitor.util.Updatable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;

@Service
public class MemoryStatusServiceImpl implements MemoryStatusService, Updatable {

    MemStatus memStatus;
    GlobalMemory globalMemory;

    @Autowired
    MemoryStatusServiceImpl(SystemInfo systemInfo, StateUpdater updater) {
        this.globalMemory = systemInfo.getHardware().getMemory();
        memStatus = new MemStatus();
        updater.addUpdateObj(this);
    }

    @Override
    public long getAvailableMem() {
        return memStatus.getAvailableMem();
    }

    @Override
    public long getTotalMem() {
        return memStatus.getTotalMem();
    }

    @Override
    public void update() {
        memStatus.setAvailableMem(globalMemory.getAvailable());
        memStatus.setTotalMem(globalMemory.getTotal());
    }
}
