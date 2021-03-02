package com.javamentor.repository.mailTemplate;

import com.javamentor.model.templates.MailTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MailTemplateRepository extends JpaRepository<MailTemplate, Long> {
    @Query("SELECT m.htmlBody FROM MailTemplate m WHERE m.name = :name")
    String getHtmlBodyByName(String name);

}
