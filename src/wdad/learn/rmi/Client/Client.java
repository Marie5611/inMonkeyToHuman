package wdad.learn.rmi.Client;

import wdad.data.managers.PreferencesManager;
import wdad.utils.PreferencesConstantManager;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String[] args) throws Exception {
        PreferencesManager preferencesManager=PreferencesManager.getInstance();
        Registry registry = null;
        try {
            registry = LocateRegistry.getRegistry(Integer.parseInt(preferencesManager.getProperty(PreferencesConstantManager.REGISTRYPORT)));
        } catch (RemoteException er) {
            System.err.println("errorable registry");
            er.printStackTrace();
        }
        XmlDataManager xmlDataManager=(XmlDataManager)registry.lookup("XmlDataManager");
        System.out.println(xmlDataManager.salaryAverage());
        xmlDataManager.salaryAverage("Bedroom");
    }
}

