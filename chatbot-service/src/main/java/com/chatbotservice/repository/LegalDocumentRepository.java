// src/main/java/com/chatbotservice/repository/LegalDocumentRepository.java
package com.chatbotservice.repository;

import com.chatbotservice.model.LegalDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LegalDocumentRepository extends JpaRepository<LegalDocument, String> {

    /**
     * Renvoie les 5 premiers documents dont la langue correspond
     * et dont le contenu (contentText) contient la requête (ignore la casse).
     */
    List<LegalDocument> findTop5ByLanguageAndContentTextContainingIgnoreCase(
            String language,
            String content
    );
}
