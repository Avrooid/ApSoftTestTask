package ru.Avrooid.ApSoftTestTask.models;

import jakarta.persistence.*;

/**
 * Сущность файла в БД
 */
@Entity
public class ProcessedFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jsonData")
    private byte[] jsonData;

    public ProcessedFile(byte[] jsonData) {
        this.jsonData = jsonData;
    }

    public ProcessedFile() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getJsonData() {
        return jsonData;
    }
}
