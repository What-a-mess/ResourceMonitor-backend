package edu.scut.resourcemonitor.util;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * 采用订阅机制，定时执行updateStatus函数更新后端的信息
 */
@Component
public class StateUpdater {
    ArrayList<Updatable> updateObjs = new ArrayList<>();

    public void addUpdateObj(Updatable obj) {
        updateObjs.add(obj);
    }

    public void removeUpdateObj(Updatable obj) {
        updateObjs.remove(obj);
    }

    @Scheduled(fixedRate = 500)
    public void updateStatus() {
        for (Updatable u : updateObjs) {
            u.update();
        }
    }
}
