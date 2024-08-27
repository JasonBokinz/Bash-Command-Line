/**
 * This class allows a user to interact with a file system implemented by an instance of DirectoryTree.
 * @author jasonbokinz, ID: 112555537, R:03
 */
import java.util.Scanner;
public class BashTerminal {
	/**
	 * This method is used to take in arguments from the user
	 * @param args
	 * String of arguments
	 * @throws IllegalArgumentException
	 * Thrown if the 'name' argument is invalid.
	 * @throws FullDirectoryException
	 * Thrown if all child references of this directory are occupied.
	 * @throws NotADirectoryException
	 * Thrown if the current node is a file (Since addChild is used)
	 */
	public static void main(String[] args) throws IllegalArgumentException, FullDirectoryException, NotADirectoryException {
		DirectoryTree tree = new DirectoryTree();
		String selection = "";
		String consoleString = "[user@host]: $ ";
		Scanner input = new Scanner(System.in);
		DirectoryTree constructRoot = new DirectoryTree();
		
		System.out.println("Starting bash terminal.");
		
		while (!selection.equals("exit")) {
			
			try {
			
				System.out.print(consoleString);
				selection = input.nextLine();
			
				switch(selection) {
			
				case "pwd":
					String path = tree.presentWorkingDirectory();
					System.out.println(path);
					break;
				
				case "ls":
					String list = tree.listDirectory();
					System.out.println(list);
					break;
				
				case "ls -R":
					System.out.println();
					tree.printDirectoryTree(tree.getCursor(), 0);
					System.out.println();
					break;
				
				case "cd /":
					tree.resetCursor();
					break;
					
				case "cd ..":
					if (tree.getCursor().equals(tree.getRoot()))
						System.out.println("ERROR: Already at root directory.");
					else
						tree.setCursor(tree.getCursor().getParent());
					break;
				
				case "exit":
					break;
			
				default:
					if (selection.startsWith("mkdir ")) {
						String name = selection.substring(6);
						tree.makeDirectory(name);
					}
					else if (selection.startsWith("touch ")) {
						String name = selection.substring(6);
						tree.makeFile(name);
					}
					else if (selection.startsWith("find ")) {
						String name = selection.substring(5);
						DirectoryNode temp = tree.getCursor();
						tree.find(tree.getRoot(), name);
						if (tree.getCursor().equals(temp) && !name.equals("root"))
							System.out.println("ERROR: No such file exits.");
						tree.setCursor(temp);
					}
					else if (selection.startsWith("cd ")) {
						String typeOfInput = selection.substring(3).trim();
						if (!typeOfInput.contains("/")) {
							tree.changeDirectory(typeOfInput);
						}
						else {
							String [] array = selection.split("/");
							DirectoryNode node = tree.cdPath(array, tree.getRoot())[0];
							tree.setCursor(node);
						}
					}
					else if (selection.startsWith("mv ")) {
						DirectoryNode temp = tree.getCursor();
						String mvString = selection.substring(3);
						String [] array = mvString.split(" ");
						
						String [] src = array[0].split("/");
						DirectoryNode [] srcArray = tree.cdPath(src, tree.getRoot());
						DirectoryNode srcNode = srcArray[0];
						DirectoryNode srcParent = srcArray[1];
						
						String [] dst = array[1].split("/");
						DirectoryNode dstNode = tree.cdPath(dst, tree.getRoot())[0];
						
						dstNode.addChild(srcNode);
						
						//Set old reference to null
						for (int i = 0; i < 10; i++) {
							if (srcParent.getChildren()[i] != null) {
								if (srcParent.getChildren()[i].equals(srcNode)) {
									DirectoryNode[] newArray = srcParent.getChildren();
									newArray[i] = null;
									srcParent.setChildren(newArray);
								}
							}
						}
						tree.setCursor(temp);
					}
					break;
				}
			} catch (IllegalArgumentException ex) {
				System.out.println(ex);
			}
			catch (Exception ex) {
				System.out.println(ex);
			}
		}
		System.out.println("Program terminating normally");
	}
}
