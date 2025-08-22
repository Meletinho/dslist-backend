package com.devsuperior.dslist.seervicies;


import com.devsuperior.dslist.dto.GameMinDTO;
import com.devsuperior.dslist.entities.Game;
import com.devsuperior.dslist.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
//Chama o repository no BD, devolvendo o DTO
public class GameService {


    @Autowired
    private GameRepository gameRepository;


    public List<GameMinDTO> findAll() {
           //Faz uma busca no banco de dados pra listar todos os jogos no BD
           List<Game> result =  gameRepository.findAll();
           List<GameMinDTO> dto = result.stream().map(x -> new GameMinDTO(x)).toList();

           return dto;
    }


}
