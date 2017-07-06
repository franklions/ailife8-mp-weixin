package com.franklions.ailife.webapp.handler;


import com.franklions.ailife.webapp.builder.TextBuilder;
import com.franklions.ailife.webapp.domain.ApplyData;
import com.franklions.ailife.webapp.domain.ApplyPerson;
import com.franklions.ailife.webapp.service.IApplyDataService;
import com.franklions.ailife.webapp.service.IApplyPersonService;
import com.franklions.ailife.webapp.utils.JsonUtils;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutNewsMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutNewsMessage.Item;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Component
public class MsgHandler extends AbstractHandler {

    @Autowired
    IApplyDataService applyDataService;

    @Autowired
    IApplyPersonService applyPersonService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) {

        if (!wxMessage.getMsgType().equals(WxConsts.XML_MSG_EVENT)) {
            //TODO 可以选择将消息保存到本地
        }

        //当用户输入关键词如“你好”，“客服”等，并且有客服在线时，把消息转发给在线客服
        try {
            if (StringUtils.startsWithAny(wxMessage.getContent(), "你好", "客服")
                    && weixinService.getKefuService().kfOnlineList()
                    .getKfOnlineList().size() > 0) {
                return WxMpXmlOutMessage.TRANSFER_CUSTOMER_SERVICE()
                        .fromUser(wxMessage.getToUser())
                        .toUser(wxMessage.getFromUser()).build();
            }
        } catch (WxErrorException e) {
            e.printStackTrace();
        }

        //TODO 组装回复消息
//        String content = "收到信息内容：" + JsonUtils.toJson(wxMessage);
        String replyContent= "即将上线 敬请期待！";
        String[] keys = wxMessage.getContent().split(" ");
        String menuKey;
        if(keys.length < 1){
            menuKey = "null";
        }else{
            menuKey = keys[0].toLowerCase();
        }

        switch (menuKey)
        {
            case "1":           //中签查询
                replyContent=GetApplyDatas(wxMessage.getContent());
                break;
            case "2":           //绑定申请编号
                replyContent=bindApplyPersion(wxMessage);
                break;
            case "3":           //活动一下
                Item item = new Item();
                Item item2 = new Item();

                item.setDescription("“一直到现在，我还无法平复心情，早上8点不到我从萨家湾上的公交车，跟往常一样去新街口上班，8点多到了鼓楼站，我把座位让给了一个老奶奶，然后我的噩梦就来了……”7月14日下午，南京女孩在其微博上写出了自己的经历，“称13日的早高峰，她乘公交车时遭遇性骚扰。”");
                item.setPicUrl("https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=243142541,3253755964&fm=80");
                item.setTitle("南京女孩公交遇色狼");
                item.setUrl("");

                item2.setDescription("打开朋友圈，经常看到好友在晒自己养的多肉植物。这么“肉”迷们不仅把多肉当“宠物”养，还是交友的纽带。为什么大家那么喜欢多肉植物呢，在这里就跟大家浅谈一下养殖多肉植物的有哪些好处。");
                item2.setPicUrl("https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=825981594,1825948726&fm=80");
                item2.setTitle("养殖多肉植物都有哪些好处？");
                item2.setUrl("");

                return WxMpXmlOutMessage.NEWS().addArticle(item).addArticle(item2).fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser()).build();
            case "4":           //多彩生活
                replyContent="即将上线 敬请期待！";
                break;
            case "5":           //生活机器人
                replyContent="即将上线 敬请期待！";
                break;
            case "6":           //关于我们
                replyContent="即将上线 敬请期待！";
                break;
            case "cx":          //绑定中签查询
                replyContent=GetApplyDataByWxUser(wxMessage);
                break;
            default:
                return createMenuBuilder(wxMessage,weixinService);
        }

        logger.info("返回结果：\n"+replyContent);

        return new TextBuilder().build(replyContent, wxMessage, weixinService);

    }

    private String GetApplyDataByWxUser(WxMpXmlMessage wxMessage) {
        //查询用户是否已经存在
        ApplyPerson applyPerson = applyPersonService.getPersonBywxUser(wxMessage.getFromUser());

        if(applyPerson == null) {
            return "您尚未绑定，请先绑定！";
        }

        List<ApplyData> queryData = applyDataService.searchApplyByCode(applyPerson.getApplycode());

        String searchResult = "中签结果：\n";
        if(queryData == null || queryData.size() <1)
        {
            searchResult="抱歉，该申请人本次未中签！\n预祝下次中签！";
        }else
        {
            for (ApplyData applyData : queryData) {
                searchResult =applyData.getApplycode() +"|"+applyData.getApplyname()+"|"+applyData.getIssuenumber() + "\n";
            }

        }

        return searchResult;
    }

    /**
     * 绑定申请用户
     * @param wxMessage
     * @return
     */
    private String bindApplyPersion(WxMpXmlMessage wxMessage) {
        String personContent = wxMessage.getContent();  //2 0028101124981 18622436962
        String wxUser = wxMessage.getFromUser();
        String[] personInfos = personContent.split(" ");

        if(personInfos.length <3) {
            return "参数错误!";
        }

        //验证applycode

        //验证 手机号

        //查询用户是否已经存在
        ApplyPerson wxPerson = applyPersonService.getPersonBywxUser(wxUser);

        if(wxPerson == null) {

            ApplyPerson applyPerson = new ApplyPerson();
            applyPerson.setApplycode(personInfos[1]);
            applyPerson.setPhonenum(personInfos[2]);
            applyPerson.setWxuser(wxUser);
            applyPerson.setTs(System.currentTimeMillis());


            if (!applyPersonService.savePerson(applyPerson)) {
                return "服务器错误，请重试!";
            }
        }else{
            wxPerson.setApplycode(personInfos[1]);
            wxPerson.setPhonenum(personInfos[2]);
            wxPerson.setTs(System.currentTimeMillis());

            if (!applyPersonService.updatePerson(wxPerson)) {
                return "服务器错误，请重试!";
            }
        }

        return "恭喜绑定成功,查询中签只需要输入'cx'.";
    }

    /**
     * 查询中签结果
     * @param keys
     * @return
     */
    private String GetApplyDatas(String keys) {
        String[] searchUser = keys.split(" ");
        if(searchUser.length >1)
        {
            List<ApplyData> applys =applyDataService.searchApply(searchUser[1] );

            String searchResult = "中签结果：\n";
            if(applys.size() >0)
            {
                for (ApplyData applyData : applys) {
                    searchResult +=applyData.getApplycode() +"|"+applyData.getApplyname()+"|"+applyData.getIssuenumber() + "\n";
                }
            }else
            {
                searchResult="抱歉，该申请人本次未中签！\n预祝下次中签！";
            }

            return searchResult;
        }else
        {
            return "查询参数错误.";
        }
    }

    /**
     * 创建自动回复菜单
     * @param wxMessage
     * @param service
     * @return
     */
    private WxMpXmlOutMessage createMenuBuilder(WxMpXmlMessage wxMessage,
                                                WxMpService service) {
        Item item1 = new Item();
        Item item2 = new Item();
        Item item3 = new Item();
        Item item4 = new Item();
        Item item5 = new Item();
        Item item6 = new Item();

        item1.setTitle("1.摇号查询");
        item1.setDescription("查询方式： 指令编号+空格+内容");
        item1.setPicUrl("https://mmbiz.qlogo.cn/mmbiz/Qtf4hVTObN5qSMndA8Lw94QVGq984HeUVRLsqbVR8LzmKegHlTymCs1y2mFk4y5XiabGSujzlwE18KNq78dbMrQ/0?wx_fmt=png");
        item1.setUrl("http://63450fc0.ngrok.io");
        item2.setTitle("2.绑定申请编号");
        item2.setDescription("绑定申请编号");
        item2.setPicUrl("https://mmbiz.qlogo.cn/mmbiz/Qtf4hVTObN5qSMndA8Lw94QVGq984HeUgnibaHIGGenrO9njmQsucEWykGn87iaDKpCAwibacea7icHjXFMXC4Ztrg/0?wx_fmt=jpeg");
        item2.setUrl("http://63450fc0.ngrok.io");
        item3.setTitle("3.活动一下");
        item3.setDescription("活动一下");
        item3.setPicUrl("https://mmbiz.qlogo.cn/mmbiz/Qtf4hVTObN5qSMndA8Lw94QVGq984HeUVRLsqbVR8LzmKegHlTymCs1y2mFk4y5XiabGSujzlwE18KNq78dbMrQ/0?wx_fmt=png");
        item3.setUrl("http://63450fc0.ngrok.io");
        item4.setTitle("4.多彩生活");
        item4.setDescription("多彩生活");
        item4.setPicUrl("https://mmbiz.qlogo.cn/mmbiz/Qtf4hVTObN5qSMndA8Lw94QVGq984HeUicE71EuoEqeuibIC451U6S1kwXejrDicIPwWjibupBgrBeawUmsD2nhwWw/0?wx_fmt=jpeg");
        item4.setUrl("http://63450fc0.ngrok.io");
        item5.setTitle("5.生活机器人");
        item5.setDescription("生活机器人");
        item5.setPicUrl("https://mmbiz.qlogo.cn/mmbiz/Qtf4hVTObN5qSMndA8Lw94QVGq984HeUhicK8Lw0cmpUxzCGcqgkaapf1m4v86arMhflwpCMozjd26gd9HjkXpw/0?wx_fmt=jpeg");
        item5.setUrl("http://63450fc0.ngrok.io");
        item6.setTitle("6.关于我们");
        item6.setDescription("关于我们");
        item6.setPicUrl("https://mmbiz.qlogo.cn/mmbiz/Qtf4hVTObN5qSMndA8Lw94QVGq984HeU7PGFKxx0BcQkIqfansaFqzsFfmPS4Qza4NGGqgGssib7E9kF0MOB6Tg/0?wx_fmt=jpeg");
        item6.setUrl("http://63450fc0.ngrok.io");
        return WxMpXmlOutMessage.NEWS().addArticle(item1)
                .addArticle(item2)
                .addArticle(item3)
                .addArticle(item4)
                .addArticle(item5)
                .addArticle(item6).fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser()).build();
    }

}
