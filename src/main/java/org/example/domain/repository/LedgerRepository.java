package org.example.domain.repository;

import org.example.domain.entity.Ledger;
import org.springframework.stereotype.Repository;

@Repository
public interface LedgerRepository extends BaseRepository<Ledger, Integer> {
}
