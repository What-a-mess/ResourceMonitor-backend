package edu.scut.resourcemonitor.service;

import edu.scut.resourcemonitor.entity.TxPacket;
import edu.scut.resourcemonitor.repository.TxPacketDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class PacketServiceImpl implements PacketService {
    TxPacketDao txPacketDao;

    @Autowired
    PacketServiceImpl(TxPacketDao txPacketDao) {
        this.txPacketDao = txPacketDao;
    }
    @Override
    public List<TxPacket> getPackets(String IFName) {
        return txPacketDao.findByIFNameAndTime(IFName, new Timestamp(System.currentTimeMillis() - 1000));
    }

}
