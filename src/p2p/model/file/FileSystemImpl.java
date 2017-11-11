package p2p.model.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.channels.FileChannel;

public class FileSystemImpl implements IFileSystem, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public Long writeFile(File sourceFile, File destFile) {
		if(!destFile.exists()) {
			try {
				destFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		FileChannel source = null;
		FileChannel destination = null;
		Long startTime = 0L;
		Long endTime = 0L;
		try {
			source = new FileInputStream(sourceFile).getChannel();
			String destinationPath = destFile+"/"+sourceFile.getName();
			destination = new FileOutputStream(destinationPath).getChannel();
			startTime = System.currentTimeMillis();
			destination.transferFrom(source, 0, source.size());
			endTime = System.currentTimeMillis();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try{
				if(source != null) {
					source.close();
				}
				if(destination != null) {
					destination.close();
				}
			}catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		Long writingTime = endTime - startTime;
		return writingTime;
	}
}
