package p2p.model.peer;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import p2p.model.peer.remoting.IPeer;
import p2p.rmiRegistry.RmiRegistry;

public class Root extends AbstractPeer {

	private static final long serialVersionUID = 1L;
	private ConcurrentHashMap<String, IPeer> thePeers;

	public Root(String id, String pathFileSystem) throws RemoteException {
		super(RmiRegistry.HOST, id, pathFileSystem);
		setThePeers(new ConcurrentHashMap<String, IPeer>());
		thePeers.put(id, this);
	}

	@Override
	public List<IPeer> getAllConnectPeer() throws RemoteException {
		return mapToList();
	}

	@Override
	public void addPeer(IPeer peer) throws RemoteException {
		thePeers.put(peer.getId(), peer);
		try {
			Naming.rebind(peer.getId(), peer);
			System.out.println(peer.getId()+" peer is ready in rmi registry.");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void deletePeer(IPeer peerDisconnected) throws RemoteException {
		thePeers.remove(peerDisconnected.getId(), peerDisconnected);
		try {
			Naming.unbind(peerDisconnected.getId());
			System.out.println(peerDisconnected.getId()+" peer destroyed in rmi registry.");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public IPeer getPeer(String idPeer) throws RemoteException {
		return thePeers.get(idPeer);
	}
	
	@Override
	public String[] getAllConnectPeerNames() throws RemoteException {
		String[] res = new String[thePeers.keySet().size()];
		int i=0;
		for(Iterator<String> it = thePeers.keySet().iterator();it.hasNext();i++){
			res[i]=it.next();
		}
		return res;
	}
	
	private List<IPeer> mapToList(){
		List<IPeer> result = new LinkedList<IPeer>();
		for(String peerId : getThePeers().keySet()){
			result.add(getThePeers().get(peerId));
		}
		return result;
	}
	
	/**
	 * @return the thePeers
	 */
	public ConcurrentHashMap<String, IPeer> getThePeers() {
		return thePeers;
	}

	/**
	 * @param thePeers the thePeers to set
	 */
	public void setThePeers(ConcurrentHashMap<String, IPeer> thePeers) {
		this.thePeers = thePeers;
	}

	@Override
	public void exitApp() throws RemoteException {
		
		thePeers.remove(getId());
		// If root shutdown, kill all peer on the network P2P
		for(String peerDistant : thePeers.keySet()){
			try {
				IPeer peer = (IPeer) Naming.lookup (RmiRegistry.HOST + peerDistant);
				peer.exitApp();
			} catch (Exception e) {
				continue;
			}
		}
		RmiRegistry.stopRmiRegistry();
		System.exit(0);
	}

}
