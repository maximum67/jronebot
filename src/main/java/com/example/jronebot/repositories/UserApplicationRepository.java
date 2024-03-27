package com.example.jronebot.repositories;

import com.example.jronebot.models.UserApplication;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserApplicationRepository extends JpaRepository<UserApplication, Long> {

     UserApplication findByName(String name);

}
