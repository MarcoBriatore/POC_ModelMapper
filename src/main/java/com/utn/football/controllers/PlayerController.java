package com.utn.football.controllers;



import com.utn.football.mappers.PlayerConverter;
import com.utn.football.models.Player;
import com.utn.football.repositories.PlayerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequestMapping("/player")
@RestController
public class PlayerController {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("")
    public ResponseEntity<String> addPlayer(@RequestBody Player p) {
        playerRepository.save(p);
        return new ResponseEntity<>("Jugador creado",HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<List<PlayerConverter>> getPlayers() {

        List<PlayerConverter> mappedPlayer = new ArrayList<>();

        List<Player> allPlayers = playerRepository.findAll();

        for (Player t: allPlayers) {
            mappedPlayer.add(modelMapper.map(t, PlayerConverter.class));
        }

        return new ResponseEntity<>(mappedPlayer,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerConverter> getPlayer(@PathVariable("id") final Integer id) {

        Player p = playerRepository.findById(id).orElse(null);

        if(p!=null)
            return new ResponseEntity<PlayerConverter>(modelMapper.map(p, PlayerConverter.class),HttpStatus.OK);
        else
            return new ResponseEntity<PlayerConverter>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateTeam(@RequestBody Player t){
        try {
            if (!playerRepository.findByNameAndLastname()) {
                throw new HttpClientErrorException(HttpStatus.CONFLICT);
            }
            playerRepository.save(t);
            return new ResponseEntity<>("Jugador actualizado", HttpStatus.CREATED);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<String>("Jugador NO actualizado", HttpStatus.NOT_MODIFIED);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTeam(@PathVariable("id") final Integer id) {
        try {
            if (Objects.isNull(playerRepository.findById(id))) {
                throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
            }
            playerRepository.deleteById(id);
            return new ResponseEntity<>("Jugador borrado", HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<String>("EL jugador NO FUE borrado", HttpStatus.NOT_FOUND);
        }
    }

}
