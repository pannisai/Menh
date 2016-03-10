package mfs.biller.barcode.data.xml.element;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "field")
@XmlType(name = "", propOrder = {
		"name",
		"size",
		"index",
		"type"
	})
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlField {

	@XmlAttribute(required = true)
	private  String name;
	
	@XmlAttribute(required = true)
	private  int size;

	
	@XmlAttribute(required = true)
	private  int index;
	
	@XmlAttribute(required = true)
	private  String type;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}




	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
