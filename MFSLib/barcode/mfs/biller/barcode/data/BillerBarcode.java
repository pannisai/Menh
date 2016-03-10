package mfs.biller.barcode.data;

import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

import javax.xml.bind.JAXBException;

import mfs.biller.barcode.data.xml.element.BillerBarcodeConfig;
import mfs.biller.barcode.data.xml.element.XmlBarcode;
import mfs.biller.barcode.data.xml.element.XmlField;
import mfs.biller.barcode.data.xml.element.XmlFields;
import mfs.biller.barcode.data.xml.element.XmlTable;
import mfs.config.xml.util.XmlUtil;

import org.apache.commons.collections.map.MultiKeyMap;

public class BillerBarcode implements Serializable {

	private boolean debug = false;
	/**
	 * 
	 */
	private static final long serialVersionUID = -4970884734104235575L;
	private MultiKeyMap cache = new MultiKeyMap();
	private MultiKeyMap cache2 = new MultiKeyMap();

	private static final String DUE_DATE = "DueDate";
	private static final String AMOUNT = "Amount";
	private static final String AMOUNT_TRIM = "AmountTrim";

	private HashMap<String, String> cacheTable = new HashMap<String, String>();

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	protected void printDebug(String msg) {
		if (debug)
			System.out.println(msg);
	}

	public void readXML(String xml) throws JAXBException {
		long starttime = System.currentTimeMillis();
		BillerBarcodeConfig barcode_conf = (BillerBarcodeConfig) XmlUtil
				.convertXmlToObj(xml, BillerBarcodeConfig.class);
		printDebug("readXML" + (System.currentTimeMillis() - starttime)
				+ " ms.");
		if (null != barcode_conf)
			putAllData(barcode_conf);
	}

	public void readXML(InputStreamReader isr) throws FileNotFoundException,
			JAXBException {

		long starttime = System.currentTimeMillis();

		BillerBarcodeConfig barcode_conf = (BillerBarcodeConfig) XmlUtil
				.readXmlAsStreamtoObj(isr, BillerBarcodeConfig.class, true);

		printDebug("readXMLtoBean finis."
				+ (System.currentTimeMillis() - starttime) + " ms.");

		if (null != barcode_conf) {
			starttime = System.currentTimeMillis();
			putAllData(barcode_conf);
			printDebug("put data to map finis. "
					+ (System.currentTimeMillis() - starttime) + " ms.");
		}
	}

	private void putDataTable(XmlTable table) {
		cacheTable.put(table.getName(), table.getField());
	}

	private String getDataTable(String name) {
		return (String) cacheTable.get(name);
	}

	private void putAllData(BillerBarcodeConfig barcode_conf) {
		for (XmlTable table : barcode_conf.getConstant().getTable()) {
			putDataTable(table);
		}

		for (XmlBarcode bar : barcode_conf.getBarcode()) {
			putData(bar);
		}
	}

	public void clear() {
		cache.clear();
		cache2.clear();
		cacheTable.clear();
	}

	public int getSize() {
		return cache.size();
	}

	private void putData(XmlBarcode barcode) {

		printDebug("readXML put data key name:" + barcode.getName()
				+ ",service=" + barcode.getService() + ",length="
				+ barcode.getLength() + ",id=" + barcode.getId());
		cache.put(barcode.getName(), barcode.getService(), barcode.getLength(),
				barcode);

		cache2.put(barcode.getName(), barcode.getService(), barcode);
	}

	private XmlBarcode getData(String name, String service, int barcode_length) {
		return (XmlBarcode) cache.get(name, service, barcode_length);
	}

	private XmlBarcode getData(String name, String service) {
		return (XmlBarcode) cache2.get(name, service);
	}

	public XmlFields getFields(String name, String service, int barcode_length) {
		XmlBarcode bar = getData(name, service, barcode_length);
		if (null != bar) {

			printDebug(String
					.format("Success : XmlBarcode bar = getFields(String name, String service, int barcode_length) : (%s,%s,%s)",
							name, service, barcode_length));

			return bar.getFields();
		} else {

			bar = getData(name, service);
			if (null != bar) {
				printDebug(String
						.format("Success : XmlBarcode bar = getFields(String name, String service) : (%s,%s)",
								name, service));
				return bar.getFields();
			}
		}

		printDebug(String
				.format("Not found : XmlBarcode bar =  From both getData(): cach.size[%s], cach2.size[%s]",
						cache.size(), cache2.size()));

		System.out
				.println(String
						.format("Not found : XmlBarcode bar =  From both getData(): cach.size[%s], cach2.size[%s]",
								cache.size(), cache2.size()));

		return null;
	}

	public XmlFields getFields(String name, String service, String barcode) {
		return getFields(name, service, barcode.length());
	}

	public String convertDate(String date, String formatdate)
			throws ParseException {
		printDebug("convertDate:" + date + ",format:" + formatdate);

		String[] fdate = formatdate.split("\\(");

		if (2 == fdate.length) {
			fdate[1] = fdate[1].replace(")", "");

			printDebug("convertDate:" + date + ",format:" + fdate[0] + ":"
					+ fdate[1]);

			SimpleDateFormat df = new SimpleDateFormat(fdate[0], Locale.US);
			SimpleDateFormat df2 = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

			// Convert String date to date new date formate (US)
			String cfdated = df2.format(df.parse(date));
			String yearSubfix = cfdated.substring(8);
			printDebug("convertDate from:" + cfdated + " year:" + yearSubfix);

			String rtndate = cfdated;
			String yearPrefix = "20";

			if ("BD".equals(fdate[1])) {

				printDebug("year convert from " + yearSubfix + " to AD.");

				int year = new Integer(yearSubfix);
				year -= 43;
				if (year > 9) {
					yearSubfix = "" + year;
				} else {
					yearSubfix = "0" + year;
				}

				rtndate = cfdated.substring(0, 6) + yearPrefix + yearSubfix;
			}

			printDebug("convertDate to:" + rtndate);

			return rtndate;

		}

		return date;
	}

	private String convertAmountToDecimalWithDot(String samount, String format)
			throws BillerBarcodeException {
		double decimaldigit = 2;
		String decimalFormat = "###0.00";
		int amount = new Integer(samount);

		if ("AMOUNT_2DECIMAL_NODOT".equals(format)) {
			decimaldigit = 100.0;
			decimalFormat = "###0.00";
		} else if ("AMOUNT_3DECIMAL_NODOT".equals(format)) {
			decimaldigit = 1000.0;
			decimalFormat = "###0.000";
		} else if ("AMOUNT_4DECIMAL_NODOT".equals(format)) {
			decimaldigit = 10000.0;
			decimalFormat = "###0.0000";
		} else {
			throw new BillerBarcodeException("amount invalid format. amount:"
					+ samount + ",format:" + format);
		}

		double damount = amount / decimaldigit;

		DecimalFormat df = new DecimalFormat(decimalFormat);

		return df.format(damount);

	}

	private String covertValue(String name, String barcode, XmlField field)
			throws BillerBarcodeException {

		printDebug(name + "|name=" + field.getName() + ":size="
				+ field.getSize() + ":index=" + field.getIndex() + ":type="
				+ field.getType());
		int end = 0;
		String value = "";

		if (AMOUNT_TRIM.equals(field.getName())) {
			value = barcode.substring(field.getIndex()).trim();
		} else {
			end = field.getIndex() + field.getSize();
			value = barcode.substring(field.getIndex(), end).trim();
		}

		if (DUE_DATE.equals(field.getName())) {
			// covert date to format dd-MM-yyyy AD
			try {
				return convertDate(value, field.getType());
			} catch (Exception e) {
				throw new BillerBarcodeException(
						"due date invalid format. due_date:" + value
								+ ",format:" + field.getType());
			}
		} else if (AMOUNT.equals(field.getName())
				|| AMOUNT_TRIM.equals(field.getName())) {
			return convertAmountToDecimalWithDot(value, field.getType());
		} else {
			return value;
		}

	}

	private HashMap<String, String> barcodeChop(String name, String service,
			String barcode, String type) throws BillerBarcodeException {
		HashMap<String, String> map = new HashMap<String, String>();
		XmlFields fields = getFields(name, service, barcode);
		if (null != fields && fields.getField().size() > 0) {
			for (XmlField field : fields.getField()) {
				if ("N".equals(type)) {
					map.put(field.getName(), covertValue(name, barcode, field));
				} else if ("T".equals(type)) {
					map.put(getDataTable(field.getName()),
							covertValue(name, barcode, field));
				} else {
					throw new BillerBarcodeException(
							"barcode output type unknow.");
				}
			}
		} else {
			throw new BillerBarcodeException("barcode is unknown. (biller:"
					+ name + ",barcode:" + barcode + ",length:"
					+ barcode.length() + ")");
		}
		return map;
	}

	public HashMap<String, String> barcodeChop(String name, String service,
			String barcode) throws BillerBarcodeException {
		return barcodeChop(name, service, barcode, "N");
	}

	public HashMap<String, String> barcodeChopToTable(String name,
			String service, String barcode) throws BillerBarcodeException {
		return barcodeChop(name, service, barcode, "T");
	}

	public void printMap(HashMap<String, String> hm) {

		Iterator<String> iter = hm.keySet().iterator();

		while (iter.hasNext()) {
			String key = (String) iter.next();
			String val = (String) hm.get(key);
			System.out.println("key,val: " + key + "," + val);

		}

	}
}
