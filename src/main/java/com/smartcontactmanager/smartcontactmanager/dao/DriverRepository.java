package com.smartcontactmanager.smartcontactmanager.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartcontactmanager.smartcontactmanager.Entities.Driver;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Integer> {}
