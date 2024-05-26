package com.dailycodework.sbend2endapplication.Controllers;

import com.dailycodework.sbend2endapplication.entities.CharityAction;
import com.dailycodework.sbend2endapplication.entities.Donation;
import com.dailycodework.sbend2endapplication.services.CharityActionService;
import com.dailycodework.sbend2endapplication.services.DonationService;
import com.dailycodework.sbend2endapplication.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/donations")
public class DonationController {

    @Autowired
    private final DonationService donationService;

    @Autowired
    private final UserService userService; // Assurez-vous que ce service est disponible

    @Autowired
    private final CharityActionService charityActionService; // Assurez-vous que ce service est disponible

    public DonationController(DonationService donationService, UserService userService, CharityActionService charityActionService) {
        this.donationService = donationService;
        this.userService = userService;
        this.charityActionService = charityActionService;
    }

    @GetMapping
    public String getAllDonations(Model model) {
        List<Donation> donations = donationService.getAllDonations();
        model.addAttribute("donations", donations);
        model.addAttribute("donation", new Donation());
        model.addAttribute("users", userService.getAllUsers()); // Ajoutez cette ligne
        model.addAttribute("charityActions", charityActionService.getAllCharityActions()); // Ajoutez cette ligne

        return "Donation/list";
    }

    @GetMapping("/{id}")
    public String getDonationById(@PathVariable Long id, Model model) {
        Optional<Donation> donation = donationService.getDonationById(id);
        model.addAttribute("donation", donation);
        return "Donation/view";
    }

    @GetMapping("/create")
    public String createDonationForm(Model model) {
        model.addAttribute("donation", new Donation());
        model.addAttribute("users", userService.getAllUsers()); // Ajoutez cette ligne
        model.addAttribute("charityActions", charityActionService.getAllCharityActions()); // Ajoutez cette ligne
        return "Donation/list";
    }

    @PostMapping("/add")
    public String createDonation(@RequestParam Long userId, @RequestParam Long charityActionId, @ModelAttribute Donation donation) {
        donationService.saveDonationWithUserAndAction(donation, userId, charityActionId);
        return "redirect:/donations";
    }

    @GetMapping("/edit/{id}")
    public String updateCharityActionForm(@PathVariable Long id, Model model) {
        Optional<Donation> donation = donationService.getDonationById(id);
        model.addAttribute("donation", donation.get());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("charityActions", charityActionService.getAllCharityActions());
        return "Donation/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateCharityAction(@PathVariable Long id, @ModelAttribute Donation donation) {
        donationService.updateDonation(id, donation);
        return "redirect:/donations";
    }

    @GetMapping("/delete")
    public String deleteDonation(@RequestParam Long id, RedirectAttributes ra) {
        try {
            donationService.deleteDonation(id);
            ra.addFlashAttribute("deleted", "The charity action has been deleted successfully.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Error occurred while deleting the charity action.");
        }
        return "redirect:/donations";
    }
}
