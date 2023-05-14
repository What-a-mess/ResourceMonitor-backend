package edu.scut.resourcemonitor.service;

import edu.scut.resourcemonitor.entity.NetworkIFStatus;
import org.springframework.beans.factory.annotation.Autowired;
import oshi.SystemInfo;

import java.util.List;

public class NetworkIFStatusServiceImpl implements NetworkIFStatusService {
    SystemInfo systemInfo;

    @Override
    public List<NetworkIFStatus> getAllNetworkIFStatus() {
        return null;
    }

    @Override
    public NetworkIFStatus getNetworkIFStatus(int hashcode) {
        return null;
    }

    @Override
    public void update() {

    }

    @Override
    public void setUpdateRate(int second) {

    }
}
