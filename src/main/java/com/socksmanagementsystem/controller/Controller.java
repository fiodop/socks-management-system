package com.socksmanagementsystem.controller;

import com.socksmanagementsystem.dto.SockDto;
import com.socksmanagementsystem.model.Sock;
import com.socksmanagementsystem.service.ExelService;
import com.socksmanagementsystem.service.SockService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/socks")
@AllArgsConstructor
public class Controller {
    private final SockService sockService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ExelService exelService;

    @PostMapping("/income")
    public ResponseEntity<SockDto> addSocks(@RequestBody SockDto sockRequest) {
        sockService.addSocks(sockRequest);

        logger.info("Addinng socks: {}", sockRequest.toString());
        return ResponseEntity.ok(sockRequest);
    }

    @PostMapping("/outcome")
    public ResponseEntity<SockDto> removeSocks(@RequestBody SockDto sockRequest) {
        sockService.removeSocks(sockRequest);

        logger.info("Removing socks: {}", sockRequest.toString());
        return ResponseEntity.ok(sockRequest);
    }

    @GetMapping
    public ResponseEntity<List<Sock>> filterSocks(@RequestParam String color,
                                                  @RequestParam String operator,
                                                  @RequestParam int cottonPart
                                                  ) {
        List<Sock> filteredSockList = sockService.filterSocks(color, operator, cottonPart);

        logger.info("Filtering socks: {}", filteredSockList.toString());
        return ResponseEntity.ok(filteredSockList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sock> updateSocks(@PathVariable int sockId, @RequestBody SockDto sockRequest) {
        Sock updatedSock = sockService.updateSock(sockId, sockRequest);

        logger.info("Updating sock: {}", updatedSock.toString());
        return ResponseEntity.ok(updatedSock);
    }

    @PostMapping("/batch")
    public ResponseEntity<List<Sock>> addBatchSocks(@RequestBody MultipartFile file) throws IOException {
        List<Sock> importedSocks = exelService.importExelToDatabase(file);

        return ResponseEntity.ok(importedSocks);
    }

}
