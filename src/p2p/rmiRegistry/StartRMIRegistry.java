package p2p.rmiRegistry;

import java.io.File;

public class StartRMIRegistry {
	
	public static void main(String[] args) {
		
		int rmiPort = RmiRegistry.RMI_REGISTRY_DEFAULT_PORT;
		String rootName = RmiRegistry.getIdRacine();
		String rootFileSystemPath = RmiRegistry.getRootFileSystemPath();
		
		if(args.length == 0){
			System.err.println("Usage : $java "+StartRMIRegistry.class.getName()+" <rmiPort> <rootName> <rootFileSystemPath>");
			System.out.println("Exemple : $java "+StartRMIRegistry.class.getName()+" 1099 root /Users/tarik/Desktop/test");
		}else{
			rmiPort = isPortValid(args[0]);
			rootName = args[1]!=null && !args[1].isEmpty() ? args[1] : RmiRegistry.getIdRacine();
			rootFileSystemPath = args[2]!=null && new File(args[2]).exists() ? args[2] : RmiRegistry.getRootFileSystemPath();
		}
		RmiRegistry.rmiRegistryStart(rmiPort, rootName, rootFileSystemPath);
	}
	
	private static int isPortValid(String number){
		try{
			return Integer.parseInt(number);
		}catch(NumberFormatException nfe){
			return RmiRegistry.RMI_REGISTRY_DEFAULT_PORT;
		}
	}
}
