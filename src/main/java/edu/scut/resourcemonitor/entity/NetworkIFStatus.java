package edu.scut.resourcemonitor.entity;

import lombok.Data;

@Data
public class NetworkIFStatus {
    String name;
    String displayName;
    String IPv4Addr;
    String IPv6Addr;
    String MACAddr;
    long speedRate;

    long byteSent;
    long byteRecv;
    long timestamp;

    double sendSpeed;
    double recvSpeed;
    int hashcode;
}
