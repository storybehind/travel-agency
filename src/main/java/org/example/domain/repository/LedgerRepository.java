package org.example.domain.repository;

import org.example.domain.entity.Ledger;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LedgerRepository extends BaseRepository<Ledger, Integer> {

    List<Ledger> findByPassengerNumber(int passengerNumber);
}
