package com.bluesgao.dds;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataSourceEntity {
    private String ip;
    private int port;
    private String username;
    private String password;
    private String db;
}
