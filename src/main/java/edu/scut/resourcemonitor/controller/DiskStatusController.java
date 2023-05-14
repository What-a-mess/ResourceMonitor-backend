package edu.scut.resourcemonitor.controller;

import edu.scut.resourcemonitor.entity.DiskStatus;
import edu.scut.resourcemonitor.service.DiskStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("disk")
public class DiskStatusController {
    DiskStatusService diskStatusService;
    @Autowired
    DiskStatusController(DiskStatusService diskStatusService) {
        this.diskStatusService = diskStatusService;
    }

    @GetMapping("alldiskstatus")
    List<DiskStatus> getAllDiskStatus() {
        return diskStatusService.getAllDiskStatus();
    }

    @GetMapping("status/{hashcode}")
    DiskStatus genDiskStatus(@PathVariable Integer hashcode) {
        return diskStatusService.getDiskStatus(hashcode);
    }
}
