package com.javaniu.servlet;

import lombok.Getter;

/**
 * @author Lzf
 * ONE(1,"齐"，v1,v2,c3...) 可加入多个值
 */
public enum CountryEnum {

    ONE(1,"齐"),
    TWO(2,"楚"),
    THREE(3,"燕"),
    FOUR(4,"赵"),
    FIVE(5,"魏"),
    SIX(6,"韩");

    @Getter private Integer retCode;
    @Getter private String retMessage;

    CountryEnum(Integer retCode, String retMessage) {
        this.retCode = retCode;
        this.retMessage = retMessage;
    }

    public static CountryEnum forEach_CountryEnum(int index){
        CountryEnum[] array = CountryEnum.values();
        for (CountryEnum element : array) {
            if(index == element.getRetCode()){
                return element;
            }
        }
        return null;
    }
}
