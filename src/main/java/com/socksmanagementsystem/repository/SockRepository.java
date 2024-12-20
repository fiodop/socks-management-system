package com.socksmanagementsystem.repository;

import com.socksmanagementsystem.model.Sock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping
public interface SockRepository extends JpaRepository<Sock, Integer> {
    Sock findSocksByColorAndCottonPart(String color, int cottonPart);

    List<Sock> getSocksByColorAndCottonPartGreaterThanEqual(String color, int cottonPart);

    List<Sock> getSocksByColorAndCottonPartIsLessThanEqual(String color, int cottonPartIsLessThan);

    List<Sock> getSocksByColorAndCottonPart(String color, int cottonPart);

    Sock getSocksById(int id);
}
