package mfs.biller.barcode.data.xml.element;


import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "fields")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlFields {

	@XmlElement(required = false)
	private List<XmlField>  field ;

	public List<XmlField> getField() {
		return field;
	}

	public void setField(List<XmlField> field) {
		this.field = field;
	}


	
	
	
}
