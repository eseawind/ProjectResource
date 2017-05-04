package com.ireport.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.jasperreports.JasperReportsHtmlView;

import com.commonUtil.OperationException;
import com.commonUtil.StringUtils;
import com.ireport.dao.IireportDao;
import com.ireport.service.IireportService;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;

/**
 * Created by SShi11 on 5/3/2017.
 */
@Service
public class IreportServiceImpl implements IireportService {
    @Autowired
    private IireportDao iireportDao;

    @Override
    public List<Map<String, Object>> queryMapList(Map<String, Object> params) throws Exception {
        return null;
    }

    @Override
    public int updateEntity(Map<String, Object> params) throws Exception {
        return 0;
    }


    @Override
    public Map<String, Object>  queryUsers(Map<String, Object> params,Map<String, Object> model) throws Exception {
        List<Map<String, ?>> rs;
        try {
            rs = iireportDao.queryUsers(params);
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperationException("查询异常！");
        }
        JRDataSource jrDataSource=new JRMapCollectionDataSource(rs);
        model.put("url", "/module/ireportFiles/test.jasper");
        model.put("format", "html");
        model.put("dataSource", jrDataSource);
        return model;
    }
    
    /**
     * 导出
     * @param params
     * @param model
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object>  ExportUsers(Map<String, Object> params,Map<String, Object> model) throws Exception {
        List<Map<String, ?>> rs;
        String type=StringUtils.toStr(params.get("type"));
        try {
            rs = iireportDao.queryUsers(params);
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperationException("查询异常！");
        }
        JRDataSource jrDataSource=new JRMapCollectionDataSource(rs);
        model.put("url", "/module/ireportFiles/test.jasper");
        model.put("format", type);
        model.put("dataSource", jrDataSource);
        return model;
    }
}
