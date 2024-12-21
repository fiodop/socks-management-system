package com.socksManagementSystem.service;

import com.socksManagementSystem.common.exceptions.IllegalOperatorException;
import com.socksManagementSystem.common.exceptions.InsufficientSockQuantityException;
import com.socksManagementSystem.common.exceptions.SockNotFoundException;
import com.socksManagementSystem.dto.SockDto;
import com.socksManagementSystem.model.Sock;
import com.socksManagementSystem.repository.SockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;


class SockServiceTest {

    @Mock
    private SockRepository sockRepository;

    @InjectMocks
    private SockService sockService;

    private SockDto sockDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sockDto = new SockDto("red", 50, 10);
    }

    @Test
    void testAddSocksNewSock() {
        when(sockRepository.findSocksByColorAndCottonPart(sockDto.getColor(), sockDto.getCottonPart()))
                .thenReturn(null);

        sockService.addSocks(sockDto);

        verify(sockRepository, times(1)).save(any(Sock.class));
    }

    @Test
    void testAddSocksExistingSock() {
        Sock existingSock = new Sock();
        existingSock.setColor(sockDto.getColor());
        existingSock.setCottonPart(sockDto.getCottonPart());
        existingSock.setQuantity(5);

        when(sockRepository.findSocksByColorAndCottonPart(sockDto.getColor(), sockDto.getCottonPart()))
                .thenReturn(existingSock);

        sockService.addSocks(sockDto);

        assertEquals(15, existingSock.getQuantity());
        verify(sockRepository, times(1)).save(existingSock);
    }

    @Test
    void testRemoveSocksSuccess() {
        Sock sock = new Sock();
        sock.setColor(sockDto.getColor());
        sock.setCottonPart(sockDto.getCottonPart());
        sock.setQuantity(20);

        when(sockRepository.findSocksByColorAndCottonPart(sockDto.getColor(), sockDto.getCottonPart()))
                .thenReturn(sock);

        sockService.removeSocks(sockDto);

        assertEquals(10, sock.getQuantity());
        verify(sockRepository, times(1)).save(sock);
    }

    @Test
    void testRemoveSocksInsufficientQuantity() {
        Sock sock = new Sock();
        sock.setColor(sockDto.getColor());
        sock.setCottonPart(sockDto.getCottonPart());
        sock.setQuantity(5);

        when(sockRepository.findSocksByColorAndCottonPart(sockDto.getColor(), sockDto.getCottonPart()))
                .thenReturn(sock);

        SockDto insufficientSockDto = new SockDto(sockDto.getColor(), sockDto.getCottonPart(), 10);

        assertThrows(InsufficientSockQuantityException.class, () -> {
            sockService.removeSocks(insufficientSockDto);
        });
    }

    @Test
    void testFilterSocksMoreThan() {
        when(sockRepository.getSocksByColorAndCottonPartGreaterThanEqual("red", 50))
                .thenReturn(List.of(new Sock()));

        var result = sockService.filterSocks("red", "moreThan", 50);

        assertNotNull(result);
        verify(sockRepository, times(1)).getSocksByColorAndCottonPartGreaterThanEqual("red", 50);
    }

    @Test
    void testFilterSocksLessThan() {
        when(sockRepository.getSocksByColorAndCottonPartIsLessThanEqual("red", 50))
                .thenReturn(List.of(new Sock()));

        var result = sockService.filterSocks("red", "lessThan", 50);

        assertNotNull(result);
        verify(sockRepository, times(1)).getSocksByColorAndCottonPartIsLessThanEqual("red", 50);
    }

    @Test
    void testFilterSocksEqual() {
        when(sockRepository.getSocksByColorAndCottonPart("red", 50))
                .thenReturn(List.of(new Sock()));

        var result = sockService.filterSocks("red", "equal", 50);

        assertNotNull(result);
        verify(sockRepository, times(1)).getSocksByColorAndCottonPart("red", 50);
    }

    @Test
    void testFilterSocksIllegalOperator() {
        assertThrows(IllegalOperatorException.class, () -> {
            sockService.filterSocks("red", "invalidOperator", 50);
        });
    }

    @Test
    void testUpdateSockSuccess() {
        Sock existingSock = new Sock();
        existingSock.setId(1);
        existingSock.setColor("red");
        existingSock.setCottonPart(50);
        existingSock.setQuantity(10);

        SockDto updateDto = new SockDto("blue", 60, 20);

        when(sockRepository.getSocksById(1)).thenReturn(existingSock);

        Sock updatedSock = sockService.updateSock(1, updateDto);

        assertEquals("blue", updatedSock.getColor());
        assertEquals(60, updatedSock.getCottonPart());
        assertEquals(20, updatedSock.getQuantity());
        verify(sockRepository, times(1)).save(updatedSock);
    }

    @Test
    void testUpdateSockNotFound() {
        SockDto updateDto = new SockDto("blue", 60, 20);

        when(sockRepository.getSocksById(1)).thenReturn(null);

        assertThrows(SockNotFoundException.class, () -> {
            sockService.updateSock(1, updateDto);
        });
    }
}
