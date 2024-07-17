# 🌐 VMwatt: Energy Monitoring & Reporting for Virtual Machines (VMs) 🌐

 <img src="https://github.com/Pon-Dinesh-kumar/VMWatt/blob/main/SS.png">

Welcome to the VMwatt project repository! This project aims to develop a comprehensive tool for monitoring and reporting energy consumption in virtualized environments, specifically targeting Virtual Machines (VMs).

## 📚 Table of Contents
- [Problem Statement](#-problem-statement)
- [Motivation](#-motivation)
- [Objectives](#-objectives)
- [Technology Stack](#️-technology-stack)
- [How to Clone and Run](#️-how-to-clone-and-run)
- [Project Structure](#-project-structure)
- [Main Logic Explanation](#-main-logic-explanation)


## 🧩 Problem Statement
In the realm of energy monitoring and reporting, several critical challenges persist:
- **Lack of Accurate Hardware-Level Monitoring**: Current systems often struggle to provide the necessary precision for individual VMs.
- **Limitations of Guest Operating Systems**: Aggregating and reporting energy usage across the entire VM is complex.
- **Unifying Energy Monitoring and Reporting Solution**: Developing a unified solution capable of providing detailed insights into energy consumption.

## 💡 Motivation
My motivation stems from the pressing demand to enhance resource efficiency, reduce operational costs, and embrace environmental sustainability in data centers and cloud environments. I aim to create a versatile and precise tool that empowers organizations to make well-informed decisions regarding their energy consumption.

## 🎯 Objectives
The primary objectives of this project are:
- **Precise Power Consumption Calculation**: Develop a computational engine that accurately calculates the power consumption of both the host server and individual guest VMs.
- **Intuitive GUI Interface**: Create a user-friendly graphical interface for easy input of initialization details for the host and guest VMs.
- **Real-Time Meter Visualizations**: Implement a graphical representation module that provides real-time visual displays of power consumption.

## 🛠️ Technology Stack
- **CloudSim Plus**: Java 17 simulation framework for Cloud computing modeling.
- **Medusa**: Gauge library for JavaFX to create visually appealing and interactive gauges and displays.
- **OpenJFX (JavaFX)**: Framework for building rich client applications using Java.
- **Maven**: Project management and build automation tool.

## 🖥️ How to Clone and Run
Clone the repository:
```sh
git clone https://github.com/yourusername/vmwatt.git
cd vmwatt
.\mvnw.cmd clean install
.\mvnw javafx:run
```

## 📁 Project Structure
```
vmwatt/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com/
│   │   │   │   └── yourpackage/
│   │   │   │       ├── Main.java
│   │   │   │       ├── controllers/
│   │   │   │       ├── models/
│   │   │   │       └── views/
│   │   ├── resources/
│   │   │   └── fxml/
│   └── test/
├── pom.xml
└── README.md
```

## 🧠 Main Logic Explanation
The application uses CloudSim Plus for simulation purposes. The main components of CloudSim Plus are:
- **Datacentre**: Represents a location of cloud infrastructure with its VMs and hosts.
- **Hosts**: Physical machines or servers in the datacentre that run multiple VMs.
- **Virtual Machines (VMs)**: Virtualized computing instances that run on hosts.
- **Cloudlets**: User-defined tasks executed in a cloud environment.
- **Brokers**: Intermediaries between the cloud user and the datacentre.


## 🚀 Future Improvements
- **Enhanced Data Visualization**: Integrate more advanced data visualization tools for better insights.
- **Machine Learning Integration**: Use machine learning algorithms for predictive analysis of energy consumption.
- **Cloud Integration**: Extend the tool to support cloud providers like AWS, Azure, and GCP.


Feel free to contribute to this project by forking the repository and creating pull requests. For any issues, please open an issue in the repository. Happy coding! 🚀
