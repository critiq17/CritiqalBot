package com.example.repository;

import com.example.entity.BenchPressLog;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BenchPressRepository implements PanacheRepository<BenchPressLog> {

}
