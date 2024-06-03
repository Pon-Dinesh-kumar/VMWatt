package com.enerymonitoring.tool.enerymonitoring;


import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.skins.ModernSkin;
import javafx.scene.paint.Color;

public class VMEnergyConsumptionGauge {
    private Gauge gauge; // Declare a private Gauge instance


    // Constructor
    public VMEnergyConsumptionGauge() {
        // Initialize the Gauge instance and customize it
        gauge = new Gauge();
        gauge.setSkin(new ModernSkin(gauge));
        gauge.setMinValue(0);
        gauge.setMaxValue(10000);
        gauge.setMajorTickSpace(1000); // Set the interval to 100
        gauge.setUnit("Joules");
        gauge.setTitle("Energy Consumption of VM");
        gauge.setForegroundBaseColor(Color.WHITE);
    }
    public Gauge getGauge() {
        return gauge;
    }

    // Create a method to update the gauge's value
    public void updateValue(double value) {
        gauge.setValue(value);
    }


}

