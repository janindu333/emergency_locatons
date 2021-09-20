package info.androidhive.firebase.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;


import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import butterknife.Unbinder;
import info.androidhive.firebase.MainActivity;
import info.androidhive.firebase.R;
import info.androidhive.firebase.util.AppSchedular;
import info.androidhive.firebase.util.IScheduler;

public abstract class BaseFragment extends Fragment {

    protected IScheduler scheduler;
    protected Toolbar mToolBar;
    protected Unbinder unbinder;
    protected boolean isViewVisible;
    protected MainActivity mainActivity;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         scheduler = new AppSchedular();
        mToolBar = null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mainActivity = (MainActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mainActivity = null;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActionBarToolbar(view);
        try {
            setUpUI();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setUpToolBar();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) unbinder.unbind();
    }

    protected abstract void setUpUI() throws CertificateException, NoSuchAlgorithmException,
            KeyStoreException, KeyManagementException, IOException;

    protected abstract void updateUI();

    protected abstract void setUpToolBar();

    protected Toolbar getActionBarToolbar(View v) {
        mToolBar = (Toolbar) v.findViewById(R.id.toolbar);
        if (mToolBar != null) {
            ((MainActivity) getActivity()).setSupportActionBar(mToolBar);
            mToolBar.setContentInsetsAbsolute(0, 0);
        }
        return mToolBar;
    }

    protected void showLongToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    public abstract void enableView();

    public abstract void disableView();

    public void showProgressBar() {
        if (getActivity() != null && !getActivity().isFinishing()) {
            ((MainActivity)getActivity()).showProgressBar();
        }
    }

    public void hideProgressBar() {
        if (getActivity() != null && !getActivity().isFinishing()) {
            ((MainActivity)getActivity()).hideProgressBar();
        }
    }
}
