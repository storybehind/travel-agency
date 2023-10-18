package org.example.domain.repository;

import org.example.domain.entity.Activity;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends BaseRepository<Activity, Integer> {
}
