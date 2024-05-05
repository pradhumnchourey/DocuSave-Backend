package com.docuSave.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.docuSave.demo.model.Docs;
import com.docuSave.demo.model.User;
import com.docuSave.demo.repo.DocRepository;
import com.docuSave.demo.repo.UserRepository;

@Service
public class DocService {

    @Autowired
    private DocRepository docRepository;

    @Autowired
    private UserRepository userRepository;

    public void savePdf(String originalFilename, String docType, String docUri, int userId, String category) {
        // switch (docType) {
        //     case "application/pdf":
        //         docType = "pdf";
        //         break;
        //     case "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet":
        //         docType = "excel";
        //         break;
        //     case "application/vnd.openxmlformats-officedocument.presentationml.presentation":
        //         docType = "ppt";
        //         break;
        //     case "application/vnd.openxmlformats-officedocument.wordprocessingml.document":
        //         docType = "word";
        //         break;
        //     default:
        //         // Handle the default case if needed
        //         break;
        // }
        Docs pdfFile = new Docs();
        pdfFile.setFileName(originalFilename);
        pdfFile.setDocUri(docUri);
        pdfFile.setDocType(docType);
        pdfFile.setCategory(category);

        Optional<User> user = userRepository.findById(userId);
        user.ifPresent(pdfFile::setUser);

        docRepository.save(pdfFile);
    }

    public Docs getPdfById(long pdfId) {
        return docRepository.findById(pdfId).orElse(null);
    }

    // public List<PdfFile> getPdfByUserId(int userId) {
    // return pdfRepository.findByUserId(userId);
    // }

    public boolean deleteDocument(long docId) {
        if (docRepository.existsById(docId)) {
            docRepository.deleteById(docId);
            return true;
        }
        return false;
    }

    public List<Docs> getPdfByUserIdAndCategory(int userId, String category) {
        return docRepository.findByUserIdAndCategory(userId, category);
    }

    public List<Docs> getPdfByUserIdAndCategoryAndType(int userId, String category, String type) {
        return docRepository.findByUserIdAndCategoryAndType(userId, category, type);
    }
}