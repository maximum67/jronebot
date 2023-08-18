package com.example.jeronbot.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// https://www.callicoder.com/spring-boot-file-upload-download-jpa-hibernate-mysql-database-example/

@Entity
@Table(name="parsersetting")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ParserSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="jroneNo")
    private long jroneNo;

    @Column(name="turboMaker")
    private long turboMaker;

    @Column(name="turboModel")
    private long turboModel;

    @Column(name="turboOeNo")
    private long turboOeNo;

    @Column(name="vehicleOeNo")
    private long vehicleOeNo;

    @Column(name="brand")
    private long brand;

    @Column(name="makerModel")
    private long makerModel;

    @Column(name="engine")
    private long engine;

    @Column(name="year")
    private long year;

    @Column(name="chra")
    private long chra;

    @Column(name="shaftAndWheels")
    private long shaftAndWheels;

    @Column(name="compressorWheels")
    private long compressorWheels;

    @Column(name="sealPlates")
    private long sealPlates;

    @Column(name="thrustCollarKits")
    private long thrustCollarKits;

    @Column(name="thrustBearings")
    private long thrustBearings;

    @Column(name="journalAndBallBearings")
    private long journalAndBallBearings;

    @Column(name="bearingHousings")
    private long bearingHousings;

    @Column(name="heatShields")
    private long heatShields;

    @Column(name="actuatorAssy")
    private long actuatorAssy;

    @Column(name="gasketKits")
    private long gasketKits;

    @Column(name="nozzleRingAssy")
    private long nozzleRingAssy;

    @Column(name="serviceKits")
    private long serviceKits;

   @Column(name="fileName")
    private String fileName;
}
