package com.docuSave.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_files")
public class Docs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long docId;

    @Column(nullable = false)
    private String docName;

    private String docType;

    private String docUri;

    @ManyToOne
    @JoinColumn(name="categoryId", nullable = false)
    private Category category;
    
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;
}