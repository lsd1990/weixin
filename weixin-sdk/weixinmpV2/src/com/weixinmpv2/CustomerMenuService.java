package com.weixinmpv2;

import com.weixinmpv2.bean.BaseResp;
import com.weixinmpv2.bean.CustomMenu;
import com.weixinmpv2.bean.CustomMenu.CustomButton;
import com.weixinmpv2.exception.WeixinmpException;
import com.weixinmpv2.uitl.Util;


public class CustomerMenuService {

    private final AbstractWeixinmpController controller;

    protected CustomerMenuService(AbstractWeixinmpController controller) {
        this.controller = controller;
    }

    /**
     * 重新定义整个自定义菜单，包括一级和二级
     * @param button 自定义菜单数据
     * @return WeixinException 如发生错误
     * @throws WeixinmpException 
     */
    public void updateMenu(CustomButton button) throws WeixinmpException {
    	
    	String url = Util.getProperty("menu_create_url");
    	controller.postWithJson(url, button, BaseResp.class);
    }

    /**
     * 获取现有的自定义菜单
     * @return 自定义菜单数据
     * @return WeixinException 如发生错误
     */
    public CustomMenu getMenu() throws WeixinmpException {
        String url = Util.getProperty("menu_query_url");
        CustomMenu menu = controller.postWithJson(url, null, CustomMenu.class);
        return menu;
    }

    /***
     * 删除整个自定义菜单
     * @return WeixinException 如发生错误
     */
    public void deleteMenu() throws WeixinmpException {
        String url = Util.getProperty("menu_delete_url");
        controller.postWithJson(url, null, BaseResp.class);
    }

}
