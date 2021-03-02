package com.javamentor.model.templates;

import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.context.annotation.Primary;

import javax.persistence.*;

@Entity
@Table(name = "html_templates")
public class MailTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "html_body", columnDefinition = "text")
    private String htmlBody;

    @Column(name = "name")
    private String name;

    public MailTemplate() {
    }

    public MailTemplate(String htmlBody) {
        this.htmlBody = htmlBody;
    }

    public MailTemplate(String htmlBody, String name) {
        this.htmlBody = htmlBody;
        this.name = name;
    }

    public MailTemplate(Long id, String htmlBody, String name) {
        this.id = id;
        this.htmlBody = htmlBody;
        this.name = name;
    }

    public MailTemplate(Long id, String htmlBody) {
        this.id = id;
        this.htmlBody = htmlBody;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHtmlBody() {
        return htmlBody;
    }

    public void setHtmlBody(String htmlBody) {
        this.htmlBody = htmlBody;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
