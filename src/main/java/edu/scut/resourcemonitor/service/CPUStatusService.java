package edu.scut.resourcemonitor.service;

import edu.scut.resourcemonitor.entity.CPUInfo;

public interface CPUStatusService {
    double getCPUTotalUsage();

    double[] getCPUCoreUsage();

    CPUInfo getCPUInfo();

}
