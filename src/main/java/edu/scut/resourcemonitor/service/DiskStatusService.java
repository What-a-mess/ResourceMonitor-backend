package edu.scut.resourcemonitor.service;

import edu.scut.resourcemonitor.entity.DiskStatus;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 由于DiskStatus中主要的数据就是传输速度，
 * 并且其中各数据高度相关，因此目前只提供getDiskStatus一类的方法
 */
@Service
public interface DiskStatusService {
    /**
     * @return 一组DiskStatus对象，是计算机上所有硬盘状态的集合
     */
    List<DiskStatus> getAllDiskStatus();

    /**
     * @param diskName 硬盘的名字，如sda
     * @return 对应名称的硬盘的状态对象
     */
    DiskStatus getDiskStatus(String diskName);
}
