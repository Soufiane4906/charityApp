package com.dailycodework.sbend2endapplication.Repositories;

import com.dailycodework.sbend2endapplication.entities.CharityAction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CharityActionRepository extends JpaRepository<CharityAction, Long> {
    List<CharityAction> findByTitleContainingIgnoreCase(String keyword);
}