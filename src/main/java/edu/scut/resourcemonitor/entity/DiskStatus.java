package edu.scut.resourcemonitor.entity;

import lombok.Data;

@Data
public class DiskStatus {
    String name;
    long readBytes;
    long writeBytes;
    /**
     * 获得上面的数据时服务器上的时间
     * 用于计算两次数据间时间间隔，可以计算传输速度
     */
    long timestamp;

    /**
     * 迄今为止花费在IO上的时间
     */
    long transferTime;

    /**
     * 读写速度readSpeed和writeSpeed
     * 均以Bytes/s为单位
     */
    double readSpeed;
    double writeSpeed;
    /**
     * 一个0~1的值，表示磁盘的忙时间占比，
     * 由两个status间的timeSpent差除以两个timestamp的差得到，
     * 表示一段时间内磁盘用于读写时间的比例，表示磁盘占用率（不是空间占用率）
     */
    double diskBusyRate;
    /**
     * 由于硬盘名称可能含有特殊符号，需要hashCode用来访问特定的硬盘
     */
    int hashCode;
}
