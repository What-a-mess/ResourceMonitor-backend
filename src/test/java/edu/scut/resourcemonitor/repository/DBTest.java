package edu.scut.resourcemonitor.repository;

import edu.scut.resourcemonitor.entity.TxPacket;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DBTest {
    @Autowired
    TxPacketDao txPacketDao;

    @Test
    public void dbTest() {
        TxPacket packet = new TxPacket();
        packet.setDest("localhost");
        packet.setSrc("localhost");
        packet.setDestPort(443);
        packet.setSrcPort(12138);
        txPacketDao.save(packet);

        List<TxPacket> packets = txPacketDao.findAll();
        for (TxPacket p : packets) {
            System.out.println(p);
        }
    }

}
