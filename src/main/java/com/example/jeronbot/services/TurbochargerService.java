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
        turbocharger.setTurboOeNo((turbocharger.getTurboOeNo()).replaceAll("[^[0-9A-Za-z/]]",""));
        turbocharger.setVehicleOeNo((turbocharger.getVehicleOeNo()).replaceAll("[^[0-9A-Za-z/]]",""));
        turbochargerRepository.save(turbocharger);
    }

    public void updateTurbochargerById(Long id, Turbocharger updateTurbocharger){
       Turbocharger turbocharger = turbochargerRepository.getReferenceById(id);
       turbocharger.setJroneNo(updateTurbocharger.getJroneNo());
       turbocharger.setTurboMaker(updateTurbocharger.getTurboMaker());
       turbocharger.setTurboModel(updateTurbocharger.getTurboModel());
       turbocharger.setTurboOeNo((updateTurbocharger.getTurboOeNo()).replaceAll("[^[0-9A-Za-z/]]",""));
       turbocharger.setVehicleOeNo((updateTurbocharger.getVehicleOeNo()).replaceAll("[^[0-9A-Za-z/]]",""));
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
        List<Turbocharger> list = turbochargerRepository
                                  .findAllByTurboOeNoContainingOrVehicleOeNoContaining(turboOeNo,turboOeNo);
        if (list.size()>=7) list.clear();
        return list;
    }
}
