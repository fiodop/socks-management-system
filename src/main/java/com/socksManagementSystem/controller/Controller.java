package com.socksManagementSystem.controller;

import com.socksManagementSystem.dto.SockDto;
import com.socksManagementSystem.model.Sock;
import com.socksManagementSystem.service.ExelService;
import com.socksManagementSystem.service.SockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Tag(name = "Socks managment API", description = "Managing socks in storage")
@RestController
@RequestMapping("api/socks")
@AllArgsConstructor
public class Controller {
    private final SockService sockService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ExelService exelService;

    @Operation(summary = "Add socks", description = "Add sock to storage and create new sock row in table if this sock didn't exist before")
    @PostMapping("/income")
    public ResponseEntity<SockDto> addSocks(@RequestBody SockDto sockRequest) {
        sockService.addSocks(sockRequest);

        logger.info("Adding socks: {}", sockRequest.toString());
        return ResponseEntity.ok(sockRequest);
    }

    @Operation(summary = "Subtract socks", description = "subtract socks from storage and decrease quantity of socks in table")
    @PostMapping("/outcome")
    public ResponseEntity<SockDto> removeSocks(@RequestBody SockDto sockRequest) {
        sockService.removeSocks(sockRequest);

        logger.info("Removing socks: {}", sockRequest.toString());
        return ResponseEntity.ok(sockRequest);
    }

    @Operation(summary = "Filter socks", description = "filtering socks by color and cotton part with different operators")
    @GetMapping
    public ResponseEntity<List<Sock>> filterSocks(@RequestParam String color,
                                                  @RequestParam String operator,
                                                  @RequestParam int cottonPart
                                                  ) {
        List<Sock> filteredSockList = sockService.filterSocks(color, operator, cottonPart);

        logger.info("Filtering socks: {}", filteredSockList.toString());
        return ResponseEntity.ok(filteredSockList);
    }

    @Operation(summary = "Update info about socks", description = "updating info about socks in table")
    @PutMapping("/{id}")
    public ResponseEntity<Sock> updateSocks(@PathVariable int sockId, @RequestBody SockDto sockRequest) {
        Sock updatedSock = sockService.updateSock(sockId, sockRequest);

        logger.info("Updating sock: {}", updatedSock.toString());
        return ResponseEntity.ok(updatedSock);
    }

    @Operation(summary = "Parse .xls file", description = "parsing .xls file and add data to table")
    @PostMapping("/batch")
    public ResponseEntity<List<Sock>> addBatchSocks(@RequestBody MultipartFile file) throws IOException {
        List<Sock> importedSocks = exelService.importExelToDatabase(file);

        return ResponseEntity.ok(importedSocks);
    }

}
