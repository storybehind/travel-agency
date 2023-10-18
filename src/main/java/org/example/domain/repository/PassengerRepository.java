package org.example.domain.repository;

import org.example.domain.entity.Passenger;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerRepository extends BaseRepository<Passenger, Integer> {
}
