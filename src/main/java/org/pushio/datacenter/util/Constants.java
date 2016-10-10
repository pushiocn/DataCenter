package org.pushio.datacenter.util;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public abstract class Constants {

	public static final DateFormat DF = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public static final Basecode INLAND = new Basecode(5, "内陆");
	public static final Basecode IMPORT = new Basecode(6, "进口");
	public static final Basecode EXPORT = new Basecode(7, "出口");
	public static final Basecode BOX_HEAVY = new Basecode(8, "重柜");
	public static final Basecode BOX_EMPTY = new Basecode(9, "吉柜");

	/**
	 * 记录状态
	 * 
	 * @author deli
	 * 
	 */
	public interface Status {
		public static final Basecode SAVE = new Basecode(1000, "保存");
		public static final Basecode SUBMIT = new Basecode(1001, "提交");
		public static final Basecode PASS = new Basecode(1003, "审核通过");
		public static final Basecode BACK = new Basecode(1004, "审核退回");
		public static final Basecode STOP = new Basecode(1007, "停用");
		public static final Basecode DELETE = new Basecode(1009, "删除");
	}

	/**
	 * 采集状态
	 * 
	 * @author deli
	 * 
	 */
	public interface AcquireStatus {
		public static final Basecode HAS_COLL = new Basecode(5101, "已采集");
		public static final Basecode NO_COLL = new Basecode(5102, "未采集");
	}

	/**
	 * 基准价状态
	 * 
	 * @author deli
	 * 
	 */
	public interface StandardStatus {
		public static final Basecode HAS_COLL = new Basecode(5201, "有采集价");
		public static final Basecode NO_COLL = new Basecode(5202, "无采集价");
		public static final Basecode UP_COLL = new Basecode(5203, "采集价有更新");
		public static final Basecode NO_STAND = new Basecode(5204, "无基准价");
	}

	/**
	 * 港口类型
	 * 
	 * @author deli
	 * 
	 */
	public interface PortType {
		public static final Basecode BARGE_STARTPORT = new Basecode(4401,
				"驳船始发港");
		public static final Basecode STARTPORT = new Basecode(4402, "始发港");
		public static final Basecode ENDPORT = new Basecode(4403, "目的港");
		public static final Basecode BARGE_ENDPORT = new Basecode(4404, "驳船目的港");
	}

	/**
	 * 付款对象类型
	 * 
	 * @author deli
	 * 
	 */
	public interface PayCorpType {
		public static final Basecode VSL_CORP = new Basecode(4801, "船公司");
		public static final Basecode START_TRUCK = new Basecode(4802, "起驳车队");
		public static final Basecode REACH_TRUCK = new Basecode(4803, "达驳车队");
		public static final Basecode BARGE_CORP = new Basecode(4804, "驳船船东");
		public static final Basecode WHARF_CORP = new Basecode(4805, "码头公司");
	}

	/**
	 * 提取环节
	 * 
	 * @author deli
	 * 
	 */
	public interface ExtracSetp {
		public static final Basecode START_DISP = new Basecode(4901, "起驳调度");
		public static final Basecode START_MATCH_BARGE = new Basecode(4902,
				"起驳配驳船");
		public static final Basecode MATCH_SHIP = new Basecode(4903, "大船");
		public static final Basecode REACH_MATCH_BARGE = new Basecode(4904,
				"达驳配驳船");
		public static final Basecode REACH_DISP = new Basecode(4905, "达驳调度");
	}

	/**
	 * 主要价格费用类型
	 * 
	 * @author deli
	 * 
	 */
	public interface PriceType {
		public static final Basecode TRUCK = new Basecode(5001, "集卡费");
		public static final Basecode SHIP = new Basecode(5002, "海运费");
		public static final Basecode BARGE = new Basecode(5003, "驳船费");
		public static final Basecode TRAIN = new Basecode(5004, "火车费");
	}

	/**
	 * 委托单提交状态
	 * 
	 * @author zjq
	 * 
	 */
	public interface BusOrderStatus {
		public static final Basecode UNCOMMITTED = new Basecode(8302, "未提交");
		public static final Basecode SUBMIT = new Basecode(8301, "已提交");
		public static final Basecode ACCOMPLISH = new Basecode(5804, "完成");
		public static final Basecode STOP = new Basecode(5803, "停用");
		public static final Basecode SAVE = new Basecode(5805, "保存");
	}

	/**
	 * 合同类型
	 * 
	 * @author zjq
	 * 
	 */
	public interface ContractType {
		public static final Basecode COMPREHENSIVE = new Basecode(5305, "综合类合同");
		public static final Basecode SHIP = new Basecode(5306, "船公司合同");
		public static final Basecode SUPPILER = new Basecode(5307, "供应商合同");
		public static final Basecode CLIENT = new Basecode(5308, "客户合同");
		public static final Basecode RENTSHIP = new Basecode(5309, "租船合同");
		public static final Basecode REACHTRUCKGROUP = new Basecode(5310,
				"达驳车队合同");
	}

	/**
	 * 合同付款方式
	 * 
	 * @author zjq
	 * 
	 */
	public interface payType {
		public static final Basecode BYCASH = new Basecode(5504, "现金");
		public static final Basecode BYCHECK = new Basecode(5505, "支票");
		public static final Basecode TRANSFERACCOUNT = new Basecode(5506, "转账");
		public static final Basecode ACCEPT = new Basecode(5507, "承兑");
	}

	/**
	 * 是否开发票
	 * 
	 * @author zjq
	 * 
	 */
	public interface isInvoice {
		public static final Basecode YES = new Basecode(1, "是");
		public static final Basecode NO = new Basecode(0, "否");
	}

	/**
	 * 根据ID获取Constants类中定义的Basecode
	 * 
	 * @param id
	 * @return
	 */
	public static Basecode get(int id) {
		return get(Constants.class, id);
	}

	/**
	 * 根据ID获取对应类里定义的Basecode
	 * 
	 * @param clazz
	 * @param id
	 * @return
	 */
	public static Basecode get(Class<?> clazz, int id) {
		Field[] fields = clazz.getFields();
		for (Field field : fields) {
			if (field.getType().equals(Basecode.class)) {
				try {
					Basecode basecode = (Basecode) field.get(new Basecode());
					if (id == basecode.getId()) {
						return basecode;
					}
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * @see 起达驳状态
	 * @author hgc
	 * 
	 */
	public interface BargeStatus {
		public static final Basecode START_BARGE = new Basecode(5701, "起驳");
		public static final Basecode END_BARGE = new Basecode(5702, "达驳");
	}
}
