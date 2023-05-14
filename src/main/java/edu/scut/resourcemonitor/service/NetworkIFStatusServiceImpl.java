package edu.scut.resourcemonitor.service;

import edu.scut.resourcemonitor.entity.NetworkIFStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.hardware.NetworkIF;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
public class NetworkIFStatusServiceImpl implements NetworkIFStatusService {
    private long SAMPLE_INT = 1;
    SystemInfo systemInfo;
    List<NetworkIFStatus> statusList;
    Map<Integer, Integer> hashcodeToIndexMapping;
    private ScheduledExecutorService scheduledExecutorService;

    @Autowired
    public NetworkIFStatusServiceImpl(SystemInfo systemInfo) {
        this.systemInfo = systemInfo;
        this.statusList = new ArrayList<>();
        this.hashcodeToIndexMapping = new HashMap<>();
        initScheduleTask();
    }

    @Override
    public List<NetworkIFStatus> getAllNetworkIFStatus() {
        return this.statusList;
    }

    @Override
    public NetworkIFStatus getNetworkIFStatus(int hashcode) {
        return statusList.get(hashcodeToIndexMapping.get(hashcode));
    }

    public void update() {
        List<NetworkIF> networkIFList = systemInfo.getHardware().getNetworkIFs();
        List<NetworkIFStatus> newStatusList = new ArrayList<>();
        for (int i = 0; i < networkIFList.size(); i++) {
            NetworkIF tempIF = networkIFList.get(i);
            tempIF.updateAttributes();
            NetworkIFStatus tempStatus = new NetworkIFStatus();
            int tempHashCode = tempIF.getName().hashCode();
            tempStatus.setName(tempIF.getName());
            tempStatus.setHashcode(tempHashCode);
            tempStatus.setTimestamp(tempIF.getTimeStamp());
            tempStatus.setDisplayName(tempIF.getDisplayName());
            tempStatus.setByteSent(tempIF.getBytesSent());
            tempStatus.setByteRecv(tempIF.getBytesRecv());
            tempStatus.setPacketRecv(tempIF.getPacketsRecv());
            tempStatus.setPacketSent(tempIF.getPacketsSent());
            tempStatus.setIPv4Addr(String.join(".", tempIF.getIPv4addr()));
            tempStatus.setIPv6Addr(String.join(":", tempIF.getIPv6addr()));
            tempStatus.setMACAddr(tempIF.getMacaddr());
            if (!hashcodeToIndexMapping.containsKey(tempHashCode) ||
                statusList.get(hashcodeToIndexMapping.get(tempHashCode)).getTimestamp() == tempStatus.getTimestamp()) {
                newStatusList.add(tempStatus);
            } else {
                NetworkIFStatus oldStatus = statusList.get(hashcodeToIndexMapping.get(tempHashCode));
                long timeDiff = tempStatus.getTimestamp() - oldStatus.getTimestamp();
                tempStatus.setRecvSpeed((tempStatus.getByteRecv() - oldStatus.getByteRecv())*1.0 / timeDiff * 1000.0);
                tempStatus.setSendSpeed((tempStatus.getByteSent() - oldStatus.getByteSent())*1.0 / timeDiff * 1000.0);
                tempStatus.setSendPktSpeed((tempStatus.getPacketSent() - oldStatus.getPacketSent())*1.0 / timeDiff * 1000.0);
                tempStatus.setRecvPktSpeed((tempStatus.getPacketRecv() - oldStatus.getPacketRecv())*1.0 / timeDiff * 1000.0);
                newStatusList.add(tempStatus);
            }
            hashcodeToIndexMapping.put(tempHashCode, i);
        }

        this.statusList = newStatusList;
    }

    @Override
    public void setUpdateRate(int second) {
        SAMPLE_INT = second;
        this.scheduledExecutorService.shutdown();
        initScheduleTask();
    }

    private void initScheduleTask() {
        this.scheduledExecutorService = new ScheduledThreadPoolExecutor(2);
        this.scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                update();
            }
        }, 0, SAMPLE_INT, TimeUnit.SECONDS);
    }

}
