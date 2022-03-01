package com.example.application.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.application.data.ActivationRecord;

public interface ActivationRecordRepository extends JpaRepository<ActivationRecord, Long>{

}
