SELECT T.MODULE_CODE,T.CONFIG_ITEM_CODE,T.PARAM_CODE,T.PARAM_VALUE, T.DEFAULT_PARAM_VALUE
FROM T_SYS_CONFIG_ITEM T
#if($configItem)
WHERE 1 = 1
    #if($configItem.configItemId)
        AND T.CONFIG_ITEM_ID = :configItem.configItemId
    #end
    #if($configItem.moduleCode)
        AND T.MODULE_CODE = :configItem.moduleCode
    #end
    #if($configItem.directoryCode)
        AND T.DIRECTORY_CODE = :configItem.directoryCode
    #end
    #if($configItem.configItemCode)
        AND T.CONFIG_ITEM_CODE = :configItem.configItemCode
    #end
#end