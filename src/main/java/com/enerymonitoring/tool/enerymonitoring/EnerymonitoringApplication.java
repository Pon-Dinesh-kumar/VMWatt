package com.enerymonitoring.tool.enerymonitoring;

import org.cloudsimplus.brokers.DatacenterBroker;
import org.cloudsimplus.brokers.DatacenterBrokerSimple;
import org.cloudsimplus.builders.tables.CloudletsTableBuilder;
import org.cloudsimplus.cloudlets.Cloudlet;
import org.cloudsimplus.cloudlets.CloudletSimple;
import org.cloudsimplus.core.CloudSimPlus;
import org.cloudsimplus.datacenters.Datacenter;
import org.cloudsimplus.datacenters.DatacenterSimple;
import org.cloudsimplus.hosts.Host;
import org.cloudsimplus.hosts.HostSimple;
import org.cloudsimplus.power.models.PowerModelHostSimple;
import org.cloudsimplus.resources.Pe;
import org.cloudsimplus.resources.PeSimple;
import org.cloudsimplus.schedulers.vm.VmSchedulerTimeShared;
import org.cloudsimplus.utilizationmodels.UtilizationModelDynamic;
import org.cloudsimplus.utilizationmodels.UtilizationModelFull;
import org.cloudsimplus.vms.HostResourceStats;
import org.cloudsimplus.vms.Vm;
import org.cloudsimplus.vms.VmResourceStats;
import org.cloudsimplus.vms.VmSimple;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import static java.util.Comparator.comparingLong;


public class EnerymonitoringApplication {


	private static final int SCHEDULING_INTERVAL = 10;
	private static int HOSTS = 2;

	/** PES  stands for Processing Element.*/
	private static int HOST_PES = 8;

	private static final int HOST2_PES= 4; //second host config

	private static long HOST_RAM = 2048; //in Megabytes
	private static long HOST_BW = 10000; //bandwidth in Megabits/s
	private static long HOST_Storage = 1000000; //in Megabytes

	/**   time (in seconds) the Host takes to start up. */
	private static final double HOST_START_UP_DELAY = 5;

	/**   time (in seconds) the Host takes to shut down. */
	private static final double HOST_SHUT_DOWN_DELAY = 3;

	/**  Host power consumption (in Watts) during startup. */
	private static final double HOST_START_UP_POWER = 5;

	/**  Host power consumption (in Watts) during shutdown. */
	private static final double HOST_SHUT_DOWN_POWER = 3;

	private static int VMS = 4;

	/** HERE WE CAN SPECIFY WHICH VM TO USE THE CUSTOM CONFIG USING VMS_CUSTOM_CONFIG **/

	private static int VM_PES_DEFAULT = 4;

	//private static int[] vmPesConfigurations = {3, 3}; // Configurations for each VM


	private static int VM_RAM_DEFAULT = 512;

	private static int VM_BW_DEFAULT= 1000;

	private static int VM_STORAGE_DEFAULT = 10000;

	/**  Cloudlet  represents an application running inside a Vm */
	private static int CLOUDLETS = 8;
	private static int CLOUDLET_PES = 2;

	private static final int CLOUDLET2_PES = 4;//secondary VM config
	private static int CLOUDLET_LENGTH = 50000;

	private static final int CLOUDLET2LENGTH= 40000;

	/**
	 * Defines the power a Host uses, even if it's idle (in Watts).
	 */
	private static final double STATIC_POWER = 35;

	/**
	 * The max power a Host uses (in Watts).
	 */
	private static final int MAX_POWER = 50;

	private final CloudSimPlus simulation;
	private final DatacenterBroker broker0;
	private List<Vm> vmList;

	private List<Cloudlet> cloudletList;
	private Datacenter datacenter0;
	private final List<Host> hostList;
    private ByteArrayOutputStream outputBuffer;
    private PrintStream originalOut;

	private double averageHostEnergyConsumption;
	private double averageVMEnergyConsumption;

	Map<Host, Double> hostPowerConsumptionMap = new HashMap<>();
	Map<Host, Double> hostFinishTimeMap = new HashMap<>();
	Map<Vm, Double> vmPowerConsumptionMap = new HashMap<>();
	Map<Vm, Double> vmFinishTimeMap = new HashMap<>();
    private double totalHostEnergyConsumption;
    private double totalVMEnergyConsumption;

    public static void main(String[] args) {
        EnerymonitoringApplication energyMonitoringApp = new EnerymonitoringApplication();
        String output = energyMonitoringApp.getOutput();
        System.out.println("Simulation Output:");
        System.out.println(output);
	}




	EnerymonitoringApplication() {
		simulation = new CloudSimPlus();
		hostList = new ArrayList<>(HOSTS);
		/**  Datacenter represent the different data centers that make up the cloud infrastructure.*/

		datacenter0 = createDatacenter();
		//Creates a broker that is a software acting on behalf of a cloud customer to manage his/her VMs and Cloudlets
		broker0 = new DatacenterBrokerSimple(simulation);

		vmList = createVms();
		cloudletList = createCloudlets();
		broker0.submitVmList(vmList);
		broker0.submitCloudletList(cloudletList);
        // Initialize outputBuffer and originalOut for capturing output
        outputBuffer = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputBuffer));
		simulation.start();
		System.out.println("------------------------------- SIMULATION FOR SCHEDULING INTERVAL = " + SCHEDULING_INTERVAL+" -------------------------------");
		System.out.println("SIMULATION OF HOSTS = " + HOSTS + " CLOUDLETS = " + CLOUDLETS + " VM = " + VMS);
		final var cloudletFinishedList = broker0.getCloudletFinishedList();
		final Comparator<Cloudlet> hostComparator = comparingLong(cl -> cl.getVm().getHost().getId());
		cloudletFinishedList.sort(hostComparator.thenComparing(cl -> cl.getVm().getId()));

		new CloudletsTableBuilder(cloudletFinishedList).build();
		printHostsCpuUtilizationAndPowerConsumption();
		printVmsCpuUtilizationAndPowerConsumption();
		printhostenergyconsumption();
		printvmenergyconsumption();
        printAverageHostEnergyConsumption();
        printAverageVMEnergyConsumption();



	}



	private void printVmsCpuUtilizationAndPowerConsumption() {
		vmList.sort(comparingLong(vm -> vm.getHost().getId()));
		for (Vm vm : vmList) {
			final var powerModel = vm.getHost().getPowerModel();
			final double hostStaticPower = powerModel instanceof PowerModelHostSimple powerModelHost ? powerModelHost.getStaticPower() : 0;
			final double hostStaticPowerByVm = hostStaticPower / vm.getHost().getVmCreatedList().size();

			//VM CPU utilization relative to the host capacity
			final double vmRelativeCpuUtilization = vm.getCpuUtilizationStats().getMean() / vm.getHost().getVmCreatedList().size();
			final double vmPower = powerModel.getPower(vmRelativeCpuUtilization) - hostStaticPower + hostStaticPowerByVm; // W
			final VmResourceStats cpuStats = vm.getCpuUtilizationStats();

			// Store power consumption and finish time for VM
			vmPowerConsumptionMap.put(vm, vmPower);
			vmFinishTimeMap.put(vm, simulation.clock());
		}
	}





	public double getAverageHostEnergyConsumption() {
		return averageHostEnergyConsumption;
	}

	public double getAverageVMEnergyConsumption() {
		return averageVMEnergyConsumption;
	}


	/**
	 * The Host CPU Utilization History is only computed
	 * if VMs utilization history is enabled by calling
	 * {@code vm.getUtilizationHistory().enable()}.
	 */
	private void printHostsCpuUtilizationAndPowerConsumption() {
		System.out.println();
		for (final Host host : hostList) {
			printHostCpuUtilizationAndPowerConsumption(host);
		}
		System.out.println();
	}

	private void printHostCpuUtilizationAndPowerConsumption(Host host) {
		final HostResourceStats cpuStats = host.getCpuUtilizationStats();

		//The total Host's CPU utilization for the time specified by the map key
		final double utilizationPercentMean = cpuStats.getMean();
		final double watts = host.getPowerModel().getPower(utilizationPercentMean);
		// Store power consumption and finish time for Host
		hostPowerConsumptionMap.put(host, watts);
		hostFinishTimeMap.put(host, simulation.clock());

	}



	private void printhostenergyconsumption() {
		System.out.println("Host Energy Consumption based on Power Consumption and Time:");
		for (Map.Entry<Host, Double> entry : hostPowerConsumptionMap.entrySet()) {
			Host host = entry.getKey();
			double powerConsumption = entry.getValue();
			double finishTime = hostFinishTimeMap.getOrDefault(host, 0.0);
			double energyConsumed = powerConsumption * finishTime;
            totalHostEnergyConsumption += energyConsumed;// Add to the total energy consumption

            System.out.printf("Host %2d - Power Consumption: %.2f W, Finish Time: %.2f s%n", host.getId(), powerConsumption, finishTime);

			System.out.printf("Host %2d - Energy Consumption: %.2f Joules%n", host.getId(), energyConsumed);
		}
        averageHostEnergyConsumption = totalHostEnergyConsumption / HOSTS;


    }



	private void printvmenergyconsumption() {
		System.out.println("VM Energy Consumption based on Power Consumption and Time:");
		for (Map.Entry<Vm, Double> entry : vmPowerConsumptionMap.entrySet()) {
			Vm vm = entry.getKey();
			double powerConsumption = entry.getValue();
			double finishTime = vmFinishTimeMap.getOrDefault(vm, 0.0);
			double energyConsumed = powerConsumption * finishTime;
            totalVMEnergyConsumption += energyConsumed; // Add to the total energy consumption
            System.out.printf("VM %2d - Power Consumption: %.2f W, Finish Time: %.2f s%n", vm.getId(), powerConsumption, finishTime);
			System.out.printf("VM %2d - Energy Consumption: %.2f Joules%n", vm.getId(), energyConsumed);
		}
// Calculate the average energy consumption for VMs
        averageVMEnergyConsumption = totalVMEnergyConsumption / VMS;

	}

    private void printAverageHostEnergyConsumption() {
        //System.out.println("Average Host Energy Consumption: " + averageHostEnergyConsumption + " Joules");
    }

    private void printAverageVMEnergyConsumption() {
        //System.out.println("Average VM Energy Consumption: " + averageVMEnergyConsumption + " Joules");
    }







	/**
	 * Creates a {@link Datacenter} and its {@link Host}s.
	 */
	private Datacenter createDatacenter() {

		for(int i = 0; i < HOSTS; i++) {
			final var host = createPowerHost(i);
			hostList.add(host);
		}

		final var dc = new DatacenterSimple(simulation, hostList);
		dc.setSchedulingInterval(SCHEDULING_INTERVAL);
		return dc;
	}

	private Host createPowerHost(final int id) {
		final var peList = new ArrayList<Pe>(HOST_PES);
		//List of Host's CPUs (Processing Elements, PEs)
		for (int i = 0; i < HOST_PES; i++) {
			peList.add(new PeSimple(1000));
		}


		final var vmScheduler = new VmSchedulerTimeShared();

		final var host = new HostSimple(HOST_RAM, HOST_BW, HOST_Storage, peList);
		host.setStartupDelay(HOST_START_UP_DELAY)
				.setShutDownDelay(HOST_SHUT_DOWN_DELAY);

		final var powerModel = new PowerModelHostSimple(MAX_POWER, STATIC_POWER);
		powerModel
				.setStartupPower(HOST_START_UP_POWER)
				.setShutDownPower(HOST_SHUT_DOWN_POWER);

		host.setId(id)
				.setVmScheduler(vmScheduler)
				.setPowerModel(powerModel);
		host.enableUtilizationStats();
		// Change HOST_PES for the last host (for example, set it to 4 for the last host)


		return host;
	}

	/**
	 * Creates a list of VMs.
	 * with  the vm cpu in term of processing elements
	 * ram  , bandwith storage spec defined
	 */
	private List<Vm> createVms() {

			final var list = new ArrayList<Vm>(VMS);
			for (int i = 0; i < VMS ; i++) {
				//final int vmPes = (i < vmPesConfigurations.length) ? vmPesConfigurations[i] : VM_PES_DEFAULT;
				final var vm = new VmSimple(i, 1000, VM_PES_DEFAULT);
					vm.setRam(VM_RAM_DEFAULT).setBw(VM_BW_DEFAULT).setSize(VM_STORAGE_DEFAULT).enableUtilizationStats();
					list.add(vm);


			}

			return list;


	}

	/**
	 * Creates a list of Cloudlets.
	 * they are representation  of  application running inside a Vm
	 */
	private List<Cloudlet> createCloudlets() {
		final var cloudletList = new ArrayList<Cloudlet>(CLOUDLETS);
		final var utilization = new UtilizationModelDynamic(0.2);
		for (int i = 0; i < CLOUDLETS; i++) {

			//Sets half of the cloudlets with the defined length and the other half with the double of it
				final long length = i < CLOUDLETS / 2 ? CLOUDLET_LENGTH : CLOUDLET_LENGTH * 2;

				final var cloudlet =
						new CloudletSimple(i, length, CLOUDLET_PES)
								.setFileSize(1024)
								.setOutputSize(1024)
								.setUtilizationModelCpu(new UtilizationModelFull())
								.setUtilizationModelRam(utilization)
								.setUtilizationModelBw(utilization);
				cloudletList.add(cloudlet);

		}

		return cloudletList;
	}


	// Setter method to update the VM_PES variable
	public static void setVmPes(int vmPes) {
		VM_PES_DEFAULT = vmPes;
	}

	public static void  setHosts(int noofhost){
		HOSTS = noofhost;
	}
	public static void setVms(int vmno){
		VMS = vmno;
	}
	public static void setCloudletno(int cloudletno){
		CLOUDLETS = cloudletno;
	}

	public static void setHostcores(int hostcores) {HOST_PES = hostcores;}
	public static void setCLOUDLETS_core(int cloudletCore){
		CLOUDLET_PES =  cloudletCore;
	}

	public static void setVM_RAM_DEFAULT(int ramDefault) {
		VM_RAM_DEFAULT = ramDefault;
	}

	public static void setVM_BW_DEFAULT(int bwDefault) {
		VM_BW_DEFAULT = bwDefault;
	}

	public static void setVM_STORAGE_DEFAULT(int storageDefault) {
		VM_STORAGE_DEFAULT = storageDefault;
	}

	public static void setCLOUDLET_LENGTH(int cloudletLength) {
		CLOUDLET_LENGTH = cloudletLength;
	}

	public static void setHOST_RAM(int hosts_ram){
		HOST_RAM = hosts_ram;

	}

	public static void setHOST_BW(int host_bw){
		HOST_BW = host_bw;
	}
	public 	static void setHOST_Storage(int host_storage){
		HOST_Storage = host_storage;
	}

    public String getOutput() {
        // Reset System.out to the original output stream
        System.setOut(originalOut);
        // Convert the captured output to a string and return it
        return outputBuffer.toString();
    }

}

