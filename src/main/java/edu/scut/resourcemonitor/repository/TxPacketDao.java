package edu.scut.resourcemonitor.repository;

import edu.scut.resourcemonitor.entity.TxPacket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.sql.Timestamp;
import java.util.List;

public interface TxPacketDao extends JpaRepository<TxPacket, Long> {

    List<TxPacket> findByIFNameAndTime(String IFName, Timestamp time);
}
