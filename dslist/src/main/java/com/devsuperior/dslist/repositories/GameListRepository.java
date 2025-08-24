package com.devsuperior.dslist.repositories;

import com.devsuperior.dslist.entities.GameList;

import org.springframework.data.jpa.repository.JpaRepository;


//Faz consulta com o banco de dados
public interface GameListRepository extends JpaRepository<GameList, Long> {






}
