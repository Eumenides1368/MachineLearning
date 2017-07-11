package decision.tree;

import java.util.HashMap;
import java.util.Map;

public class Sample {
	//����+����
	private Map<String, Object> attributes = new HashMap<String, Object>();
	
	//��������������
	private Object category;

	public Map<String, Object> getAttributes() {
		return attributes;
	}
	
	public Object getAttributes(String name){
		return attributes.get(name);
	}

	public void setAttributes(String attrName, Object attrValue) {
		this.attributes.put(attrName,attrValue);
	}

	public Object getCategory() {
		return category;
	}

	public void setCategory(Object category) {
		this.category = category;
	}
	
	public String toString(){
		return attributes.toString();
	}
}
