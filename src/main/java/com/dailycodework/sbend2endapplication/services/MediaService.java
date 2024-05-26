package com.dailycodework.sbend2endapplication.services;

import com.dailycodework.sbend2endapplication.entities.Media;
import com.dailycodework.sbend2endapplication.Repositories.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MediaService {
    private final MediaRepository mediaRepository;

    @Autowired
    public MediaService(MediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
    }

    public List<Media> getAllMedia() {
        return mediaRepository.findAll();
    }

    public Media getMediaById(Long id) {
        return mediaRepository.findById(id).orElse(null);
    }

    public Media saveMedia(Media media) {
        return mediaRepository.save(media);
    }

    public void deleteMedia(Long id) {
        mediaRepository.deleteById(id);
    }

    public List<Media> getMediaByType(Media.MediaType type) {
        return mediaRepository.findByType(type);
    }
}