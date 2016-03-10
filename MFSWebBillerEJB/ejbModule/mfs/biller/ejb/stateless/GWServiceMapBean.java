package mfs.biller.ejb.stateless;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import javaee.CallByReference;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import mfs.biller.ejb.interfaces.GWServiceMapBeanLocal;
import mfs.biller.ejb.interfaces.GWServiceMapBeanRemote;
import mfs.biller.persistence.bean.GWServiceMap;
import mfs.biller.persistence.bean.GWXmlDataSrcBean;
import mfs.biller.persistence.bean.ObjMapGWxml;
import mfs.biller.persistence.bean.UserInfoBean;
import mfs.biller.util.JpaResultUtil;
import mfs.biller.util.Timer;
import mfs.exception.IsExistException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

/**
 * Session Bean implementation class GWServiceMapBean
 */

@Stateless(name = "GWServiceMapBean", mappedName = "mfs.biller.ejb.GWServiceMapBean")
@CallByReference
public class GWServiceMapBean implements GWServiceMapBeanRemote,
		GWServiceMapBeanLocal {
	
	private Logger log = Logger.getLogger("EJBSERVICEMAP");
	private String page = "EJBSERVICEMAP";
	@PersistenceContext(unitName = "mfs.db.MFSDAO")
	private EntityManager em;
	@SuppressWarnings("unchecked")
	public Collection<GWServiceMap> getGWServiceMapAll(String DATA_MAP_ID,
			String DATA_MAP_NAME) throws Exception {
 
		try {

			Timer timer = new Timer("-");
			log.info("EJBSERVICEMAP|getGWServiceMapAll|Time:" + timer.getStartTime());
			String sql = "SELECT * "
					+ "FROM GW_SERVICE_MAP ";

			Vector<String> v = new Vector<String>();

			if (DATA_MAP_ID != null && !"".equals(DATA_MAP_ID))
				v.add("DATA_MAP_ID='" + DATA_MAP_ID + "'");

			if (DATA_MAP_NAME != null && !"".equals(DATA_MAP_NAME))
				v.add("DATA_MAP_NAME='" + DATA_MAP_NAME + "'");

			StringBuffer sb = new StringBuffer();

			if (!v.isEmpty()) {
				sb.append(" WHERE ");
				for (int i = 0; i < v.size(); i++) {
					if (i != 0) {
						sb.append(" AND ");
					}
					sb.append(v.get(i));
				}
			}

			sql = sql + sb.toString();
			sql += " ORDER BY DATA_MAP_NAME desc";

			log.info("EJBSERVICEMAP|getGWServiceMapAll|sql:" + sql);

			Collection<GWServiceMap> colreturn = new Vector();

			Query query = em.createNativeQuery(sql, GWServiceMap.class);

			List list = (List) query.getResultList();
			
			log.info("EJBSERVICEMAP|getGWServiceMapAll|ist.size():"
					+ list.size());

			for (int i = 0; i < list.size(); i++) {
				Object o = list.get(i);
				GWServiceMap mastTrnsPrt = (GWServiceMap) o;

				colreturn.add(mastTrnsPrt);
			}
			log.info("EJBSERVICEMAP|getGWServiceMapAll|Time:" + timer.getStopTime());
			return colreturn;

		} catch (Exception e) {
			log.error(ExceptionUtils.getStackTrace(e));
			log.info("EJBSERVICEMAP|getGWServiceMapAll|Exception:" + e);
		}

		return new Vector();
	}

	public ObjMapGWxml findGWServiceMap(int DATA_MAP_ID,  UserInfoBean user)
			throws Exception {
		ArrayList arraylist = null;
		try {

			Timer timer = new Timer("-");
			log.info("EJBSERVICEMAP|findGWServiceMap|Time:" + timer.getStartTime());

			String sql = "SELECT t1.DATA_MAP_ID,t1.DATA_MAP_NAME,t2.XML_DATA_ID,t2.XML_DATA_TYPE,t2.XML_DATA_SRC,t2.XML_DATA_VRSN,t2.XML_DATA_DESC,t2.XML_DATA_STTS "
					+ "  FROM GW_SERVICE_MAP t1 , GW_XML_DATA_SRC t2  "	
					+"  WHERE  t1.DATA_MAP_XML_SRC_ID = t2.XML_DATA_ID  AND  t1.DATA_MAP_ID = "+DATA_MAP_ID+" "	;

			log.info("EJBSERVICEMAP|findGWServiceMap|sql:" + sql);

			Query query = em.createNativeQuery(sql);
			List<Object[]> list = query.getResultList();
			ObjMapGWxml ObjMapGWxml = new ObjMapGWxml(); 
			log.info(user.getName() + "|EJBSERVICEMAP|findGWServiceMap|List.Size:" + list.size());
			for (Object[] item : list){
				int x = 0;
				
				ObjMapGWxml.setDATA_MAP_ID(JpaResultUtil.getInteger(item, x++));
				ObjMapGWxml.setDATA_MAP_NAME(JpaResultUtil.getString(item, x++));
				ObjMapGWxml.setXML_DATA_ID(JpaResultUtil.getInteger(item, x++));
				ObjMapGWxml.setXML_DATA_TYPE(JpaResultUtil.getString(item, x++));
				ObjMapGWxml.setXML_DATA_SRC(JpaResultUtil.getString(item, x++));				
				ObjMapGWxml.setXML_DATA_VRSN(JpaResultUtil.getInteger(item, x++));				
				ObjMapGWxml.setXML_DATA_DESC(JpaResultUtil.getString(item, x++));
				ObjMapGWxml.setXML_DATA_STTS(JpaResultUtil.getString(item, x++));
				
				
				
			}
			log.info("EJBSERVICEMAP|findGWServiceMap|Time:" + timer.getStopTime());
				return ObjMapGWxml;
				
		} catch (Exception e) {
			log.error(ExceptionUtils.getStackTrace(e));
			log.info("EJBSERVICEMAP|findGWServiceMap|Exception:" + e);
			log.info(user.getName() + "|EJBSERVICEMAP|findGWServiceMap|Exception:" + e.getMessage());
			throw e;
		}

	}
	public int insertGWServiceMap(ObjMapGWxml bean, UserInfoBean user)throws IsExistException, Exception{
		try{

			Timer timer = new Timer("-");
			log.info(user.getName() + "|EJBSERVICEMAP|insertGWServiceMap|ObjMapGWxml|Time|" + timer.getStartTime());
			log.info(user.getName() + "|EJBSERVICEMAP|insertGWServiceMap|ObjMapGWxml|Param|" + bean.toString());

			Query query = em.createNativeQuery("SELECT SEQ_GW_XML_DATA_SRC.nextval from DUAL");
			BigDecimal result = (BigDecimal)query.getSingleResult();
			int XML_DATA_ID = result.intValue();
			
			
			Query query1 = em.createNativeQuery("SELECT SEQ_GW_SERCIVE_MAP.nextval from DUAL");
			BigDecimal result1 = (BigDecimal)query.getSingleResult();
			int DATA_MAP_ID = result1.intValue();
			
			
			log.info(user.getName() + "|EJBSERVICEMAP|insertGWServiceMap|DATA_MAP_ID:" + DATA_MAP_ID);
			log.info(user.getName() + "|EJBSERVICEMAP|insertGWServiceMap|XML_DATA_ID:" + XML_DATA_ID);
			
			String sql = "INSERT INTO GW_SERVICE_MAP(DATA_MAP_ID, DATA_MAP_NAME, DATA_MAP_XML_SRC_ID )"
					   + "VALUES(?, ?, ?)";
			int i = 0;
			query = em.createNativeQuery(sql);
			query.setParameter(++i, DATA_MAP_ID);
			query.setParameter(++i, bean.getXML_DATA_TYPE());
			query.setParameter(++i, XML_DATA_ID);
			
			query.executeUpdate();
			
			GWXmlDataSrcBean GWXmlDataSrcBean =new GWXmlDataSrcBean();
			GWXmlDataSrcBean.setXML_DATA_DESC(bean.getXML_DATA_DESC());
			GWXmlDataSrcBean.setXML_DATA_ID(XML_DATA_ID);
			GWXmlDataSrcBean.setXML_DATA_SRC(bean.getXML_DATA_SRC());
			GWXmlDataSrcBean.setXML_DATA_STTS(bean.getXML_DATA_STTS());
			GWXmlDataSrcBean.setXML_DATA_TYPE(bean.getXML_DATA_TYPE());
			GWXmlDataSrcBean.setXML_DATA_VRSN((bean.getXML_DATA_VRSN()));
			log.info(user.getName() + "|EJBSERVICEMAP|insertGWServiceMap|sql"+sql);
			
			String sql1 = "INSERT INTO GW_XML_DATA_SRC(XML_DATA_ID, XML_DATA_TYPE, XML_DATA_SRC,XML_DATA_VRSN,XML_DATA_DESC,XML_DATA_STTS )"
					   + "VALUES(?, ?, ?, ?, ?, ?)";
			 i = 0;
			query1 = em.createNativeQuery(sql1);
			query1.setParameter(++i, XML_DATA_ID);
			query1.setParameter(++i, GWXmlDataSrcBean.getXML_DATA_TYPE());
			query1.setParameter(++i, GWXmlDataSrcBean.getXML_DATA_SRC());
			query1.setParameter(++i, GWXmlDataSrcBean.getXML_DATA_VRSN());
			query1.setParameter(++i, GWXmlDataSrcBean.getXML_DATA_DESC());
			query1.setParameter(++i, GWXmlDataSrcBean.getXML_DATA_STTS());
			
			query1.executeUpdate();
			log.info(user.getName() + "|EJBSERVICEMAP|insertGWServiceMap|sql"+sql1);
			log.info(user.getName() + "|EJBSERVICEMAP|insertGWServiceMap|Success");
			log.info(user.getName() + "|EJBSERVICEMAP|insertGWServiceMap|ObjMapGWxml|Time|" + timer.getStopTime());
			return DATA_MAP_ID;
			
		}catch(Exception e){
			em.getTransaction().rollback();
			log.error(user.getName() + "|EJBSERVICEMAP|insertGWInboundMap|Exception:" + e.getMessage());
			throw e;
		}
		
	}
	public void updateGWServiceMap(ObjMapGWxml bean, UserInfoBean user)throws IsExistException, Exception{
		try{

			Timer timer = new Timer("-");
			log.info(user.getName() + "|EJBSERVICEMAP|updateGWServiceMap|Time|" + timer.getStartTime());
			log.info(user.getName() + "|EJBSERVICEMAP|updateGWServiceMap|Param|" + bean.toString());

			Query query1 = em.createNativeQuery("SELECT  T1.XML_DATA_ID  from GW_XML_DATA_SRC T1 join GW_SERVICE_MAP T2  on T1.XML_DATA_ID = T2.DATA_MAP_XML_SRC_ID  WHERE  T2.DATA_MAP_ID = "+bean.getDATA_MAP_ID()+"");
			BigDecimal result = (BigDecimal)query1.getSingleResult();
			int XML_DATA_ID = result.intValue();
			StringBuffer sb = new StringBuffer();
			sb.append("UPDATE GW_SERVICE_MAP ")
					.append("SET DATA_MAP_NAME = ? ")
					.append("WHERE DATA_MAP_ID = ? ");			
			int i = 0;
			Query query = em.createNativeQuery(sb.toString());
			query.setParameter(++i, bean.getXML_DATA_TYPE());
			query.setParameter(++i, bean.getDATA_MAP_ID());
			query.executeUpdate();			
			log.info(user.getName() + "|EJBSERVICEMAP|updateGWServiceMap|Success");
			
			
			StringBuffer sb1 = new StringBuffer();
			sb1.append("UPDATE GW_XML_DATA_SRC ")
					.append("SET XML_DATA_TYPE = ? ")
					.append(", XML_DATA_DESC = ? ")
					.append(", XML_DATA_SRC = ? ")
					.append("WHERE XML_DATA_ID = ? ");
			
			GWXmlDataSrcBean GWXmlDataSrcBean =new GWXmlDataSrcBean();
			GWXmlDataSrcBean.setXML_DATA_DESC(bean.getXML_DATA_DESC());
			GWXmlDataSrcBean.setXML_DATA_SRC(bean.getXML_DATA_SRC());
			GWXmlDataSrcBean.setXML_DATA_STTS(bean.getXML_DATA_STTS());
			GWXmlDataSrcBean.setXML_DATA_TYPE(bean.getXML_DATA_TYPE());
			GWXmlDataSrcBean.setXML_DATA_VRSN((bean.getXML_DATA_VRSN()));
			
			 i = 0;
			Query quer1 = em.createNativeQuery(sb1.toString());
			quer1.setParameter(++i, GWXmlDataSrcBean.getXML_DATA_TYPE());
			quer1.setParameter(++i, GWXmlDataSrcBean.getXML_DATA_DESC());
			quer1.setParameter(++i, GWXmlDataSrcBean.getXML_DATA_SRC());
			quer1.setParameter(++i, XML_DATA_ID);
			quer1.executeUpdate();
			
			log.info(user.getName() + "|EJBSERVICEMAP|updateGWServiceMap|Success");
			log.info(user.getName() + "|EJBSERVICEMAP|updateGWServiceMap|Time|" + timer.getStopTime());
	
		}catch(Exception e){
			em.getTransaction().rollback();
			log.error(user.getName() + "|EJBSERVICEMAP|updateGWServiceMap|Exception:" + e.getMessage());
			throw e;
		}
		
	}
	public static String OBJchkNull( Object value ) 
	{
		return (value == null)?(""):(value.toString()); 
	}
}
