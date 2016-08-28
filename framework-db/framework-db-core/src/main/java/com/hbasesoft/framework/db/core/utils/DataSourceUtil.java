/**************************************************************************************** 
 Copyright © 2003-2012 hbasesoft Corporation. All rights reserved. Reproduction or       <br>
 transmission in whole or in part, in any form or by any means, electronic, mechanical <br>
 or otherwise, is prohibited without the prior written consent of the copyright owner. <br>
 ****************************************************************************************/
package com.hbasesoft.framework.db.core.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ServiceLoader;

import javax.sql.DataSource;

import com.hbasesoft.framework.common.utils.Assert;
import com.hbasesoft.framework.db.core.DataSourceRegister;

/**
 * <Description> <br>
 * 
 * @author 王伟<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2016年8月28日 <br>
 * @since V1.0<br>
 * @see com.hbasesoft.framework.db.core <br>
 */
public final class DataSourceUtil {

    private static Map<String, List<DataSource>> dataSourceMap;

    private static Random random = new Random();

    private DataSourceUtil() {
    }

    public static List<DataSource> getDataSources(String name) {
        return getDataSourceMap().get(name);
    }

    public static DataSource getDataSource(String name) {
        List<DataSource> dsList = getDataSources(name);
        Assert.notEmpty(dsList, "数据库链接不存在 name=[{0}]", name);
        int index = random.nextInt(dsList.size());
        return dsList.get(index);
    }

    private static Map<String, List<DataSource>> getDataSourceMap() {
        if (dataSourceMap == null) {
            ServiceLoader<DataSourceRegister> registerLoader = ServiceLoader.load(DataSourceRegister.class);
            if (registerLoader != null) {
                dataSourceMap = new HashMap<String, List<DataSource>>();
                registerLoader.forEach(register -> {
                    List<DataSource> dsList = dataSourceMap.get(register.getTypeName());
                    if (dsList == null) {
                        dsList = new ArrayList<DataSource>();
                        dataSourceMap.put(register.getTypeName(), dsList);
                    }
                    dsList.add(register.regist());
                });
            }

        }
        return dataSourceMap;
    }

}
