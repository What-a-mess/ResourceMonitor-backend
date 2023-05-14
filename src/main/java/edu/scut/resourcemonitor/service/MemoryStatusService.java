package edu.scut.resourcemonitor.service;

import edu.scut.resourcemonitor.util.Updatable;

public interface MemoryStatusService extends Updatable {
    public long getAvailableMem();
    public long getTotalMem();
}
