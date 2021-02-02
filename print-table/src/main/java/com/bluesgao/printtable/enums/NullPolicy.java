package com.bluesgao.printtable.enums;

import com.bluesgao.printtable.table.Cell;

/**
 * @ClassName：NullPolicy
 * @Description：
 * @Author：bluesgao
 * @Date：2021/2/2 10:56
 **/
public enum NullPolicy {

    THROW {
        @Override
        public Cell getCell(Cell cell) {
            throw new IllegalArgumentException("cell or value is null: " + cell);
        }
    },
    NULL_STRING {
        @Override
        public Cell getCell(Cell cell) {
            if(cell == null){
                return new Cell("null");
            }
            cell.setValue("null");
            return cell;
        }
    },
    EMPTY_STRING {
        @Override
        public Cell getCell(Cell cell) {
            if(cell == null){
                return new Cell("");
            }
            cell.setValue("");
            return cell;
        }
    };

    public abstract Cell getCell(Cell cell);

}
