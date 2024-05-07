package com.docuSave.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.docuSave.demo.model.Docs;
import com.docuSave.demo.model.User;

public interface DocRepository extends JpaRepository<Docs, Long> {
    List<Docs> findByUser(User user);

    // @Query("SELECT pdf FROM PdfFile pdf WHERE pdf.user.userId = :userId")
    // List<PdfFile> findByUserId(int userId);

    @Query("SELECT doc FROM Docs doc WHERE doc.user.userId = :userId AND doc.category.categoryId = :categoryId")
    List<Docs> findByUserIdAndCategory(int userId, int categoryId);

    @Query("SELECT doc FROM Docs doc WHERE doc.user.userId = :userId AND doc.category.categoryId = :categoryId AND doc.docType= :type")
    List<Docs> findByUserIdAndCategoryAndType(int userId, int categoryId, String type);
}