package com.ryan.view_siso;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;


/**
 * Created by air on 15/11/15.
 */
public class SmsUtil {

    public static void sendSmsWithDefault(Context context) {
        Uri smsToUri = Uri.parse("smsto:15995490890");
        Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
        intent.putExtra("sms_body","谢谢支持!~~"+'\n');
        context.startActivity(intent);
    }


}
