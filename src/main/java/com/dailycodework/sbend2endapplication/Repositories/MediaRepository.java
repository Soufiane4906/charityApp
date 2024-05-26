package com.dailycodework.sbend2endapplication.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dailycodework.sbend2endapplication.entities.Media;

import java.util.List;

public interface MediaRepository extends JpaRepository<Media, Long> {
    List<Media> findByType(Media.MediaType type);
    // Add any additional query methods you need
}