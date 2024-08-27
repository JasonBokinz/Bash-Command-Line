/**
 * This class represents the nodes in the file tree.
 * @author jasonbokinz, ID: 112555537, R:03
 */
public class DirectoryNode {
	/**
	 * @param name
	 * the name of the node in the tree
	 * @param isFile
	 * if the node is a file or not
	 * @param children
	 * array of children directory nodes
	 */
	private String name;
	private boolean isFile;
	private DirectoryNode parent;
	private DirectoryNode[] children;
	/**
	 * This method is used to access the children of the nodes
	 * @return
	 * array of the children of the nodes
	 */
	public DirectoryNode[] getChildren() {
		return children;
	}
	/**
	 * This method is used to set the children of the node
	 * @param children
	 * new array of children
	 */
	public void setChildren(DirectoryNode[] children) {
		this.children = children;
	}
	/**
	 * This method is used to access the parent of the node
	 * @return
	 * reference to the parent of the node
	 */
	public DirectoryNode getParent() {
		return parent;
	}
	/**
	 * This method is used to set the node's parent
	 * @param parent
	 * parent of the node
	 */
	public void setParent(DirectoryNode parent) {
		this.parent = parent;
	}
	/**
	 * This creates a default constructor for a DirectoryNode
	 */
	public DirectoryNode() {
		parent = null;
		name = null;
		isFile = false;
		children = new DirectoryNode[10];
	}
	/**
	 * This method is used to access if the node is a file
	 * @return
	 * true if it is a file and false if not
	 */
	public boolean getIsFile() {
		return isFile;
	}
	/**
	 * This method is used to set the node's isFile variable
	 * @param isFile
	 * if the node is a file
	 */
	public void setIsFile(boolean isFile) {
		this.isFile = isFile;
	}
	/**
	 * This method is used to access the node's name
	 * @return
	 * the name of the node
	 */
	public String getName() {
		return name;
	}
	/**
	 * This method is used to set the node's name
	 * @param name
	 * node's new name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * This method adds a new child to any of the open child positions of this node
	 * @param node
	 * new child node being added
	 * @throws FullDirectoryException
	 * Thrown if all child references of this directory are occupied
	 * @throws NotADirectoryException
	 * Thrown if the current node is a file
	 */
	public void addChild(DirectoryNode newChild) throws FullDirectoryException, NotADirectoryException {
		boolean changed = false;
		if (this.getIsFile())
			throw new NotADirectoryException("This node is a file!");
		for (int i=0; i < 10; i++) {
			if (children[i] == null) {
				children[i] = newChild;
				changed = true;
				break;
			}
		}
		if (changed == false) {
			throw new FullDirectoryException("ERROR: Present directory is full.");
		}
		newChild.setParent(this);
	}
}
