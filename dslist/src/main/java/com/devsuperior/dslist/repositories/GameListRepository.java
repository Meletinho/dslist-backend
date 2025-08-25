package com.devsuperior.dslist.repositories;

import com.devsuperior.dslist.entities.GameList;

import com.devsuperior.dslist.projections.GameMinProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


//Faz consulta com o banco de dados
public interface GameListRepository extends JpaRepository<GameList, Long> {


    List<GameMinProjection> searchByList(Long listId);
}
