package com.pranati.promptlibrary.service;

import com.pranati.promptlibrary.entity.Prompt;
import com.pranati.promptlibrary.entity.User;
import com.pranati.promptlibrary.repository.PromptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromptService {

    @Autowired
    private PromptRepository promptRepository;

    // ==========================
    // Save Prompt
    // ==========================

    public Prompt savePrompt(Prompt prompt) {
        return promptRepository.save(prompt);
    }

    // ==========================
    // View All
    // ==========================

    public List<Prompt> getAllPrompts(User user) {
        return promptRepository.findByUser(user);
    }

    // ==========================
    // Pagination
    // ==========================

    public Page<Prompt> getPromptsPage(User user, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        return promptRepository.findByUser(user, pageable);

    }

    // ==========================
    // Search
    // ==========================

    public List<Prompt> searchPrompts(User user, String keyword) {

        return promptRepository
                .findByUserAndTitleContainingIgnoreCaseOrUserAndContentContainingIgnoreCase(
                        user,
                        keyword,
                        user,
                        keyword
                );

    }

    // ==========================
    // Category
    // ==========================

    public List<Prompt> getPromptsByCategory(User user,
                                             String category) {

        return promptRepository.findByUserAndCategory(user, category);

    }

    // ==========================
    // Get Prompt
    // ==========================

    public Prompt getPromptById(Long id) {

        return promptRepository.findById(id).orElse(null);

    }

    // ==========================
    // Delete
    // ==========================

    public void deletePrompt(Long id) {

        promptRepository.deleteById(id);

    }

    // ==========================
    // Dashboard Statistics
    // ==========================

    public long getTotalPrompts(User user) {
        return promptRepository.countByUser(user);
    }

    public long getProgrammingCount(User user) {
        return promptRepository.countByUserAndCategory(user, "Programming");
    }

    public long getSpringBootCount(User user) {
        return promptRepository.countByUserAndCategory(user, "Spring Boot");
    }

    public long getLibraryCount(User user) {
        return promptRepository.countByUserAndCategory(user, "Library");
    }

    public long getDatabaseCount(User user) {
        return promptRepository.countByUserAndCategory(user, "Database");
    }

    public long getAICount(User user) {
        return promptRepository.countByUserAndCategory(user, "AI");
    }

    public long getCareerCount(User user) {
        return promptRepository.countByUserAndCategory(user, "Career");
    }

    // ==========================
    // Favorites
    // ==========================

    public List<Prompt> getFavoritePrompts(User user) {
        return promptRepository.findByUserAndFavoriteTrue(user);
    }

    public long getFavoriteCount(User user) {
        return promptRepository.countByUserAndFavoriteTrue(user);
    }

    public void toggleFavorite(Long id) {

        Prompt prompt = promptRepository.findById(id).orElse(null);

        if (prompt != null) {

            prompt.setFavorite(!prompt.isFavorite());

            promptRepository.save(prompt);

        }

    }

    // ==========================
    // Sorting
    // ==========================

    public List<Prompt> sortPrompts(User user, String sort) {

        switch (sort) {

            case "titleAsc":
                return promptRepository.findByUserOrderByTitleAsc(user);

            case "titleDesc":
                return promptRepository.findByUserOrderByTitleDesc(user);

            case "oldest":
                return promptRepository.findByUserOrderByIdAsc(user);

            case "newest":
                return promptRepository.findByUserOrderByIdDesc(user);

            default:
                return promptRepository.findByUser(user);
        }

    }

    // ==========================
    // PDF Export
    // ==========================

    public List<Prompt> exportPrompts(User user) {

        return promptRepository.findByUser(user);

    }

}