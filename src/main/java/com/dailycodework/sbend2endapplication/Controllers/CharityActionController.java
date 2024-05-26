package com.dailycodework.sbend2endapplication.Controllers;

import com.dailycodework.sbend2endapplication.entities.CharityAction;
import com.dailycodework.sbend2endapplication.services.CharityActionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/charityActions")
public class CharityActionController {
    private final CharityActionService charityActionService;

    public CharityActionController(CharityActionService charityActionService) {
        this.charityActionService = charityActionService;
    }

    @GetMapping
    public String getAllCharityActions(Model model) {
        model.addAttribute("charityActions", charityActionService.getAllCharityActions());
        return "charityActions/list";
    }

    @GetMapping("/{id}")
    public String getCharityActionById(@PathVariable Long id, Model model) {
        model.addAttribute("charityAction", charityActionService.getCharityActionById(id));
        return "charityActions/view";
    }

    @PostMapping("/add")
    public String createCharityAction(@ModelAttribute CharityAction charityAction) {
        charityActionService.createCharityAction(charityAction);
        return "redirect:/charityActions";
    }

    @GetMapping("/edit/{id}")
    public String updateCharityActionForm(@PathVariable Long id, Model model) {
        Optional<CharityAction> charityAction =charityActionService.getCharityActionById(id);
        model.addAttribute("charityAction",charityAction.get() );
        return "charityActions/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateCharityAction(@PathVariable Long id, @ModelAttribute CharityAction charityAction) {
        charityActionService.updateCharityAction(id, charityAction);
        return "redirect:/charityActions";
    }

    @GetMapping("/delete")
    public String deleteCharityAction(@RequestParam Long id, RedirectAttributes ra){
        try{
            charityActionService.deleteCharityAction(id);
            ra.addFlashAttribute("deleted", "The charity action has been deleted successfully.");
        } catch (Exception e){
            ra.addFlashAttribute("error", "Error occurred while deleting the charity action.");
        }
        return "redirect:/charityActions";
    }


    @GetMapping("/search")
    public String searchCharityActions(@RequestParam String keyword, Model model) {
        List<CharityAction> searchResults = charityActionService.searchCharityActions(keyword);
        model.addAttribute("charityActions", searchResults);
        return "charityActions/search";
    }
}
