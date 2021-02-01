package com.bluesgao.esdemo.entity.search;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
public class RangeDto implements Serializable {
    private static final long serialVersionUID = 3649202759701145894L;
    private Long min;
    private Long minE;
    private Long max;
    private Long maxE;

    private String minStr;
    private String minEStr;
    private String maxStr;
    private String maxEStr;
}
