package com.socksManagementSystem.service;

import com.socksManagementSystem.common.exceptions.IllegalOperatorException;
import com.socksManagementSystem.common.exceptions.InsufficientSockQuantityException;
import com.socksManagementSystem.common.exceptions.SockNotFoundException;
import com.socksManagementSystem.dto.SockDto;
import com.socksManagementSystem.model.Sock;
import com.socksManagementSystem.repository.SockRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SockService {
    private SockRepository sockRepository;
    public void addSocks(SockDto sockRequest) {
        Sock sock = sockRepository.findSocksByColorAndCottonPart(sockRequest.getColor(), sockRequest.getCottonPart());

        if(sock == null) {
            Sock sock1 = Sock.builder()
                    .color(sockRequest.getColor())
                    .cottonPart(sockRequest.getCottonPart())
                    .quantity(sockRequest.getQuantity())
                    .build();
            sockRepository.save(sock1);
        } else {
            sock.setQuantity(sock.getQuantity() + sockRequest.getQuantity());
            sockRepository.save(sock);
        }


    }

    public void removeSocks(SockDto sockRequest) {
        Sock sockToRemove = sockRepository.findSocksByColorAndCottonPart(sockRequest.getColor(),
                sockRequest.getCottonPart());

        if(sockToRemove == null) {
            throw new SockNotFoundException("Sock with color " + sockRequest.getColor() + " with cotton part = "
                    + sockRequest.getCottonPart() + " not found");
        } else {
            if(sockToRemove.getQuantity() < sockRequest.getQuantity()) {
                throw new InsufficientSockQuantityException("Not enough socks in stock to remove");
            } else {
                sockToRemove.setQuantity(sockToRemove.getQuantity() - sockRequest.getQuantity());
            }
        }
    }

    public List<Sock> filterSocks(String color, String operator, int cottonPart) {
        switch (operator){
            case "moreThan" :
                return sockRepository.getSocksByColorAndCottonPartGreaterThanEqual(color, cottonPart);

            case "lessThan" :
                return sockRepository.getSocksByColorAndCottonPartIsLessThanEqual(color, cottonPart);

            case "equal" :
                return sockRepository.getSocksByColorAndCottonPart(color, cottonPart);

            default:
                throw new IllegalOperatorException("Illegal operator: " + operator);
        }
    }

    public Sock updateSock(int sockId, SockDto sockRequest) {
        Sock sockToUpdate = sockRepository.getSocksById(sockId);

        if(sockToUpdate == null) {
            throw new SockNotFoundException("Sock with id " + sockId + " not found");
        } else {
            sockToUpdate.setColor(sockRequest.getColor());
            sockToUpdate.setCottonPart(sockRequest.getCottonPart());
            sockToUpdate.setQuantity(sockRequest.getQuantity());

            sockRepository.save(sockToUpdate);
        }
        return sockToUpdate;
    }
}
