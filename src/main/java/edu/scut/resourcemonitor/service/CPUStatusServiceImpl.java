package edu.scut.resourcemonitor.service;

import edu.scut.resourcemonitor.entity.CPUInfo;
import edu.scut.resourcemonitor.entity.CPULoadEntity;
import edu.scut.resourcemonitor.entity.CPUStatus;
import edu.scut.resourcemonitor.util.StateUpdater;
import edu.scut.resourcemonitor.util.Updatable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.driver.linux.proc.DiskStats;
import oshi.hardware.CentralProcessor;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
public class CPUStatusServiceImpl implements CPUStatusService {

    /**
     * 采用scheduledExecutor让每个service自己维护相关的系统数据，以实现：
     * 1. 更新与请求解耦。用户请求一次更新一次在可能存在多个客户端获取数据的情况下是存在问题的
     * 2. 每个Service可以根据自己的需要调整更新频率
     */
    private ScheduledExecutorService scheduledExecutorService;

    private final CentralProcessor centralProcessor;
    private final CPUStatus cpuStatus;
    int SAMPLE_MILLS = 1;

    @Autowired
    CPUStatusServiceImpl(SystemInfo systemInfo) {
        this.centralProcessor = systemInfo.getHardware().getProcessor();
        this.cpuStatus = new CPUStatus();
        initScheduleTask();
    }

    @Override
    public double getCPUTotalUsage() {
        return cpuStatus.getCPUTotalUsage();
    }

    @Override
    public double[] getCPUCoreUsage() {
        return cpuStatus.getCPUCoreUsage();
    }

    @Override
    public CPUInfo getCPUInfo() {
        CPUInfo cpuInfo = new CPUInfo();
        cpuInfo.setName(centralProcessor.getProcessorIdentifier().getName());
        cpuInfo.setPhysicalProcessorsCnt(centralProcessor.getPhysicalProcessorCount());
        cpuInfo.setLogicalProcessorsCnt(centralProcessor.getLogicalProcessorCount());
        cpuInfo.setBaseFreq(centralProcessor.getProcessorIdentifier().getVendorFreq());
        return cpuInfo;
    }

    @Override
    public void update() {
        cpuStatus.setCPUTotalUsage(centralProcessor.getSystemCpuLoad(SAMPLE_MILLS * 1000L));
        cpuStatus.setCPUCoreUsage(centralProcessor.getProcessorCpuLoad(SAMPLE_MILLS * 1000L));
    }

    private void initScheduleTask() {
        this.scheduledExecutorService = new ScheduledThreadPoolExecutor(2);
        this.scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                update();
            }
        }, 0, SAMPLE_MILLS, TimeUnit.SECONDS);
    }

    @Override
    public void setUpdateRate(int second) {
        SAMPLE_MILLS = second;
        this.scheduledExecutorService.shutdown();
        initScheduleTask();
    }
}
