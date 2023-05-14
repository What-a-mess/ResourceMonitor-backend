package edu.scut.resourcemonitor.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class NetworkIFStatus {
    String name;
    String displayName;
    String IPv4Addr;
    String IPv6Addr;
    String MACAddr;
    long speedRate;

    long byteSent;
    long byteRecv;
    long packetSent;
    long packetRecv;
    long timestamp;

    double sendSpeed;
    double recvSpeed;
    double sendPktSpeed;
    double recvPktSpeed;
    int hashcode;
}
