package p2p.model.file;

import java.io.File;

public interface IFileSystem {
	
	/**
	 * Ecrire un fichier en entree sur le systeme de fichier à la destination indiquee
	 * @param source le fichier que l'on souhaite ecrire
	 * @param dest la destination de la copie sur le systeme de fichier
	 * @return le temps en milliseconde de la copie en ecriture du fichier
	 */
	public Long writeFile(File source, File dest);

}
