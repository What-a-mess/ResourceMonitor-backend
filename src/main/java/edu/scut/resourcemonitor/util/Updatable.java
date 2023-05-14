package edu.scut.resourcemonitor.util;

/**
 * An interface for update
 * 一个用于更新的接口
 * 目的是为了让服务端的测量值始终以自己的步调进行更新，将客户端请求与后端数据更新的过程进行解耦
 */
public interface Updatable {
    void update();
}
