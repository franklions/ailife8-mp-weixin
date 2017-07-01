package com.franklions.ailife.crawler.webmagic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.franklions.ailife.crawler.domain.ApplyData;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;
import us.codecraft.webmagic.utils.HttpConstant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/26.
 * 天津摇号中签编码爬取
 * 爬取地址：http://apply.tjjttk.gov.cn/apply/norm/personQuery.html
 */
public class ApplyPageProcessor implements PageProcessor {

    private Site site;

    public ApplyPageProcessor() {
        site = Site.me().setRetryTimes(3).setSleepTime(0);
    }

    @Override
    public void process(Page page) {
        /**
         * 第一步对请求结果进行解析，然后与数据库地址进行对比，数据库中没有的加入到队列
         *
         */
        String url = page.getUrl().get();

        if(url==("http://apply.tjjttk.gov.cn/apply/norm/personQuery.html")) {

            List<Selectable> selectNodes = page.getHtml().xpath("//*[@id='issueNumber']//option").nodes();
            for (Selectable selectable : selectNodes) {
                String issueNumber = selectable.xpath("option/text(0)").get();

                Request request = new Request("http://apply.tjjttk.gov.cn/apply/norm/personQuery.html?" + issueNumber);
                Map nameValuePair = new HashMap();
                NameValuePair[] values = new NameValuePair[3];
                values[0] = new BasicNameValuePair("applyCode", "");
                values[1] = new BasicNameValuePair("issueNumber", issueNumber);
                values[2] = new BasicNameValuePair("pageNo", "1");
                nameValuePair.put("nameValuePair", values);
                request.setExtras(nameValuePair);
                request.setMethod(HttpConstant.Method.POST);
                if (!"最近六个月".equals(issueNumber)) {
                    page.addTargetRequest(request);
                }

            }
            page.setSkip(true);
        }else if(url.matches("http://apply.tjjttk.gov.cn/apply/norm/personQuery.html\\?[0-9]{6}")){
            List<Selectable> pageNumNodes = page.getHtml().xpath("//span[@class='f_orange']").nodes();
            if(pageNumNodes.size() >0)
            {

                String issueNumber = url.split("\\?")[1];
                String pageIndex= pageNumNodes.get(0).xpath("span/text(0)").get();
                Integer pageNum = Integer.parseInt(pageIndex);
                for(Integer i =1 ;i<=pageNum;i++)
                {
                    Request request = new Request("http://apply.tjjttk.gov.cn/apply/norm/personQuery.html?n"+issueNumber+"p"+i.toString());
                    Map nameValuePair = new HashMap();
                    NameValuePair[] values = new NameValuePair[3];
                    values[0] = new BasicNameValuePair("applyCode", "");
                    values[1] = new BasicNameValuePair("issueNumber", issueNumber);
                    values[2] = new BasicNameValuePair("pageNo", i.toString());
                    nameValuePair.put("nameValuePair", values);
                    request.setExtras(nameValuePair);
                    request.setMethod(HttpConstant.Method.POST);

                    page.addTargetRequest(request);
                    //TODO test code
//                    if(i >=3) break;
                }
            }

            page.setSkip(true);
        }else{
            String number =url.split("\\?")[1];
            number = number.substring(1, 7);
            Integer issueNumber = Integer.parseInt(number);
            List<Selectable> selectNodes = page.getHtml().xpath("//*[@class='ge2_content']//tr").nodes();
            List<ApplyData> applyDataList = new ArrayList<ApplyData>();
            for (Selectable selectable : selectNodes) {
                String code = selectable.xpath("//tr/td[1]/text(0)").get();
                String name = selectable.xpath("//tr/td[2]/text(0)").get();
                if(code != null && code !="")
                {
                    ApplyData apply = new ApplyData();
                    apply.setApplycode(code);
                    apply.setApplyname(name);
                    apply.setIssuenumber(issueNumber);
                    apply.setTs(System.currentTimeMillis());
                    applyDataList.add(apply);
                }
            }
            ObjectMapper mapper = new ObjectMapper();
            try {
                page.putField("applys", mapper.writeValueAsString(applyDataList));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Site getSite() {
        return site;
    }
}
