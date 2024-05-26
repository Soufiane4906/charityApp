package com.dailycodework.sbend2endapplication.services;

import com.dailycodework.sbend2endapplication.Repositories.CharityActionRepository;
import com.dailycodework.sbend2endapplication.entities.CharityAction;
import com.dailycodework.sbend2endapplication.entities.Donation;
import com.dailycodework.sbend2endapplication.Repositories.DonationRepository;
import com.dailycodework.sbend2endapplication.user.User;
import com.dailycodework.sbend2endapplication.user.UserRepository;
import com.dailycodework.sbend2endapplication.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@Service
public class DonationService {
    private final DonationRepository donationRepository;
    private final UserRepository userRepository; // Assurez-vous que ce dépôt est disponible
    private final CharityActionRepository charityActionRepository; // Assurez-vous que ce dépôt est disponible

    @Autowired
    public DonationService(DonationRepository donationRepository, UserRepository userRepository, CharityActionRepository charityActionRepository) {
        this.donationRepository = donationRepository;
        this.userRepository = userRepository;
        this.charityActionRepository = charityActionRepository;
    }

    public List<Donation> getAllDonations() {
        return donationRepository.findAll();
    }

    public Optional<Donation> getDonationById(Long id) {
        return donationRepository.findById(id);
    }

    public Donation saveDonation(Donation donation) {
        return donationRepository.save(donation);
    }

    public void deleteDonation(Long id) {
        donationRepository.deleteById(id);
    }

    public void saveDonationWithUserAndAction(Donation donation, Long userId, Long charityActionId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        CharityAction charityAction = charityActionRepository.findById(charityActionId).orElseThrow(() -> new RuntimeException("Charity Action not found"));
        donation.setUser(user);
        donation.setCharityAction(charityAction);
        donationRepository.save(donation);
    }

    public Donation updateDonation(Long id, Donation donation) {
        // Ensure the donation to update exists
        Donation existingDonation = donationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Donation not found with id: " + id));

        // Update the existing donation with the new data
        existingDonation.setAmount(donation.getAmount());
        existingDonation.setDate(donation.getDate());
        existingDonation.setUser(donation.getUser());
        existingDonation.setUser(donation.getUser());
        // Update other fields as needed

        // Save and return the updated donation
       return donationRepository.save(existingDonation);
    }
}
