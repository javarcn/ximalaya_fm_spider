package com.javarcn;

import com.javarcn.file.FmTxt;
import com.javarcn.service.FmService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Hello world!
 */
public class App {

    private final static Logger log= LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        log.info("开始爬取喜马拉雅FM:>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ");
        long start_time=System.currentTimeMillis();
        List<String> list=new ArrayList<String>();
        List<String> categoryList=FmService.getCategoryList();
        for(String category:categoryList){
            List<String> fmList=FmService.getFmListByCategory(category);
            if(fmList.size()>1){
                list.addAll(fmList);
            }
        }
        log.info("共爬取有效分类："+categoryList.size()+"个，有效FM："+list.size());
        long end_time=System.currentTimeMillis();
        FmTxt.save(list);
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<共耗时："+(end_time-start_time)/1000+"秒");
    }
}
