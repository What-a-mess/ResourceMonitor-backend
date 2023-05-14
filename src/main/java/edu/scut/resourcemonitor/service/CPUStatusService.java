package edu.scut.resourcemonitor.service;

import edu.scut.resourcemonitor.entity.CPUInfo;

public interface CPUStatusService {
    /**
     * @return 一个0～1的double表示整体CPU占用率
     */
    double getCPUTotalUsage();

    /**
     * @return 一个double数组，表示每个核心上的CPU占用率，数组长度与逻辑处理器数量一致
     */
    double[] getCPUCoreUsage();

    /**
     * @return 一个CPUInfo对象描述CPU的基本信息，详细内容参见CPUInfo定义
     */
    CPUInfo getCPUInfo();
}
