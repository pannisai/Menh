package mfs.biller.barcode.data.xml.element;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlRootElement(name = "table")
@XmlType(name = "", propOrder = {
		"name",
		"field"
		
	})
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlTable {
	
	@XmlAttribute(required = true)
	private  String name;
	
	@XmlAttribute(required = true)
	private  String field;
	
	

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
