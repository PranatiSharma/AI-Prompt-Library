package com.pranati.promptlibrary.controller;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import com.pranati.promptlibrary.CustomUserDetails;
import com.pranati.promptlibrary.entity.Prompt;
import com.pranati.promptlibrary.entity.User;
import com.pranati.promptlibrary.service.PromptService;

import jakarta.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/prompts")
public class PromptController {

    @Autowired
    private PromptService promptService;

    // ==========================
    // Add Prompt
    // ==========================

    @GetMapping("/add")
    public String showAddPromptPage(Model model) {

        model.addAttribute("prompt", new Prompt());

        return "add-prompt";
    }

    // ==========================
    // Save Prompt
    // ==========================

    @PostMapping("/save")
    public String savePrompt(@ModelAttribute Prompt prompt) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        User user = userDetails.getUser();

        prompt.setUser(user);

        promptService.savePrompt(prompt);

        return "redirect:/prompts";
    }

    // ==========================
    // View All Prompts
    // ==========================

    @GetMapping
    public String viewAllPrompts(Model model) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        User user = userDetails.getUser();

        model.addAttribute("prompts",
                promptService.getAllPrompts(user));

        return "view-prompts";
    }

    // ==========================
    // Sort
    // ==========================

    @GetMapping("/sort")
    public String sortPrompts(@RequestParam String sort,
                              Model model) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        User user = userDetails.getUser();

        model.addAttribute("prompts",
                promptService.sortPrompts(user, sort));

        return "view-prompts";
    }

    // ==========================
    // Search
    // ==========================

    @GetMapping("/search")
    public String searchPrompts(@RequestParam("keyword") String keyword,
                                Model model) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        User user = userDetails.getUser();

        model.addAttribute("prompts",
                promptService.searchPrompts(user, keyword));

        model.addAttribute("keyword", keyword);

        return "view-prompts";
    }

    // ==========================
    // Category
    // ==========================

    @GetMapping("/category")
    public String filterByCategory(@RequestParam("category") String category,
                                   Model model) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        User user = userDetails.getUser();

        model.addAttribute("prompts",
                promptService.getPromptsByCategory(user, category));

        model.addAttribute("selectedCategory", category);

        return "view-prompts";
    }

    // ==========================
    // Delete
    // ==========================

    @GetMapping("/delete/{id}")
    public String deletePrompt(@PathVariable Long id) {

        promptService.deletePrompt(id);

        return "redirect:/prompts";
    }

    // ==========================
    // Edit
    // ==========================

    @GetMapping("/edit/{id}")
    public String showEditPage(@PathVariable Long id,
                               Model model) {

        Prompt prompt = promptService.getPromptById(id);

        model.addAttribute("prompt", prompt);

        return "edit-prompt";
    }

    @PostMapping("/update")
    public String updatePrompt(@ModelAttribute Prompt prompt) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        User user = userDetails.getUser();

        prompt.setUser(user);

        promptService.savePrompt(prompt);

        return "redirect:/prompts";
    }

    // ==========================
    // View Prompt
    // ==========================

    @GetMapping("/view/{id}")
    public String viewPrompt(@PathVariable Long id,
                             Model model) {

        Prompt prompt = promptService.getPromptById(id);

        model.addAttribute("prompt", prompt);

        return "view-prompt";
    }

    // ==========================
    // Favorites
    // ==========================

    @GetMapping("/favorite/{id}")
    public String toggleFavorite(@PathVariable Long id) {

        promptService.toggleFavorite(id);

        return "redirect:/prompts";
    }

    @GetMapping("/favorites")
    public String favoritePrompts(Model model) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        User user = userDetails.getUser();

        model.addAttribute("prompts",
                promptService.getFavoritePrompts(user));

        return "view-prompts";
    }
    @GetMapping("/export/pdf")
    public void exportPDF(HttpServletResponse response)
            throws DocumentException, IOException {

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition",
                "attachment; filename=AI_Prompts.pdf");

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        User user = userDetails.getUser();

        List<Prompt> prompts =
                promptService.getAllPrompts(user);

        Document document = new Document();

        PdfWriter.getInstance(document,
                response.getOutputStream());

        document.open();

        Font titleFont =
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);

        document.add(new Paragraph("AI Prompt Library", titleFont));
        document.add(new Paragraph(" "));

        for (Prompt prompt : prompts) {

            document.add(new Paragraph("Title : " + prompt.getTitle()));
            document.add(new Paragraph("Category : " + prompt.getCategory()));
            document.add(new Paragraph("Content : " + prompt.getContent()));
            document.add(new Paragraph("Favorite : " + (prompt.isFavorite() ? "Yes" : "No")));
            document.add(new Paragraph("------------------------------------------------"));
        }

        document.close();
    }

    // ==========================
    // Export Excel
    // ==========================

    @GetMapping("/export/excel")
    public void exportExcel(HttpServletResponse response) throws IOException {

        response.setContentType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        response.setHeader(
                "Content-Disposition",
                "attachment; filename=AI_Prompts.xlsx");

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        User user = userDetails.getUser();

        List<Prompt> prompts =
                promptService.getAllPrompts(user);

        XSSFWorkbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("Prompts");

        Row header = sheet.createRow(0);

        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Title");
        header.createCell(2).setCellValue("Category");
        header.createCell(3).setCellValue("Content");
        header.createCell(4).setCellValue("Favorite");

        int rowNum = 1;

        for (Prompt prompt : prompts) {

            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(prompt.getId());

            row.createCell(1).setCellValue(prompt.getTitle());

            row.createCell(2).setCellValue(prompt.getCategory());

            row.createCell(3).setCellValue(prompt.getContent());

            row.createCell(4).setCellValue(prompt.isFavorite() ? "Yes" : "No");
        }

        for (int i = 0; i < 5; i++) {
            sheet.autoSizeColumn(i);
        }

        workbook.write(response.getOutputStream());

        workbook.close();
    }

}


