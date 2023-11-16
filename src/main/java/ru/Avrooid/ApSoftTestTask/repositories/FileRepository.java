package ru.Avrooid.ApSoftTestTask.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.Avrooid.ApSoftTestTask.models.ProcessedFile;

/**
 * Репозиторий для работы с БД для сущности ProcessedFile
 */
@Repository
public interface FileRepository extends JpaRepository<ProcessedFile, Long> {
}
