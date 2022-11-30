package com.kodlamaio.invantoryServer.dataAccess;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kodlamaio.invantoryServer.entites.Model;

public interface ModelRepository extends JpaRepository<Model, String> {

}