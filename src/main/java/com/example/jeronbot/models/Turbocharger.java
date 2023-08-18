package com.example.jeronbot.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="turbocharger")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Turbocharger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="jroneNo")
    private String jroneNo;

    @Column(name="turboMaker")
    private String turboMaker;

    @Column(name="turboModel")
    private String turboModel;

    @Column(name="turboOeNo")
    private String turboOeNo;

    @Column(name="vehicleOeNo")
    private String vehicleOeNo;

    @Column(name="brand")
    private String brand;

    @Column(name="makerModel")
    private String makerModel;

    @Column(name="engine")
    private String engine;

    @Column(name="year")
    private String year;

    @Column(name="chra")
    private String chra;

    @Column(name="shaftAndWheels")
    private String shaftAndWheels;

    @Column(name="compressorWheels")
    private String compressorWheels;

    @Column(name="sealPlates")
    private String sealPlates;

    @Column(name="thrustCollarKits")
    private String thrustCollarKits;

    @Column(name="thrustBearings")
    private String thrustBearings;

    @Column(name="journalAndBallBearings")
    private String journalAndBallBearings;

    @Column(name="bearingHousings")
    private String bearingHousings;

    @Column(name="heatShields")
    private String heatShields;

    @Column(name="actuatorAssy")
    private String actuatorAssy;

    @Column(name="gasketKits")
    private String gasketKits;

    @Column(name="nozzleRingAssy")
    private String nozzleRingAssy;

    @Column(name="serviceKits")
    private String serviceKits;
}
