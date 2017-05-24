package com.ctinute.foody.View.Listener;

import com.ctinute.foody.Object.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public interface AsyncHttpListener {
    void onSend();
    void onFinish(List list);
}
