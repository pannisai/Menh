package mfs.biller.ejb.stateless;

import java.math.BigDecimal;
import java.util.Calendar;
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
import mfs.biller.persistence.bean.UserInfoBean;
import mfs.biller.util.Timer;
import mfs.biller.util.ValidateUtil;

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

	public List<BillerBarcode> searchBillerBarcode(BillerBarcodeParam PARAM, UserInfoBean user) throws Exception {
		List<BillerBarcode> list = null;
		try {

			Timer timer = new Timer("-");
			log.info(user.getName() + "|" + page + "|searchBillerBarcode|Time|" + timer.getStartTime());
			log.info(user.getName() + "|" + page + "|searchBillerBarcode|Param|" + PARAM.toString());

//			String sql = "SELECT * FROM BILLER_BARCODE ";
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT  bb.*,bs.BLLR_SRVC_NAME_EN " + " FROM " + " BILLER_BARCODE " + " bb");

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

			Query query = em.createNativeQuery(sql.toString(), BillerBarcode.class);
			list = query.getResultList();
			log.info(user.getName() + "|" + page + "|searchBillerBarcode|List.Size:" + list.size());
			log.info(user.getName() + "|" + page + "|searchBillerBarcode|Time|" + timer.getStopTime());
		} catch (Exception e) {
			log.error(user.getName() + "|" + page + "|searchBillerBarcode|Exception:" + e.getMessage());
			throw e;
		}

		return list;
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
