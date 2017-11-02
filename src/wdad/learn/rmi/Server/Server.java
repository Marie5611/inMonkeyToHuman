package wdad.learn.rmi.Server;

import wdad.data.managers.PreferencesManager;
import wdad.learn.rmi.Client.XmlDataManager;
import wdad.utils.PreferencesConstantManager;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {

    public static void main(String[] args) throws Exception {
        PreferencesManager preferencesManager = PreferencesManager.getInstance();
        Registry registry = null;
        try {
            if (preferencesManager.getProperty(PreferencesConstantManager.CREATEREGISTRY).equals("yes"))
                registry = LocateRegistry.createRegistry(Integer.parseInt(preferencesManager.getProperty(PreferencesConstantManager.REGISTRYPORT)));
            else
                registry = LocateRegistry.getRegistry(Integer.parseInt(preferencesManager.getProperty(PreferencesConstantManager.REGISTRYPORT)));
        } catch (RemoteException er) {
            System.err.println("errorable registry");
            er.printStackTrace();
        }
        registry.rebind("XmlDataManager", new XmlDataManagerImpl());
        preferencesManager.addBindedObject("XmlDataManager", XmlDataManager.class.getCanonicalName());
        System.out.println("Запуск сервера");
    }
}

