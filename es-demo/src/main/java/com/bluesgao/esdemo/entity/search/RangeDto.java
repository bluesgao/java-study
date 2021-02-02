package com.bluesgao.esdemo.entity.search;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
public class RangeDto implements Serializable {
    private static final long serialVersionUID = 3649202759701145894L;
    private FromDto fromDto;
    private ToDto toDto;

    public static class FromDto {
        private long from;
        private boolean includeUpper = false;

        public FromDto() {
        }

        public FromDto(long from, boolean includeUpper) {
            this.from = from;
            this.includeUpper = includeUpper;
        }

        public long getFrom() {
            return from;
        }

        public void setFrom(long from) {
            this.from = from;
        }

        public boolean isIncludeUpper() {
            return includeUpper;
        }

        public void setIncludeUpper(boolean includeUpper) {
            this.includeUpper = includeUpper;
        }
    }

    public static class ToDto {

        private long to;
        private boolean includeLower = false;

        public ToDto() {
        }

        public ToDto(long to, boolean includeLower) {
            this.to = to;
            this.includeLower = includeLower;
        }

        public long getTo() {
            return to;
        }

        public void setTo(long to) {
            this.to = to;
        }

        public boolean isIncludeLower() {
            return includeLower;
        }

        public void setIncludeLower(boolean includeLower) {
            this.includeLower = includeLower;
        }
    }
}
