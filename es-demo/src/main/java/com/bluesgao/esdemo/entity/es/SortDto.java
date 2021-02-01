package com.bluesgao.esdemo.entity.es;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SortDto implements Serializable {
    private static final long serialVersionUID = -8887937071202420058L;
    private String column;

    private SortEnum sortEnum;
}
