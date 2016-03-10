package mfs.biller.barcode.junit;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import mfs.biller.barcode.data.BillerBarcode;
import mfs.biller.barcode.data.xml.element.BillerBarcodeConfig;
import mfs.biller.barcode.data.xml.element.XmlBarcode;
import mfs.biller.barcode.data.xml.element.XmlField;
import mfs.biller.barcode.data.xml.element.XmlFields;
import mfs.biller.barcode.data.xml.element.XmlTable;
import mfs.config.xml.util.XmlUtil;

import org.junit.Test;

public class BarcodeTest {

	@Test
	public void testHome() {
		System.out.print(System.getProperty("user.home"));

	}

	// @Test
	public void test() {

		int[] arr = new int[] { 30, 8, 20, 15 };

		SubStringIndex subss = new SubStringIndex();
		for (int x = 0; x < arr.length; x++) {
			int length = arr[x];
			// int ist = (i == 0) ? i : ++i;
			// i += length - 1;
			// int iend = i;

			// int[] iarr = genStartEnd(length, i);
			// i = iarr[1];

			genStartEnd(subss, length);

			System.out.print(x + "|Start0:" + subss.start);
			System.out.println("|end:" + subss.end);
		}

	}

	class SubStringIndex {
		int run = 0;
		int start = 0;
		int end = 0;
	}

	private void genStartEnd(SubStringIndex bean, int length) {
		int ist = (bean.run == 0) ? bean.run : ++bean.run;
		bean.run += length - 1;
		int iend = bean.run;

		bean.start = ist;
		bean.end = iend;
		bean.run = iend;
	}

	// @Test
	public void testXMLXJaxB1ToObject() throws Exception {
		// long starttime= System.currentTimeMillis();
		String filename = "/mfs/biller/barcode/data/xml/conf/barcode1.xml";
		InputStream is = getClass().getResourceAsStream(filename);
		InputStreamReader isr = new InputStreamReader(is);

		// XMLConfigManagement xml = new
		// XMLConfigManagement(OutputStreamToString.getStringFromInputStreamReader(isr),bPrint);
		long starttime = System.currentTimeMillis();

		BillerBarcodeConfig barcode = (BillerBarcodeConfig) XmlUtil.readXmlAsStreamtoObj(isr, BillerBarcodeConfig.class, true);

		System.out.println(System.currentTimeMillis() - starttime + " ms.");

		System.out.println("---------------Display-------------------------------");
		for (XmlTable table : barcode.getConstant().getTable()) {
			System.out.println("field=" + table.getField() + ":name=" + table.getName());
		}
		System.out.println("---------------Barcode-------------------------------");
		for (XmlBarcode bar : barcode.getBarcode()) {
			System.out.println("name=" + bar.getName() + ":length=" + bar.getLength() + ":id=" + bar.getId());
			for (XmlField field : bar.getFields().getField()) {
				System.out.println("name=" + field.getName() + ":length=" + field.getSize() + ":index=" + field.getIndex() + ":type=" + field.getType());
			}

		}

	}

	// @Test
	public void testReadXMLBarcode() throws Exception {
		String filename = "/mfs/biller/barcode/data/xml/conf/barcode1.xml";
		InputStream is = getClass().getResourceAsStream(filename);
		InputStreamReader isr = new InputStreamReader(is);

		long starttime = System.currentTimeMillis();
		BillerBarcode bb = new BillerBarcode();
		bb.setDebug(true);
		bb.readXML(isr);

		System.out.println(System.currentTimeMillis() - starttime + " ms.  size:" + bb.getSize());

		System.out.println("---------------Get data-------------------------------");
		XmlFields fields = bb.getFields("PEA", "001", 30);
		for (XmlField field : fields.getField()) {
			System.out.println("name=" + field.getName() + ":length=" + field.getSize() + ":index=" + field.getIndex() + ":type=" + field.getType());
		}
		System.out.println("---------------Get data-------------------------------");
		fields = bb.getFields("PEA", "001", 32);
		for (XmlField field : fields.getField()) {
			System.out.println("name=" + field.getName() + ":length=" + field.getSize() + ":index=" + field.getIndex() + ":type=" + field.getType());
		}

		System.out.println("---------------Get data PEA -------------------------------");
		String bar1 = "00708101020000806308511107130507";
		String bar2 = "000001434743000134000002023998";

		HashMap<String, String> hm = bb.barcodeChop("PEA", "001", bar1);
		bb.printMap(hm);

		hm = bb.barcodeChopToTable("PEA", "001", bar2);
		bb.printMap(hm);

		System.out.println("---------------MEA data chop -------------------------------");
		String MEAbar = "|41020302550 01137124123475037946 012302531          4376";
		System.out.println("MEA bar size=" + MEAbar.length());

		hm = bb.barcodeChopToTable("MEA", "001", MEAbar);
		bb.printMap(hm);

		String MWAbar = "212064484440561000100000016927230152";
		System.out.println("MWA bar size=" + MWAbar.length());

		hm = bb.barcodeChopToTable("MWA", "001", MWAbar);
		bb.printMap(hm);

		String PWAbar1 = "55302111042009460013881440    16900200356";
		String PWAbar2 = "118411840037132118456000000385     8560200356";

		System.out.println("---------------PWA1 data chop -------------------------------");
		System.out.println("PWA bar 1 size=" + PWAbar1.length());

		hm = bb.barcodeChopToTable("PWA", "001", PWAbar1);
		bb.printMap(hm);

		System.out.println("---------------PWA2 data chop -------------------------------");
		System.out.println("PWA bar 2 size=" + PWAbar2.length());
		hm = bb.barcodeChopToTable("PWA", "001", PWAbar2);
		bb.printMap(hm);

		System.out.println("---------------GSB data chop -------------------------------");
		String GSBbar = "|099400016489102740617777297550    ";
		System.out.println("GSB bar size=" + GSBbar.length());

		hm = bb.barcodeChopToTable("GSB", "001", GSBbar);
		bb.printMap(hm);

		System.out.println("---------------TAC data chop -------------------------------");
		String TACbar = "|310174637200 000894067774        066            516777718         53230     ";
		System.out.println("TAC bar size=" + TACbar.length());

		hm = bb.barcodeChopToTable("TAC", "001", TACbar);
		bb.printMap(hm);

		System.out.println("---------------DTN data chop -------------------------------");
		String DTNbar = "|0105549034548000804306946     024535863711         31993     ";
		System.out.println("DTN bar size=" + DTNbar.length());

		hm = bb.barcodeChopToTable("DTN", "001", DTNbar);
		bb.printMap(hm);

		System.out.println("---------------SSO data chop -------------------------------");
		String SSObar = "|0994000159676003140700113260     140002            10000     ";
		System.out.println("SSO bar size=" + DTNbar.length());

		hm = bb.barcodeChopToTable("SSO", "001", SSObar);
		bb.printMap(hm);

		System.out.println("---------------TOT data chop -------------------------------");
		String TOTbar = "|010754500016100 91000123456784  4376          486715    ";
		System.out.println("TOT bar size=" + TOTbar.length());

		hm = bb.barcodeChopToTable("TOT", "001", TOTbar);
		bb.printMap(hm);

		// hm = bb.barcodeChopToTable("TOT1",TOTbar+"00");

	}
}
