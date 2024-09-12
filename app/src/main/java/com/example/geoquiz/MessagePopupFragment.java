package com.example.geoquiz;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MessagePopupFragment extends DialogFragment {

    private static final String ARG_MESSAGE = "message";
    private static final String ARG_IS_CORRECT = "is_correct";

    public interface OnPopupDismissListener {
        void onPopupDismissed(boolean isCorrect);
    }

    public static MessagePopupFragment newInstance(String message, boolean isCorrect) {
        MessagePopupFragment fragment = new MessagePopupFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MESSAGE, message);
        args.putBoolean(ARG_IS_CORRECT, isCorrect);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.message_popup, null);

        TextView messageTextView = dialogView.findViewById(R.id.popup_message);
        Button okButton = dialogView.findViewById(R.id.popup_ok_button);

        assert getArguments() != null;
        String message = getArguments().getString(ARG_MESSAGE);
        boolean isCorrect = getArguments().getBoolean(ARG_IS_CORRECT);

        messageTextView.setText(message);
        messageTextView.setTextColor(getResources().getColor(android.R.color.black));
        if (isCorrect) {
            dialogView.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
        } else {
            dialogView.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
        }

        okButton.setOnClickListener(v -> {
            dismiss();
            ((OnPopupDismissListener) getActivity()).onPopupDismissed(isCorrect);
        });

        builder.setView(dialogView);

        return builder.create();
    }

    @Override
    public int getTheme() {
        return R.style.AppDialog;
    }
}