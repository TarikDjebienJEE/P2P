package p2p.rmiRegistry;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import p2p.model.peer.Root;
import p2p.model.peer.remoting.IPeer;

public final class RmiRegistry {

	public static final int RMI_REGISTRY_DEFAULT_PORT = 1099;
	private static boolean isRunning = false;
	public static final String HOST = "//localhost/";
	private static String ID_RACINE = "root";
	private static final String ROOT_FILE_SYSTEM_PATH = "/Users/tarik/Desktop/test";

	public static void rmiRegistryStart(int rmiPort, String rootName, String rootFileSystemPath) {
		try {
			java.rmi.registry.LocateRegistry.createRegistry( rmiPort > 0 ? rmiPort:RMI_REGISTRY_DEFAULT_PORT);
			startRmiRegistry();
		} catch (Exception e) {
			System.out.println("Exception starting RMI registry:");
			e.printStackTrace();
		}
		if(rmiRegistryIsRunning()){
			try {
				addRootToRegistry(rootName, rootFileSystemPath);
			} catch (Exception e) {
				e.printStackTrace();
			}
			while(rmiRegistryIsRunning()){}
			System.exit(0);
		}
	}

	private static void addRootToRegistry(String rootName, String rootFileSystemPath) throws Exception  {
		IPeer root = (IPeer) new Root(
				rootName!=null && !rootName.isEmpty() ? rootName : ID_RACINE, 
				rootFileSystemPath!=null && !rootFileSystemPath.isEmpty() ? rootFileSystemPath : ROOT_FILE_SYSTEM_PATH);
		ID_RACINE = root.getId();
		Naming.rebind(root.getId(), root);
		System.out.println(root.getId()+" peer is ready in rmi registry.");
	}

	/**
	 * @return the idRacine
	 */
	public static String getIdRacine() {
		return ID_RACINE;
	}
	
	/**
	 * @return the rootFileSystemPath
	 */
	public static String getRootFileSystemPath() {
		return ROOT_FILE_SYSTEM_PATH;
	}

	private static void startRmiRegistry(){
		isRunning = true;
		System.out.println("RMI registry is ready.");
	}

	public static void stopRmiRegistry(){
		try {
			Naming.unbind(ID_RACINE);
			System.out.println(ID_RACINE+" peer destroyed in rmi registry.");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}finally {
			isRunning = false;
			System.out.println("RMI registry is shutdown.");
		}
		
	}

	public static boolean rmiRegistryIsRunning(){
		return isRunning;
	}

	public static String generateRandomName() {
		String peerName = "peer("+ Math.random()+")";
		return peerName;
	}
}
