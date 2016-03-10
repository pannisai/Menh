package com.dtac.billerweb.common;

import java.util.Date;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.Paging;

import com.dtac.billerweb.exception.BillerWebException;
import com.dtac.billerweb.exception.BillerWebSessionTimeOutException;
import com.dtac.billerweb.util.AppUtil;

public abstract class SearchPageController extends BaseControllor {

	private Logger log = Logger.getLogger(SearchPageController.class);
	@Wire
	protected Grid gdResult;
	@Wire
	protected Paging pagingTop;
	@Wire
	protected Paging paging;

	public SearchPageController(String pageName,String pageCode) throws BillerWebSessionTimeOutException {
		String init = getParameter("init");
		if ((!AppUtil.isEmpty(init)) && init.equals("true")) {
			super.setAuthorizationSession();
		}
		super.pageName = pageName;
		super.pageCode = pageCode;

	}

	public void doAfterCompose(Component com) throws Exception {
		Date requestDate = AppUtil.getCurrent();
		super.doAfterCompose(com);
		try {
			log.info(getOperationLogMessage(pageName, "Init Page", "Start Do AffterCompose"));

			paging.setPageSize(Integer.parseInt(AppConfiguration.getValue(AppConfiguration.PAGE_SIZE_KEY)));
			if (pagingTop != null) {
				pagingTop.setPageSize(Integer.parseInt(AppConfiguration.getValue(AppConfiguration.PAGE_SIZE_KEY)));
			}
			init(com);

		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Init Page", ex));
			setTxErrLogMessage(pageCode + "#init", requestDate, ex);
			showErrorMessage(ex);
		} finally {

		}

		log.info("Stop:" + System.currentTimeMillis());
		setTxLogMessage(pageCode + "#init", requestDate, "");
	}

	public void doFinally() throws Exception {
		super.doFinally();
		checkSessionTimeOut("Initial " + pageName);
	}

	@Listen("onLastInputRender=listbox")
	public void onAfterRender() {

		log.info(getOperationLogMessage(pageName, "Start page from session", ""));
		try {
			setupPageFromSession();
		} catch (BillerWebSessionTimeOutException stex) {
			throw stex;
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Start page from session", ex));
			showErrorMessage(ex);
		} finally {

		}
	}

	protected abstract void init(Component com) throws Exception;

	protected abstract void setupPageFromSession();

	protected <V> void setGridResultModel(ListModel<V> results) throws Exception {
		paging.setTotalSize(results.getSize());
		gdResult.setModel(results);
		if (pagingTop != null) {
			pagingTop.setTotalSize(results.getSize());
			setAutoOnPagingEventTop(pagingTop, paging);
		}

	}

	protected <V> void setGridResultModel(AbstractPagingListModel<V> results) throws Exception {
		results.refreshPaging(paging.getActivePage() + 1, Integer.parseInt(AppConfiguration.getValue(AppConfiguration.PAGE_SIZE_KEY)), getUserInfo());
		super.setResultTablePaging(pagingTop, paging, gdResult, results, paging.getActivePage(), Integer.parseInt(AppConfiguration.getValue(AppConfiguration.PAGE_SIZE_KEY)));
		gdResult.invalidate();
	}

	private <T> void setAutoOnPagingEventTop(final Paging pagingTop, final Paging paging) {
		try {
			pagingTop.addEventListener("onPaging", new EventListener() {
				@Override
				public void onEvent(Event even) throws Exception {
					paging.setActivePage(pagingTop.getActivePage());
				}
			});
			paging.addEventListener("onPaging", new EventListener() {
				@Override
				public void onEvent(Event even) throws Exception {
					pagingTop.setActivePage(paging.getActivePage());
				}
			});
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		}
	}

}
