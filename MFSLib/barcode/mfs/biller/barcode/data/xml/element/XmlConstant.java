package mfs.biller.barcode.data.xml.element;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement(name = "constant")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlConstant {
	@XmlElement(required = false)
	private List<XmlTable> table ;

	public List<XmlTable> getTable() {
		return table;
	}

	public void setTable(List<XmlTable> table) {
		this.table = table;
	}
	
	
}
