package task.marami.Shubhaprada.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import task.marami.Shubhaprada.Interfaces.IAgentProfile;
import task.marami.Shubhaprada.Presenters.AgentProfilePresenter;
import task.marami.Shubhaprada.R;
import task.marami.Shubhaprada.Utils.ConnectionDirectory;
import task.marami.Shubhaprada.Utils.Contents;
import task.marami.Shubhaprada.Utils.SingletonAlertDialog;
import task.marami.Shubhaprada.Utils.StoragePrefer;
import task.marami.Shubhaprada.VenturesList;

public class Profile extends Fragment implements IAgentProfile.view {
    TextView tvProfileName, tvJoiningDate, tvProfileEmail, tvProfileMobile, tvPancard, tvAadharCard, tvAddress, tvAgentCode,tvuser,tvTextemail,tvColemail;
    private de.hdodenhof.circleimageview.CircleImageView imgProfilePhoto;
    AgentProfilePresenter profilePresenter;
    ProgressBar progressBar;
    StoragePrefer sp;
    String name, LoginCadre, passbook, clickedItem;
    CardView agentRecruitment, agentBookings, agentDownline, ccresetpassword;

    public static Profile newInstance() {
        Profile profile = new Profile();
        return profile;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        tvProfileName = view.findViewById(R.id.profile_name);
        imgProfilePhoto = view.findViewById(R.id.profile_image);
        tvProfileEmail = view.findViewById(R.id.profile_email);
        tvProfileMobile = view.findViewById(R.id.profile_mobile);
        tvJoiningDate = view.findViewById(R.id.txt_joining_date);
        tvPancard = view.findViewById(R.id.profile_pancard);
        tvAadharCard = view.findViewById(R.id.profile_aadhar);
        tvAddress = view.findViewById(R.id.profile_address);
        progressBar = view.findViewById(R.id.prog_profile);
        tvAgentCode = view.findViewById(R.id.txt_agentcode);
        agentRecruitment = view.findViewById(R.id.ccagentRecruitment);
        agentDownline = view.findViewById(R.id.ccAgentDownline);
        agentBookings = view.findViewById(R.id.ccAgentBookings);
        tvTextemail = view.findViewById(R.id.tvTextemail);
        tvColemail = view.findViewById(R.id.tvColemail);
        tvuser = view.findViewById(R.id.tvuser);
        /*ccresetpassword = view.findViewById(R.id.ccresetPassword);
        ccresetpassword.setVisibility(View.VISIBLE);*/
        sp = new StoragePrefer(getContext());

        profilePresenter = new AgentProfilePresenter(getContext(), this);
        if (ConnectionDirectory.isConnected(getContext())) {
            profilePresenter.onAgentProfileLoad();
        } else {
            Snackbar.make(container, Contents.No_Internet, Snackbar.LENGTH_LONG)
                    .setAction("Settings", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                        }
                    }).show();
        }
       if (sp.getSprString(Contents.PTEF_KEY_USER_TYPE).equals("user")) {
           // agentRecruitment.setVisibility(View.VISIBLE);
            agentDownline.setVisibility(View.VISIBLE);
            agentBookings.setVisibility(View.VISIBLE);
           /* agentRecruitment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent in = new Intent(getContext(), AgentCreation.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("loginCadre", LoginCadre);
                    sp.sprStoreString("loginCadre", LoginCadre);
                    in.putExtras(bundle);
                    startActivity(in);

                }
            });*/
            agentBookings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickedItem = "bookings";
                    Intent in = new Intent(getContext(), VenturesList.class);
                    in.putExtra("item", clickedItem);
                    startActivity(in);

                }
            });
            agentDownline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickedItem = "tree";
                    Intent in = new Intent(getContext(), VenturesList.class);
                    in.putExtra("item", clickedItem);
                    startActivity(in);
                }
            });

        } else {
            //agentRecruitment.setVisibility(View.GONE);
            agentDownline.setVisibility(View.VISIBLE);
            agentBookings.setVisibility(View.VISIBLE);
            agentBookings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickedItem = "bookings";
                    Intent in = new Intent(getContext(), VenturesList.class);
                    in.putExtra("item", clickedItem);
                    startActivity(in);
                }
            });
            agentDownline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickedItem = "tree";
                    Intent in = new Intent(getContext(), VenturesList.class);
                    in.putExtra("item", clickedItem);
                    startActivity(in);
                }
            });
        }
       /* ccresetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ResetPassword.class));
            }
        });*/
        return view;
    }

    @Override
    public void onStartProg() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStopProg() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void OnError(String msg) {
        SingletonAlertDialog.alertDialogShow(getContext(),msg);
    }

    @Override
    public void onSuccess(JSONObject response) {
        try {
            if (!response.getJSONObject("result").equals("")) {
                JSONObject result = response.getJSONObject("result");
                Log.d("response:::", String.valueOf(result));
                if (sp.getSprString(Contents.PTEF_KEY_USER_TYPE).equals("user")) {
                    if (result.getString("image").equals("null")) {
                        imgProfilePhoto.setImageResource(R.drawable.profile);
                    } else {
                        String binarydata = result.getString("image");
                        if (binarydata != null) {
                            byte[] decodedString = Base64.decode(binarydata, Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            imgProfilePhoto.setImageBitmap(decodedByte);
                        }
                    }
                    tvuser.setText("AgentCode");
                } else {
                    imgProfilePhoto.setImageResource(R.drawable.profile);

                    tvuser.setText("EmpCode");
                }
                passbook = result.getString("id");
                if (!result.getString("id").equals("null")) {
                    tvAgentCode.setText(result.getString("id"));
                } else {
                    tvAgentCode.setText("");
                }
                name = result.getString("name");
                LoginCadre = result.getString("cadre");
                if(!LoginCadre.equals("null")) {
                    tvProfileName.setText(result.getString("name") + "(" + result.getString("cadre") + ")");
                }else{
                    tvProfileName.setText(result.getString("name"));
                }
                tvProfileMobile.setText(result.getString("mobile"));
                if (!result.getString("email").equals("null")) {
                    tvProfileEmail.setText(result.getString("email"));
                    tvColemail.setVisibility(View.VISIBLE);
                    tvTextemail.setVisibility(View.VISIBLE);
                    tvProfileEmail.setVisibility(View.VISIBLE);
                } else {
                    tvProfileEmail.setText("");
                    tvProfileEmail.setVisibility(View.GONE);
                    tvColemail.setVisibility(View.GONE);
                    tvTextemail.setVisibility(View.GONE);
                }
                if (!result.getString("joiningdate").equals("null")) {
                    tvJoiningDate.setText(result.getString("joiningdate"));
                } else {
                    tvJoiningDate.setText("");
                }

                if (!result.getString("pancard").equals("null")) {
                    tvPancard.setText(result.getString("pancard"));
                } else {
                    tvPancard.setText("");
                }
                if (!result.getString("aadhar").equals("null")) {
                    tvAadharCard.setText(result.getString("aadhar"));
                } else {
                    tvAadharCard.setText("");
                }
                if (!result.getString("address").equals("null")) {
                    tvAddress.setText(result.getString("address"));
                } else {
                    tvAddress.setText("");
                }
            } else {
                OnError("No Agent Profile Data");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}