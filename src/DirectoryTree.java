/**
 * This class implements a tree of DirectoryNodes.
 * @author jasonbokinz, ID: 112555537, R:03
 */
public class DirectoryTree {
	/**
	 * @param root
	 * reference to the root of the tree
	 * @param cursor
	 * reference to the present working directory
	 */
	private DirectoryNode root, cursor;
	/**
	 * This method is used to access the root node
	 * @return
	 */
	public DirectoryNode getRoot() {
		return root;
	}
	/**
	 * This method is used to access the cursor node
	 * @return
	 * cursor node
	 */
	public DirectoryNode getCursor() {
		return cursor;
	}
	/**
	 * This method is used to set the cursor
	 * @param cursor
	 * new cursor to be set
	 */
	public void setCursor(DirectoryNode cursor) {
		this.cursor = cursor;
	}
	/**
	 * This creates a default constructor that is called to create the tree with the first node being the root
	 */
	public DirectoryTree() {
		root = new DirectoryNode();
		root.setName("root");
		cursor = root;
	}	
	/**
	 * This method is used to find a the node with the same name and print its working directory
	 * @param node
	 * node you are currently at
	 * @param name
	 * name of node in search of
	 */
	public void find(DirectoryNode node, String name) {
		if (node == null)
			return;
		if (node.getName().equals(name)) {
			setCursor(node);
			System.out.println(presentWorkingDirectory());
			return;
		}
		for (int i=0; i < 10; i++) {
			find(node.getChildren()[i], name);
		}
		return;
	}

	/**
	 * This method is used to reset the cursor back to the root
	 */
	public void resetCursor() {
		cursor = root;
	}
	/**
	 * This method is used to locate a directory with the name indicated
	 * @param name
	 * name of the directory in search of
	 * @throws NotADirectoryException
	 * Thrown if the node with the indicated name is a file
	 */
	public void changeDirectory(String name) throws NotADirectoryException {
		DirectoryNode changed = null;
		for (int i = 0; i < 10;i++) {
			if (cursor.getChildren()[i] != null) {
				if (cursor.getChildren()[i].getName().equals(name)) {
					changed = cursor.getChildren()[i];
					break;
				}
			}
		}
		if (changed != null) {
			if (!changed.getIsFile()) {
				cursor = changed;
			}
			else
				throw new NotADirectoryException("ERROR: Cannot change directory into a file.");
		}
		else {
			System.out.println("exception case");
			throw new NotADirectoryException("ERROR: No such directory named '" + name + "'.");
		}
	}
	/**
	 * This method is used to return a String containing the path of directory names from the root node of the tree to the cursor
	 * @return
	 * string described above
	 */
	public String presentWorkingDirectory() {
		String current = "";
		String result = "";
		DirectoryNode temp = cursor;
		while(temp.getParent() != null) {
			current = temp.getName();
			result = "/" + current + result;
			temp = temp.getParent();
		}
		/**
		 * Adds the root to the array
		 */
		result = temp.getName() + result;
		return result;
	}
	/**
	 * This method is used to return a string containing a space-separated list of names of all the child directories or files
	 * @return
	 * string described above
	 */
	public String listDirectory() {
		String result = "";
		for (int i = 0; i < 10; i++) {
			if (cursor.getChildren()[i] != null)
				result += cursor.getChildren()[i].getName() + " ";
		}
		return result.trim();
	}
	/**
	 * This method prints the directory starting from the cursor
	 */
	public void printDirectoryTree(DirectoryNode node, int depth) {
		if (node == null)
			return;
		for (int i = 0; i < depth; i++)
			System.out.print("    ");
		if (!node.getIsFile())
			System.out.print("|");
		
		System.out.println("- " + node.getName());
		for (int i = 0; i < 10; i++) {
			if (i == 0) {
				depth = depth +1;
				printDirectoryTree(node.getChildren()[0], depth);
			}
			else
				printDirectoryTree(node.getChildren()[i], depth);
		}
	}
	/**
	 * This method creates a new directory with the indicated name
	 * @param name
	 * name of the directory
	 * @throws FullDirectoryException
	 * Thrown if all child references of this directory are occupied.
	 * @throws IllegalArgumentException
	 * Thrown if the 'name' argument is invalid.
	 * @throws NotADirectoryException
	 * Thrown if the current node is a file(Since addChild is used)
	 */
	public void makeDirectory(String name) throws FullDirectoryException, IllegalArgumentException, NotADirectoryException {
		if (name.contains(" ") || name.contains("/"))
			throw new IllegalArgumentException(name + " contains an invalid character!");
		DirectoryNode newNode = new DirectoryNode();
		newNode.setName(name);
		cursor.addChild(newNode);
	}
	/**
	 * This method creates a new file with the indicated name
	 * @param name
	 * name of the file
	 * @throws FullDirectoryException
	 * Thrown if all child references of this directory are occupied.
	 * @throws IllegalArgumentException
	 * Thrown if the 'name' argument is invalid.
	 * @throws NotADirectoryException
	 * Thrown if the current node is a file (Since addChild is used)
	 */
	public void makeFile(String name) throws FullDirectoryException, IllegalArgumentException, NotADirectoryException {
		if (name.contains(" ") || name.contains("/"))
			throw new IllegalArgumentException(name + " contains an invalid character!");
		DirectoryNode newNode = new DirectoryNode();
		newNode.setName(name);
		newNode.setIsFile(true);
		cursor.addChild(newNode);
	}
	/**
	 * This method is used to set to find a node given its path as an array
	 * @param array
	 * string array path to node
	 * @param node
	 * node currently at
	 * @return
	 * array of the final node and it's parent
	 */
	public DirectoryNode [] cdPath(String [] array, DirectoryNode node) {
		DirectoryNode parent = null;
		for (int i =0; i < array.length; i++) {
			parent = node;
			for (int j = 0; j < 10; j++) {
				if (node.getChildren()[j] != null) {
					if (node.getChildren()[j].getName().equals(array[i])) {
						node = node.getChildren()[j];
						break;
					}
				}
				else
					break;
			}
		}
		DirectoryNode [] result = new DirectoryNode[2];
		result[0] = node;
		result[1] = parent;
		return result;
	}
}
