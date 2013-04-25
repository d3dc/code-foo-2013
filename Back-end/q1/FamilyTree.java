import java.util.LinkedList;
import java.util.List;

public class FamilyTree {
	private FamilyMember _root;
	
	public FamilyTree() {_root = null;}
	public FamilyTree(String name) {
		_root = new FamilyMember(name);
	}
	public FamilyTree(FamilyMember root) {
		_root = root;
	}
	
	public List<FamilyMember> search(String name) {
		LinkedList<FamilyMember> found = new LinkedList<FamilyMember>();
		int gen = 1;
		List<FamilyMember> cur = search(gen);
		while(!cur.isEmpty()) {
			for (FamilyMember member : cur) {
				if (member.name().contains(name)) found.add(member);
			}
			cur = search(++gen);
		}
		
		return found;
	}
	
	public List<FamilyMember> search(String name, int generation) {
		LinkedList<FamilyMember> found = new LinkedList<FamilyMember>();
		List<FamilyMember> cur = search(generation);
		
		for (FamilyMember member : cur) {
			if (member.name().contains(name)) found.add(member);
		}
		return found;
	}
	
	public List<FamilyMember> search(int generation) {
		LinkedList<FamilyMember> q = new LinkedList<FamilyMember>();
		if (_root != null) {
			q.add(_root);
			if (_root.spouses() != null)
				for (FamilyMember spouse : _root.spouses())
					q.add(spouse);
			
			int level = 1;
			
			while(level != generation) {
				LinkedList<FamilyMember> q2 = new LinkedList<FamilyMember>();
				
				for (FamilyMember member : q) 
					if (member.children() != null)
						for (FamilyMember child : member.children()) {
							if (!q2.contains(child)) q2.add(child);
							if (child.spouses() != null)
								for (FamilyMember spouse : child.spouses())
									if (!q2.contains(spouse)) q2.add(spouse);
						}	
				
				q = q2;
				level++;
			}
		}
		return q;
	}
	
	public FamilyMember getRoot() {return _root;}
	
	public void printTree() {
		if (_root != null)
			printTreeAux(_root, 0);
	}
	
	private void printTreeAux(FamilyMember member, int n) {
		for (int i = 0; i < n; i++)
			System.out.print("\t");
		System.out.print(member.name());
		
		if (member.spouses() != null) {
			for (FamilyMember spouse : member.spouses()) {
				System.out.print("---" + spouse.name());
			}
		}
		System.out.println();
		
		if (member.children() != null) {
			for (FamilyMember child : member.children()) {
				printTreeAux(child, n+1);
			}
		}
	}
}
