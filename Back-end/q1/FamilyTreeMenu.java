import java.util.List;
import java.util.Scanner;


public class FamilyTreeMenu {
	
	public static void main(String[] args) {
        Scanner sc= new Scanner(System.in);
        
        FamilyTree T = new FamilyTree();
        FamilyMember cur = null;
        
        List<FamilyMember> r;
        String s1;
        String s2;
        
        while(true){
            System.out.println("-------MENU---------");
                        System.out.println("  ---- Family Tree Building -- ");
            System.out.println("1.  Reset the tree and enter the root");
            System.out.println("2.  Print the tree");
            System.out.println("3.  Select the root");
            System.out.println("4.  Set current family member");
            System.out.println("5.  Print current family member");
            System.out.println("6.  Insert spouse of current family member");
            System.out.println("7.  Insert child of current family member");
                        System.out.println("  ---- Search Operations -- ");
            System.out.println("8.  Search for name");
            System.out.println("9.  Search for generation");
            System.out.println("10.  Search for name in generation");
            System.out.println("0.  Quit  \n");
            System.out.print("  Select an option: ");
            int option = sc.nextInt();
            sc.nextLine();
            switch(option){
            case 0: System.exit(0);
            break;

            case 1:System.out.print("Enter name for root:  ");
            	s1 = sc.nextLine();
            	T = new FamilyTree(s1);
            	cur = T.getRoot();
            break;

            case 2:System.out.println("Print Tree");
            	System.out.println("----------------");
            	T.printTree();
            break;

            case 3:
                cur = T.getRoot();
                break;
            case 4:System.out.print("Enter name of family member:  ");
            	s1 = sc.nextLine();
                r = T.search(s1);
                while (r.size() > 1) {
                	System.out.println("Multiple family members found. Options are:");
                	for (FamilyMember member : r) System.out.println(member.name());
                	System.out.print("Enter name of family member:  ");
                	s1 = sc.nextLine();
                	r= T.search(s1);
                }
                if (r.size() == 0) System.out.println("No family member found.");
                else cur = r.get(0);
            break;
            case 5:
            	if (cur == null) System.out.println("null");
            	else System.out.println(cur.name());
            break;
            case 6:System.out.print("Enter name of spouse:  ");
            	s1 = sc.nextLine();
            	cur.marry(new FamilyMember(s1));
            break;

            case 7:System.out.print("Enter name of child:  ");
            	s1 = sc.nextLine();
            	System.out.print("Enter name of spouse or leave blank:  ");
            	s2 = sc.nextLine();
            	if (s2.length() > 0) {
            		FamilyMember s = null;
                    for (FamilyMember m : cur.spouses())
                    	if (m.name().contains(s2))
                    		s = m;
                    if (s == null) System.out.println("No spouse with that name found. We lost your baby.");
                    else cur.spawn(s, new FamilyMember(s1));
            	} else cur.spawn(new FamilyMember(s1));            	
            break;

            case 8:System.out.print("Enter name to search for:  ");
            	s1 = sc.nextLine();
            	r = T.search(s1);
            	if (r.isEmpty()) System.out.println("Name not found");
            	else for (FamilyMember m : r) System.out.println(m.name());
            break;

            case 9:
                System.out.print("Enter generation to search for:  ");
                int genNum = sc.nextInt();
                r = T.search(genNum);
                if (r.isEmpty()) System.out.println("Generation not found");
            	else for (FamilyMember m : r) System.out.println(m.name());  
            break;

            case 10:System.out.print("Enter part of name to search for:  ");
            	s1 = sc.nextLine();
            	System.out.print("Enter generation to search in:  ");
            	int gen = sc.nextInt();
            	r = T.search(s1, gen);
            	if (r.isEmpty()) System.out.println("Name not found in generation " + gen);
            	else for (FamilyMember m : r) System.out.println(m.name());
            break;

            default : System.out.println("Invalid Command");
	            break;
	            };
            System.out.println();
	}

}
}