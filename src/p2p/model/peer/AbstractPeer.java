package p2p.model.peer;

import java.io.File;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import p2p.model.file.FileSystemImpl;
import p2p.model.file.IFileSystem;
import p2p.model.peer.remoting.IPeer;
import p2p.view.NodeUI;

public abstract class AbstractPeer extends UnicastRemoteObject implements IPeer, Serializable {
	
	private static final long serialVersionUID = 1L;

	protected String id;
	protected String hostDistant;
	protected String pathFileSystem;
	private IFileSystem fileSystemUtil;
	protected ConcurrentHashMap<String, File> availableFiles;
	protected NodeUI ihm;
	
	
	protected AbstractPeer(String hostDistant, String id, String pathFileSystem) throws RemoteException {
		super();
		this.hostDistant = hostDistant;
		this.id = id;
		this.pathFileSystem = pathFileSystem;
		this.setFileSystemUtil(new FileSystemImpl());
		this.availableFiles = new ConcurrentHashMap<String, File>();
		for(File file : new File(pathFileSystem).listFiles()){
			availableFiles.put(file.getName(), file);
		}
		ihm = new NodeUI(id, this);
	}
	
	@Override
	public List<File> getAllLocalFile() throws RemoteException {
		this.availableFiles = new ConcurrentHashMap<String, File>();
		for(File file : new File(pathFileSystem).listFiles()){
			availableFiles.put(file.getName(), file);
		}
		List<File> result = new LinkedList<File>();
		for(String fileName : availableFiles.keySet()){
			result.add(availableFiles.get(fileName));
		}
		return result;
	}
	
	@Override
	public String[] getAllLocalFileNames() throws RemoteException {
		String[] res = new String[getAllLocalFile().size()];
		int i=0;
		for(File file : getAllLocalFile()){
			res[i]=file.getName();
			i++;
		}
		return res;
	}
	
	@Override
	public File getFile(String fileName) throws RemoteException {
		return availableFiles.get(fileName);
	}

	/**
	 * @return the id
	 */
	@Override
	public String getId() {
		return id;
	}

	/**
	 * @return the fileSystemUtil
	 */
	@Override
	public IFileSystem getFileSystemUtil() throws RemoteException {
		return fileSystemUtil;
	}

	/**
	 * @param fileSystemUtil the fileSystemUtil to set
	 */
	public void setFileSystemUtil(IFileSystem fileSystemUtil) {
		this.fileSystemUtil = fileSystemUtil;
	}
	
}
