package com.example.jeronbot.services;

import com.example.jeronbot.models.Turbocharger;
import com.example.jeronbot.repositories.TurbochargerRepository;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public void updateTurbochargerList (List<Turbocharger> list) {
        List<Turbocharger> turbochargerRepositoryAll = turbochargerRepository.findAll();
        boolean contains = false;
        for (Turbocharger turbocharger: list) {
            for(Turbocharger turbochargerRepos : turbochargerRepositoryAll) {
                if (turbocharger.getTurboOeNo().equals(turbochargerRepos.getTurboOeNo())) {
                    updateTurbochargerById(turbochargerRepos.getId(), turbocharger);
                    contains = true;
                    break;
                }
            }
              if (!contains)  updateTurbocharger(turbocharger);
              contains = false;
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


    public List<Turbocharger> getTurbochargerByOeNo(String turboOeNo) {
        List<Turbocharger> turbochargerList = turbochargerRepository.findAll();
        List<Turbocharger> list = new ArrayList<>();
        for (Turbocharger turbocharger : turbochargerList) {
            if (turbocharger.getTurboOeNo().startsWith(turboOeNo)) {
                list.add(turbocharger);
            }
        }
        return list;
    }
}
