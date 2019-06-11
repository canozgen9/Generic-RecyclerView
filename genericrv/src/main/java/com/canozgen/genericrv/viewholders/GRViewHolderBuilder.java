package com.canozgen.genericrv.viewholders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.InvocationTargetException;

public class GRViewHolderBuilder<T> {
    private Class aClass;
    private Context content;
    private int layoutID;

    public GRViewHolderBuilder<T> typeOf(Class aClass) {
        this.aClass = aClass;
        return this;
    }

    public GRViewHolderBuilder<T> withContext(Context context) {
        this.content = context;
        return this;
    }

    public GRViewHolderBuilder<T> withLayout(int layoutID) {
        this.layoutID = layoutID;
        return this;
    }

    public T build(ViewGroup root, GRViewHolderClickEventListener clickEventListener) {
        View view = LayoutInflater.from(content).inflate(layoutID, root, false);
        try {
            return (T) aClass.getDeclaredConstructor(View.class, GRViewHolderClickEventListener.class).newInstance(view, clickEventListener);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        } catch (InstantiationException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }
}
