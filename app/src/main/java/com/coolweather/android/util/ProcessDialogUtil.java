package com.coolweather.android.util;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.coolweather.android.R;

/**
 * Created by zhouyang on 2019/5/28.
 */

public class ProcessDialogUtil {
    private static AlertDialog myAlertDialog;

    /**
     * 打开对话进度框
     */
    public static void openProgressDialog(Context context,String str)
    {
        if (myAlertDialog == null)
        {
            myAlertDialog = new AlertDialog.Builder(context).create();
        }

        View loadView = LayoutInflater.from(context).inflate(R.layout.processdialog_view,null);

        myAlertDialog.setView(loadView,0,0,0,0);

        String text = "Loading...";
        if (str!=null)
        {
            text = str;
        }

        TextView textView = loadView.findViewById(R.id.progressText);
        textView.setText(text);

        myAlertDialog.show();
    }

    /**
     * 关闭进度条
     */
     public static void dismiss()
     {
         if (myAlertDialog!=null&&myAlertDialog.isShowing())
             myAlertDialog.dismiss();
     }
}
