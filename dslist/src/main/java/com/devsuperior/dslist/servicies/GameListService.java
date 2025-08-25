package com.devsuperior.dslist.servicies;


import com.devsuperior.dslist.dto.GameListDTO;
import com.devsuperior.dslist.entities.GameList;
import com.devsuperior.dslist.projections.GameMinProjection;
import com.devsuperior.dslist.repositories.GameListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

//Chama o repository no BD, devolvendo o DTO
@Service
public class GameListService {


    @Autowired
    private GameListRepository gameListRepository;

    //Assegura que não irei bloquear meu banco p escrita
    @Transactional(readOnly = true)
    public List<GameListDTO> findAll() {
           //Faz uma busca no banco de dados pra listar todos os jogos no BD
        List<GameList> result = gameListRepository.findAll();
        return result.stream().map(GameListDTO::new).collect(Collectors.toList());

    }

    public void move(Long listId, int sourceIndex, int destinationIndex) {
        List<GameMinProjection> list = gameListRepository.searchByList(listId);
    }


}
