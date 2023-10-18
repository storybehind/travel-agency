package org.example.domain.repository;

import org.example.domain.entity.Ledger;
import org.example.domain.entity.Passenger;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LedgerRepository extends BaseRepository<Ledger, Integer> {

    List<Ledger> findByPassenger(Passenger passenger);
}
