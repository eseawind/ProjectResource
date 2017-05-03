package com.ireport.service.impl;

import com.commonUtil.OperationException;
import com.ireport.dao.IireportDao;
import com.ireport.service.IireportService;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRMapArrayDataSource;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
    public List<Map<String, ?>> queryUsers(Map<String, Object> params, Map<String, Object> model) throws Exception {
        List<Map<String, ?>> rs;
        try {
            rs = iireportDao.queryUsers(params);
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperationException("查询异常！");
        }
        JRDataSource jrDataSource=new JRMapCollectionDataSource(rs);
        model.put("url", "/module/ireportFiles/test.jasper");
        model.put("format", "pdf");
        model.put("dataSource", jrDataSource);
        return rs;
    }
}
