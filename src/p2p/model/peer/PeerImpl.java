package p2p.model.peer;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import p2p.model.peer.remoting.IPeer;
import p2p.rmiRegistry.RmiRegistry;

public class PeerImpl extends AbstractPeer {

	private static final long serialVersionUID = 1L;
	private IPeer root;
	private List<IPeer> availablePeers;

	public PeerImpl(String hostDistant, String id, String pathFileSystem) throws Exception {
		super(hostDistant, id, pathFileSystem);
		String lookupPath = hostDistant + RmiRegistry.getIdRacine();
		root = (IPeer) Naming.lookup (lookupPath);
		availablePeers = new ArrayList<IPeer>();
		root.addPeer(this);
	}
	
	@Override
	public IPeer getPeer(String idPeer) throws RemoteException {
		return root.getPeer(idPeer);
	}

	@Override
	public List<IPeer> getAllConnectPeer() throws RemoteException {
		this.availablePeers = root.getAllConnectPeer();
		return availablePeers;
	}

	@Override
	public void addPeer(IPeer peer) throws RemoteException {
		availablePeers.add(peer);
	}
	
	@Override
	public void deletePeer(IPeer peerDisconnected) throws RemoteException {
		availablePeers.remove(peerDisconnected);
	}

	@Override
	public String[] getAllConnectPeerNames() throws RemoteException {
		String[] res = new String[getAllConnectPeer().size()];
		for(int i=0;i<getAllConnectPeer().size();i++){
			res[i]=getAllConnectPeer().get(i).getId();
		}
		return res;
	}

	@Override
	public void exitApp() throws RemoteException {
		root.deletePeer(this);
		ihm.dispose();
		System.exit(0);
	}

}
