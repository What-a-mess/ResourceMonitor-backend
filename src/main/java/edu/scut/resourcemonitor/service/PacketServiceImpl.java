package edu.scut.resourcemonitor.service;

import edu.scut.resourcemonitor.entity.TxPacket;
import edu.scut.resourcemonitor.repository.TxPacketDao;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.Pcaps;
import org.pcap4j.packet.Packet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

    private void savePacket(Packet packet) {

    }

    public void packetCapture() {
        String filter = "tcp||udp";
        String tcpFilter = "tcp";
        String udpFilter = "udp";

        PcapNetworkInterface nif;
        List<PcapNetworkInterface> allDevs = null;
        try {
            try {
                allDevs = Pcaps.findAllDevs();
            } catch (PcapNativeException var3) {
                throw new IOException(var3.getMessage());
            }
            if (allDevs != null && !allDevs.isEmpty()) {
                // do something here
                int deviceNum = 0;
                nif = Pcaps.getDevByName(allDevs.get(deviceNum).getName());
            } else {
                throw new IOException("No NIF to capture.");
            }

        } catch (IOException e) {
            e.printStackTrace();
            return;
        } catch (PcapNativeException e) {
            throw new RuntimeException(e);
        }

        if (nif == null) {
            return;
        }

        for (PcapNetworkInterface dev : allDevs) {

        }


    }

}
