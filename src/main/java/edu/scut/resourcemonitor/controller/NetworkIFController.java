package edu.scut.resourcemonitor.controller;

import edu.scut.resourcemonitor.entity.NetworkIFStatus;
import edu.scut.resourcemonitor.service.NetworkIFStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("if")
public class NetworkIFController {
    NetworkIFStatusService networkIFStatusService;
    @Autowired
    NetworkIFController(NetworkIFStatusService networkIFStatusService) {
        this.networkIFStatusService = networkIFStatusService;
    }

    @GetMapping("allifstatus")
    List<NetworkIFStatus> getAllIFStatus() {
        return networkIFStatusService.getAllNetworkIFStatus();
    }

    @GetMapping("{hashcode}/status")
    NetworkIFStatus getIFStatus(@PathVariable Integer hashcode) {
        return networkIFStatusService.getNetworkIFStatus(hashcode);
    }
}
