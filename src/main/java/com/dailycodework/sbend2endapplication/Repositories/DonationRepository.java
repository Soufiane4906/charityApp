package com.dailycodework.sbend2endapplication.Repositories;

import com.dailycodework.sbend2endapplication.entities.CharityAction;
import org.springframework.data.jpa.repository.JpaRepository;
import com.dailycodework.sbend2endapplication.entities.Donation;

import java.util.List;

public interface DonationRepository extends JpaRepository<Donation, Long> {
}