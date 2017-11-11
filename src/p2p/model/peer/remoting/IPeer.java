package p2p.model.peer.remoting;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import p2p.model.file.IFileSystem;

public interface IPeer extends Remote {

	/**
	 * Recupere la liste des noeuds auxquels le peer a accés.
	 * @return la liste des noeuds que le peer connait.
	 * @throws RemoteException
	 */
	public List<IPeer> getAllConnectPeer() throws RemoteException;
	
	/**
	 * Recupere la liste des noms des noeuds 
	 */
	public String[] getAllConnectPeerNames() throws RemoteException;
	
	/**
	 * Recupere la liste des fichiers que le peer dispose localement.
	 * @return la liste des fichiers disponibles localement.
	 * @throws RemoteException
	 */
	public List<File> getAllLocalFile() throws RemoteException;
	
	/**
	 * Recupere la liste des noms des fichiers 
	 */
	public String[] getAllLocalFileNames() throws RemoteException;
	
	/**
	 * Recupere le peer avec son nom
	 */
	public IPeer getPeer(String idPeer) throws RemoteException;
	
	/**
	 * Recupere le fichier avec son nom
	 */
	public File getFile(String fileName) throws RemoteException;
	
	/**
	 * Enregistrer un nouveau peer sur la base de connaissance du peer courant.
	 * @param peer le nouveau peer que l'on souhaite ajouter
	 * @throws RemoteException
	 */
	public void addPeer(IPeer peer) throws RemoteException;
	
	/**
	 * Supprimer un  peer sur la base de connaissance de la racine root.
	 * @param peer le peer que l'on souhaite supprimer apres sa deconnexion
	 * @throws RemoteException
	 */
	public void deletePeer(IPeer peerDisconnected) throws RemoteException;
	
	/**
	 * Recupere le nom d'un peer sur le reseau P2P
	 * @return
	 * @throws RemoteException
	 */
	public String getId() throws RemoteException;

	/**
	 * Objet donnant une abstraction sur les operations sur les fichiers
	 * @return
	 * @throws RemoteException
	 */
	public IFileSystem getFileSystemUtil() throws RemoteException;

	/**
	 * Exiting application handler
	 */
	public void exitApp() throws RemoteException;

}
