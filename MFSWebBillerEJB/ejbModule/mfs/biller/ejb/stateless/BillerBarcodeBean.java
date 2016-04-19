package mfs.biller.ejb.stateless;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javaee.CallByReference;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import mfs.biller.ejb.interfaces.BillerBarcodeBeanLocal;
import mfs.biller.ejb.interfaces.BillerBarcodeBeanRemote;
import mfs.biller.persistence.bean.BillerBarcode;
import mfs.biller.persistence.bean.BillerBarcodeParam;
import mfs.biller.persistence.bean.BillerBarcodeSearchBean;
import mfs.biller.persistence.bean.UserInfoBean;
import mfs.biller.util.Timer;
import mfs.biller.util.ValidateUtil;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

@Stateless(name = "BillerBarcodeBean", mappedName = "mfs.biller.ejb.BillerBarcodeBean")
@CallByReference
public class BillerBarcodeBean implements BillerBarcodeBeanLocal, BillerBarcodeBeanRemote {

	private Logger log = Logger.getLogger("EJBBLLRBARC");
	private String page = "BillerBarcodeBean";
	@PersistenceContext(unitName = "mfs.db.MFSDAO")
	private EntityManager em;

	public List<BillerBarcode> getBillerBarcodeAll(UserInfoBean user) throws Exception {
		List<BillerBarcode> list = null;
		try {

			Timer timer = new Timer("-");
			log.info(user.getName() + "|" + page + "|getBillerBarcodeAll|Time:" + timer.getStartTime());
			String sql = "SELECT * FROM BILLER_BARCODE ORDER BY BARC_ID ";

			log.info(user.getName() + "|" + page + "|getBillerBarcodeAll|SQL:" + sql);

			Query query = em.createNativeQuery(sql, BillerBarcode.class);
			list = query.getResultList();
			log.info(user.getName() + "|" + page + "|getBillerBarcodeAll|List.Size:" + list.size());
			log.info(user.getName() + "|" + page + "|getBillerBarcodeAll|Time:" + timer.getStopTime());
		} catch (Exception e) {
			log.error(user.getName() + "|" + page + "|getBillerBarcodeAll|Exception:" + e.getMessage());
			throw e;
		}

		return list;
	}
	public List<BillerBarcodeSearchBean> searchBillerBarcode(BillerBarcodeParam PARAM, UserInfoBean user) throws Exception {
		
//	public List<BillerBarcode> searchBillerBarcode(BillerBarcodeParam PARAM, UserInfoBean user) throws Exception {
//		List<BillerBarcode> list = null;
		List<BillerBarcodeSearchBean> barcodeSearchBeans = new ArrayList<BillerBarcodeSearchBean>();
		try {

			Timer timer = new Timer("-");
			log.info(user.getName() + "|" + page + "|searchBillerBarcode|Time|" + timer.getStartTime());
			log.info(user.getName() + "|" + page + "|searchBillerBarcode|Param|" + PARAM.toString());

//			String sql = "SELECT * FROM BILLER_BARCODE ";
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT  bb.BARC_ID, bb.BARC_NAME, bb.BARC_CRRG_RETN_FLAG, bb.BARC_PERF_FLAG, bb.BARC_PERF_VALUE, bb.BARC_LINE_MAX, bb.BARC_EFFT_DATE, bb.BARC_LINE_NO, bb.BLLR_SRVC_ID, bb.BARC_NEW_LINE_POST, bb.BARC_SRVC_CODE, bb.ACT_FLAG, bb.CRTD_BY, bb.CRTD_DTTM, bb.LAST_CHNG_BY, bb.LAST_CHNG_DTTM, bb.BARC_TYPE, bb.BARC_EXPR_DATE,bs.BLLR_SRVC_NAME_EN " + " FROM " + " BILLER_BARCODE " + " bb");
//			sql.append("SELECT  bb.*,bs.BLLR_SRVC_NAME_EN " + " FROM " + " BILLER_BARCODE " + " bb");

			sql.append(" LEFT JOIN BILLER_SERVICE bs ON bb.BLLR_SRVC_ID=bs.BLLR_SRVC_ID");
			sql.append(" WHERE 1=1");
			Vector<String> v = new Vector<String>();
			StringBuffer sb = new StringBuffer();
			if (!ValidateUtil.isEmpty(PARAM.getBLLR_SRVC_ID())) {
				v.add("bb.BLLR_SRVC_ID = " + PARAM.getBLLR_SRVC_ID());
			}
			if (!ValidateUtil.isEmpty(PARAM.getBARC_ID())) {
				v.add("bb.BARC_ID = " + PARAM.getBARC_ID());
			}
			if (!ValidateUtil.isEmpty(PARAM.getACT_FLAG())) {
				v.add("bb.ACT_FLAG = '" + PARAM.getACT_FLAG() + "'");
			}

			if (!v.isEmpty()) {
				sb.append(" AND (");
				for (int i = 0; i < v.size(); i++) {
					if (i != 0) {
						sb.append(" AND ");
					}
					sb.append(v.get(i));
				}
				sb.append(")");
			}
			sql.append(sb.toString());
			sql.append(" ORDER BY BARC_ID");

			log.info(user.getName() + "|" + page + "|searchBillerBarcode|SQL:" + sql.toString());
			
			Query query = em.createNativeQuery(sql.toString());
			List<Object[]>list = query.getResultList();
			for (Object[] objects : list) {
				barcodeSearchBeans.add(mapBarcodeBean(objects));
			}
			
			log.info(user.getName() + "|" + page + "|searchBillerBarcode|List.Size:" + list.size());
			log.info(user.getName() + "|" + page + "|searchBillerBarcode|Time|" + timer.getStopTime());
		} catch (Exception e) {
			log.error(user.getName() + "|" + page + "|searchBillerBarcode|Exception:" + e.getMessage());
			throw e;
		}

		return barcodeSearchBeans;
	}
	
	private BillerBarcodeSearchBean mapBarcodeBean(Object[] obj){
		
		BillerBarcodeSearchBean bean = new BillerBarcodeSearchBean();
		try{
		bean.setBARC_ID(obj[0]!=null?Integer.parseInt(obj[0].toString()):null);
		bean.setBARC_NAME(obj[1]!=null?(String)obj[1]:null);
		bean.setBARC_CRRG_RETN_FLAG(obj[2]!=null?(String)obj[2]:null);
		bean.setBARC_PERF_FLAG(obj[3]!=null?(String)obj[3]:null);
		bean.setBARC_PERF_VALUE(obj[4]!=null?(String)obj[4]:null);
		bean.setBARC_LINE_MAX(obj[5]!=null?Integer.parseInt(obj[5].toString()):null);
		bean.setBARC_EFFT_DATE(obj[6]!=null?(Date)obj[6]:null);
		bean.setBARC_LINE_NO(obj[7]!=null?new Double(obj[7].toString()).intValue():null);
		bean.setBLLR_SRVC_ID(obj[8]!=null?(Integer)obj[8]:null);
		bean.setBARC_NEW_LINE_POST(obj[9]!=null?(String)obj[9]:null);
		bean.setBARC_SRVC_CODE(obj[10]!=null?(String)obj[10]:null);
		bean.setACT_FLAG(obj[11]!=null?(String)obj[11]:null);
		bean.setCRTD_BY(obj[12]!=null?(String)obj[12]:null);
		bean.setCRTD_DTTM(obj[13]!=null?(Date)obj[13]:null);
		bean.setLAST_CHNG_BY(obj[14]!=null?(String)obj[14]:null);
		bean.setLAST_CHNG_DTTM(obj[15]!=null?(Date)obj[15]:null);
		bean.setBARC_TYPE(obj[16]!=null?(String)obj[16]:null);
		bean.setBARC_EXPR_DATE(obj[17]!=null?(Date)obj[17]:null);
		bean.setBLLR_SRVC_NAME_EN(obj[18]!=null?(String)obj[18]:null);
		}catch(Exception e){
			log.error("Cannot Map BillerBarcode (barc_id=+"+obj[0].toString()+")");
		}
		return bean;
	}

	public BillerBarcode findBillerBarcode(int BARC_ID, UserInfoBean user) throws Exception {
		try {

			Timer timer = new Timer("-");
			log.info(user.getName() + "|" + page + "|findBillerBarcode|Time:" + timer.getStartTime());
			String sql = "SELECT * FROM BILLER_BARCODE WHERE BARC_ID = " + BARC_ID;
			log.info(user.getName() + "|" + page + "|findBillerBarcode|SQL:" + sql);

			Query query = em.createNativeQuery(sql, BillerBarcode.class);
			List<BillerBarcode> list = query.getResultList();
			log.info(user.getName() + "|" + page + "|findBillerBarcode|Time:" + timer.getStopTime());
			if ((list != null) && (list.size() > 0)) {
				return (BillerBarcode) list.get(0);
			} else {
				throw new Exception("Not Found Data");
			}

		} catch (Exception e) {
			log.error(user.getName() + "|" + page + "|findBillerBarcode|Exception:" + e.getMessage());
			throw e;
		}
	}

	public int insertBillerBarcode(BillerBarcode bean, UserInfoBean user) throws Exception {
		try {

			Timer timer = new Timer("-");
			log.info(user.getName() + "|" + page + "|insertBillerBarcode|Time|" + timer.getStartTime());
			log.info(user.getName() + "|" + page + "|insertBillerBarcode|Param|" + bean.toString());
			
			em.getTransaction().begin();
			Query query = em.createNativeQuery("SELECT nextval('SEQ_BILLER_BARCODE')");
			BigDecimal result = new BigDecimal((Long)query.getSingleResult());
			int BARC_ID = result.intValue();

			log.info(user.getName() + "|" + page + "|insertBillerBarcode|BARC_ID:" + BARC_ID);

			bean.setBARC_ID(BARC_ID);
			bean.setCRTD_BY(user.getName());
			bean.setCRTD_DTTM(Calendar.getInstance().getTime());
			bean.setLAST_CHNG_BY(user.getName());
			bean.setLAST_CHNG_DTTM(Calendar.getInstance().getTime());
			em.persist(bean);
			em.getTransaction().commit();

			log.info(user.getName() + "|" + page + "|insertBillerBarcode|Success");
			log.info(user.getName() + "|" + page + "|insertBillerBarcode|Time|" + timer.getStopTime());
			return BARC_ID;

		} catch (Exception e) {
			log.error(user.getName() + "|" + page + "|insertBillerBarcode|Exception:" + e.getMessage());
			throw e;
		}finally{
			em.clear();
		}
	}

	public void updateBillerBarcode(BillerBarcode bean, UserInfoBean user) throws Exception {
		try {

			Timer timer = new Timer("-");
			log.info(user.getName() + "|" + page + "|updateBillerBarcode|Time|" + timer.getStartTime());
			log.info(user.getName() + "|" + page + "|updateBillerBarcode|Param|" + bean.toString());


			em.getTransaction().begin();
			bean.setLAST_CHNG_DTTM(Calendar.getInstance().getTime());
			em.merge(bean);
			em.getTransaction().commit();

			log.info(user.getName() + "|" + page + "|updateBillerBarcode|Success");
			log.info(user.getName() + "|" + page + "|updateBillerBarcode|Time|" + timer.getStopTime());
		} catch (Exception e) {
			log.error(user.getName() + "|" + page + "|updateBillerBarcode|Exception:" + e.getMessage());
			throw e;
		}finally{
			em.clear();
		}
	}
}
