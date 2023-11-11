package com.example.jeronbot.services;

import com.example.jeronbot.models.ParserSetting;
import com.example.jeronbot.models.Turbocharger;
import com.example.jeronbot.repositories.ParserSettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor

public class ParserSettingService {

    private final ParserSettingRepository parserSettingRepository;

    public List<Turbocharger> parsing (ParserSetting parserSetting) throws IOException {

        Map<String, Long> parsingMap = new LinkedHashMap<>();
        parsingMap.put("JroneNo", parserSetting.getJroneNo());
        parsingMap.put("TurboMarker", parserSetting.getTurboMaker());
        parsingMap.put("TurboModel", parserSetting.getTurboModel());
        parsingMap.put("TurboOeNo", parserSetting.getTurboOeNo());
        parsingMap.put("VehicleOeNo", parserSetting.getVehicleOeNo());
        parsingMap.put("Brand", parserSetting.getBrand());
        parsingMap.put("MakerModel", parserSetting.getMakerModel());
        parsingMap.put("Engine", parserSetting.getEngine());
        parsingMap.put("Year", parserSetting.getYear());
        parsingMap.put("Chra", parserSetting.getChra());
        parsingMap.put("ShaftAndWheels", parserSetting.getShaftAndWheels());
        parsingMap.put("CompressorWheels", parserSetting.getCompressorWheels());
        parsingMap.put("SealPlates", parserSetting.getSealPlates());
        parsingMap.put("ThrustCollarKits", parserSetting.getThrustCollarKits());
        parsingMap.put("ThrustBearings", parserSetting.getThrustBearings());
        parsingMap.put("JournalAndBallBearings", parserSetting.getJournalAndBallBearings());
        parsingMap.put("BearingHousings", parserSetting.getBearingHousings());
        parsingMap.put("HeatShields", parserSetting.getHeatShields());
        parsingMap.put("ActuatorAssy", parserSetting.getActuatorAssy());
        parsingMap.put("GasketKits", parserSetting.getGasketKits());
        parsingMap.put("NozzleRingAssy", parserSetting.getNozzleRingAssy());
        parsingMap.put("ServiceKits", parserSetting.getServiceKits());


        List<Turbocharger> turbochargerList = new ArrayList<>();
            ParsingFile parsingFile = new ParsingFile();
            Map<Integer, List<Object>> map = parsingFile.read(getFile().toString());
        for (int i = 0; i < map.size(); i++) {
            Turbocharger turbocharger = new Turbocharger();
             for ( Map.Entry<String, Long> el : parsingMap.entrySet()){
//                System.out.println( el.getKey()+" - "+map.get(i).get(Math.toIntExact(el.getValue())) + " !!! ");
                switch (el.getKey()){
                    case "JroneNo": turbocharger.setJroneNo((String) map.get(i).get(Math.toIntExact(el.getValue())));
                        break;
                    case "TurboMarker": turbocharger.setTurboMaker((String) map.get(i).get(Math.toIntExact(el.getValue())));
                        break;
                    case "TurboModel": turbocharger.setTurboModel((String) map.get(i).get(Math.toIntExact(el.getValue())));
                        break;
                    case "TurboOeNo": turbocharger.setTurboOeNo((String) map.get(i).get(Math.toIntExact(el.getValue())));
                        break;
                    case "VehicleOeNo": turbocharger.setVehicleOeNo((String) map.get(i).get(Math.toIntExact(el.getValue())));
                        break;
                    case "Brand": turbocharger.setBrand((String) map.get(i).get(Math.toIntExact(el.getValue())));
                        break;
                    case "MakerModel": turbocharger.setMakerModel((String) map.get(i).get(Math.toIntExact(el.getValue())));
                        break;
                    case "Engine": turbocharger.setEngine((String) map.get(i).get(Math.toIntExact(el.getValue())));
                        break;
                    case "Year": turbocharger.setYear((String) map.get(i).get(Math.toIntExact(el.getValue())));
                        break;
                    case "Chra": turbocharger.setChra((String) map.get(i).get(Math.toIntExact(el.getValue())));
                        break;
                    case "ShaftAndWheels": turbocharger.setShaftAndWheels((String) map.get(i).get(Math.toIntExact(el.getValue())));
                        break;
                    case "CompressorWheels": turbocharger.setCompressorWheels((String) map.get(i).get(Math.toIntExact(el.getValue())));
                        break;
                    case "SealPlates": turbocharger.setSealPlates((String) map.get(i).get(Math.toIntExact(el.getValue())));
                        break;
                    case "ThrustCollarKits": turbocharger.setThrustCollarKits((String) map.get(i).get(Math.toIntExact(el.getValue())));
                        break;
                    case "ThrustBearings": turbocharger.setThrustBearings((String) map.get(i).get(Math.toIntExact(el.getValue())));
                        break;
                    case "JournalAndBallBearings": turbocharger.setJournalAndBallBearings((String) map.get(i).get(Math.toIntExact(el.getValue())));
                        break;
                    case "BearingHousings": turbocharger.setBearingHousings((String) map.get(i).get(Math.toIntExact(el.getValue())));
                        break;
                    case "HeatShields": turbocharger.setHeatShields((String) map.get(i).get(Math.toIntExact(el.getValue())));
                        break;
                    case "ActuatorAssy": turbocharger.setActuatorAssy((String) map.get(i).get(Math.toIntExact(el.getValue())));
                        break;
                    case "GasketKits": turbocharger.setGasketKits((String) map.get(i).get(Math.toIntExact(el.getValue())));
                        break;
                    case "NozzleRingAssy": turbocharger.setNozzleRingAssy((String) map.get(i).get(Math.toIntExact(el.getValue())));
                        break;
                    case "ServiceKits": turbocharger.setServiceKits((String) map.get(i).get(Math.toIntExact(el.getValue())));
                        break;
                }
            }

//            System.out.println("_____________________________________");
//            System.out.println(turbocharger.getJroneNo());
//            System.out.println("_____________________________________");
            turbochargerList.add(turbocharger);
        }
//        for (Turbocharger t: turbochargerList) {
//            System.out.println(t.getJroneNo());
//        }
        return turbochargerList;
    }

    public List<ParserSetting> list(){
       return parserSettingRepository.findAll();
    }

    public void updateParserSetting(ParserSetting updateparserSetting, Long id) {
        if (id == 0) {
            parserSettingRepository.save(updateparserSetting);
        } else {
            ParserSetting parserSetting = parserSettingRepository.getReferenceById(id);
            parserSetting.setJroneNo(updateparserSetting.getJroneNo());
            parserSetting.setTurboMaker(updateparserSetting.getTurboMaker());
            parserSetting.setTurboModel(updateparserSetting.getTurboModel());
            parserSetting.setTurboOeNo(updateparserSetting.getTurboOeNo());
            parserSetting.setVehicleOeNo(updateparserSetting.getVehicleOeNo());
            parserSetting.setBrand(updateparserSetting.getBrand());
            parserSetting.setMakerModel(updateparserSetting.getMakerModel());
            parserSetting.setEngine(updateparserSetting.getEngine());
            parserSetting.setYear(updateparserSetting.getYear());
            parserSetting.setChra(updateparserSetting.getChra());
            parserSetting.setShaftAndWheels(updateparserSetting.getShaftAndWheels());
            parserSetting.setCompressorWheels(updateparserSetting.getCompressorWheels());
            parserSetting.setSealPlates(updateparserSetting.getSealPlates());
            parserSetting.setThrustCollarKits(updateparserSetting.getThrustCollarKits());
            parserSetting.setThrustBearings(updateparserSetting.getThrustBearings());
            parserSetting.setJournalAndBallBearings(updateparserSetting.getJournalAndBallBearings());
            parserSetting.setBearingHousings(updateparserSetting.getBearingHousings());
            parserSetting.setHeatShields(updateparserSetting.getHeatShields());
            parserSetting.setActuatorAssy(updateparserSetting.getActuatorAssy());
            parserSetting.setGasketKits(updateparserSetting.getGasketKits());
            parserSetting.setNozzleRingAssy(updateparserSetting.getNozzleRingAssy());
            parserSetting.setServiceKits(updateparserSetting.getServiceKits());

            parserSettingRepository.save(parserSetting);
        }
    }



    public ParserSetting getSetting(){
        List<ParserSetting> parserSettingList = parserSettingRepository.findAll();;
        if (parserSettingList.isEmpty()){
           return new ParserSetting();
        }else{

           return parserSettingList.get(0);
        }
    }
    public void deleteParserSetting(Long id){
        parserSettingRepository.deleteById(id);
    }

    public ParserSetting getSettingById(Long id){
       return parserSettingRepository.getReferenceById(id);
    }

    public File getFile(){
        String str = "";
        File file = new File(str);
        File[] files = file.listFiles();
        if (files!=null) {
            for (File f : files) {
                return new File(str + "/" + f.getName());
            }
        }
            return file;
    }
}
