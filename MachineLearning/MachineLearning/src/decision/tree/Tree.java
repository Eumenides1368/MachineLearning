package decision.tree;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Tree {
	
	private String attr;
	
	private Map<Object, Object> children = new HashMap<Object, Object>();
	
	public Tree(String attribute) {  
        this.attr = attribute;  
    }  

	public String getAttr() {
		return attr;
	}

	public void setAttr(String attr) {
		this.attr = attr;
	}

	public Object getChildren(Object attr) {
		return children.get(attr);
	}

	public void setChildren(Object attrValue, Object child) {
		children.put(attrValue, child);
	}

	public Set<Object> getAttributeValues() {
		// TODO Auto-generated method stub
		return children.keySet();
	}

	

}
