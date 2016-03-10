package mfs.biller.barcode.data.xml.element;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "barcode")
@XmlType(name = "", propOrder = { "name", "service", "length", "id", "fields" })
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlBarcode {

	@XmlAttribute(required = true)
	private String name;

	@XmlAttribute(required = true)
	private String service;

	@XmlAttribute(required = true)
	private int length;

	@XmlAttribute(required = false)
	private int id;

	@XmlElement(required = false)
	private XmlFields fields;

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public XmlFields getFields() {
		return fields;
	}

	public void setFields(XmlFields fields) {
		this.fields = fields;
	}

}
