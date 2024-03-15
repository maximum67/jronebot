package com.example.jeronbot.repositories;

import com.example.jeronbot.models.Turbocharger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TurbochargerRepository extends JpaRepository<Turbocharger,Long> {

    List<Turbocharger> findAllByTurboOeNoContainingOrVehicleOeNoContaining(String turboOeNo, String vehicleOeNo);

}
