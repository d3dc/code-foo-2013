import java.util.ArrayList;
import java.util.List;

public class FamilyMember {
	private String _name;
    private FamilyMember _father;
    private FamilyMember _mother;
    private List<FamilyMember> _spouses;
    private List<FamilyMember> _children;
    
    //An object representing a family member in the family tree. 
    //Assumes no incest, but allows poligamy. Past names of a family member are not stored if they are changed.
    //Spouses and children are stored in an ArrayList by default, as more than 10 kids or partners is an exceptional case (like, DAAAAAMN!).
    
    public FamilyMember(String name) {
    	BabyMaker(name,null,null,null,null);
    }
    public FamilyMember(String name, FamilyMember father, FamilyMember mother) {
    	BabyMaker(name,father,mother,null,null);
    }
    public FamilyMember(String name, List<FamilyMember> spouses) {
    	BabyMaker(name,null,null,spouses,null);
    }
    public FamilyMember(String name, List<FamilyMember> spouses, List<FamilyMember> children) {
    	BabyMaker(name,null,null,spouses,children);
    }
    public FamilyMember(String name, FamilyMember father, FamilyMember mother, List<FamilyMember> spouses, List<FamilyMember> children) {
    	BabyMaker(name,father,mother,spouses,children);
    }
    
    private void BabyMaker(String name, FamilyMember father, FamilyMember mother, List<FamilyMember> spouses, List<FamilyMember> children) {
    	_name = name;
    	_father = father;
    	_mother = mother;
    	_spouses = spouses;
    	_children = children;
    }
    
    //Mutators
    public void changeName(String name) {
    	_name = name;
    }
    
    public void marry(FamilyMember spouse) {
    	if (_spouses == null) {
    		_spouses = new ArrayList<FamilyMember>(); 
    	}
    	_spouses.add(spouse);
    }
    
    public void spawn(FamilyMember spouse, FamilyMember child) {
    	spawn(child);
    	spouse.spawn(child);
    }
    
    public void spawn(FamilyMember child) {
    	if (_children == null) {
    		_children = new ArrayList<FamilyMember>(); 
    	}
    	_children.add(child);
    }
    
    public void bornTo(FamilyMember father, FamilyMember mother) {
    	_father = father;
    	_mother = mother;
    }
    
    
    //Getters    
    public List<FamilyMember> spouses() {
    	return _spouses;
    }
    
    public List<FamilyMember> children() {
    	return _children;
    }
    
    public FamilyMember father() {
    	return _father;
    }
    
    public FamilyMember mother() {
    	return _mother;
    }
    
    public String name() {
    	return _name;
    }
}
