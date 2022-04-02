package com.flatangle.rubbishsearch.common;

/**
 * @author DaY1zz
 * @create 2022-04-02-17:23
 */
public class WechatException extends Exception
{
    private int value;
    public WechatException()
    {
        super();
    }
    public WechatException(String msg)    //无参构造方法
    {
        super(msg);
    }
    public WechatException(String msg, int value)    //含String参数的构造方法
    {
        super(msg);
        this.value = value;
    }
    public int getValue()
    {
        return value;
    }
}
