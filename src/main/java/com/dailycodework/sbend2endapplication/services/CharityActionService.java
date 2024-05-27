package com.dailycodework.sbend2endapplication.services;

import com.dailycodework.sbend2endapplication.Repositories.CharityActionRepository;
import com.dailycodework.sbend2endapplication.entities.CharityAction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CharityActionService {
    private final CharityActionRepository charityActionRepository;

    public CharityActionService(CharityActionRepository charityActionRepository) {
        this.charityActionRepository = charityActionRepository;
    }

    public Page<CharityAction> getProducts(int page) {
        return charityActionRepository
                .findAll(PageRequest.of(page,10));
    }

    public List<CharityAction> getAllCharityActions() {
        return charityActionRepository.findAll();
    }

    public Optional<CharityAction> getCharityActionById(Long id) {
        return charityActionRepository.findById(id);
    }

    public CharityAction createCharityAction(CharityAction charityAction) {
        return charityActionRepository.save(charityAction);
    }

    public CharityAction updateCharityAction(Long id, CharityAction charityAction) {
        // Ensure the charity action to update exists
        CharityAction existingCharityAction = charityActionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CharityAction not found with id: " + id));

        // Update the existing charity action with the new data
        existingCharityAction.setTitle(charityAction.getTitle());
        existingCharityAction.setDescription(charityAction.getDescription());
        existingCharityAction.setDate(charityAction.getDate());
        existingCharityAction.setLocation(charityAction.getLocation());
        existingCharityAction.setFundraisingGoal(charityAction.getFundraisingGoal());
        existingCharityAction.setAmountRaised(charityAction.getAmountRaised());
        // Update other fields as needed

        // Save and return the updated charity action
        return charityActionRepository.save(existingCharityAction);
    }

    public void deleteCharityAction(Long id) {
        if (charityActionRepository.existsById(id)) {
            charityActionRepository.deleteById(id);
        } else {
            throw new RuntimeException("CharityAction not found with id: " + id);
        }
    }

    public List<CharityAction> searchCharityActions(String keyword) {
        return charityActionRepository.findByTitleContainingIgnoreCase(keyword);
    }
}