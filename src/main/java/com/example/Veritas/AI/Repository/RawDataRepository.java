package com.example.Veritas.AI.Repository;

import com.example.Veritas.AI.modular.RawData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

    public interface RawDataRepository extends JpaRepository<RawData,Long>{

    List<RawData> findBySessionId(String sessionId);
}

