package com.javadevelop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javadevelop.entity.NewEntity;

public interface NewRepository extends JpaRepository<NewEntity, Long> {

}
