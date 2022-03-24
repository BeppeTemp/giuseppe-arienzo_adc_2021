package it.isislab.p2p.git.interfaces;

import java.io.File;

public interface PublishSubscribe {
	public boolean subscribetoTopic(String _topic_name);
	public boolean publishToTopic(String _topic_name, Object _obj);
	public boolean unsubscribeFromTopic(String _topic_name);

	public boolean leaveNetwork();
	
	/**
	 * Creates new repository in a directory
	 * @param _repo_name a String, the name of the repository.
	 * @param _directory a File, the directory where create the repository.
	 * @return true if it is correctly created, false otherwise.
	 */
	public boolean createRepository(String _repo_name, File _directory);

	// /**
	//  * Adds a list of File to the given local repository. 
	//  * @param _repo_name a String, the name of the repository.
	//  * @param files a list of Files to be added to the repository.
	//  * @return true if it is correctly added, false otherwise.
	//  */
	// public boolean addFilesToRepository(String _repo_name, List<File> files);

	// /**
	//  * Apply the changing to the files in the local repository.
	//  * @param _repo_name a String, the name of the repository.
	//  * @param _message a String, the message for this commit.
	//  * @return true if it is correctly committed, false otherwise.
	//  */
	// public boolean commit(String _repo_name, String _message);

	// /**
	//  * Push all commits on the Network. If the status of the remote repository is changed, 
	//  * the push fails, asking for a pull.
	//  * @param _repo_name _repo_name a String, the name of the repository.
	//  * @return a String, operation message.
	//  */
	// public String push(String _repo_name);

	// /**
	//  * Pull the files from the Network. If there is a conflict, the system duplicates 
	//  * the files and the user should manually fix the conflict.
	//  * @param _repo_name _repo_name a String, the name of the repository.
	//  * @return a String, operation message.
	//  */
	// public String pull(String _repo_name);
}