package com.dtac.bmweb.util;


public class ValidateChannelServiceUtils {
	
	private static final MapBean MOBILE_CREDIT_CARD = new MapBean(0, 1);
	
	public int[] decimal2BinaryStr(int number) {
		int binary[] = new int[10];
		int index = 0;
		while (number > 0) {
			binary[index++] = number % 2;
			number = number / 2;
		}
		return binary;
	}
	
	public boolean isMobileCreditCard(int addChannelId){
		return validate(MOBILE_CREDIT_CARD, addChannelId);
	}
	
	private boolean validate(MapBean bean, int featureId){
		int[] transFeatureId = this.decimal2BinaryStr(featureId);
//		this.print(transFeatureId);
		int[] validator = this.decimal2BinaryStr(bean.getValue());
//		this.print(validator);
		if(null != transFeatureId || null != validator){
			if((validator[bean.getBit()] & transFeatureId[bean.getBit()]) == 1){
				return true;
			}
		}
		return false;
	}

	public static void main(String a[]) {
		ValidateChannelServiceUtils bn = new ValidateChannelServiceUtils();
		System.out.println(bn.isMobileCreditCard(3));
	}
	
	public void print(int[] transFeatureId){
		for (int i = (transFeatureId.length - 1); i >= 0; i--) {
			System.out.print(transFeatureId[i]);
		}
		System.out.println();
	}
	
   
}

class MapBean{
	private int bit;
	private int value;
	
	public MapBean(int bit, int value) {
		this.bit = bit;
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public int getBit() {
		return bit;
	}	
}
