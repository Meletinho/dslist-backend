package com.devsuperior.dslist.servicies;


import com.devsuperior.dslist.dto.GameDTO;
import com.devsuperior.dslist.dto.GameMinDTO;
import com.devsuperior.dslist.entities.Game;
import com.devsuperior.dslist.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

//Chama o repository no BD, devolvendo o DTO
@Service
public class GameService {


    @Autowired
    private GameRepository gameRepository;

    //Assegura que não irei bloquear meu banco p escrita
    @Transactional(readOnly = true)
    public GameDTO findById(Long id) {
        Game result = gameRepository.findById(id).get();
        GameDTO dto = new GameDTO(result);
        return dto;
    }


    public List<GameMinDTO> findAll() {
           //Faz uma busca no banco de dados pra listar todos os jogos no BD
        List<Game> result = gameRepository.findAll();
        return result.stream().map(GameMinDTO::new).collect(Collectors.toList());


    }


}
