package com.game.kalah.repository;

import com.game.kalah.model.Kalah;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository to store game details
 * @author Renuka Naik
 */
public interface KalahRepository extends CrudRepository<Kalah, Integer> {

}
