package com.example.jeronbot.services;

import com.example.jeronbot.models.Turbocharger;
import com.example.jeronbot.repositories.TurbochargerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TurbochargerService {

    private final TurbochargerRepository turbochargerRepository;


    public List<Turbocharger> list() {
        if (turbochargerRepository.findAll().isEmpty()){
            return null;
        }else {
            return turbochargerRepository.findAll();
        }
    }

    public void updateTurbocharger(Turbocharger turbocharger){
        turbochargerRepository.save(turbocharger);
    }

    public void updateTurbochargerById(Long id, Turbocharger updateTurbocharger){
       Turbocharger turbocharger = turbochargerRepository.getReferenceById(id);
       turbocharger.setJroneNo(updateTurbocharger.getJroneNo());
       turbocharger.setTurboMaker(updateTurbocharger.getTurboMaker());
       turbocharger.setTurboModel(updateTurbocharger.getTurboModel());
       turbocharger.setTurboOeNo(updateTurbocharger.getTurboOeNo());
       turbocharger.setVehicleOeNo(updateTurbocharger.getVehicleOeNo());
       turbocharger.setBrand(updateTurbocharger.getBrand());
       turbocharger.setMakerModel(updateTurbocharger.getMakerModel());
       turbocharger.setEngine(updateTurbocharger.getEngine());
       turbocharger.setYear(updateTurbocharger.getYear());
       turbocharger.setChra(updateTurbocharger.getChra());
       turbocharger.setShaftAndWheels(updateTurbocharger.getShaftAndWheels());
       turbocharger.setCompressorWheels(updateTurbocharger.getCompressorWheels());
       turbocharger.setSealPlates(updateTurbocharger.getSealPlates());
       turbocharger.setThrustCollarKits(updateTurbocharger.getThrustCollarKits());
       turbocharger.setThrustBearings(updateTurbocharger.getThrustBearings());
       turbocharger.setJournalAndBallBearings(updateTurbocharger.getJournalAndBallBearings());
       turbocharger.setBearingHousings(updateTurbocharger.getBearingHousings());
       turbocharger.setHeatShields(updateTurbocharger.getHeatShields());
       turbocharger.setActuatorAssy(updateTurbocharger.getActuatorAssy());
       turbocharger.setGasketKits(updateTurbocharger.getGasketKits());
       turbocharger.setNozzleRingAssy(updateTurbocharger.getNozzleRingAssy());
       turbocharger.setServiceKits(updateTurbocharger.getServiceKits());
       turbochargerRepository.save(turbocharger);

    }

    public void deleteTurbocharger(Long id){
        turbochargerRepository.deleteById(id);
    }
}
