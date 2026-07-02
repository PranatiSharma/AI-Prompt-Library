package com.pranati.promptlibrary.repository;

import com.pranati.promptlibrary.entity.Prompt;
import com.pranati.promptlibrary.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromptRepository extends JpaRepository<Prompt, Long> {

    // View All Prompts
    List<Prompt> findByUser(User user);

    Page<Prompt> findByUser(User user, Pageable pageable);

    // Search
    List<Prompt> findByUserAndTitleContainingIgnoreCaseOrUserAndContentContainingIgnoreCase(
            User user1,
            String title,
            User user2,
            String content
    );

    // Category
    List<Prompt> findByUserAndCategory(User user, String category);

    // Dashboard
    long countByUser(User user);

    long countByUserAndCategory(User user, String category);

    // Favorites
    List<Prompt> findByUserAndFavoriteTrue(User user);

    long countByUserAndFavoriteTrue(User user);

    // ===========================
    // SORTING
    // ===========================

    List<Prompt> findByUserOrderByTitleAsc(User user);

    List<Prompt> findByUserOrderByTitleDesc(User user);

    List<Prompt> findByUserOrderByIdAsc(User user);

    List<Prompt> findByUserOrderByIdDesc(User user);

}