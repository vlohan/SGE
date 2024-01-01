package com.ifrn.sge.repositories;

import com.ifrn.sge.models.Park;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkRepository extends JpaRepository<Park, String> {
}
