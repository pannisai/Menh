package mfs.biller.barcode.data.xml.element;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlRootElement(name = "biller_barcode")
@XmlType(name = "", propOrder = {
    "constant",
	"barcode"
})
@XmlAccessorType(XmlAccessType.FIELD)
public class BillerBarcodeConfig {
	
	@XmlElement(required = false)
	private XmlConstant constant;
	
	@XmlElement(required = false)
	private List<XmlBarcode> barcode ;
	


	public XmlConstant getConstant() {
		return constant;
	}

	public void setConstant(XmlConstant constant) {
		this.constant = constant;
	}

	public List<XmlBarcode> getBarcode() {
		return barcode;
	}

	public void setBarcode(List<XmlBarcode> barcode) {
		this.barcode = barcode;
	}

}
