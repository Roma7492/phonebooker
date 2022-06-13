package com.romanshilov.phoneBooker.repository;

import com.romanshilov.phoneBooker.domain.entity.FonoapiBackupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FonoapiBackupRepository extends JpaRepository<FonoapiBackupEntity, Long> {

    FonoapiBackupEntity findByPhoneName(final String phoneName);
}
