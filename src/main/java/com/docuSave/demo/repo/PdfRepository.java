package com.docuSave.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.docuSave.demo.model.PdfFile;
import com.docuSave.demo.model.User;

public interface PdfRepository extends JpaRepository<PdfFile, Long>{
    List<PdfFile> findByUser(User user);

    @Query("SELECT fileName FROM PdfFile fileName JOIN FETCH fileName.user")
    List<PdfFile> findAllWithUser();
}
