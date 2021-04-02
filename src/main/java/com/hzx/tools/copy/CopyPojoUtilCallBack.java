package com.hzx.tools.copy;

public interface CopyPojoUtilCallBack<S, T> {

    void callBack(S t, T s);
}