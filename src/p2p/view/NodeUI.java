package p2p.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ScrollPaneConstants;

import p2p.model.peer.remoting.IPeer;

public class NodeUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
	protected JList lnode, lfile ;
	protected JButton brefresh, bfiles, bupload, bexit ;
    protected IPeer peer ;
	
    public NodeUI (String idPeer, IPeer peer) {
		super (idPeer) ;
		this.peer = peer ;
		this.init () ;
		this.pack () ;
		this.setVisible (true) ;
		this.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE) ;
	}
	
	public void init () {
		/* list of nodes */
		this.lnode = new JList () ;
		javax.swing.JScrollPane scrollPaneln = new javax.swing.JScrollPane(lnode);
		scrollPaneln.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		/* list of file for a selected node */
		this.lfile = new JList () ;
		javax.swing.JScrollPane scrollPanelf = new javax.swing.JScrollPane(lfile);
		scrollPanelf.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		javax.swing.JSplitPane splitPane = new javax.swing.JSplitPane(javax.swing.JSplitPane.HORIZONTAL_SPLIT,scrollPaneln,scrollPanelf);
		this.getContentPane().add (splitPane, BorderLayout.CENTER) ;
		
		JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		/* button for refreshing node list */
		this.brefresh = new JButton ("Nodes") ;
		this.brefresh.addActionListener (new RefreshListener ()) ;
		p.add (this.brefresh) ;

		/* button for refreshing files list */
		this.bfiles = new JButton ("Files") ;
		this.bfiles.addActionListener (new FilesListener ()) ;
		p.add (this.bfiles) ;

		/* button for downloading file on P2P */
		this.bupload = new JButton ("Upload") ;
		this.bupload.addActionListener (new UploadListener ()) ;
		p.add (this.bupload) ;
		
		/* button for exiting app P2P */
		this.bexit = new JButton ("Exit") ;
		this.bexit.addActionListener (new ExitListener ()) ;
		p.add (this.bexit) ;
		
		this.getContentPane().add (p, BorderLayout.SOUTH) ;
	}
	
	class RefreshListener implements ActionListener {
		public void actionPerformed (ActionEvent evt) {
			try {
			    lnode.setListData(peer.getAllConnectPeerNames());
				lfile.setListData(new String[0]);
			} catch (Exception e) {
				e.printStackTrace () ;
			}
		}
	}

	class FilesListener implements ActionListener {
		public void actionPerformed (ActionEvent evt) {
			try {
				lfile.setListData(new String[0]);
				String nname = (String)lnode.getSelectedValue () ;
				if (nname == null) return ;
				IPeer distant = (IPeer) peer.getPeer(nname);
				lfile.setListData(distant.getAllLocalFileNames());
			} catch (Exception e) {
				e.printStackTrace () ;
			}
		}
	}

	class UploadListener implements ActionListener {
		public void actionPerformed (ActionEvent evt) {
			try {
				String nname = (String) lnode.getSelectedValue () ;
				String fname = (String) lfile.getSelectedValue () ;
				if (nname == null || fname == null) return ;
				IPeer distant = (IPeer) peer.getPeer(nname);
				File source = distant.getFile(fname);
				File dest = new File("/Users/tarik/Desktop/testDest");
				Long timeTask = distant.getFileSystemUtil().writeFile(source, dest);
				System.out.println(timeTask);
				//TODO faire une barre de progression du download en Swing avec la duree du telechargement
			} catch (Exception e) {
				e.printStackTrace () ;
			}
		}
	}
	
	class ExitListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
				peer.exitApp();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				System.exit(EXIT_ON_CLOSE);
			}
		}
		
	}

}
