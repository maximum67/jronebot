package com.example.jeronbot.services;

import com.example.jeronbot.models.ParserSetting;
import com.example.jeronbot.repositories.ParserSettingRepository;
import com.example.jeronbot.storage.StorageProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;


import javax.lang.model.element.Element;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor

public class ParserSettingService {

    private final ParserSettingRepository parserSettingRepository;

    public void parsing (ParserSetting parserSetting) throws IOException {

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
        parsingMap.put("JournalAndBallBearings", parserSetting.getJournalAndBallBearings());
        parsingMap.put("BearingHousings", parserSetting.getBearingHousings());
        parsingMap.put("HeatShields", parserSetting.getHeatShields());
        parsingMap.put("ActuatorAssy", parserSetting.getActuatorAssy());
        parsingMap.put("GasketKits", parserSetting.getGasketKits());
        parsingMap.put("NozzleRingAssy", parserSetting.getNozzleRingAssy());
        parsingMap.put("ServiceKits", parserSetting.getServiceKits());

            ParsingFile parsingFile = new ParsingFile();
            Map<Integer, List<Object>> map = parsingFile.read(getFile().toString());
        for (int i = 0; i < map.size(); i++) {
             for ( Map.Entry<String, Long> el : parsingMap.entrySet()){
                System.out.println( el.getKey()+" - "+map.get(i).get(Math.toIntExact(el.getValue())) + " !!! ");
            }
            System.out.println("_____________________________________");
            System.out.println();
        }
//            for (int i = 0; i < map.size(); i++) {
//                for (int j = 0; j < map.get(i).size(); j++) {
//                    System.out.print(map.get(i).get(j) + "  ");
//                }
//                System.out.println();
//            }

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

        StorageProperties storageProperties = new StorageProperties();
        String str = storageProperties.getLocation();
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
