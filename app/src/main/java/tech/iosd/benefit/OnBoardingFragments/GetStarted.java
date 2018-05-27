package tech.iosd.benefit.OnBoardingFragments;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import tech.iosd.benefit.DashboardActivity;
import tech.iosd.benefit.Model.Response;
import tech.iosd.benefit.Model.UserForLogin;
import tech.iosd.benefit.Network.NetworkUtil;
import tech.iosd.benefit.R;
import tech.iosd.benefit.Utils.Constants;

public class GetStarted extends Fragment implements View.OnClickListener
{
    Context ctx;
    FragmentManager fm;
    private SharedPreferences mSharedPreferences;
    private CompositeSubscription mSubscriptions;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.onboarding_get_started, container, false);
        ctx = rootView.getContext();
        fm = getFragmentManager();

        ImageView motto_guy = rootView.findViewById(R.id.get_started_motto_logo);
        TextView motto = rootView.findViewById(R.id.get_started_motto);
        motto_guy.startAnimation(AnimationUtils.loadAnimation(ctx, R.anim.left_show));
        Animation motto_fade = AnimationUtils.loadAnimation(ctx, R.anim.fade_in);
        motto_fade.setStartOffset(1000);
        motto.startAnimation(motto_fade);

        final Button startBtn = rootView.findViewById(R.id.get_started_startBtn);
        Animation start_fade = AnimationUtils.loadAnimation(ctx, R.anim.fade_in);
        start_fade.setStartOffset(1200);
        startBtn.startAnimation(start_fade);
        startBtn.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.get_started_startBtn:
            {
                mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                if(mSharedPreferences.getString(Constants.TOKEN,"").compareTo("")==0 || mSharedPreferences.getString(Constants.EMAIL,"").compareTo("")==0)
                {
                    fm.beginTransaction().replace(R.id.onboarding_content, new ChooseAGoal())
                            .addToBackStack(null)
                            .commit();
                }
                else {
                    if(getActivity() != null)
                    {
                        Intent myIntent = new Intent(getActivity(), DashboardActivity.class);
                        startActivity(myIntent);
                        getActivity().finish();
                    }
                   // loginProcess();
                }

                break;
            }
        }
    }

    private void loginProcess(UserForLogin userForLogin) {

        mSubscriptions.add(NetworkUtil.getRetrofit().login(userForLogin)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse,this::handleError));
    }

    private void handleResponse(Response response) {


        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(Constants.TOKEN,response.token.token);
        editor.putString(Constants.EMAIL,response.getMessage());
        editor.apply();



        Activity activity = getActivity();
        if(activity != null)
        {
            Intent myIntent = new Intent(activity, DashboardActivity.class);
            startActivity(myIntent);
            getActivity().finish();
        }

    }

    private void handleError(Throwable error) {


        if (error instanceof HttpException) {

            Gson gson = new GsonBuilder().create();

            try {

                String errorBody = ((HttpException) error).response().errorBody().string();
                Response response = gson.fromJson(errorBody,Response.class);
                showSnackBarMessage(response.getMessage());

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            showSnackBarMessage("Network Error !");
        }
    }

    private void showSnackBarMessage(String message) {

        if (getView() != null) {

            Snackbar.make(getView(),message, Snackbar.LENGTH_SHORT).show();
        }
    }
}
