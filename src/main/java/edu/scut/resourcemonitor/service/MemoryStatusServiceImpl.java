package edu.scut.resourcemonitor.service;

import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;

@Service
public class MemoryStatusServiceImpl implements MemoryStatusService {

    GlobalMemory globalMemory;

    MemoryStatusServiceImpl(SystemInfo systemInfo) {
        this.globalMemory = systemInfo.getHardware().getMemory();
    }

    @Override
    public long getAvailableMem() {
        return globalMemory.getAvailable();
    }

    @Override
    public long getTotalMem() {
        return globalMemory.getTotal();
    }
}
