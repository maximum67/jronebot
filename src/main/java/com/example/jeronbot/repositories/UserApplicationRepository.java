package com.example.jeronbot.repositories;

import com.example.jeronbot.models.UserApplication;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserApplicationRepository extends JpaRepository<UserApplication, Long> {

     UserApplication findByName(String name);

}
