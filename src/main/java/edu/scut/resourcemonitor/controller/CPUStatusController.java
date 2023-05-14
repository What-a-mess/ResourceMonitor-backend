package edu.scut.resourcemonitor.controller;

import edu.scut.resourcemonitor.entity.CPUInfo;
import edu.scut.resourcemonitor.service.CPUStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cpu")
public class CPUStatusController {
    CPUStatusService cpuStatusService;

    @Autowired
    CPUStatusController(CPUStatusService cpuStatusService) {
        this.cpuStatusService = cpuStatusService;
    }

    @GetMapping("totalusage")
    double getTotalCPUUsage() {
        return cpuStatusService.getCPUTotalUsage();
    }

    @GetMapping("coreusage")
    double[] getCPUCoreUsage() {
        return cpuStatusService.getCPUCoreUsage();
    }

    @GetMapping("cpuinfo")
    CPUInfo getCPUInfo() {
        return new CPUInfo();
    }


}
