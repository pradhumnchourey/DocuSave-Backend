package com.docuSave.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocList {
    private long fileId;
    private String fileName;
    private String docType;
}
