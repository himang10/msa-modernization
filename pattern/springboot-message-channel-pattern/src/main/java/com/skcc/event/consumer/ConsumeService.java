package com.skcc.event.consumer;

import java.lang.reflect.Method;

public interface ConsumeService {

    void subscribe(String[] topics, Method method);
}
