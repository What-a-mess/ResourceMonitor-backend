package edu.scut.resourcemonitor.controller;

import edu.scut.resourcemonitor.entity.CPULoadEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("cpu")
public class CpuStatusController {
    @GetMapping("totalusage")
    CPULoadEntity getTotalCPULoad() {
        return null;
    }

}
