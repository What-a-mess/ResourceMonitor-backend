package edu.scut.resourcemonitor.entity;

import lombok.Data;

@Deprecated
@Data
public class CPULoadEntity {
    long user;
    long nice;
    long system;
    long idle;
    long ioWait;
    long irq;
    long softIrq;
    long steal;
}
