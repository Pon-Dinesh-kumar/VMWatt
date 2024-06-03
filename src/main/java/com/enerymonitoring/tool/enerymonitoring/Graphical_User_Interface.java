
        package com.enerymonitoring.tool.enerymonitoring;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

        public class Graphical_User_Interface extends Application {
    private EnerymonitoringApplication energyMonitoringApp;

    private TextArea outputTextArea;// TextArea to display the output

    private Slider vmCoresSlider; // Slider for VM CPU Cores

    private Slider host_no_slider;
    private Slider host_cores;
    private Slider vm_no_slider;

    private Slider host_bw_slider;

    private Slider host_storage_slider;
    private Slider cloudlet_no_slider;
    private Slider cloudlet_core;
    private String darkModeStyles;
    private Slider vmRamSlider;
    private Slider vmBwSlider;
    private Slider vmStorageSlider;
    private Slider cloudletLengthSlider;

            private Slider host_ram_slider;


            public static void main(String[] args) {
                    launch(args);
                }



    @Override
    public void start(Stage primaryStage) {
        HostEnergyConsumptionGauge energyGauge = new HostEnergyConsumptionGauge();

        VMEnergyConsumptionGauge vmenergyGauge = new VMEnergyConsumptionGauge();

        StackPane Host_root = new StackPane(energyGauge.getGauge());
        StackPane vm_root = new StackPane(vmenergyGauge.getGauge());

        // Create a configuration manager
        ConfigurationManager configManager = new ConfigurationManager();

        // Add multiple configurations to the manager
        configManager.addConfiguration("Default", 4, 2, 4, 8, 8, 2, 512, 1000, 10000, 50000, 2048, 1000000, 10000);
        configManager.addConfiguration("Pass Config 1", 4, 2, 2, 2, 8, 4, 256, 200, 2000, 10000, 8192, 4000, 5000);
        configManager.addConfiguration("Pass Config 2", 3, 3, 3, 3, 9, 3, 768, 300, 3000, 15000, 12288, 6000, 7500);
        configManager.addConfiguration("Pass Config 3", 2, 4, 4, 4, 8, 4, 512, 400, 4000, 20000, 16384, 8000, 10000);
        configManager.addConfiguration("Pass Config 4", 4, 2, 2, 2, 8, 4, 512, 200, 2000, 10000, 8192, 4000, 5000);
        configManager.addConfiguration("Pass Config 5", 3, 3, 3, 3, 9, 3, 768, 300, 3000, 15000, 12288, 6000, 7500);
        configManager.addConfiguration("Pass Config 6", 2, 4, 4, 4, 8, 4, 512, 400, 4000, 20000, 16384, 10000, 10000);
        configManager.addConfiguration("Pass Config 7", 4, 2, 2, 2, 8, 4, 512, 200, 2000, 10000, 8192, 4000, 5000);
        configManager.addConfiguration("Pass Config 8", 3, 3, 3, 3, 9, 3, 768, 300, 3000, 15000, 12288, 6000, 7500);


        // Create configurations that trigger the conditions
        configManager.addConfiguration("Trigger Config 1", 8, 3, 5, 10, 12, 3, 1024, 1500, 12000, 60000, 4096, 2000, 10000);
        configManager.addConfiguration("Trigger Config 2", 5, 4, 10, 2, 20, 1, 256, 800, 8000, 30000, 8192, 800, 4000);
        configManager.addConfiguration("Trigger Config 3", 3, 6, 9, 3, 15, 2, 768, 1200, 9000, 40000, 16384, 1200, 6000);
        configManager.addConfiguration("Trigger Config 4", 8, 3, 5, 10, 12, 3, 1024, 1500, 12000, 60000, 4096, 2000, 10000);
        configManager.addConfiguration("Trigger Config 5", 5, 4, 10, 2, 20, 1, 256, 800, 8000, 30000, 8192, 800, 4000);
        configManager.addConfiguration("Trigger Config 6", 3, 6, 9, 3, 15, 2, 768, 1200, 9000, 40000, 16384, 1200, 6000);
        configManager.addConfiguration("Trigger Config 7", 2, 8, 8, 4, 12, 4, 1024, 1500, 8000, 45000, 8192, 1000, 5000);
        configManager.addConfiguration("Trigger Config 8", 4, 2, 6, 2, 10, 2, 512, 800, 12000, 30000, 4096, 500, 2500);

        ObservableList<String> configNames = FXCollections.observableArrayList(configManager.getConfigurationNames());

        ComboBox<String> configComboBox = new ComboBox<>(configNames);
        configComboBox.setValue("Select a Configuration");

        configComboBox.setOnAction(e -> {
            Configuration selectedConfig = configManager.getConfiguration(configComboBox.getValue());
            vmCoresSlider.setValue(selectedConfig.vmCores);
            host_no_slider.setValue(selectedConfig.noOfHosts);
            host_cores.setValue(selectedConfig.hostCores);
            vm_no_slider.setValue(selectedConfig.noOfVMs);
            cloudlet_no_slider.setValue(selectedConfig.noOfCloudlets);
            cloudlet_core.setValue(selectedConfig.cloudletCores);
            host_ram_slider.setValue(selectedConfig.hostRam);
            host_storage_slider.setValue(selectedConfig.hoststorage);
            host_bw_slider.setValue(selectedConfig.hostbw);
            vmRamSlider.setValue(selectedConfig.vmRam);
            vmBwSlider.setValue(selectedConfig.vmBw);
            vmStorageSlider.setValue(selectedConfig.vmStorage);
            cloudletLengthSlider.setValue(selectedConfig.cloudletLength);


            //add more
        });
        VBox configComboBoxBox = new VBox(10);
        configComboBoxBox.getChildren().addAll(new Label("Select Configuration"), configComboBox);

        HBox hbox_gauge = new HBox(10); // 10 pixels spacing
        Label aboutUsLabel = new Label("Welcome to the VMWattEco!\n\n" +
                "This project is a part of the Capstone Project of  CSD5002 - Virtualization Essentials (Fall Semester 2023-24) by Dr. Hemraj S.L.\n" +
                "By M.Pon Dinesh Kumar (Reg No: 20MEI10010)\n");

        Button moreButton = new Button("More");

        VBox aboutUsAndMoreLayout = new VBox(10); // 10 pixels spacing
        aboutUsAndMoreLayout.getChildren().addAll(aboutUsLabel, moreButton);

        hbox_gauge.getChildren().addAll(Host_root, vm_root, aboutUsAndMoreLayout);

        moreButton.setStyle(darkModeStyles);


        // Create GUI components
        Label errorLabel = new Label();



        // Create a Slider for VM CPU Cores
        vmCoresSlider = new Slider(0, 10, 0); // Min: 1, Max: 10, Initial: 1
        Label vmCoresValueLabel = new Label("VM CPU Cores: 0");

        host_no_slider = new Slider(0, 10, 0);
        Label hostnoValueLabel = new Label("NO OF HOSTS: 0");

        host_ram_slider = new Slider(0, 8192, 0);
        Label hostramValueLabel = new Label("Host RAM (MB): 0");

        host_cores = new Slider(0, 10, 0);
        Label hostcoreValueLabel = new Label("Host CPU Cores : 0");

        vm_no_slider = new Slider(0, 10, 0);
        Label vmnoValueLabel = new Label("NO OF VM: 0");

        cloudlet_no_slider = new Slider(0, 10, 0);
        Label cloudletnoValueLabel = new Label("NO OF Cloudlets: 0");

        cloudlet_core = new Slider(0, 10, 0);
        Label cloudletcoreValueLabel = new Label("Cloudlets CPU Cores: 0");

        // Create a Slider for VM RAM
         vmRamSlider = new Slider(0, 8192, 0); // Min: 0 MB, Max: 8192 MB, Initial: 512 MB
        Label vmRamValueLabel = new Label("VM RAM (MB): 0");

// Create a Slider for VM Bandwidth
         vmBwSlider = new Slider(0, 2000, 0); // Min: 0 Mbps, Max: 2000 Mbps, Initial: 1000 Mbps
        Label vmBwValueLabel = new Label("VM Bandwidth (Mbps): 0");

// Create a Slider for VM Storage
         vmStorageSlider = new Slider(0, 20000, 0); // Min: 0 MB, Max: 20000 MB, Initial: 10000 MB
        Label vmStorageValueLabel = new Label("VM Storage (MB): 0");

// Create a Slider for Cloudlet Length
         cloudletLengthSlider = new Slider(0, 100000, 0); // Min: 0, Max: 100000, Initial: 50000
        Label cloudletLengthValueLabel = new Label("Cloudlet Length: 0");

        host_bw_slider = new Slider(0, 2000, 0);
        Label host_bw_valuelabel = new Label("HOST Bandwidth (Mbps): 0");

        host_storage_slider = new Slider(0, 20000, 0);
        Label hostStorageValueLabel = new Label("HOST Storage (MB): 0");




        // Add a listener to update the label when the Slider value changes
        vmCoresSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            int selectedValue = newValue.intValue();
            vmCoresValueLabel.setText("VM CPU Cores: " + selectedValue);
        });
        host_ram_slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            int selectedValue = newValue.intValue();
            hostramValueLabel.setText("Host RAM (MB): " + selectedValue);
        });

        host_no_slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            int selectedValue = newValue.intValue();
            hostnoValueLabel.setText("NO OF HOSTS: " + selectedValue);
        });
        host_cores.valueProperty().addListener((observable, oldValue, newValue) -> {
            int selectedValue = newValue.intValue();
            hostcoreValueLabel.setText("Host CPU Cores: " + selectedValue);
        });

        vm_no_slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            int selectedValue = newValue.intValue();
            vmnoValueLabel.setText("NO OF VM: " + selectedValue);
        });
        cloudlet_no_slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            int selectedValue = newValue.intValue();
            cloudletnoValueLabel.setText("NO OF Cloudlets: " + selectedValue);
        });
        cloudlet_core.valueProperty().addListener((observable, oldValue, newValue) -> {
            int selectedValue = newValue.intValue();
            cloudletcoreValueLabel.setText("Host CPU Cores: " + selectedValue);
        });

        vmRamSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            int selectedValue = newValue.intValue();
            vmRamValueLabel.setText("VM RAM (MB): " + selectedValue);
        });

        vmBwSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            int selectedValue = newValue.intValue();
            vmBwValueLabel.setText("VM Bandwidth (Mbps): " + selectedValue);
        });

        vmStorageSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            int selectedValue = newValue.intValue();
            vmStorageValueLabel.setText("VM Storage (MB): " + selectedValue);
        });

        cloudletLengthSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            int selectedValue = newValue.intValue();
            cloudletLengthValueLabel.setText("Cloudlet Length: " + selectedValue);
        });
        host_bw_slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            int selectedValue = newValue.intValue();
            host_bw_valuelabel.setText("HOST Bandwidth (Mbps): " + selectedValue);
        });
        host_storage_slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            int selectedValue = newValue.intValue();
            hostStorageValueLabel.setText("HOST Storage (MB): " + selectedValue);
        });


        // Initialize the energy gauge

        Button startButton = new Button("Start Energy Monitoring");


        // Create an HBox to hold the buttons
        HBox buttonBox = new HBox(10); // 10 pixels spacing
        buttonBox.getChildren().addAll(startButton);


        // Create a layout and add components
        VBox vbox_final = new VBox(10);// 10 pixels spacing

        // Create dark mode styles
        darkModeStyles =
                "-fx-background-color: #2C2B34;" +
                        "-fx-control-inner-background: #2C2B34;" +
                        "-fx-text-fill: white;";


        VBox.setMargin(errorLabel, new Insets(10, 10, 10, 10));// Set top and bottom margins to 10 pixels
        VBox.setMargin(vmCoresSlider, new Insets(0, 170, 0, 10));
        VBox.setMargin(host_no_slider, new Insets(0, 170, 0, 10));// Set top and bottom margins to 10 pixels
        VBox.setMargin(hostnoValueLabel, new Insets(10, 10, 0, 10));// Set top and bottom margins to 10 pixels
        VBox.setMargin(host_ram_slider, new Insets(0, 170, 0, 10));// Set top and bottom margins to 10 pixels
        VBox.setMargin(hostramValueLabel, new Insets(10, 10, 0, 10));// Set top and bottom margins to 10 pixels
        VBox.setMargin(host_cores, new Insets(0, 170, 0, 10));// Set top and bottom margins to 10 pixels
        VBox.setMargin(hostcoreValueLabel, new Insets(0, 10, 0, 10));// Set top and bottom margins to 10 pixels
        VBox.setMargin(vm_no_slider, new Insets(0, 170, 0, 10));// Set top and bottom margins to 10 pixels
        VBox.setMargin(vmnoValueLabel, new Insets(0, 10, 0, 10));// Set top and bottom margins to 10 pixels
        VBox.setMargin(vmCoresValueLabel, new Insets(0, 10, 0, 10));// Set top and bottom margins to 10 pixels
        VBox.setMargin(cloudlet_no_slider, new Insets(0, 170, 0, 10));// Set top and bottom margins to 10 pixels
        VBox.setMargin(cloudletnoValueLabel, new Insets(0, 10, 0, 10));// Set top and bottom margins to 10 pixels
        VBox.setMargin(cloudlet_core, new Insets(0, 170, 0, 10));// Set top and bottom margins to 10 pixels
        VBox.setMargin(cloudletcoreValueLabel, new Insets(0, 10, 0, 10));// Set top and bottom margins to 10 pixels
        VBox.setMargin(vmRamSlider, new Insets(0, 170, 0, 10));// Set top and bottom margins to 10 pixels
        VBox.setMargin(vmRamValueLabel, new Insets(0, 10, 0, 10));// Set top and bottom margins to 10 pixels
        VBox.setMargin(vmBwSlider, new Insets(0, 170, 0, 10));// Set top and bottom margins to 10 pixels
        VBox.setMargin(vmBwValueLabel, new Insets(0, 10, 0, 10));// Set top and bottom margins to 10 pixels
        VBox.setMargin(host_bw_slider, new Insets(0, 170, 0, 10));// Set top and bottom margins to 10 pixels
        VBox.setMargin(host_bw_valuelabel, new Insets(0, 10, 0, 10));// Set top and bottom margins to 10 pixels
        VBox.setMargin(host_storage_slider, new Insets(0, 170, 0, 10));// Set top and bottom margins to 10 pixels
        VBox.setMargin(hostStorageValueLabel, new Insets(0, 10, 0, 10));// Set top and bottom margins to 10 pixels
        VBox.setMargin(vmStorageSlider, new Insets(0, 170, 0, 10));// Set top and bottom margins to 10 pixels
        VBox.setMargin(vmStorageValueLabel, new Insets(0, 10, 0, 10));// Set top and bottom margins to 10 pixels
        VBox.setMargin(cloudletLengthSlider, new Insets(0, 170, 0, 10));// Set top and bottom margins to 10 pixels
        VBox.setMargin(cloudletLengthValueLabel, new Insets(0, 10, 0, 10));// Set top and bottom margins to 10 pixels
        VBox.setMargin(startButton, new Insets(10));
        VBox.setMargin(buttonBox, new Insets(10, 10, 0, 10));
        vbox_final.setStyle(darkModeStyles); // Set the background color to black
        errorLabel.setStyle(darkModeStyles);
        hostnoValueLabel.setStyle(darkModeStyles);
        hostcoreValueLabel.setStyle(darkModeStyles);
        hostramValueLabel.setStyle(darkModeStyles);
        vmnoValueLabel.setStyle(darkModeStyles);
        vmCoresValueLabel.setStyle(darkModeStyles);
        cloudletnoValueLabel.setStyle(darkModeStyles);
        cloudletcoreValueLabel.setStyle(darkModeStyles);
        aboutUsLabel.setStyle(darkModeStyles);
        vmRamValueLabel.setStyle(darkModeStyles);
        hostStorageValueLabel.setStyle(darkModeStyles);
        host_bw_valuelabel.setStyle(darkModeStyles);
        vmBwValueLabel.setStyle(darkModeStyles);
        vmStorageValueLabel.setStyle(darkModeStyles);
        cloudletLengthValueLabel.setStyle(darkModeStyles);

        errorLabel.setStyle("-fx-text-fill: red;"); // Set the error text color to red

        vbox_final.getChildren().addAll(
                configComboBoxBox,
                hostnoValueLabel, host_no_slider, hostcoreValueLabel, host_cores,hostramValueLabel,host_ram_slider,hostStorageValueLabel,host_storage_slider,host_bw_valuelabel,host_bw_slider,
                vmnoValueLabel, vm_no_slider, vmCoresValueLabel, vmCoresSlider,
                cloudletnoValueLabel, cloudlet_no_slider, cloudletcoreValueLabel, cloudlet_core, vmRamValueLabel,
                vmRamSlider,
                vmBwValueLabel,
                vmBwSlider,
                vmStorageValueLabel,
                vmStorageSlider,
                cloudletLengthValueLabel,
                cloudletLengthSlider,
                buttonBox, errorLabel, createOutputTextArea(), hbox_gauge
        );
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(vbox_final);
        scrollPane.pannableProperty().set(true);
        scrollPane.setFitToWidth(true);  // This line will make the scroll pane take the full width
        // Get the vertical scrollbar of the scroll pane
        ScrollBar verticalScrollBar = (ScrollBar) scrollPane.lookup(".vertical .scroll-bar");





        //stackpane to expand

        //StackPane stackPane = new StackPane(scrollPane);
        //bind
        //scrollPane.prefWidthProperty().bind(stackPane.widthProperty());
        //scrollPane.prefHeightProperty().bind(stackPane.heightProperty());
        // Create a scene
        Scene scene = new Scene(scrollPane);

        // Set the stage title
        primaryStage.setTitle("VMWattEco");
        primaryStage.setMaximized(true);
        primaryStage.setFullScreen(false);
        // to handle maximize screen
        primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
            // Adjust the ScrollPane's width when the window is resized
            double newWidth = (double)newVal;
            scrollPane.setPrefWidth(newWidth);
        });

        primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
            // Adjust the ScrollPane's height when the window is resized
            double newHeight = (double)newVal;
            scrollPane.setPrefHeight(newHeight);
        });

        // Set the action for the "Start" button

        startButton.setOnAction(e -> {
            int vmcpuCoresValue = (int) vmCoresSlider.getValue();
            int ionohosts = (int) host_no_slider.getValue();
            int iohostram = (int) host_ram_slider.getValue();
            int vmNoIO = (int) vm_no_slider.getValue();
            int cloudlets = (int) cloudlet_no_slider.getValue();
            int host_cpu = (int) host_cores.getValue();
            int cloudlets_core = (int) cloudlet_core.getValue();
            int vmram = (int) vmRamSlider.getValue();
            int vmbw = (int) vmBwSlider.getValue();
            int vmstor = (int) vmStorageSlider.getValue();
            int cloudletlen = (int) cloudletLengthSlider.getValue();
            int HOST_bw = (int) host_bw_slider.getValue();
            int HOST_storage =(int) host_storage_slider.getValue();

            // Calculate total available host resources (you need to define these)
            int totalHostCores = ionohosts * host_cpu;
            int totalRAM = ionohosts * iohostram;
            int totalBW = ionohosts * HOST_bw;
            int totalStorage = ionohosts * HOST_storage;

            String errorMessage = null; // Initialize error message as null




                    if (vmcpuCoresValue > host_cpu || vmram > (totalRAM / vmNoIO)) {
                        errorMessage = "Host resources cannot handle the VM requirements.";
                    }
                    if (cloudlets_core > host_cpu) {
                        errorMessage = "Host resources cannot handle the cloudlet requirements.";
                    }
                    if (totalHostCores < vmcpuCoresValue * vmNoIO) {
                        errorMessage = "Not enough host CPU cores for VMs.";
                    }
                    if (totalHostCores <  vmcpuCoresValue * vmNoIO) {
                        errorMessage = "Combined CPU core requirements exceed host capacity.";
                    }
                    if (totalRAM < vmram * vmNoIO) {
                        errorMessage = "Combined RAM requirements exceed host capacity.";
                    }
                    if (totalBW < vmbw * vmNoIO) {
                        errorMessage = "Combined bandwidth requirements exceed host capacity.";
                    }
                    if (totalStorage < vmstor * vmNoIO) {
                        errorMessage = "Combined storage requirements exceed host capacity.";
                    }

                    if (errorMessage != null) {
                        errorLabel.setText(errorMessage);
                    } else {


            try {
                    scrollPane.setVvalue(1.0);

                    // Initialize variables with default values

                    int vmPesValue = 4;
                    int noofhost = 2;
                    int vmno = 4;
                    int cloudletno = 8;
                    int hostcores = 8;
                    int cloudletCore = 2;
                    int ramDefault = 512;
                    int bwDefault =  1000;
                    int storageDefault = 10000;
                    int cloudletLength = 50000;
                    int hostRAM = 2048;
                    int host_bw = 10000;
                    int host_storage = 1000000;
                    // Parse the user input as an integer if provided
                    if (vmcpuCoresValue != 0) {
                        vmPesValue = vmcpuCoresValue;
                    }
                    if (ionohosts != 0) {
                        noofhost = ionohosts;
                    }
                    if (iohostram != 0){
                        hostRAM = iohostram;
                    }
                    if (vmNoIO != 0) {
                        vmno = vmNoIO;
                    }
                    if (cloudlets != 0) {
                        cloudletno = cloudlets;
                    }
                    if (host_cpu != 0) {
                        hostcores = host_cpu;
                    }
                    if (cloudlets_core != 0) {
                        cloudletCore = cloudlets_core;
                    }
                    if (vmram != 0) {
                        ramDefault = vmram;
                    }
                    if (vmbw != 0){
                        bwDefault = vmbw;
                    }
                    if (vmstor != 0){
                        storageDefault=vmstor;
                    }
                    if (cloudletlen != 0){
                        cloudletLength=cloudletlen;
                    }
                    if (HOST_bw != 0){
                        host_bw = HOST_bw;
                    }
                    if (HOST_storage != 0){
                        host_storage = HOST_storage;
                    }
                    // Parse the user input as an integer





                    // Call the setVmPes method in the EnerymonitoringApplication class
                    errorLabel.setText(""); // Clear any previous error message
                    EnerymonitoringApplication.setVmPes(vmPesValue);
                    EnerymonitoringApplication.setHosts(noofhost);
                    EnerymonitoringApplication.setVms(vmno);
                    EnerymonitoringApplication.setCloudletno(cloudletno);
                    EnerymonitoringApplication.setHostcores(hostcores);
                    EnerymonitoringApplication.setCLOUDLETS_core(cloudletCore);
                    EnerymonitoringApplication.setVM_RAM_DEFAULT(ramDefault);
                    EnerymonitoringApplication.setVM_BW_DEFAULT(bwDefault);
                    EnerymonitoringApplication.setVM_STORAGE_DEFAULT(storageDefault);
                    EnerymonitoringApplication.setHOST_RAM(hostRAM);
                    EnerymonitoringApplication.setCLOUDLET_LENGTH(cloudletLength);
                    EnerymonitoringApplication.setHOST_BW(host_bw);
                    EnerymonitoringApplication.setHOST_Storage(host_storage);


                    // Instantiate and start the EnerymonitoringApplication
                    energyMonitoringApp = new EnerymonitoringApplication();
                    // Assuming you have a method to start the energy monitoring

                    double averageHostConsumption = energyMonitoringApp.getAverageHostEnergyConsumption();
                    energyGauge.updateValue(averageHostConsumption);

                    double averageVMConsumption = energyMonitoringApp.getAverageVMEnergyConsumption();
                    vmenergyGauge.updateValue(averageVMConsumption);



                /*/ Update the gauge's value with new energy consumption data
                double averageHostConsumption = energyMonitoringApp.getAverageHostEnergyConsumption();
                energyGauge.updateValueHost(averageHostConsumption);
                System.out.println("Updated HOST Gauge Value: " + energyGauge   .getHostGauge().getValue());


                double averageVMConsumption = energyMonitoringApp.getAverageVMEnergyConsumption();
                energyGauge.updateValueVM(averageVMConsumption);
                System.out.println("Updated VM Gauge Value: " + energyGauge.getVMGauge().getValue());*/


                    // Update the circular meter with the average energy consumption values

                    // Display the output in a new window or dialog
                    displayOutputInTextArea(energyMonitoringApp.getOutput());
                } catch (NumberFormatException ex) {
                    // Handle the case where the input is not a valid integer
                    System.err.println("Invalid input. Please enter a valid integer.");
                    // You can display an error message to the user as needed
                }
            }




        });
        moreButton.setOnAction(e -> {
            showAboutUsPage();
        });

        // Set the scene and show the GUI
        primaryStage.setScene(scene);
        primaryStage.show();
    }




    // Add a method to display the output in a new window or dialog
    private VBox createOutputTextArea() {
        outputTextArea = new TextArea();
        outputTextArea.setEditable(false);
        outputTextArea.setWrapText(true);
        outputTextArea.setPrefRowCount(10);// Set the number of visible rows
        Button openOutputWindowButton = new Button("Open Output Window");
        openOutputWindowButton.setOnAction(e -> openOutputWindow(outputTextArea));
        openOutputWindowButton.setVisible(false);
        openOutputWindowButton.setManaged(false);
        VBox vbox_outputtextarea = new VBox(10, openOutputWindowButton, outputTextArea);
        return vbox_outputtextarea;
    }

            private void openOutputWindow(TextArea outputTextArea) {
                Stage outputStage = new Stage();
                outputStage.setTitle("Energy Monitoring Output");
                TextArea outputInWindow = new TextArea(outputTextArea.getText());
                outputInWindow.setEditable(false);
                outputInWindow.setStyle(darkModeStyles);
                outputInWindow.setWrapText(true);

                Scene outputScene = new Scene(new StackPane(outputInWindow), 800, 600);

                outputStage.setScene(outputScene);
                outputStage.show();
            }

            // Method to display the output in the TextArea
    private void displayOutputInTextArea(String output) {
        outputTextArea.appendText("\nEnergy Monitoring Output:\n" + output);
        outputTextArea.positionCaret(outputTextArea.getText().length());
    }
    private void showAboutUsPage() {
        Stage aboutUsStage = new Stage();

        // Create the content for the "About Us" page
        Label aboutUsLabel = new Label("Welcome to the VMWattEco!\n\n" +
                "This project is a part of the Capstone Project of  CSD5002 - Virtualization Essentials (Fall Semester 2023-24) by Dr. Hemraj S.L.\n" +
                "By  M.Pon Dinesh Kumar (Reg No: 20MEI10010)\n\n"+
                "This application uses Medusa for gauges, OpenJFX (JavaFX) for the graphical user interface, and Maven for project management. Here's a brief overview of each:\n" +
                "- CloudSim Plus: A Java 17 simulation framework that simplifies Cloud computing modeling and experimentation. It empowers developers to focus on design without worrying about low-level infrastructure details, based on CloudSim 3.\n" +
                "- Medusa: A gauge library for JavaFX that enables the creation of visually appealing and interactive gauges and displays.\n" +
                "- OpenJFX (JavaFX): A framework for building rich client applications using Java. It's commonly used for creating graphical user interfaces (GUIs) in Java applications.\n" +
                "- Maven: A project management and build automation tool. It simplifies the build process and handles project dependencies.");

        // Create a layout for the "About Us" page
        VBox aboutUsLayout = new VBox(10);
        aboutUsLayout.getChildren().addAll(aboutUsLabel);
        aboutUsLayout.setStyle(darkModeStyles);
        aboutUsLabel.setStyle(darkModeStyles);
        aboutUsLayout.setMargin(aboutUsLabel, new Insets(20));// Set top and bottom margins to 10 pixels

        // Create the "Back" button to return to the main GUI
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            aboutUsStage.close();
        });
        aboutUsLayout.getChildren().add(backButton);

        // Create a scene for the "About Us" page
        Scene aboutUsScene = new Scene(aboutUsLayout, 1300, 500);

        // Set the stage title and show the "About Us" page
        aboutUsStage.setTitle("About Us");
        aboutUsStage.setScene(aboutUsScene);
        aboutUsStage.show();
    }
    class ConfigurationManager {
        private final Map<String, Configuration> configurations = new HashMap<>();

            public void addConfiguration(String name, int vmCores, int noOfHosts, int vmNo, int cloudletNo,
                                         int hostCores, int cloudletCores, int vmRam, int vmBw, int vmStorage,
                                         int cloudletLength, int hostRam, int hoststorage, int hostbw  ) {
                configurations.put(name, new Configuration(vmCores, noOfHosts, vmNo, cloudletNo,
                        hostCores, cloudletCores, vmRam, vmBw, vmStorage, cloudletLength, hostRam,hoststorage,hostbw));
            }

        public Set<String> getConfigurationNames() {
            return configurations.keySet();
        }
        public Configuration getConfiguration(String name) {
            return configurations.get(name);
        }
    }
            class Configuration {
                int vmCores;
                int noOfHosts;
                int noOfVMs;
                int noOfCloudlets;
                int hostCores;
                int cloudletCores;
                int vmRam;
                int vmBw;
                int vmStorage;
                int cloudletLength;
                int hostRam;
                int hoststorage;
                int hostbw;

                public Configuration(int vmCores, int noOfHosts, int noOfVMs, int noOfCloudlets, int hostCores, int cloudletCores,
                                     int vmRam, int vmBw, int vmStorage, int cloudletLength, int hostRam, int hoststorage, int hostbw ) {
                    this.vmCores = vmCores;
                    this.noOfHosts = noOfHosts;
                    this.noOfVMs = noOfVMs;
                    this.noOfCloudlets = noOfCloudlets;
                    this.hostCores = hostCores;
                    this.cloudletCores = cloudletCores;
                    this.vmRam = vmRam;
                    this.vmBw = vmBw;
                    this.vmStorage = vmStorage;
                    this.cloudletLength = cloudletLength;
                    this.hostRam = hostRam;
                    this.hostbw = hostbw;
                    this.hoststorage = hoststorage;
                }
            }
        }


