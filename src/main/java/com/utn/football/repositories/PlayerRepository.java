package com.utn.football.repositories;


import com.utn.football.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {

    boolean findByNameAndLastname();
}
