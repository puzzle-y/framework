/**
 * 
 */
package com.hbasesoft.framework.config.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.hbasesoft.framework.cache.core.CacheConstant;
import com.hbasesoft.framework.common.GlobalConstants;
import com.hbasesoft.framework.common.ServiceException;
import com.hbasesoft.framework.common.utils.CommonUtil;
import com.hbasesoft.framework.common.utils.PropertyHolder;
import com.hbasesoft.framework.common.utils.logger.Logger;
import com.hbasesoft.framework.config.core.bean.DictionaryDataPojo;
import com.hbasesoft.framework.config.core.service.ConfigService;
import com.hbasesoft.framework.config.core.service.DictionaryDataService;

/**
 * <Description> <br>
 * 
 * @author xgf<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2014年10月26日 <br>
 * @since V1.0<br>
 * @see com.hbasesoft.framework.log.config <br>
 */

public final class ConfigHelper {

    /**
     * logger
     */
    private static Logger logger = new Logger(ConfigHelper.class);

    /**
     * configService
     */
    private static ConfigService configService;

    private static DictionaryDataService dictionaryDataService;

    public static String getModuleCode() {
        return PropertyHolder.getProperty(CacheConstant.MODULE_CODE);
    }

    public static String getDictName(String dictCode, String value) {
        try {
            return dictionaryDataService.queryDictData(dictCode, value);
        }
        catch (ServiceException e) {
            logger.warn(e.getMessage(), e);
        }
        return GlobalConstants.BLANK;
    }

    public static Map<String, String> getDict(String dictCode) {
        Map<String, String> dicts = new HashMap<String, String>();
        try {
            List<DictionaryDataPojo> dictDatas = dictionaryDataService.qryAlldictData(dictCode);
            if (CommonUtil.isNotEmpty(dictDatas)) {
                dictDatas.forEach((dict) -> {
                    dicts.put(dict.getDictDataName(), dict.getDictDataValue());
                });
            }
        }
        catch (ServiceException e) {
            logger.warn(e.getMessage(), e);
        }
        return dicts;
    }

    /**
     * 根据key获取配置值：字符串类型
     * 
     * @param key key
     * @return 值
     */
    public static String getString(String key) {
        String value = PropertyHolder.getProperty(key);
        if (value == null) {
            try {
                String[] code = StringUtils.split(key, GlobalConstants.PERIOD);
                if (code.length == 2) {
                    value = configService.queryConfig(getModuleCode(), code[0], code[1]);
                }
            }
            catch (Exception e) {
                logger.warn(e, "get cache error. key is [{0}]", key);
            }
        }
        return value;
    }

    /**
     * Description: 根据key获取配置值：字符串类型 <br>
     * 
     * @author 王伟<br>
     * @taskId <br>
     * @param key key
     * @param defaultValue 默认值
     * @return 值<br>
     */
    public static String getString(String key, String defaultValue) {
        String value = getString(key);
        return CommonUtil.isEmpty(value) ? defaultValue : value;
    }

    /**
     * 根据key获取配置值：布尔型
     * 
     * @param key key
     * @return 值
     */
    public static boolean getBoolean(String key) {
        return StringUtils.equalsIgnoreCase("true", getString(key));
    }

    /**
     * 根据key获取配置值：布尔型
     * 
     * @param key key
     * @return 值
     */
    public static boolean getBoolean(String key, boolean defaultValue) {
        String value = getString(key);
        return StringUtils.isEmpty(value) ? defaultValue : StringUtils.equalsIgnoreCase("true", getString(key));
    }

    /**
     * 根据key获取配置值：整型
     * 
     * @param key key
     * @return 值
     */
    public static int getInt(String key) {
        return Integer.valueOf(getString(key));
    }

    /**
     * 根据key获取配置值：整型
     * 
     * @param key key
     * @return 值
     */
    public static int getInt(String key, int defaultValue) {
        String value = getString(key);
        return StringUtils.isEmpty(value) ? defaultValue : Integer.valueOf(value);
    }

    /**
     * 根据key获取配置值：长整型
     * 
     * @param key key
     * @return 值
     */
    public static long getLong(String key) {
        return Long.valueOf(getString(key));
    }

    /**
     * Description: <br>
     * 
     * @author 王伟<br>
     * @taskId <br>
     * @param key <br>
     * @param defaultValue <br>
     * @return <br>
     */
    public static long getLong(String key, Long defaultValue) {
        String value = getString(key);
        return CommonUtil.isEmpty(value) ? defaultValue : Long.valueOf(value);
    }

    public static void setConfigService(ConfigService configService) {
        ConfigHelper.configService = configService;
    }

    /**
     * Description: <br>
     * 
     * @author 王伟<br>
     * @taskId <br>
     * @param dictionaryDataService <br>
     */
    public static void setDictionaryDataService(DictionaryDataService dictionaryDataService) {
        ConfigHelper.dictionaryDataService = dictionaryDataService;
    }
}