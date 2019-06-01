package com.utn.football.controllers;

import com.utn.football.mappers.TeamConverter;
import com.utn.football.models.Team;
import com.utn.football.repositories.TeamRepository;
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

@RequestMapping("/team")
@RestController
public class TeamController {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("")
    public ResponseEntity<String> addTeam(@RequestBody Team t) {
        try {
            if (teamRepository.findByName()) {
                throw new HttpClientErrorException(HttpStatus.CONFLICT);
            }
            teamRepository.save(t);
            return new ResponseEntity<>("Equipo creado", HttpStatus.CREATED);
        }
        catch (HttpClientErrorException e)
        {
            return new ResponseEntity<>("Equipo creado", HttpStatus.CONFLICT);
        }
    }

    @GetMapping("")
    public ResponseEntity<List<TeamConverter>> getTeams() {

        List<TeamConverter> mappedTeam = new ArrayList<>();
        List<Team> allTeam = teamRepository.findAll();

        allTeam.forEach(t -> mappedTeam.add(modelMapper.map(t,TeamConverter.class)));

        return new ResponseEntity<>(mappedTeam,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamConverter> getTeam(@PathVariable("id") final Integer id) {


        Team t = teamRepository.findById(id).orElse(null);

        if(t!=null)
            return new ResponseEntity<TeamConverter>(modelMapper.map(t, TeamConverter.class),HttpStatus.OK);
        else
            return new ResponseEntity<TeamConverter>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateTeam(@RequestBody Team t){
        try {
            if (!teamRepository.findByName()) {
                throw new HttpClientErrorException(HttpStatus.CONFLICT);
            }
            teamRepository.save(t);
            return new ResponseEntity<>("Equipo actualizado", HttpStatus.CREATED);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<String>("Equipo NO actualizado", HttpStatus.NOT_MODIFIED);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTeam(@PathVariable("id") final Integer id) {
        try {
            if (Objects.isNull(teamRepository.findById(id))) {
                throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
            }
            teamRepository.deleteById(id);
            return new ResponseEntity<>("Equipo borrado", HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<String>("EL Equipo NO FUE borrado", HttpStatus.NOT_FOUND);
        }
    }
}
