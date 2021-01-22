package com.blusgao.dirver.manager;


import com.blusgao.driver.export.Driver;

import java.util.Iterator;
import java.util.ServiceLoader;

public class DriverManger {
    static {
        loadInitialDrivers();
        System.out.println("JDBC DriverManager initialized");
    }

    private static void loadInitialDrivers() {
        ServiceLoader<Driver> loadedDrivers = ServiceLoader.load(Driver.class);
        Iterator<Driver> driversIterator = loadedDrivers.iterator();
        try {
            while (driversIterator.hasNext()) {
                Driver driver = driversIterator.next();
                System.out.println("driver name:" + driver.getDriverName());
            }
        } catch (Throwable t) {
            // Do nothing
        }
    }

}
