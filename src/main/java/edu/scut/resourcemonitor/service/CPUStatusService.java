package edu.scut.resourcemonitor.service;

import edu.scut.resourcemonitor.entity.CPULoadEntity;

public interface CPUStatusService {
    CPULoadEntity getTotalCPULoad();
}
