package edu.scut.resourcemonitor.service;

import edu.scut.resourcemonitor.entity.NetworkIFStatus;
import edu.scut.resourcemonitor.util.Updatable;

import java.util.List;

public interface NetworkIFStatusService extends Updatable {
    List<NetworkIFStatus> getAllNetworkIFStatus();
    NetworkIFStatus getNetworkIFStatus(int hashcode);
}
