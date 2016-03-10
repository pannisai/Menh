package com.dtac.billerweb.test;

import java.util.Collection;

import mfs.biller.ejb.interfaces.BatchMastFileBeanRemote;
import mfs.biller.persistence.bean.BatchMastFile;
import mfs.biller.persistence.bean.BatchMastFileParam;

import com.dtac.billerweb.common.EJBInitialContext;

public class TestEJB {
	public String test() {
		String message = "";
		System.out.println("Start Test Ejb");
		try {

			// Context ctx = EJBInitialContext.getInitialContext();

			// ctx.lookup("Test");
			// System.out.println(ctx);
			// BillerMasterBeanRemote tdb=
			// (BillerMasterBeanRemote)EJBInitialContext.lookup(BillerMasterBeanRemote.JNDI_WEBLOGIC);

			// Collection<BillerMaster> coll= tdb.getBillerMasterAll("", "");
			// for(BillerMaster tmp:coll){
			// System.out.println(tmp.getBLLR_MSTR_ID()+"  "+tmp.getBLLR_CODE());
			// }
			BatchMastFileBeanRemote bmb = (BatchMastFileBeanRemote) EJBInitialContext
					.lookup(BatchMastFileBeanRemote.JNDI_WEBLOGIC);
			BatchMastFileParam batchMastFileParam=new BatchMastFileParam();
			batchMastFileParam.setPAGE_NO(1);
			batchMastFileParam.setPAGE_SIZE(10);
//			batchMastFileParam.setBTCH_MAST_FILE_ID(800);
//			batchMastFileParam.setFROM_DTTM(new Date());
			
			Collection<BatchMastFile> batchMastFiles=bmb.getBatchMastFileAll(batchMastFileParam);
			System.out.println("Size::"+batchMastFiles.size());
			System.out.println(bmb.countRowAll(new BatchMastFileParam()).intValue());
			for(BatchMastFile tmp:batchMastFiles){
				System.out.println(tmp.getBTCH_DEST_CODE());
			}
//			byte[] b=bmb.getBatchMastFile_byte("800");
//			File file = new  File("d:\\testbyte.txt");
//			System.out.println(b.length);
////		    ByteArrayInputStream bis=new ByteArrayInputStream(b);
//		    
//		    FileOutputStream bos=new  FileOutputStream(file);
//		    bos.write(b);
//		    bos.close();
			message = "Initial Context created";
		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
		}
		return message;
	}

	public static void main(String[] args) {
		TestEJB testEjb = new TestEJB();
		System.out.println(testEjb.test());
	}
}
