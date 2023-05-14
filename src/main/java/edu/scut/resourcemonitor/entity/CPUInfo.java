package edu.scut.resourcemonitor.entity;

import lombok.Data;

@Data
public class CPUInfo {
    /**
     *  CPU名称，例如"Intel(R) Core(TM)2 Duo CPU T7300 @ 2.00GHz"
     */
    String name;

    /**
     * CPU基础频率(Hz)
     */
    long baseFreq;

    /**
     * 逻辑处理器数量
     */
    int logicalProcessorsCnt;

    /**
     * 物理处理器数量
     */
    int physicalProcessorsCnt;
}
