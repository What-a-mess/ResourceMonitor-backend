package edu.scut.resourcemonitor.entity;

import lombok.Data;

@Data
public class CPUInfo {
    String name;
    String baseFreq;
    int logicalProcessorsCnt;
    int physicalProcessorsCnt;
}
