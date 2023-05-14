package edu.scut.resourcemonitor.entity;

import lombok.Data;

@Data
public class CPUStatus {
    private double CPUTotalUsage;
    private double[] CPUCoreUsage;
}
