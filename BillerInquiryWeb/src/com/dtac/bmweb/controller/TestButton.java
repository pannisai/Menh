package com.dtac.bmweb.controller;

import org.zkoss.zul.Button;
import org.zkoss.zul.Messagebox;

public class TestButton extends Button{
public static void onClick(Button button){
	Messagebox.show("Hello World!"+button.getId());
}
}
