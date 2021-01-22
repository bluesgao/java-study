package com.blusgao.driver.extend;

import com.blusgao.driver.export.Driver;

public class MysqlDriver implements Driver {
    @Override
    public String getDriverName() {
        return this.getClass().getCanonicalName();
    }
}
