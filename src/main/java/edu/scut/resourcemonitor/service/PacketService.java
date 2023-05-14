package edu.scut.resourcemonitor.service;

import edu.scut.resourcemonitor.entity.TxPacket;

import java.util.List;

public interface PacketService {
    List<TxPacket> getPackets(String IFName);
}
