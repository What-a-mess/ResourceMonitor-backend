package edu.scut.resourcemonitor.controller;

import edu.scut.resourcemonitor.entity.MemStatus;
import edu.scut.resourcemonitor.service.MemoryStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("memory")
public class MemoryStatusController {

    MemoryStatusService memoryStatusService;
    @Autowired
    MemoryStatusController(MemoryStatusService memoryStatusService) {
        this.memoryStatusService = memoryStatusService;
    }

    @GetMapping("status")
    public MemStatus getMemStatus() {
        MemStatus memStatus = new MemStatus();
        memStatus.setTotalMem(memoryStatusService.getTotalMem());
        memStatus.setAvailableMem(memoryStatusService.getAvailableMem());
        return memStatus;
    }

    @GetMapping("total")
    public long getTotalMemory() {
        return memoryStatusService.getTotalMem();
    }

    @GetMapping("available")
    public long getAvailableMem() {
        return memoryStatusService.getAvailableMem();
    }

    @GetMapping("consumed")
    public long getConsumedMem() {
        return memoryStatusService.getTotalMem() - memoryStatusService.getAvailableMem();
    }

//    返回一个(0,1)的数，表示目前内存占用率
    @GetMapping("usage")
    public double getMemUsage() {
        return 1 - (memoryStatusService.getAvailableMem() * 1.0 / memoryStatusService.getTotalMem());
    }
}
