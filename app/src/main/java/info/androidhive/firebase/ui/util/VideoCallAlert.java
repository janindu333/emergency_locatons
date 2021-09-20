package info.androidhive.firebase.ui.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import info.androidhive.firebase.MainActivity;
import info.androidhive.firebase.R;
import info.androidhive.firebase.common.Constants;

public class VideoCallAlert {
    private static AlertDialog mAlertDialog;

    public static void hideProgressBar(Activity activity) {
        if (mAlertDialog != null && activity!= null && !activity.isFinishing()&& mAlertDialog.isShowing()) {
            mAlertDialog.dismiss();
            mAlertDialog = null;
        }
    }

    public static void showProgressBar(Activity activity) {
        hideProgressBar(activity);
        if (activity != null && !activity.isFinishing()) {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
            LayoutInflater inflater = activity.getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.layout_progress_bar, null);
            dialogBuilder.setView(dialogView);
            AlertDialog alertDialog = dialogBuilder.create();
            alertDialog.setCancelable(false);
            alertDialog.show();
            mAlertDialog = alertDialog;
        }
    }

    public static void tryAgainDialog(Activity activity, String title, String message, String
            positiveBtnTxt, String negativeBtnTxt, final DialogInterface.OnClickListener
            positiveListener, final DialogInterface.OnClickListener negativeListener) {

        if (activity != null && !activity.isFinishing()) {
            hideProgressBar(activity);
            if (activity.getClass() == MainActivity.class) {
                ((MainActivity) activity).hideProgressBar();
            }

            final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
            LayoutInflater inflater = activity.getLayoutInflater();
            final CardView dialogView =
                    (CardView) inflater.inflate(R.layout.layout_alert_try, null);
            dialogBuilder.setView(dialogView);
            TextView txtTitle = dialogView.findViewById(R.id.txt_title);
            if (title == null) {
                txtTitle.setVisibility(View.GONE);
            } else {
                txtTitle.setVisibility(View.VISIBLE);
                txtTitle.setText(title);
            }
            CardView cv = (CardView)dialogView.findViewById(R.id.cardv);
            cv.setBackgroundResource(R.drawable.bg_alert);
            TextView txtMessage = dialogView.findViewById(R.id.txt_message);
            txtMessage.setText(message);
            if (!activity.isFinishing()) {
                final AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.setCancelable(false);
                alertDialog.show();

                Button btnSubmit = dialogView.findViewById(R.id.btn_submit);
                btnSubmit.setText(positiveBtnTxt);
                btnSubmit
                        .setOnClickListener(view -> positiveListener.onClick(alertDialog, 0));

                Button btnCancel = dialogView.findViewById(R.id.btn_cancel);
                btnCancel.setText(negativeBtnTxt);
                btnCancel
                        .setOnClickListener(view -> negativeListener.onClick(alertDialog, 1));

                mAlertDialog = alertDialog;
            }
        }
    }

    public static void show(Activity activity, String title, String message) {

        if (activity != null && !activity.isFinishing()) {
            hideProgressBar(activity);
            if (activity.getClass() == MainActivity.class) {
                ((MainActivity) activity).hideProgressBar();
            }

            String titleTxt = (title != null && !title.isEmpty()) ? title : Constants.ERROR;
            String messageTxt = (message != null && !message.isEmpty()) ? message :
                    activity.getString(R.string.some_wrong);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
            alertDialogBuilder.setTitle(titleTxt);
            alertDialogBuilder.setMessage(messageTxt);
            alertDialogBuilder.setPositiveButton(R.string.ok, (dialog, id) -> dialog.dismiss());
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.setCancelable(false);
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
            mAlertDialog = alertDialog;
        }
    }

    public static void show(Activity activity, String title, String message, String positiveBtnTxt,
                            String negativeBtnTxt, DialogInterface.OnClickListener positiveListener,
                            DialogInterface.OnClickListener negativeListener) {

        if (activity != null && !activity.isFinishing()) {
            hideProgressBar(activity);
            if (activity.getClass() == MainActivity.class) {
                ((MainActivity) activity).hideProgressBar();
            }

            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(activity);
            String titleTxt = (title != null && !title.isEmpty()) ? title : Constants.ERROR;
            String messageTxt = (message != null && !message.isEmpty()) ? message :
                    activity.getString(R.string.some_wrong);
            alertBuilder.setTitle(titleTxt);
            alertBuilder.setMessage(messageTxt);
            if (positiveBtnTxt != null && positiveListener != null) {
                alertBuilder.setPositiveButton(positiveBtnTxt, positiveListener);
            }

            if (negativeBtnTxt != null && negativeListener != null) {
                alertBuilder.setNegativeButton(negativeBtnTxt, negativeListener);
            }

            AlertDialog alertDialog = alertBuilder.create();
            alertDialog.setCancelable(false);
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
            mAlertDialog = alertDialog;
        }
    }



}
