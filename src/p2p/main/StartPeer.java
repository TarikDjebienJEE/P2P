package p2p.main;

import java.io.File;

import p2p.model.peer.PeerImpl;
import p2p.rmiRegistry.RmiRegistry;

public final class StartPeer {

	private static final String LOCAL_DIRECTORY_TO_SHARE_DEFAULT = "/Users/tarik/Desktop/testDest";
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) {
		
		String hostDistant = RmiRegistry.HOST;
		String peerId = RmiRegistry.generateRandomName();
		String localDirectoryToShare = LOCAL_DIRECTORY_TO_SHARE_DEFAULT;
		
		if(args.length == 0){
			System.err.println("Usage : $java "+StartPeer.class.getName()+" <hostDistant> <peerName> <localDirectoryToShare>");
			System.out.println("Exemple : $java "+StartPeer.class.getName()+" //localhost/ totoPeer /Users/tarik/Desktop/shareP2P");
		}else{
			hostDistant = args[0]!=null && args[0].contains("/") ? args[0] : RmiRegistry.HOST;
			peerId = args[1]!=null && !args[1].isEmpty() ? args[1] : RmiRegistry.generateRandomName();
			localDirectoryToShare = args[2]!=null && new File(args[2]).exists() ? args[2] : LOCAL_DIRECTORY_TO_SHARE_DEFAULT;
		}
		
		try {
			new PeerImpl(hostDistant, peerId, localDirectoryToShare);
		} catch (Exception e) {
			System.out.println("Root est indisponible");
			System.exit(1);
		}
	}

}
