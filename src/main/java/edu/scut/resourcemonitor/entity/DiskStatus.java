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
     * 可以只存储上面的数值，速度放在请求时再计算
     * 但在更新时一起计算readSpeed和writeSpeed，可以减少请求时的开销
     */
    double readSpeed;
    double writeSpeed;
}
