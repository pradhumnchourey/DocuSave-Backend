package com.docuSave.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.docuSave.demo.model.Category;
import com.docuSave.demo.model.Docs;
import com.docuSave.demo.model.User;
import com.docuSave.demo.repo.DocRepository;

@Service
public class DocService {

    @Autowired
    private DocRepository docRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    public void savePdf(String originalFilename, String docType, String docUri, int userId, int categoryId) {
        Docs pdfFile = new Docs();
        pdfFile.setDocName(originalFilename);
        pdfFile.setDocUri(docUri);
        pdfFile.setDocType(docType);

        Category category = categoryService.getCategoryById(categoryId);
        if (category != null)
            pdfFile.setCategory(category);
        else System.out.println("Category not available:" + categoryId);

        User user = userService.getUserById(userId);
        if (user != null)
            pdfFile.setUser(user);
        else System.out.println("User not found:" + userId);

        // Optional<User> user = userRepository.findById(userId);
        // user.ifPresent(pdfFile::setUser);

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

    public List<Docs> getPdfByUserIdAndCategory(int userId, int categoryId) {
        return docRepository.findByUserIdAndCategory(userId, categoryId);
    }

    public List<Docs> getPdfByUserIdAndCategoryAndType(int userId, int categoryId, String type) {
        return docRepository.findByUserIdAndCategoryAndType(userId, categoryId, type);
    }
}