package com.docuSave.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocList {
    private long docId;
    private String docName;
    private String docType;
    private String docUri;
    private int userId;
    private String category;

    public DocList(long docId, String docName, String docType, String docUri, String category) {
        this.docId = docId;
        this.docName = docName;
        this.docType = docType;
        this.docUri = docUri;
        this.category=category;
    }
}