package com.weixinmpv2;

import java.io.FileNotFoundException;
import java.util.List;

import com.weixinmpv2.bean.BaseResp;
import com.weixinmpv2.bean.GroupInfo;
import com.weixinmpv2.bean.GroupInfo.Group;
import com.weixinmpv2.bean.GroupInfo.Groups;
import com.weixinmpv2.bean.GroupInfo.UserGroup;
import com.weixinmpv2.bean.Users;
import com.weixinmpv2.bean.WeixinmpUser;
import com.weixinmpv2.exception.WeixinmpException;
import com.weixinmpv2.uitl.Util;

/**
 * 用户管理接口
 */
public class UserManagerService {

    /** 微信公众平台API控制器实例 */
    private final AbstractWeixinmpController controller;

    protected UserManagerService(AbstractWeixinmpController controller) {
        this.controller = controller;
    }

    /**
     * 增加一个用户分组
     * @param groupName 分组名称
     * @throws WeixinmpException 如果发生错误
     */
    public void addGroup(String groupName) throws WeixinmpException {
        String url = Util.getProperty("groups_create_url");
        GroupInfo group = new GroupInfo();
        group.group.name = groupName;
        controller.postWithJson(url, group, GroupInfo.class);
    }

    /**
     * 返回所有分组集合
     * @return 分组集合
     * @throws Exception 
     */
    public List<Group> getAllGroupList() throws Exception {
        String url = Util.getProperty("groups_get_url");
        Groups result = controller.postWithJson(url, null, Groups.class);
        return result.groups;
    }

    /**
     * 查找一个用户所在分组的ID（每个用户只能存在与一个分组）
     * @param openId 用户OpenID
     * @return 分组的ID，无分组返回null
     * @throws WeixinmpException 如果发生错误
     */
    public Integer getGroupIdByUser(String openId) throws WeixinmpException {
        String url = Util.getProperty("groups_getid_url");
        UserGroup ug = new UserGroup();
        ug.openid = openId;
        UserGroup result = controller.postWithJson(url, ug, UserGroup.class);
        return result.groupid;
    }

    /**
     * 更新一个分组名称
     * @param groupdId 分组ID
     * @param groupName 新名词
     * @throws WeixinException 如果发生错误
     */
    public void updateGroupName(Integer groupdId, String groupName) throws WeixinmpException {
        
        String url = Util.getProperty("groups_update_url");
        
        GroupInfo info = new GroupInfo();
        info.group.id = groupdId;
        info.group.name = groupName;
        controller.postWithJson(url, info, BaseResp.class);
    }

    /**
     * 移动用户到指定分组
     * @param openId 用户OpenID
     * @param toGroupId 移动到这个分组
     * @throws WeixinmpException 如果发生错误
     */
    public void moveUserGroup(String openId, Integer toGroupId) throws WeixinmpException {
    	 String url = Util.getProperty("groups_members_update_url");
         UserGroup ug = new UserGroup();
         ug.openid = openId;
         ug.to_groupid = toGroupId;
         controller.postWithJson(url, ug, BaseResp.class);
    }

    /**
     * 查询一个用户的详细信息
     * @param openId 用户OpenId
     * @return 这个用户的详细信息，null为找不到
     * @throws FileNotFoundException 如果发生错误
     * @throws Exception 
     */
    public WeixinmpUser getUser(String openId) throws Exception {
        String url = Util.getProperty("user_info_url");
        url = url.replaceFirst("OPENID", openId);
        WeixinmpUser result = controller.postWithJson(url, null, WeixinmpUser.class);
        return result;
    }

    /**
     * 查询关注者列表
     * @param nextOpenId 用于分页查询的变量，从这个用户OpenId开始查询，null为从第一个用户开始
     * @return 用户OpenId的集合，每次查询返回10000个，超过10000个时使用nextOpenId参数
     * @throws Exception 
     */
    public Users getUserList(String nextOpenId) throws Exception {
        
        String url = Util.getProperty("user_list_url");
        
        if (nextOpenId != null) {
            url = url.replaceFirst("NEXT_OPENID", nextOpenId);
        } else {
            url = url.replaceFirst("NEXT_OPENID", "");
        }
        Users result = controller.postWithJson(url, null, Users.class);
        return result;
    }

}
